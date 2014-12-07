package com.irengine.connector.service;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.connector.Constants;
import com.irengine.connector.controller.ChinaMobileRestController;
import com.irengine.connector.domain.core.Coupon;
import com.irengine.connector.domain.core.Order;
import com.irengine.connector.domain.core.OrderItemCode;
import com.irengine.connector.repository.core.CouponDao;
import com.irengine.connector.repository.core.OrderDao;
import com.irengine.connector.repository.core.OrderItemCodeDao;

@Service
@Transactional
public class OrderService {

	private static final Logger logger = LoggerFactory.getLogger(ChinaMobileRestController.class);
	
	@Autowired
	private OrderDao orderDao;

	@Autowired
	private OrderItemCodeDao itemDao;
	
	@Autowired
	private CouponDao couponDao;

	/*
	 * create new order.
	 */
	public Order createOrder(Order order) {
		
		order.setOrderId(StringUtils.join(new String[] {"CM", order.getVendorOrderId()}, "-"));
		order.setStatus(Order.STATUS.Created);
		
		return orderDao.save(order);
		
	}
	
	/*
	 * get order by vendor order id.
	 */
	public Order getOrder(String vendorOrderId) {
		
		return orderDao.findOneByVendorOrderId(vendorOrderId);
		
	}
	
	/*
	 * get coupon by code
	 */
	public Coupon getCoupon(String code) {
		
		return couponDao.findOneByCode(code);
		
	}
	
	/*
	 * get order by code or mobile
	 */
	public List<Order> getOrderByCodeOrMobile(String code) {
		return orderDao.findByCodeOrMobile(code);
	}
	
	/*
	 * generate order items from coupon
	 */
	public Order generateCode(Order order) throws CodeException {

		if (logger.isDebugEnabled())
			logger.debug("order: {}, item size: {}", order.getVendorOrderId(), order.getItemCodes().size());

		if (Order.STATUS.Created.equals(order.getStatus())) {
			order.setStatus(Order.STATUS.Not_Sent);
			
			Long count = order.getItemCount();
			Long size = order.getTotalPrice()/(order.getItemCount() * 100);
			for(int i = 0; i < count; i++) {
				List<Coupon> coupons = couponDao.findBySizeAndStatus(size, Coupon.STATUS.Unused); 
				if (0 == coupons.size()) throw new CodeException("code unavailable");
				Coupon coupon = coupons.get(0);
				
				coupon.setStatus(Coupon.STATUS.Used);
				couponDao.save(coupon);
				
				OrderItemCode itemCode = new OrderItemCode();
				itemCode.setOrder(order);
				itemCode.setItemCode(coupon.getCode());
				itemCode.setStatus(OrderItemCode.STATUS.Unused);
				order.getItemCodes().add(itemCode);
			}
			
		}
		
		return orderDao.save(order);
		
	}
	
	/*
	 * cancel order
	 */
	public void cancelCode(Order order, String itemIds) throws CodeException {
		
		String[] itemIdArray = StringUtils.split(itemIds, ",");
		for(String itemId : itemIdArray) {
			OrderItemCode item = itemDao.findOneByItemCode(itemId);
			if (OrderItemCode.STATUS.Unused.equals(item.getStatus())) {
				item.setStatus(OrderItemCode.STATUS.Canceled);
				itemDao.save(item);
			}
			else {
				throw new CodeException("code used");
			}
		}
		
		order.setStatus(Order.STATUS.Canceled);
		orderDao.save(order);
	}
	
	/*
	 * send code
	 */
	public Order sendCode(Order order) {

		boolean flag = true;
		
		Set<OrderItemCode> items = order.getItemCodes();
		for(OrderItemCode item : items) {
			Coupon coupon = couponDao.findOneByCode(item.getItemCode());
			boolean f = sendCode(order.getUserMobile(), coupon);
			if(!f) flag = false;
		}
		
		if(flag)
			order.setStatus(Order.STATUS.Sent_Success);
		else
			order.setStatus(Order.STATUS.Sent_Failure);
		
		return orderDao.save(order);
	}
	
	private static boolean sendCode(String mobile, Coupon coupon) {
		
		String message_1 = String.format(Constants.get("message_1_2"), coupon.getSize(), coupon.getCode(), coupon.getPassword());
		String message_2 = Constants.get("message_2_2");
				
		boolean f1 = SmsHelper.send(mobile, message_1);
		boolean f2 = SmsHelper.send(mobile, message_2);
		
		return (f1 && f2);
	}

	/*
	private static Random rng = new Random();
	
	private static String Populate(int max, String prefix) {

		Integer next = rng.nextInt(max) + 1;
		return String.format("%s%04d", prefix, next);

	}
	
	public static String getCoupon(long amount) {
		if (50 == amount)
			return Populate(9999, "YHQ201403100");
		else if (300 == amount)
			return Populate(9999, "YHQ201404100");
		else
			return Populate(9999, "YHQ201499100");
	}
	*/

}
