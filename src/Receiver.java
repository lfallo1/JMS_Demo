import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver {
	private ConnectionFactory factory = null;
	private Connection connection = null;
	private Session session = null;
	private Destination destination = null;
	private MessageConsumer consumer = null;

	public Receiver() {

	}

	public void receiveMessage() {
		try {
			factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
			connection = factory.createConnection();
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(User.MESSAGE_TYPE);
			consumer = session.createConsumer(destination);
			Message message = null;
			consumer.receive(1);
			while((message = consumer.receive()) != null){
				if (message instanceof ObjectMessage) {
					ObjectMessage msg = (ObjectMessage) message;
					User user = (User) msg.getObject();
					System.out.println("Type is : " + msg.getStringProperty("type"));
					System.out.println(user.toString());
				}				
			}


		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Receiver receiver = new Receiver();
		receiver.receiveMessage();
	}
}
