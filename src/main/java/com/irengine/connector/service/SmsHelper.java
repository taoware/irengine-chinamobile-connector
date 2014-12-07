package com.irengine.connector.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wondertek.esmp.esms.empp.EMPPConnectResp;
import com.wondertek.esmp.esms.empp.EMPPData;
import com.wondertek.esmp.esms.empp.EMPPObject;
import com.wondertek.esmp.esms.empp.EMPPShortMsg;
import com.wondertek.esmp.esms.empp.EMPPSubmitSM;
import com.wondertek.esmp.esms.empp.EmppApi;

public class SmsHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(SmsHelper.class);

	public static boolean send(String mobile, String content) {

		boolean flag = false;
		String host = com.irengine.connector.Constants.get("host");
		int port = Integer.parseInt(com.irengine.connector.Constants.get("port"));
		String accountId = com.irengine.connector.Constants.get("account");
		String password = com.irengine.connector.Constants.get("password");
		String serviceId = com.irengine.connector.Constants.get("service");

		EmppApi emppApi = new EmppApi();
		SmsListener listener = new SmsListener(emppApi);

		try {
			// 建立同服务器的连接
			EMPPConnectResp response = emppApi.connect(host, port, accountId, password, listener);
			if (response == null) {
				logger.error("连接超时失败");
				return flag;
			}
			if (!emppApi.isConnected()) {
				logger.error("连接失败:响应包状态位=" + response.getStatus());
				return flag;
			}
		} catch (Exception e) {
			logger.error("发生异常，导致连接失败");
			e.printStackTrace();
		}
		
		if (emppApi.isSubmitable()) {
			
			EMPPSubmitSM msg = (EMPPSubmitSM) EMPPObject
					.createEMPP(EMPPData.EMPP_SUBMIT);
			List<String> dstId = new ArrayList<String>();
			dstId.add(mobile);

			msg.setDstTermId(dstId);
			msg.setSrcTermId(accountId);
			msg.setServiceId(serviceId);
			msg.setMsgFmt(EMPPSubmitSM.EMPP_MSGFMT_GB);

			EMPPShortMsg msgContent = new EMPPShortMsg(
					EMPPShortMsg.EMPP_MSG_CONTENT_MAXLEN);

			try {
				msgContent.setMessage(content.getBytes("GBK"));
				msg.setShortMessage(msgContent);
				msg.assignSequenceNumber();
				emppApi.submitMsgAsync(msg);
				
				flag = true;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
		    Thread.sleep(1000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}

		
		return flag;
	}
}
