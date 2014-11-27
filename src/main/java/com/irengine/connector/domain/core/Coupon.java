package com.irengine.connector.domain.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "ss_coupon")
public class Coupon extends EntityBase {
	
	private String code;
	private String password;
	private long size;
	public enum STATUS { Unused, Used, Canceled }
	private STATUS status;
	
	@Column(nullable = false, unique=true)
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	@Column(nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(nullable = false)
	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

}
