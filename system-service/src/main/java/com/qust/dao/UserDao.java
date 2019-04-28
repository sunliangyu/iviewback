package com.qust.dao;


import com.qust.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserDao extends JpaRepository<User, Long> {
	User findByUsername(String username);
	User findById (Long id);
}
