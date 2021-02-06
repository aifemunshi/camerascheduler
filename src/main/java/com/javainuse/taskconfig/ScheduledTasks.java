package com.javainuse.taskconfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.javainuse.taskconfig.model.Email;
import com.javainuse.taskconfig.repo.EmailRepository;

@Component
@EnableAsync
public class ScheduledTasks {
	
	@Autowired
	EmailRepository mailRepo;
	
	
	@Value("${mail.host}")
	private String mailHost;
	
	@Value("${mail.storetype}")
	private String mailStoreType;
	
	@Value("${mail.username}")
	private String mailUserName;
	
	@Value("${mail.password}")
	private String mailPassword;
	
	@Value("${mail.received.from}")
	private String mailReceivedFrom;
	
	
	private static final String HOSTKEY = "mail.pop3.host";
	private static final String PORTKEY = "mail.pop3.port";
	private static final String PORTVAL = "995";
	private static final String STARTTLSKEY = "mail.pop3.starttls.enable";
	private static final String STARTTLSVAL = "true";
	private static final String POP3STORE = "pop3s";
	private static final String MAILINBOX = "Inbox";
	private static final String MAILSUB = "Attention: alarm";
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"MM/dd/yyyy HH:mm:ss");
	
	
	@Async
	@Scheduled(initialDelay = 3000, fixedRate = 3000)
	public void performDelayedMailReadStore() {

		System.out.println("Mail Reading Started at "
				+ dateFormat.format(new Date()));
        check(mailHost, mailStoreType, mailUserName, mailPassword);

	}
	
	
	@Async
	@Scheduled(initialDelay = 2000, fixedRate = 5000)
	public void performDelayedMessageSend() {

		System.out.println("Fetching DB Record at "
				+ dateFormat.format(new Date()));

	}
	
	private void check(String host, String storeType, String user, String password) {
		  Store store = null;
		  Folder emailFolder = null;
	      try {
		      Properties properties = new Properties();
		      properties.put(HOSTKEY, host);
		      properties.put(PORTKEY, PORTVAL);
		      properties.put(STARTTLSKEY, STARTTLSVAL);
		      Session emailSession = Session.getDefaultInstance(properties);
		      store = emailSession.getStore(POP3STORE);
		      store.connect(host, user, password);
		      emailFolder = store.getFolder(MAILINBOX);
		      emailFolder.open(Folder.READ_ONLY);
	
		      Message[] messages = emailFolder.getMessages();
		      System.out.println("Mail length:::::" + messages.length);
	
		      for (int i = 0, n = messages.length; i < n; i++) {
		         Message message = messages[i];
	
		         Object obj = message.getContent();
		         Multipart mp = (Multipart)obj;
		         BodyPart bp = mp.getBodyPart(0);
	
		         String sub = message.getSubject();
		         String mailFrom = message.getFrom()[0].toString();
		         Date receivedDate = message.getSentDate();
		         String content = bp.getContent().toString();
		         
		         System.out.println("---------------------------------");
		         System.out.println("Email Number " + (i + 1));
		         System.out.println("Subject: " + sub);
		         System.out.println("From: " + mailFrom);
		         System.out.println("Received Date:" + receivedDate);
		         System.out.println("Text: " + content);
		         if(mailReceivedFrom.equalsIgnoreCase(mailFrom) && sub.contains(MAILSUB) ){
		        	 captureMail(mailFrom,message.getSentDate());
		         }
	      }

	      } catch (NoSuchProviderException e) {
	         e.printStackTrace();
	      } catch (MessagingException e) {
	         e.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      finally{
	    	  try {
				emailFolder.close(false);
				store.close();
			} catch (MessagingException e) {
				System.out.println("Exception occured while closing emailfolder an/or session");
			}
		     
	      }
	   }


	private void captureMail(String mailFrom,Date sentDate) {
		System.out.println(":::::::::::captureMail started::::::::");
		Email mail = new Email(mailFrom+sentDate.toString(), mailFrom, "", sentDate.toString());
		try{
			mailRepo.save(mail);
		}
		catch(Exception excp){
			System.out.println("Exception occured in capturing to dynamo db,Exception Message:::"+excp);
		}
		System.out.println(":::::::::::captureMail completed::::::");
	}
} 

	

