package com.qust.dao;

import com.qust.entity.CodeOrder;
import com.qust.entity.CodeOrderPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeOrderDao extends JpaRepository<CodeOrder, CodeOrderPk> {
}
