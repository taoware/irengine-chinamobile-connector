package com.irengine.connector.repository.core;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.irengine.connector.domain.core.Order;

public interface OrderDao  extends CrudRepository<Order, Long> {

	Order findOneByVendorOrderId(String vendorOrderId);
	
	@Query("select o from Order o where o.vendorOrderId = ?1 or o.userMobile = ?1")
	List<Order> findByCodeOrMobile(String code);

}
