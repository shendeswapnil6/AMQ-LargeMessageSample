package JMS.JMStest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSSendLargeMessage  {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
			Connection conn = null;

	        try {
     	
				ActiveMQConnectionFactory connFact = new ActiveMQConnectionFactory("tcp://localhost:61616");
				MessageConsumer consumer =  null;
				conn = connFact.createConnection("abc", "abc123");
				conn.start();
		     	Session session = conn.createSession(true, Session.SESSION_TRANSACTED);
		     	Queue dest = (Queue) session.createQueue("JMS_TEST_QUEUE");
		     	
		     	//Code to test large message.. 
		     	//Also have set acceptor url as below :
		     	// <acceptor name="artemis">tcp://0.0.0.0:61616?tcpSendBufferSize=1048576;tcpReceiveBufferSize=1048576;protocols=CORE,AMQP,STOMP,HORNETQ,MQTT,OPENWIRE;useEpoll=true;amqpCredits=1000;
		     	//amqpLowCredits=300;minLargeMessageSize=10</acceptor>
		     	String fileName = "/home/swshende/Documents/LargeMessage.txt";
		     	File file = new File(fileName);
		     	FileInputStream fis = new FileInputStream(file);
		     	InputStreamReader isr = new InputStreamReader(fis);
		     	BufferedReader br = new BufferedReader(isr);
		     	StringBuffer strbuf = new StringBuffer("helo");
		     	String msg = "hello";
		     	String line;
		     	while((line = br.readLine()) != null){
		     	     //process the line
		     	    // System.out.println(line);
		     		strbuf.append(line);
		     	}
		     	br.close();
		     	
		     	
		     	MessageProducer producer = session.createProducer(dest);
		     	msg= strbuf.toString();
		     	for(int i=0; i < 1;i++){
		     	TextMessage msg1 = session.createTextMessage(msg+i);
//		     	msg1.setJMSExpiration(1000);
//		     	msg1.setJMSExpiration(1000);
//		     	producer.setTimeToLive(10000);
		     	producer.send(msg1);
		     	System.out.println("\n message sent .. ");
		     	}
		     	
		     	session.commit();
			      } 
			    catch (JMSException ex) {
			      // handle exception (details omitted)
			    	System.out.println("\n **** Error encountered ");
			    	ex.printStackTrace();
			    	
			    	
			   }
			   finally {
			         conn.close();
			      }
				}
			}

