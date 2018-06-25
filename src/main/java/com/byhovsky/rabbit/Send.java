package com.byhovsky.rabbit;// import the rabbitmq libraries that are needed to connect to RabbitMQ

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

// import the rabbitmq lbrary that is needed to create the RabbitMQ channel

/**
 * Simple Java class that creates a connection to a RabbitMQ server and
 * places a message on the queue, so MailerQ can take this message from
 * the queue and send it out as an email.
 */
public class Send {

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

    // same as the rabbitmq-outbox option in the config file
    private static String outbox = "outbox_test";

    /**
     * Settings for sending out the test email
     */

    // domain where the test message should be delivered
    private static String recipientDomain = "example.com";

    // email where the test message should be delivered
    private static String recipientEmail = "denisbyh7@gmail.com";

    // address where the email was sent from
    private static String fromAddress = "denisbyh7@gmail.com";

    /**
     * Main method
     */
    public static void main(String[] argv) throws Exception {
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
        channel.queueDeclare(outbox, true, false, false, null);

        // create the message
        String message = "message";

        // publish the message on the queue
        channel.basicPublish("exchange1", "key1", null, message.getBytes());

        // some output
        System.out.println("Sent: '" + message + "'");

        // close the channel
        channel.close();

        // close the connection
        connection.close();
    }
}
