package com.example.demo.dto;

import com.example.demo.model.ToDoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ToDoDTO {
    private String id;
    private String title;
    private boolean done;

    public ToDoDTO(final ToDoEntity entity){
        //추후에 스프링 시큐리티를 사용하여 인증을 구현할 것이므로 유저가 자기 아이디를 넘겨주지 않아도 인증이 가능하다
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }

    public static ToDoEntity toEntity(final ToDoDTO dto){
        return ToDoEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .done(dto.isDone())
                .build();
    }

}
