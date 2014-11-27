package com.irengine.connector.service;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wondertek.esmp.esms.empp.EMPPAnswer;
import com.wondertek.esmp.esms.empp.EMPPChangePassResp;
import com.wondertek.esmp.esms.empp.EMPPDeliver;
import com.wondertek.esmp.esms.empp.EMPPDeliverReport;
import com.wondertek.esmp.esms.empp.EMPPObject;
import com.wondertek.esmp.esms.empp.EMPPRecvListener;
import com.wondertek.esmp.esms.empp.EMPPReqNoticeResp;
import com.wondertek.esmp.esms.empp.EMPPSubmitSM;
import com.wondertek.esmp.esms.empp.EMPPSubmitSMResp;
import com.wondertek.esmp.esms.empp.EMPPSyncAddrBookResp;
import com.wondertek.esmp.esms.empp.EMPPTerminate;
import com.wondertek.esmp.esms.empp.EMPPUnAuthorization;
import com.wondertek.esmp.esms.empp.EmppApi;

public class SmsListener implements EMPPRecvListener {

	private static final long RECONNECT_TIME = 10 * 1000;
	private static final Logger logger = LoggerFactory.getLogger(SmsListener.class);

	private EmppApi emppApi = null;

	private int closedCount = 0;

	protected SmsListener() {

	}

	public SmsListener(EmppApi emppApi) {
		this.emppApi = emppApi;
	}

	public void onMessage(EMPPObject message) {
		if (message instanceof EMPPUnAuthorization) {
			EMPPUnAuthorization unAuth = (EMPPUnAuthorization) message;
			logger.debug("客户端无权执行此操作 commandId=" + unAuth.getUnAuthCommandId());
			return;
		}
		if (message instanceof EMPPSubmitSMResp) {
			EMPPSubmitSMResp resp = (EMPPSubmitSMResp) message;
			logger.debug("收到sumbitResp:");
			byte[] msgId = fiterBinaryZero(resp.getMsgId());

			logger.debug("msgId=" + new BigInteger(msgId));
			logger.debug("result=" + resp.getResult());
			return;
		}
		if (message instanceof EMPPDeliver) {
			EMPPDeliver deliver = (EMPPDeliver) message;
			if (deliver.getRegister() == EMPPSubmitSM.EMPP_STATUSREPORT_TRUE) {
				// 收到状态报告
				EMPPDeliverReport report = deliver.getDeliverReport();
				logger.debug("收到状态报告:");
				byte[] msgId = fiterBinaryZero(report.getMsgId());

				logger.debug("msgId=" + new BigInteger(msgId));
				logger.debug("status=" + report.getStat());

			} else {
				// 收到手机回复
				logger.debug("收到" + deliver.getSrcTermId() + "发送的短信");
				logger.debug("短信内容为：" + deliver.getMsgContent());
			}
			return;
		}
		if (message instanceof EMPPSyncAddrBookResp) {
			EMPPSyncAddrBookResp resp = (EMPPSyncAddrBookResp) message;
			if (resp.getResult() != EMPPSyncAddrBookResp.RESULT_OK)
				logger.debug("同步通讯录失败");
			else {
				logger.debug("收到服务器发送的通讯录信息");
				logger.debug("通讯录类型为：" + resp.getAddrBookType());
				logger.debug(resp.getAddrBook());
			}
		}
		if (message instanceof EMPPChangePassResp) {
			EMPPChangePassResp resp = (EMPPChangePassResp) message;
			if (resp.getResult() == EMPPChangePassResp.RESULT_VALIDATE_ERROR)
				logger.debug("更改密码：验证失败");
			if (resp.getResult() == EMPPChangePassResp.RESULT_OK) {
				logger.debug("更改密码成功,新密码为：" + resp.getPassword());
				emppApi.setPassword(resp.getPassword());
			}
			return;

		}
		if (message instanceof EMPPReqNoticeResp) {
			EMPPReqNoticeResp response = (EMPPReqNoticeResp) message;
			if (response.getResult() != EMPPReqNoticeResp.RESULT_OK)
				logger.debug("查询运营商发布信息失败");
			else {
				logger.debug("收到运营商发布的信息");
				logger.debug(response.getNotice());
			}
			return;
		}
		if (message instanceof EMPPAnswer) {
			logger.debug("收到企业疑问解答");
			EMPPAnswer answer = (EMPPAnswer) message;
			logger.debug(answer.getAnswer());

		}
		logger.debug(message.toString());

	}

	public void OnClosed(Object object) {
		// 该连接是被服务器主动断掉，不需要重连
		if (object instanceof EMPPTerminate) {
			logger.debug("收到服务器发送的Terminate消息，连接终止");
			return;
		}
		// 这里注意要将emppApi做为参数传入构造函数
		SmsListener listener = new SmsListener(emppApi);
		logger.debug("连接断掉次数：" + (++closedCount));
		for (int i = 1; !emppApi.isConnected(); i++) {
			try {
				logger.debug("重连次数:" + i);
				Thread.sleep(RECONNECT_TIME);
				emppApi.reConnect(listener);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.debug("重连成功");
	}

	public void OnError(Exception e) {
		e.printStackTrace();
	}

	private static byte[] fiterBinaryZero(byte[] bytes) {
		byte[] returnBytes = new byte[8];
		for (int i = 0; i < 8; i++) {
			returnBytes[i] = bytes[i];
		}
		return returnBytes;
	}
}
