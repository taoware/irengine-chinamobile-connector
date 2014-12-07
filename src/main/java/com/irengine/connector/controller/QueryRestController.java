package com.irengine.connector.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.irengine.connector.domain.core.Coupon;
import com.irengine.connector.domain.core.Order;
import com.irengine.connector.service.OrderService;

@Controller
public class QueryRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(QueryRestController.class);
	
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/coupon/{code}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getCoupon(@PathVariable("code") String code) {
		Coupon coupon = orderService.getCoupon(code);
		if (coupon == null) {
			logger.warn("coupon with code {} not found", code);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(coupon, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/order/{code}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getOrder(@PathVariable("code") String code) {
		List<Order> orders = orderService.getOrderByCodeOrMobile(code);
		if (orders == null || orders.size() == 0) {
			logger.warn("order with code {} not found", code);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

}
