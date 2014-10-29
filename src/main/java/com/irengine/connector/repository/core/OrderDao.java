package com.irengine.connector.repository.core;

import org.springframework.data.repository.CrudRepository;

import com.irengine.connector.domain.core.Order;

public interface OrderDao  extends CrudRepository<Order, Long> {

	Order findOneByVendorOrderId(String vendorOrderId);

}
