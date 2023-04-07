package com.tlens.kafkaproducer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Message {
    private String newsId;
    private String title;
    private String content;
}
