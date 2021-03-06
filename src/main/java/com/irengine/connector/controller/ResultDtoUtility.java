package com.irengine.connector.controller;

import org.apache.commons.lang3.StringUtils;

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

	/*
	 * Order create
	 */
	public static OrderCreateResultDto newOrderCreateResultDto(Order order) {
		OrderCreateResultDto resultDto = new OrderCreateResultDto();
		resultDto.setCode("1000");
		resultDto.setMsg("Order created.");
		resultDto.setOrderid(order.getVendorOrderId());
		resultDto.setB_orderid(order.getOrderId());
		resultDto.setSign(resultDto.getDigest());
		return resultDto;
	}

	public static OrderCreateResultDto newInvalidDataOrderCreateResultDto(Order order) {
		OrderCreateResultDto resultDto = new OrderCreateResultDto();
		resultDto.setCode("1001");
		resultDto.setMsg("Data invalid.");
		resultDto.setOrderid(order.getVendorOrderId());
		resultDto.setB_orderid(order.getOrderId());
		resultDto.setSign(resultDto.getDigest());
		return resultDto;
	}

	public static OrderCreateResultDto newInvalidMD5OrderCreateResultDto(OrderCreateDto dto) {
		OrderCreateResultDto resultDto = new OrderCreateResultDto();
		resultDto.setCode("1001");
		resultDto.setMsg("MD5 invalid.");
		resultDto.setOrderid(dto.getOrderid());
		resultDto.setSign(resultDto.getDigest());
		return resultDto;
	}

	/*
	 * Code action
	 */
	public static CodeActionResultDto newCodeActionResultDto(Order order) {
		CodeActionResultDto resultDto = new CodeActionResultDto();
		if (Order.STATUS.Sent_Success.equals(order.getStatus())) {
			resultDto.setCode("1000");
			resultDto.setMsg("Code sent.");
		}
		else {
			resultDto.setCode("1001");
			resultDto.setMsg("SMS failure.");
		}
		resultDto.setItemid(order.getItems());
		resultDto.setSign(resultDto.getDigest());
		return resultDto;
	}

	public static CodeActionResultDto newQueryCodeActionResultDto(Order order) {
		CodeActionResultDto resultDto = new CodeActionResultDto();
		resultDto.setCode("1000");
		resultDto.setMsg("Code query.");
		resultDto.setItemid(order.getItemsStatus());
		resultDto.setSign(resultDto.getDigest());
		return resultDto;
	}
	
	public static CodeActionResultDto newInvalidDataCodeActionResultDto(Order order) {
		CodeActionResultDto resultDto = new CodeActionResultDto();
		resultDto.setCode("1001");
		resultDto.setMsg("Data invalid.");
		resultDto.setItemid(order.getItems());
		resultDto.setSign(resultDto.getDigest());
		return resultDto;
	}

	public static CodeActionResultDto newInvalidMD5CodeActionResultDto() {
		CodeActionResultDto resultDto = new CodeActionResultDto();
		resultDto.setCode("1001");
		resultDto.setMsg("MD5 invalid.");
		resultDto.setSign(resultDto.getDigest());
		return resultDto;
	}
	
	public static CodeActionResultDto newNotFoundCodeActionResultDto() {
		CodeActionResultDto resultDto = new CodeActionResultDto();
		resultDto.setCode("1001");
		resultDto.setMsg("Order not found.");
		resultDto.setSign(resultDto.getDigest());
		return resultDto;
	}

	/*
	 * Code cancel
	 */
	public static CodeActionResultDto newCancelCodeActionResultDto(CodeCancelDto dto) {
		CodeActionResultDto resultDto = new CodeActionResultDto();
		resultDto.setCode("1000");
		resultDto.setMsg("Code canceled.");
		resultDto.setItemid(dto.getItemid());
		resultDto.setSign(resultDto.getDigest());
		return resultDto;
	}
	
	public static CodeActionResultDto newCodeUsedCodeActionResultDto(Order order) {
		CodeActionResultDto resultDto = new CodeActionResultDto();
		resultDto.setCode("1001");
		resultDto.setMsg("Code used.");
		resultDto.setItemid(StringUtils.EMPTY);
		resultDto.setSign(resultDto.getDigest());
		return resultDto;
	}

}
