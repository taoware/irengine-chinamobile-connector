package com.irengine.connector.controller;

import java.io.IOException;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
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
import com.irengine.connector.service.OrderService;

@RestController
@RequestMapping(value = "/api/v1/chinamobile")
public class ChinaMobileRestController {

	@Autowired
	private OrderService coreService;
	
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> home(@RequestBody String data) {
		
		System.out.println(System.currentTimeMillis());
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		try {
			DtoBase dto = mapper.readValue(data, DtoBase.class);
			
			switch(dto.getMethod()) {
			case "order_create":
				return createOrder(data);
//			case "code_send":
//				break;
//			case "code_send_once":
//				break;
//			case "code_query":
//				break;
//			case "code_cancel":
//				break;
			default:
				return new ResponseEntity<>(ResultDtoUtility.newInvalidJsonResultDto(), HttpStatus.BAD_REQUEST);
			}
		} catch (IOException e) {
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
					coreService.createOrder(order);
				} catch (Exception e) {
					return new ResponseEntity<>(ResultDtoUtility.newInvalidDataOrderCreateResultDto(order), HttpStatus.BAD_REQUEST);
				}
				
				return new ResponseEntity<>(ResultDtoUtility.newOrderCreateResultDto(order), HttpStatus.OK);
			}
			else
				return new ResponseEntity<>(ResultDtoUtility.newInvalidMD5OrderCreateResultDto(dto), HttpStatus.OK);
				
			
		} catch (IOException e) {
			return new ResponseEntity<>(ResultDtoUtility.newInvalidJsonResultDto(), HttpStatus.BAD_REQUEST);
		}
	}
}
