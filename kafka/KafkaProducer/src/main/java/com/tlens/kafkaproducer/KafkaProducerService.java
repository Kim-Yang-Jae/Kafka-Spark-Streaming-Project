package com.tlens.kafkaproducer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducerService {

    private static final String TOPIC_NAME = "tlens";

    private final ObjectMapper objectMapper = new ObjectMapper().
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, String> msgKafkaTemplate;

    public void sendJson(Message message) {
        kafkaTemplate.send(TOPIC_NAME, message);

    }

    public void sendToKafka(Message message){
        try{
            // Message 객체를 Json 형태로 변환한다.
            String jsonString = objectMapper.writeValueAsString(message);

            // <TOPIC, KEY, VALUE> 쌍을 보낸다.
            msgKafkaTemplate.send(TOPIC_NAME, message.getNewsId(), jsonString);

            System.out.println("[SUCCESS] Message가 Kafka에 성공적으로 전송되었습니다.");
        } catch (Exception e){
            System.out.println("[FAIL] Message가 Kafka에 전송되지 않았습니다.");
        }
    }

    public void send(String message) {
        msgKafkaTemplate.send(TOPIC_NAME, message);
    }

    public void sendWithCallback(String message) {
        ListenableFuture<SendResult<String, String>> future = msgKafkaTemplate.send(TOPIC_NAME, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Failed " + message + " due to : " + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent " + message + " offset:" + result.getRecordMetadata().offset());
            }
        });
    }
}
