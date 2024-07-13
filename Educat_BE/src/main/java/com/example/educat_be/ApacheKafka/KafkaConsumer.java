package com.example.educat_be.ApacheKafka;

import com.example.educat_be.DAO.StudentMessageDAO;
import com.example.educat_be.Entity.StudentMessage;
import com.example.educat_be.Service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

//    private static final Logger LOGGER= LoggerFactory.getLogger(KafkaConsumer.class);
//
//    @Autowired
//    StudentService studentService;
//
//    @KafkaListener(topics = "StudentMessage",groupId = "myGroup")
//    public void consume(StudentMessage studentMessage){
//        LOGGER.info(String.format("Message received->%s",studentMessage.toString()));
//        studentService.saveStudentMessage(studentMessage);
//    }
}
