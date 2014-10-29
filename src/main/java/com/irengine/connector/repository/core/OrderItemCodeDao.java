package com.irengine.connector.repository.core;

import org.springframework.data.repository.CrudRepository;

import com.irengine.connector.domain.core.OrderItemCode;

public interface OrderItemCodeDao  extends CrudRepository<OrderItemCode, Long> {

	OrderItemCode findOneByItemCode(String itemCode);

}
