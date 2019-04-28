package com.qust.dao;

import com.qust.entity.CodeOrderPk;
import com.qust.entity.OrderFinsh;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderFinshDao extends JpaRepository<OrderFinsh, CodeOrderPk> {
}
