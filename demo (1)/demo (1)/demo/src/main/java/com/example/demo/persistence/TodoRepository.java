package com.example.demo.persistence;

import com.example.demo.model.ToDoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<ToDoEntity, String> {

    //기본이 아닌 쿼리
    List<ToDoEntity> findByUserId(String userId);


    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
}
