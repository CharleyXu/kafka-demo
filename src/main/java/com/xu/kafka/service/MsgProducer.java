package com.xu.kafka.service;

import java.text.MessageFormat;
import java.util.StringTokenizer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

/**
 * @author CharleyXu Created on 2018/8/28.
 *
 * 生产者
 */
@Component
public class MsgProducer {

  private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  public void sendMsg(String topic, String msg) {
    kafkaTemplate.send(topic,msg);
  //消息发送的监听器，用于回调返回信息
    kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
      @Override
      public void onSuccess(String topic, Integer partition, String key, String value,
          RecordMetadata recordMetadata) {
        LOGGER.info(MessageFormat.format("topic:{0},key:{1},value:{2}",topic,key,value));
      }

      @Override
      public void onError(String topic, Integer partition, String key, String value,
          Exception exception) {

      }
    });
  }
}
