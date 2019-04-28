package com.qust.dao;

import com.qust.entity.OrderDetail;
import com.qust.entity.OrderDetailPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailDao extends JpaRepository<OrderDetail, OrderDetailPk> {
}
