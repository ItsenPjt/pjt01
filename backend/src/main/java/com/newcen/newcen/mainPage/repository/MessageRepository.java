package com.newcen.newcen.mainPage.repository;

import com.newcen.newcen.common.entity.BoardEntity;
import com.newcen.newcen.mainPage.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {


}
