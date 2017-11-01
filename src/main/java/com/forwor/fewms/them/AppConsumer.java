package com.forwor.fewms.them;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by tx on 2017/10/24.
 */
public class AppConsumer {
    private static final String url = "failover:(tcp://127.0.0.1:61616,tcp://127.0.0.1:61617,tcp://127.0.0.1:61618)?randomize=true";
    private static final String themName = "test1-topic";

    public static void main(String[] args) throws JMSException {
        //1.创建工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        //2.创建链接
         Connection connection = connectionFactory.createConnection();
         //打开链接
        connection.start();
        //3.创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目标
        Destination destination = session.createTopic(themName);
        //5.创建消费者
        MessageConsumer consumer = session.createConsumer(destination);
        //创建监听器
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("接受消息："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
