package com.irengine.connector;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.connector.domain.core.Coupon;
import com.irengine.connector.repository.core.CouponDao;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class )
public class CouponTests {
	
	@Autowired
	private CouponDao couponDao;
	
	
	@Transactional
	@Test
	public void testGenerateCoupon() {
		
		Coupon coupon = new Coupon();
		coupon.setCode("YHQ2014991008976");
		coupon.setPassword("123456");
		coupon.setSize(50);
		coupon.setStatus(Coupon.STATUS.Unused);
		
		couponDao.save(coupon);
		
		assertEquals(1, couponDao.count());
	}

	@Transactional
	@Test
	public void testGetCoupon() {
		
		Coupon coupon = new Coupon();
		coupon.setCode("YHQ2014991008976");
		coupon.setPassword("123456");
		coupon.setSize(50);
		coupon.setStatus(Coupon.STATUS.Unused);
		
		couponDao.save(coupon);
		
		assertEquals(1, couponDao.count());
		
		List<Coupon> unused = couponDao.findBySizeAndStatus(50, Coupon.STATUS.Unused);
		assertNotNull(unused);
		
		List<Coupon> used = couponDao.findBySizeAndStatus(300, Coupon.STATUS.Unused);
		assertNull(used);
	}

}
