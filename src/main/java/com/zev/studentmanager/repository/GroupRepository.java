package com.zev.studentmanager.repository;

import com.zev.studentmanager.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
