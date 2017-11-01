package com.fowor.fewms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by tx on 2017/10/24.
 */
public class AppProducter {
    private static final String url = "failover:(tcp://127.0.0.1:61617,tcp://127.0.0.1:61618)?randomize=true";
    private static final String queueName = "test2";

    public static void main(String[] args) throws JMSException {
        //1.创建ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        //2.创建Connection链接
        Connection connection = connectionFactory.createConnection();
        //3.启动链接
        connection.start();
        //4.创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //5.创建目标
        Destination destination = session.createQueue(queueName);
        //6.创建生产者
        MessageProducer producer = session.createProducer(destination);

        for (int i = 0; i < 100; i++) {
            //创建消息
            TextMessage textMessage = session.createTextMessage("textqq" + i);
            //发送消息
            producer.send(textMessage);
            System.out.println("发送消息："+textMessage.getText());
        }

        //关闭链接
        connection.close();
    }
}
