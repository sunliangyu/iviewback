package com.qust.dao;

import com.qust.entity.RestaurantMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantMemberDao extends JpaRepository<RestaurantMember,Long>{

    @Query(value = "SELECT  id,name from restaurant_member  ", nativeQuery = true)
    List<Object[]> getReS ();
}
