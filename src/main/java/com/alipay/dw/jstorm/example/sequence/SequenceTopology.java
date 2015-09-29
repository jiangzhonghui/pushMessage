package com.alipay.dw.jstorm.example.sequence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.server.SMTPServer;

public class SequenceTopology {
	private static Logger LOG = LoggerFactory.getLogger(SequenceTopology.class);

	public final static String TOPOLOGY_SPOUT_PARALLELISM_HINT = "spout.parallel";
	public final static String TOPOLOGY_BOLT_PARALLELISM_HINT = "bolt.parallel";

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.err.println("Please input configuration file");
			System.exit(-1);
		}

		LOG.debug("test");
		LOG.info("Submit log");
		
		MyMessageHandlerFactory myFactory = new MyMessageHandlerFactory() ;
		if(args[0].equals("mobile")){
			LOG.info("setPushMobile true");
			myFactory.setPushMobile(true);
		}else{
			LOG.info("setPushMobile false");
			myFactory.setPushMobile(false);
		}
        SMTPServer smtpServer = new SMTPServer(myFactory);
        smtpServer.setPort(4467);
        smtpServer.start();
	}

}
