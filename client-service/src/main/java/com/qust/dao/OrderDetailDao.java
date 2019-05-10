package com.qust.dao;

import com.qust.entity.OrderDetail;
import com.qust.entity.OrderDetailPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailDao extends JpaRepository<OrderDetail, OrderDetailPk> {

    @Query(value = "SELECT  food_name ,counts , remark ,price from order_detail where restaurant = ?1 and  indent = ?2 ", nativeQuery = true)
    List<Object[]> getOrderById (long restaurant , Long order) ;

    @Query(value = "SELECT  *  from order_detail where restaurant = ?1 and  indent = ?2 ", nativeQuery = true)
    List<OrderDetail> findByOrderDetailPkIndent (long restaurant  , Long indent);

}
