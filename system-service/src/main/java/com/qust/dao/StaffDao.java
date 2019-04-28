package com.qust.dao;

import com.qust.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffDao  extends JpaRepository<Staff,Long> {
}
