package com.alipay.dw.jstorm.example.sequence;

import cn.jpush.api.device.OnlineStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.device.TagAliasResult;

import java.util.Map;

public class DevcieExample {
	protected static final Logger LOG = LoggerFactory.getLogger(DevcieExample.class);

	private static final String appKey = "dd1066407b044738b6479275";
	private static final String masterSecret = "6b135be0037a5c1e693c3dfa";
	private static final String TAG1 = "tag1";
	private static final String ALIAS1 = "alias1";
	private static final String ALIAS2 = "alias2";
	private static final String REGISTRATION_ID1 = "0900e8d85ef";
	private static final String REGISTRATION_ID2 = "0a04ad7d8b4";

	private static JPushClient jpushClient = new JPushClient(masterSecret, appKey);

	public static void main(String[] args) {
//		testGetDeviceTagAlias();
		testGetUserOnlineStatus();
	}
	
	public static void testGetDeviceTagAlias() {
		try {
			TagAliasResult result = jpushClient.getDeviceTagAlias(REGISTRATION_ID1);
			
			LOG.info(result.alias);
			LOG.info(result.tags.toString());
			
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
			
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
		}
	}

	public static void testGetUserOnlineStatus() {
		try {
			Map<String, OnlineStatus> result =  jpushClient.getUserOnlineStatus(REGISTRATION_ID1, REGISTRATION_ID2);

			LOG.info(result.get(REGISTRATION_ID1).toString());
			LOG.info(result.get(REGISTRATION_ID2).toString());
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
		}
	}
	
}


