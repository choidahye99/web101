package com.example.demo.service;

import com.example.demo.model.ToDoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    //Validations(검증) : 넘어온 엔티티가 유효한지 검사하는 로직.
    private void vaildate(final ToDoEntity entity){

        if(entity == null){
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if(entity.getId() == null){
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

    //Create
    public List<ToDoEntity> create(final ToDoEntity entity){
        repository.save(entity);

        log.info("Entity Id : {} is saved", entity.getId());

        return repository.findByUserId(entity.getUserId());
    }

    //Get Todo List
    public List<ToDoEntity> retrieve(final String userId){
        log.info("Entity UserId : {} is based", userId);
        return repository.findByUserId(userId);
    }

    // Update
    public List<ToDoEntity> update(final ToDoEntity entity){

        // 1. 저장할 entity가 유효한지 검사한다
        vaildate(entity);

        // 2. 넘겨받은 엔티티 id를 이용해 TodoEntity를 가져온다.
        final Optional<ToDoEntity> original = repository.findById(entity.getId());

        original.ifPresent(todo -> {
            // 3. 반환된 ToDoEntity가 존재한다면 값을 새 entity의 값으로 덮어 씌운다
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            // 4. 데이터 베이스에 새 값을 저장한다
            repository.save(todo);
        });

        return retrieve(entity.getUserId());
    }

    //Delete
    public List<ToDoEntity> delete(final ToDoEntity entity){
        // 1. 저장할 엔티티가 유효한지 확인한다
        vaildate(entity);

        try {
            // 2. 엔티티를 삭제한다
            repository.delete(entity);
        } catch (Exception e){
            // 3. exception 발생 시 exception을 logging 한다
            log.error("error deleting entry", entity.getId(), e);
            throw new RuntimeException("error deleting entity " + entity.getId());
        }

        // 5. 새 Todo 리스트를 가져와 return 한다
        return retrieve(entity.getUserId());
    }

    //    public String testService(){
//        //Todo Entity 생성
//        ToDoEntity entity = ToDoEntity.builder().title("운동하기").build();
//        //Todo Entity 저장
//        repository.save(entity);
//        //Todo Entity 검색
//        ToDoEntity savedEntity = repository.findById(entity.getId()).get();
//        return savedEntity.getTitle();
//    }

}
