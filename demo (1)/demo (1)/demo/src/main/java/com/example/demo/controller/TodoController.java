package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.ToDoDTO;
import com.example.demo.model.ToDoEntity;
import com.example.demo.service.TodoService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService service;

    @PostMapping
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody ToDoDTO dto){
        try{

            // 1. TodoEntity로 변환
            ToDoEntity entity = ToDoDTO.toEntity(dto);

            //2. id를 null로 초기화 한다. (생성당시에는 id가 없어야 하기 때문)
            entity.setId(null);

            //3. Authentication Bearer Token을 통해 받은 userId를 넘긴다
            entity.setUserId(userId);


            //4. 서비스를 이용해 Todo 엔티티 생성
            List<ToDoEntity> entities = service.create(entity);

            //5. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환

            List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());

            //6. 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화 한다
            ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();

            //7. ResponseDTO를 리턴한다
            return ResponseEntity.ok().body(response);

        } catch (Exception e){
            //예외 발생 시
            String error = e.getMessage();
            ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){
        String temporaryUserId = "temporary-user"; //임의의 user id

        // 1. 서비스 메서드의 retrieve 메서드를 사용해 Todo 리스트를 가져온다
        List<ToDoEntity> entities = service.retrieve(userId);

        // 2. 자바 스트림을 활용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다
        List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());

        // 3. 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화 한다
        ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();

        // 7. ResponseDTO를 리턴한다
        return ResponseEntity.ok().body(response);

    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId, @RequestBody ToDoDTO dto){

        // 1. dto를 entity로 변환한다
        ToDoEntity entity = ToDoDTO.toEntity(dto);

        // 2. Authentication Bearer Token을 통해 받은 userId를 넘긴다
        entity.setUserId(userId);

        // 3. 서비스를 이용해 entity를 업데이트 한다
        List<ToDoEntity> entities = service.update(entity);

        // 4. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다
        List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());

        // 5. 변환된 TodoDTO를 이용해 ResponseDTO를 초기화한다
        ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();

        // 6. ResponseDTO를 리턴한다
        return ResponseEntity.ok().body(response);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId, @RequestBody ToDoDTO dto){
        try {

            // 1. TodoEntity로 변환한다
            ToDoEntity entity = ToDoDTO.toEntity(dto);

            // 2. Authentication Bearer Token을 통해 받은 userId를 넘긴다
            entity.setUserId(userId);

            // 3. 서비스를 이용해 entity를 삭제한다
            List<ToDoEntity> entities = service.delete(entity);

            // 4. 자바 스트림을 이용해 리턴된 엔티티 리스트를 dto리스트로 변환한다
            List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());

            // 5. 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
            ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();

            // 6. 반환한다
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            // 7. 예외처리
            String error = e.getMessage();
            ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
//    @GetMapping("/testService")
//    public ResponseEntity<?> testTodo(){
//        String str = service.testService();
//        List<String> list = new ArrayList<>();
//        list.add(str);
//        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
//        return ResponseEntity.ok().body(response);
//
//    }

}
