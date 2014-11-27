package com.irengine.connector.repository.core;

import org.springframework.data.repository.CrudRepository;

import com.irengine.connector.domain.core.Coupon;
import com.irengine.connector.domain.core.Coupon.STATUS;

public interface CouponDao  extends CrudRepository<Coupon, Long> {

	public Coupon findOneByCode(String code);
	public Coupon findOneBySizeAndStatus(long size, STATUS status);
	
}
