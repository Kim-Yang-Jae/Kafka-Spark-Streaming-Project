package com.tlens;

import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.openkoreantext.processor.KoreanPosJava;
import org.openkoreantext.processor.KoreanTokenJava;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.openkoreantext.processor.tokenizer.KoreanTokenizer;
import scala.collection.Seq;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.spark.sql.functions.*;

public class SparkUdfTest {

    private static final Logger log = LoggerFactory.getLogger(SparkUdfTest.class);

    private static final String KAFKA_TOPIC_NAME = "tlens";

    public static void main(String[] args) {

        SparkSession spark = SparkSession.builder()
                .appName(KAFKA_TOPIC_NAME)
                .getOrCreate();

        spark.conf().set("spark.sql.sources.partitionColumnTypeInference.enabled", "false");

        spark.sparkContext().setLogLevel(Level.WARN.toString());

        String[][] sentences = new String[][]{
                {"1", "한글 형태소 분석기 Okt를 Spark에서 사용하기"},
                {"2", "Spark와 함께 즐기는 자연어 처리"},
                {"3", "한국어 텍스트 데이터 전처리"}
        };
        log.warn("----------------------------------------------시작----------------------------------------------------------------");
        List<Row> rows = new ArrayList<>();
        for (String[] row : sentences) {
            rows.add(RowFactory.create(Integer.parseInt(row[0]), row[1]));
            log.warn(row[0] + row[1]);
        }
        log.warn("-------------------------------------------------끝-------------------------------------------------------------");
        log.warn("----------------------------------------------시작----------------------------------------------------------------");

        for (Row row : rows) {
            log.warn(row.toString());
        }
        log.warn("----------------------------------------------시작----------------------------------------------------------------");


        StructType schema = DataTypes.createStructType(new StructField[]{
                DataTypes.createStructField("id", DataTypes.IntegerType, true),
                DataTypes.createStructField("text", DataTypes.StringType, true)
        });

        Dataset<Row> df = spark.createDataFrame(rows, schema);
        log.warn("----------------------------------------------시작----------------------------------------------------------------");
        df.select(col("text")).show();
        log.warn("----------------------------------------------끝----------------------------------------------------------------");

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

        df = df.withColumn("tokens", functions.callUDF("tokenize", df.col("text")))
                .withColumn("token", concat_ws(",", col("tokens")));

        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "ssafy");
        properties.put("driver", "com.mysql.jdbc.Driver");

        df.show(false);

        df.select(col("id"), col("text"), col("token"))
                .write()
                .mode(SaveMode.Append)
                .jdbc("jdbc:mysql://j8c206.p.ssafy.io:3306/tlens", "test", properties);
    }
}