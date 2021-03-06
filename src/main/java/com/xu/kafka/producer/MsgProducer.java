package com.xu.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

/**
 * @author CharleyXu Created on 2018/8/28.
 * <p>
 * 生产者
 */
@Component
public class MsgProducer {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMsg(String topic, String msg) {
        kafkaTemplate.send(topic, msg);
    }

    public void sendMsg(String topic, String key, String msg) {
        kafkaTemplate.send(topic, key, msg);
        //消息发送的监听器，用于回调返回信息
        kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
            @Override
            public void onSuccess(ProducerRecord<String, String> producerRecord,
                                  RecordMetadata recordMetadata) {
                LOGGER.info("topic:{},key:{},value:{}", topic, key, producerRecord.toString());
            }

            @Override
            public void onError(ProducerRecord<String, String> producerRecord,
                                Exception exception) {
                LOGGER.error("[MsgProducer] record {}", producerRecord.toString());
            }
        });
    }


}
