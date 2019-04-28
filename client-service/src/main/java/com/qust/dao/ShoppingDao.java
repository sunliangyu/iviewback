package com.qust.dao;

import com.qust.entity.ShoppingCard;
import com.qust.entity.ShoppingCardPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingDao extends JpaRepository<ShoppingCard, ShoppingCardPk> {
}
