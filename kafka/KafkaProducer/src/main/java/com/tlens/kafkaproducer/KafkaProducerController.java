package com.tlens.kafkaproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

@RestController
public class KafkaProducerController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @GetMapping("/kafka/news")
    public String publish(String message) {
        kafkaProducerService.send(message);
        return message;
    }

    // GET방식으로는 기사 원문을 다 담을 수 없기 때문에, POST의 Body에 담아서 보낸다.
    @PostMapping("/kafka/data")
    public String data(@RequestBody Message message){
        kafkaProducerService.sendToKafka(message);
        return "[SUCCESS] POST 요청이 완료되었습니다.";
    }
}
