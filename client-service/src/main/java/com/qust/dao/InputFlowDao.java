package com.qust.dao;

import com.qust.entity.InputFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InputFlowDao  extends JpaRepository<InputFlow,Long> {

    @Query(value = "SELECT  sum(input) from input_flow where restaurant = ?1 and create_time  >= ?2 and create_time <?3    ", nativeQuery = true)
    Object getTodayCount (Long restaurant , String start, String stop);
}
