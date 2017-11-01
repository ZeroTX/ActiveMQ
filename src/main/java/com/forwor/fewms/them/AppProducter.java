package com.forwor.fewms.them;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by tx on 2017/10/24.
 */
public class AppProducter {
    private static final String url = "failover:(tcp://127.0.0.1:61617,tcp://127.0.0.1:61618)?randomize=true";
    private static final String themName = "test1-topic";

    public static void main(String[] args) throws JMSException {
        //1.创建工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        //2.Connection
        Connection connection = connectionFactory.createConnection();
        //.
        connection.start();
        //session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //destion
        Destination destination = session.createTopic(themName);
        //productor
        MessageProducer producer = session.createProducer(destination);
        for (int i =0;i<100;i++){
            TextMessage textMessage = session.createTextMessage("txt"+i);

            producer.send(textMessage);
            System.out.println("发送消息："+textMessage.getText());

        }
        connection.close();
    }
}
