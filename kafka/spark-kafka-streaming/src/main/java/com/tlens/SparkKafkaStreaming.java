package com.tlens;

import org.apache.spark.sql.*;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.openkoreantext.processor.tokenizer.KoreanTokenizer;
import org.openkoreantext.processor.KoreanPosJava;
import org.openkoreantext.processor.KoreanTokenJava;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import scala.collection.Seq;

import java.util.Properties;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static org.apache.spark.sql.functions.*;


public class SparkKafkaStreaming {

    private static final Logger log = LoggerFactory.getLogger(SparkKafkaStreaming.class);
    private static final String KAFKA_TOPIC_NAME = "tlens";

    public static void main(String[] args) {

        // spark session 생성
        SparkSession spark = SparkSession.builder()
                .appName(KAFKA_TOPIC_NAME)
                .getOrCreate();

        // 로그가 너무 많아서 WARN 상태의 로그만 출력하기
        spark.sparkContext().setLogLevel(Level.WARN.toString());

        // OKT 라이브러리를 사용해서 형태소 분석을 실행하는 UDF 함수 등록
        spark.udf().register("tokenize", (String text) -> {
            CharSequence normalized = OpenKoreanTextProcessorJava.normalize(text);
            Seq<KoreanTokenizer.KoreanToken> tokens = OpenKoreanTextProcessorJava.tokenize(normalized);
            return OpenKoreanTextProcessorJava.tokensToJavaKoreanTokenList(tokens)
                    .stream()
                    .filter(morph -> KoreanPosJava.Noun.equals(morph.getPos()) || KoreanPosJava.Alpha.equals(morph.getPos()))
                    .map(KoreanTokenJava::getText)
                    .filter(morphText -> !morphText.equals("를"))
                    .collect(Collectors.toList());
        }, DataTypes.createArrayType(DataTypes.StringType));

        // kafka의 topic에서 데이터 읽어오기
        Dataset<Row> df = spark.readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "j8c206.p.ssafy.io:9092") // kafka 서버 url
                .option("subscribe", KAFKA_TOPIC_NAME) // kafka topic name
                .option("startingOffsets", "latest") // 코드를 실행할 때 카프카 토픽을 가져오면서 오프셋 시작 위치 결정
                .option("maxOffsetsPerTrigger", 10)  // 한번의 배치에서 가져올 수 있는 최대 데이터 개수
                .option("stopGracefullyOnShutdown", true)
                .option("kafka.group.id", "consumer") // consumer 그룹 id
                .load();

        // spark는 기본적으로 biteArray로 데이터를 읽어오기 때문에 String으로 변환
        df = df.select(
                col("key").cast("string"),
                col("value").cast("string")
        );

        // value 데이터를 읽기 위한 json 데이터 구조
        StructType schema = new StructType()
                .add("title", DataTypes.StringType)
                .add("content", DataTypes.StringType)
                .add("newsId", DataTypes.StringType);

        // json 데이터를 각자 key 값에 따라 새로운 칼럼으로 생성
        Dataset<Row> result = df.select(from_json(col("value"), schema).as("data"))
                .select(
                        col("data.title").as("title"),
                        col("data.newsId").as("news_id"),
                        callUDF("tokenize", col("data.content")).as("words") // UDF 함수를 사용해 문장의 형태소 분석 실시 후 결과를 칼럼으로 생성
                ).withColumn("word", concat_ws(",", col("words"))); // UDF 함수 결과로 생성된 array(string) 데이터를 string으로 변환하여 칼럼 생성

        try {
            result.writeStream()
                    .outputMode(OutputMode.Append()) // 출력 모드
                    .format("jdbc")
                    .foreachBatch(SparkKafkaStreaming::insertMySQL)// Batch로 가져온 데이터를 MySQL에 저장
                    .start() // stream 실행
                    .awaitTermination(); // stream을 계속해서 유지
        } catch (StreamingQueryException | TimeoutException e) {
            log.warn("error:"  + e.getMessage());
        }
    }

    private static void insertMySQL(Dataset<Row> batchDF, Long batchId) {
        // DB 관련 정보
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "ssafy");
        properties.put("driver", "com.mysql.jdbc.Driver");

        // dataframe을 MySQL에 저장
        batchDF.select(col("news_id"), col("title"), col("word")) // 저장하기 원하는 칼럼
                .write()
                .mode(SaveMode.Append) // append는 기존 테이블에 데이터 추가, overwrite는 기존 테이블에 덮어쓰기
                .jdbc("jdbc:mysql://j8c206.p.ssafy.io:3306/tlens", "morpheme", properties); // jdbc 연결
    }
}