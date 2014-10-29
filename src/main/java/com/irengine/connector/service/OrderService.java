package com.irengine.connector.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irengine.connector.domain.core.Order;
import com.irengine.connector.repository.core.OrderDao;

@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderDao orderDao;
	
	public Order createOrder(Order order) {
		
		order.setOrderId(StringUtils.join(new String[] {"CM", order.getVendorOrderId()}, "-"));
		
		return orderDao.save(order);
		
	}
}
