import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {
	private static final int COUNT = 15;
	private ConnectionFactory factory = null;
	private Connection connection = null;
	private Session session = null;
	private Destination destination = null;
	private MessageProducer producer = null;

	public Sender() {

	}

	public void sendMessage() {

		try {
				factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
				connection = factory.createConnection();
				connection.start();
				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				destination = session.createQueue(User.MESSAGE_TYPE);
				producer = session.createProducer(destination);
				for(int i = 0; i < COUNT; i++){
					ObjectMessage message = session.createObjectMessage();
					User user = new User();
					user.setUsername("lfallo" + new Date().getTime());
					user.setPassword("password12345");
					message.setObject(user);
					message.setStringProperty("type", "user confirmation message");
					message.setJMSTimestamp(new Date().getTime());
					producer.send(message);
					System.out.println("Sent message " + (i+1) + " of " + COUNT);	
				}

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Sender sender = new Sender();
		sender.sendMessage();
	}
}
