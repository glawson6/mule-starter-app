package org.taptech.app.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;
import java.util.Map;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: tap
 * Date: 10/24/12
 * Time: 10:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class QueueClientSender {


    private static final Logger logger = LoggerFactory.getLogger(QueueClientSender.class);

    private JmsTemplate jmsTemplate;
    private Destination destination;

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }



    public QueueClientSender(JmsTemplate jmsTemplate, Destination destination) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }


    public QueueClientSender() {
    }


    public void sendTextMessage(final String textMessageStr) {
        logger.info("We are in sendTextMessage with  {}", textMessageStr);
        try {
            if (null == jmsTemplate){
                logger.error("JmsTemplate is null. Messages can not be sent!!!!!!!");
            }
            if (null == destination){
                logger.error("Destination is null. Messages can not be sent!!!!!!!");
            }
            jmsTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    TextMessage textMessage = session.createTextMessage(textMessageStr);
                    return textMessage;  //To change body of implemented methods use File | Settings | File Templates.
                }
            });
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendBytesMessage(final byte[] bytes, final Map<String, String> fileProperties,final  String responseFileName) {
        logger.info("We are in sendBytesMessage");
        try {
            if (null == jmsTemplate){
                logger.error("JmsTemplate is null. Messages can not be sent!!!!!!!");
            }
            if (null == destination){
                logger.error("Destination is null. Messages can not be sent!!!!!!!");
            }
            jmsTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    BytesMessage bytesMessage = session.createBytesMessage();
                    bytesMessage.writeBytes(bytes);
                    bytesMessage.setStringProperty("responseFileName",responseFileName);
                    for (String key:fileProperties.keySet()){
                        String operation = fileProperties.get(key);
                        bytesMessage.setStringProperty(operation,key);
                    }
                    return bytesMessage;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String createUUID() {
        String token = null;
        UUID uuid = UUID.randomUUID();
        token = uuid.toString();
        logger.debug("UUID Created => " + token);
        return token;  //To change body of created methods use File | Settings | File Templates.
    }

    /**
     * Run this example when you have Mule up and running wuth embedded ActiveMQ
     * mvn exec:java
     * @param args
     */
    public static void main(String [] args){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("activemq-client.xml");
        QueueClientSender clientSender = (QueueClientSender)applicationContext.getBean("queueClientSender");
        String testMessage = "We are sending to an embedded Mule instance!!!!!";
        logger.info("Sending text message {}",testMessage);
        clientSender.sendTextMessage(testMessage);

    }

}
