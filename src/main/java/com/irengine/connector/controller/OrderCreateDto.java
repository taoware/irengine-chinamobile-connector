package com.irengine.connector.controller;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapping;

import com.irengine.connector.Constants;

public class OrderCreateDto extends DtoBase {

	private String orderid;
	private String userid;
	private Long amount;
	private Long nums;
	private Date create_time;
	private String mobile;
	private String businessid;
	private String goodid;
	private String salessysid;
	
	@Override
	public String getDigestCode() {
		return StringUtils.join(new Object[] {getOrderid(), getSalessysid(), getBusinessid(), getUserid(), getGoodid(), getNums(), getAmount(), Constants.KEY});
//		return getOrderId() + getSalessysid() + getBusinessid() + getUserId() + getGoodid() + getNums() + getAmount() + Constants.KEY;
	}

	@Mapping("vendorOrderId")
	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	@Mapping("userId")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Mapping("totalPrice")
	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	@Mapping("itemCount")
	public Long getNums() {
		return nums;
	}

	public void setNums(Long nums) {
		this.nums = nums;
	}

	@Mapping("orderTime")
	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	@Mapping("userMobile")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Mapping("vendorId")
	public String getBusinessid() {
		return businessid;
	}

	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}

	@Mapping("itemId")
	public String getGoodid() {
		return goodid;
	}

	public void setGoodid(String goodid) {
		this.goodid = goodid;
	}

	@Mapping("applicationId")
	public String getSalessysid() {
		return salessysid;
	}

	public void setSalessysid(String salessysid) {
		this.salessysid = salessysid;
	}
	
}
