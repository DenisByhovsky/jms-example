package com.byhovsky.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Result {
    /**
     * Settings for RabbitMQ
     */

    // same as the rabbitmq-host option in the config file
    private static String hostname = "localhost";

    // same as the rabbitmq-user option in the config file
    private static String username = "guest";

    // same as the rabbitmq-password option in the config file
    private static String password = "guest";

    // same as the rabbitmq-vhost option in the config file
    private static String vhost = "/";

    // same as the rabbitmq-results option in the config file
    private static String resultbox = "results_test";

    public static void main(String[] argv)
            throws java.io.IOException, java.lang.InterruptedException {
        // create a new ConnectionFactory      
        ConnectionFactory factory = new ConnectionFactory();

        // set the host to RabbitMQ
        factory.setHost(hostname);

        // set the user name to connect to RabbitMQ
        factory.setUsername(username);

        // set the password to connect to RabbitMQ
        factory.setPassword(password);

        // set the virtual host to RabbitMQ
        factory.setVirtualHost(vhost);

        // create the new connection
        Connection connection = factory.newConnection();

        // create the channel
        Channel channel = connection.createChannel();

        // declare the queue
        channel.queueDeclare(resultbox, true, false, false, null);

        // message telling the user that the script is waiting for messages
        // that will be placed on the result queue by MailerQ
        System.out.println("Waiting for messages to be placed on the result queue. To exit press CTRL+C");

        // create the QueueingConsumer
        QueueingConsumer consumer = new QueueingConsumer(channel);

        // start the consumer
        channel.basicConsume(resultbox, true, consumer);

        // keep waiting for messages
        while (true) {
            // get a delivery from the result queue
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();

            // get the message as a string
            String message = new String(delivery.getBody());
            System.out.println(message);
        }
    }
}