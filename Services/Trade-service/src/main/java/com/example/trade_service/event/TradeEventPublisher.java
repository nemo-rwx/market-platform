package com.example.trade_service.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TradeEventPublisher {

    private static final String TOPIC = "trade.executed.v1";

    private final KafkaTemplate<String, TradeExecutedEvent> kafkaTemplate;

    public TradeEventPublisher(
            KafkaTemplate<String, TradeExecutedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishTradeExecuted(TradeExecutedEvent event) {

        kafkaTemplate.send(
                TOPIC,
                event.tradeId().toString(),
                event
        );
    }
}