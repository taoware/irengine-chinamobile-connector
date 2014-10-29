package com.irengine.connector.controller;

import com.irengine.connector.domain.core.Order;

public class ResultDtoUtility {
	
	// JSON parse failed.
	public static ResultDtoBase newInvalidJsonResultDto( ) {
		ResultDtoBase resultDto = new ResultDtoBase();
		resultDto.setCode("1001");
		resultDto.setMsg("Json invalid.");
		resultDto.setSign(resultDto.getDigest());
		return resultDto;
	}

	public static OrderCreateResultDto newOrderCreateResultDto(Order order) {
		OrderCreateResultDto resultDto = new OrderCreateResultDto();
		resultDto.setCode("1000");
		resultDto.setMsg("Order created.");
		resultDto.setOrderId(order.getVendorOrderId());
		resultDto.setB_orderId(order.getOrderId());
		resultDto.setSign(resultDto.getDigest());
		return resultDto;
	}

	public static OrderCreateResultDto newInvalidDataOrderCreateResultDto(Order order) {
		OrderCreateResultDto resultDto = new OrderCreateResultDto();
		resultDto.setCode("1001");
		resultDto.setMsg("Data invalid.");
		resultDto.setOrderId(order.getVendorOrderId());
		resultDto.setB_orderId(order.getOrderId());
		resultDto.setSign(resultDto.getDigest());
		return resultDto;
	}

	public static OrderCreateResultDto newInvalidMD5OrderCreateResultDto(OrderCreateDto dto) {
		OrderCreateResultDto resultDto = new OrderCreateResultDto();
		resultDto.setCode("1001");
		resultDto.setMsg("MD5 invalid.");
		resultDto.setOrderId(dto.getOrderId());
		resultDto.setSign(resultDto.getDigest());
		return resultDto;
	}

}
