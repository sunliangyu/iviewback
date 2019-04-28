package com.qust.dao;

import com.qust.entity.FoodNeed;
import com.qust.entity.FoodNeedPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodNeedDao extends JpaRepository<FoodNeed, FoodNeedPk> {

    public List<FoodNeed> findByIdFood(Long food);
}
