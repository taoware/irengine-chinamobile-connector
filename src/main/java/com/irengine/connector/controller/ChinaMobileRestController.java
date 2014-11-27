package com.irengine.connector.controller;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irengine.connector.domain.core.Order;
import com.irengine.connector.service.CodeException;
import com.irengine.connector.service.OrderService;

@RestController
@RequestMapping(value = "/api/v1/chinamobile")
public class ChinaMobileRestController {

	private static final Logger logger = LoggerFactory.getLogger(ChinaMobileRestController.class);
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> home(@RequestBody String data) {
		
		if (logger.isDebugEnabled())
			logger.debug("input:\ntime\n{}\ndata\n{}", System.currentTimeMillis(), data);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		try {
			DtoBase dto = mapper.readValue(data, DtoBase.class);
			
			switch(dto.getMethod()) {
			case "order_create":
				return createOrder(data);
			case "code_send":
				return sendCode(data);
			case "code_send_once":
				return retrySendCode(data);
			case "code_query":
				return queryCode(data);
			case "code_cancel":
				return cancelCode(data);
			default:
				logger.warn("Invalid json: method mismatch");
				return new ResponseEntity<>(ResultDtoUtility.newInvalidJsonResultDto(), HttpStatus.BAD_REQUEST);
			}
		} catch (IOException e) {
			logger.warn("Invalid json: {}", e);
			return new ResponseEntity<>(ResultDtoUtility.newInvalidJsonResultDto(), HttpStatus.BAD_REQUEST);
		}

	}

	private ResponseEntity<?> createOrder(String data) {
		ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		try {
			OrderCreateDto dto = jsonMapper.readValue(data, OrderCreateDto.class);
			
			if (dto.isValid()) {
				Mapper mapper = new DozerBeanMapper();
				Order order =  mapper.map(dto, Order.class);
				
				try {
					orderService.createOrder(order);
				} catch (Exception e) {
					logger.warn("Invalid data: {}", e);
					return new ResponseEntity<>(ResultDtoUtility.newInvalidDataOrderCreateResultDto(order), HttpStatus.BAD_REQUEST);
				}
				
				return new ResponseEntity<>(ResultDtoUtility.newOrderCreateResultDto(order), HttpStatus.OK);
			}
			else {
				logger.warn("Invalid MD5: {}, {}, {}", dto.getDigestCode(), dto.getDigest(), dto.getSign());
				return new ResponseEntity<>(ResultDtoUtility.newInvalidMD5OrderCreateResultDto(dto), HttpStatus.OK);
			}
				
			
		} catch (IOException e) {
			logger.warn("Invalid json: {}", e);
			return new ResponseEntity<>(ResultDtoUtility.newInvalidJsonResultDto(), HttpStatus.BAD_REQUEST);
		}
	}
	
	private ResponseEntity<?> sendCode(String data) {
		ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		try {
			CodeActionDto dto = jsonMapper.readValue(data, CodeActionDto.class);
			
			if (dto.isValid()) {
				Order order = orderService.getOrder(dto.getOrderid());
				
				if (null == order)
					return new ResponseEntity<>(ResultDtoUtility.newNotFoundCodeActionResultDto(), HttpStatus.OK);
				
				try {
					orderService.generateCode(order);
					orderService.sendCode(order);
				} catch (Exception e) {
					logger.warn("Invalid data: {}", e);
					return new ResponseEntity<>(ResultDtoUtility.newInvalidDataCodeActionResultDto(order), HttpStatus.BAD_REQUEST);
				}
				
				return new ResponseEntity<>(ResultDtoUtility.newCodeActionResultDto(order), HttpStatus.OK);
			}
			else {
				logger.warn("Invalid MD5: {}, {}, {}", dto.getDigestCode(), dto.getDigest(), dto.getSign());
				return new ResponseEntity<>(ResultDtoUtility.newInvalidMD5CodeActionResultDto(), HttpStatus.OK);
			}
				
			
		} catch (IOException e) {
			logger.warn("Invalid json: {}", e);
			return new ResponseEntity<>(ResultDtoUtility.newInvalidJsonResultDto(), HttpStatus.BAD_REQUEST);
		}
	}
	
	private ResponseEntity<?> retrySendCode(String data) {
		ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		try {
			CodeActionDto dto = jsonMapper.readValue(data, CodeActionDto.class);
			
			if (dto.isValid()) {
				Order order = orderService.getOrder(dto.getOrderid());
				
				if (null == order)
					return new ResponseEntity<>(ResultDtoUtility.newNotFoundCodeActionResultDto(), HttpStatus.OK);
				
				try {
					orderService.sendCode(order);
				} catch (Exception e) {
					logger.warn("Invalid data: {}", e);
					return new ResponseEntity<>(ResultDtoUtility.newInvalidDataCodeActionResultDto(order), HttpStatus.BAD_REQUEST);
				}
				
				return new ResponseEntity<>(ResultDtoUtility.newCodeActionResultDto(order), HttpStatus.OK);
			}
			else {
				logger.warn("Invalid MD5: {}, {}, {}", dto.getDigestCode(), dto.getDigest(), dto.getSign());
				return new ResponseEntity<>(ResultDtoUtility.newInvalidMD5CodeActionResultDto(), HttpStatus.OK);
			}
				
			
		} catch (IOException e) {
			logger.warn("Invalid json: {}", e);
			return new ResponseEntity<>(ResultDtoUtility.newInvalidJsonResultDto(), HttpStatus.BAD_REQUEST);
		}
	}

	private ResponseEntity<?> queryCode(String data) {
		ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		try {
			CodeActionDto dto = jsonMapper.readValue(data, CodeActionDto.class);
			
			if (dto.isValid()) {
				Order order = orderService.getOrder(dto.getOrderid());
				
				if (null == order)
					return new ResponseEntity<>(ResultDtoUtility.newNotFoundCodeActionResultDto(), HttpStatus.OK);
				
				return new ResponseEntity<>(ResultDtoUtility.newQueryCodeActionResultDto(order), HttpStatus.OK);
			}
			else {
				logger.warn("Invalid MD5: {}, {}, {}", dto.getDigestCode(), dto.getDigest(), dto.getSign());
				return new ResponseEntity<>(ResultDtoUtility.newInvalidMD5CodeActionResultDto(), HttpStatus.OK);
			}
				
			
		} catch (IOException e) {
			logger.warn("Invalid json: {}", e);
			return new ResponseEntity<>(ResultDtoUtility.newInvalidJsonResultDto(), HttpStatus.BAD_REQUEST);
		}
	}
	
	private ResponseEntity<?> cancelCode(String data) {
		ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		try {
			CodeCancelDto dto = jsonMapper.readValue(data, CodeCancelDto.class);
			
			if (dto.isValid()) {
				Order order = orderService.getOrder(dto.getOrderid());
				
				if (null == order || StringUtils.isEmpty(dto.getItemid()))
					return new ResponseEntity<>(ResultDtoUtility.newNotFoundCodeActionResultDto(), HttpStatus.OK);
				
				try {
					orderService.cancelCode(order, dto.getItemid());
				} catch (CodeException cue) {
					logger.warn("Code used");
					return new ResponseEntity<>(ResultDtoUtility.newCodeUsedCodeActionResultDto(order), HttpStatus.BAD_REQUEST);
				} catch (Exception e) {
					logger.warn("Invalid data: {}", e);
					return new ResponseEntity<>(ResultDtoUtility.newInvalidDataCodeActionResultDto(order), HttpStatus.BAD_REQUEST);
				}
				
				return new ResponseEntity<>(ResultDtoUtility.newCancelCodeActionResultDto(dto), HttpStatus.OK);
			}
			else {
				logger.warn("Invalid MD5: {}, {}, {}", dto.getDigestCode(), dto.getDigest(), dto.getSign());
				return new ResponseEntity<>(ResultDtoUtility.newInvalidMD5CodeActionResultDto(), HttpStatus.OK);
			}
				
			
		} catch (IOException e) {
			logger.warn("Invalid json: {}", e);
			return new ResponseEntity<>(ResultDtoUtility.newInvalidJsonResultDto(), HttpStatus.BAD_REQUEST);
		}
	}

}
