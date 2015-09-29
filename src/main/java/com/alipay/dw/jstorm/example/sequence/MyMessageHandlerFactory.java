package com.alipay.dw.jstorm.example.sequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.RejectException;


public class MyMessageHandlerFactory implements MessageHandlerFactory {
	
	private boolean pushMobile = false;
	
	

	public boolean isPushMobile() {
		return pushMobile;
	}

	public void setPushMobile(boolean pushMobile) {
		this.pushMobile = pushMobile;
	}

	private static Logger LOG = LoggerFactory.getLogger(MyMessageHandlerFactory.class);
	
    public MessageHandler create(MessageContext ctx) {
        return new Handler(ctx);
    }

    class Handler implements MessageHandler {
        MessageContext ctx;

        public Handler(MessageContext ctx) {
            this.ctx = ctx;
        }

        public void from(String from) throws RejectException {
            System.out.println("FROM:"+from);
        }

        public void recipient(String recipient) throws RejectException {
            System.out.println("RECIPIENT:"+recipient);
        }

        public void data(InputStream data) throws IOException {
            System.out.println("MAIL DATA");
            System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
            String mail = this.convertStreamToString(data);
            int start_index = mail.indexOf("7bit");
        	int end_index = mail.indexOf("Workspace");
        	if(start_index>0){
        		mail = mail.substring(start_index + 4);
        	}
        	
            LOG.info(mail);
            if(pushMobile){
            	PushExample.testSendPush(mail);
            }
            System.out.println(mail);
            System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
        }

        public void done() {
            System.out.println("Finished");
        }

        public String convertStreamToString(InputStream is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

    }
}