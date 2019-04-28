package com.qust.dao;

import com.qust.entity.RestaurantMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantMemberDao extends JpaRepository<RestaurantMember,Long>{

}
