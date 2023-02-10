package com.newcen.newcen.admin.repository;

import com.newcen.newcen.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<UserEntity, String> {

}
