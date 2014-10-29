package com.irengine.connector.service;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.connector.controller.ChinaMobileRestController;
import com.irengine.connector.domain.core.Order;
import com.irengine.connector.domain.core.OrderItemCode;
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
	
	public Order createOrder(Order order) {
		
		order.setOrderId(StringUtils.join(new String[] {"CM", order.getVendorOrderId()}, "-"));
		order.setStatus(Order.STATUS.Created);
		
		return orderDao.save(order);
		
	}
	
	public Order getOrder(String vendorOrderId) {
		
		return orderDao.findOneByVendorOrderId(vendorOrderId);
		
	}
	
	public Order generateCode(Order order) {

		if (logger.isDebugEnabled())
			logger.debug("order: {}, item size: {}", order.getVendorOrderId(), order.getItemCodes().size());

		if (Order.STATUS.Created.equals(order.getStatus())) {
			order.setStatus(Order.STATUS.Not_Sent);
			
			Long count = order.getItemCount();
			Long size = order.getTotalPrice()/(order.getItemCount() * 100);
			for(int i = 0; i < count; i++) {
				OrderItemCode itemCode = new OrderItemCode();
				itemCode.setOrder(order);
				itemCode.setItemCode(getCoupon(size));
				order.getItemCodes().add(itemCode);
			}
			
		}
		
		return orderDao.save(order);
		
	}
	
	public void cancelCode(Order order, String itemIds) throws CodeUsedException {
		
		String[] itemIdArray = StringUtils.split(itemIds, ",");
		for(String itemId : itemIdArray) {
			OrderItemCode item = itemDao.findOneByItemCode(itemId);
			if (OrderItemCode.STATUS.Unused.equals(item.getStatus())) {
				item.setStatus(OrderItemCode.STATUS.Canceled);
				itemDao.save(item);
			}
			else {
				throw new CodeUsedException("code used");
			}
		}
	}
	
	public Order sendCode(Order order) {
		boolean flag = sendCode(order.getUserMobile(), order.getItems());
		
		if(flag)
			order.setStatus(Order.STATUS.Sent_Success);
		else
			order.setStatus(Order.STATUS.Sent_Failure);
		
		return orderDao.save(order);
	}
	
	private static boolean sendCode(String mobile, String code) {
		//TODO: fake send function
		return true;
	}

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

}
