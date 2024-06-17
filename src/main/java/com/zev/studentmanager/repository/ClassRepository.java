package com.zev.studentmanager.repository;

import com.zev.studentmanager.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Class, Long> {
}
