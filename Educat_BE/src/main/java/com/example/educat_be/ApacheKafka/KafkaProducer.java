package com.example.educat_be.ApacheKafka;

import com.example.educat_be.Entity.StudentMessage;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class KafkaProducer {


//    private static final org.slf4j.Logger LOGGER=LoggerFactory.getLogger(KafkaProducer.class);
//    private KafkaTemplate<String, StudentMessage> kafkaTemplate;
//
//
//
//    public KafkaProducer(KafkaTemplate<String, StudentMessage> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void sendMessage(StudentMessage studentMessage){
//        LOGGER.info(String.format("Message sent  -> %s",studentMessage.toString()));
//        Message<StudentMessage> message= MessageBuilder.withPayload(studentMessage).setHeader(KafkaHeaders.TOPIC,"StudentMessage").build();
//        kafkaTemplate.send(message);
//
//    }
}
