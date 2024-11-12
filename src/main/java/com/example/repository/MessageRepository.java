package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    @Modifying
    @Transactional // having transactional here is the most important part
    @Query("DELETE FROM Message where messageId = :messageId")
    int deleteMessageById(@Param("messageId") int messageId);

    @Modifying
    @Transactional
    @Query("UPDATE Message SET messageText = :messageText WHERE id = :id")
    int updateMessageById(@Param("id") int messageId, @Param("messageText") String messageText);

    @Transactional
    @Query("FROM Message WHERE postedBy = :postedBy")
    List<Message> getAllMessagesByUser(@Param("postedBy") int postedBy);
}
