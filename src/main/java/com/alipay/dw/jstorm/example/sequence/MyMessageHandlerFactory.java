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
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;


public class MyMessageHandlerFactory implements MessageHandlerFactory {
	
	private boolean pushMobile = false;
	private String mobile;
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

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
        		mail = mail.substring(start_index + 4).trim();
        	}
        	
            LOG.info(mail.trim());
            if(pushMobile){
            	PushExample.testSendPush(mail.trim());
            	PushExampleIOS.testSendPush(mail.trim());
            	String alert= mail.trim();
            	if(alert.length()>=61){
            		alert = alert.substring(0,60);
    			}
            	try{
            		YunPian.sendTSSmsMessage(mobile, alert);
            		System.out.println("短信发送完成");
            	}catch(Exception ex){
            		System.out.println(ex.getMessage());
            		System.out.println("短信发送错误");
            	}
            	
//            	String url  = "http://42.96.142.170:8099/rest/tspushserver/sms_message?message="+mail.trim()+"&mobile="+mobile;
//            	String response = HttpRequest.get(url).body();
//            	System.out.println(url);
//            	System.out.println(response);
//            	LOG.info(response);
//            	Gson gson = new Gson();
//            	APIResponse dataResponse = gson.fromJson(response,
//    					new TypeToken<APIResponse>() {
//    					}.getType());
//            	if(dataResponse.getMeta().getCode()==0){
//            		System.out.println("短信发送完成");
//            	}else{
//            		System.out.println("短信发送错误");
//            	}
            }
            System.out.println(mail.trim());
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