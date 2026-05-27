package com.fx.service.kafka;

import com.fx.dto.jpa.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "taco.orders", groupId = "street-food")
    public void kafkaConsumer(Order order, ConsumerRecord<String, Order> record) {
        System.out.println("ORDER CONSUMED FROM KAFKA: " + order);
        System.out.println("RECORD CONSUMED FROM KAFKA: " + record.topic() + " " + record.timestamp()); //detailed message
        System.exit(1);
    }
}
