package com.newcen.newcen.message.repository;

import com.newcen.newcen.message.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    @Query("select m from MessageEntity m where m.receiver.id=:userId")
    List<MessageEntity> findByReceiverId(@Param("userId") String receiverId);



}
