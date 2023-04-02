package com.tlens;

import static org.apache.spark.sql.functions.col;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.Arrays;

public class SparkKafkaStreaming {

    private static final Logger log = LoggerFactory.getLogger(SparkKafkaStreaming.class);

    private static final String KAFKA_TOPIC_NAME = "tlens";

    public static void main(String[] args) throws Exception {
        SparkSession spark = SparkSession.builder()
                .appName(KAFKA_TOPIC_NAME)
                .getOrCreate();

        spark.sparkContext().setLogLevel(Level.WARN.toString());

        Dataset<Row> df = spark.readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "j8c206.p.ssafy.io:9092")
                .option("subscribe", KAFKA_TOPIC_NAME)
                .option("startingOffsets", "earliest")
                .option("maxOffsetsPerTrigger", 10)
                .option("stopGracefullyOnShutdown", true)
                .option("kafka.group.id", "consumer")
                .load();

        df = df.select(
                col("key").cast("string"),
                col("value").cast("string")
        );

        df.writeStream()
                .outputMode(OutputMode.Append())
                .format("console")
                .start()
                .awaitTermination();

        df.
    }

}
