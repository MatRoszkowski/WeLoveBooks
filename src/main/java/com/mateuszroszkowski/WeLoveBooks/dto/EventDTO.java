package com.mateuszroszkowski.WeLoveBooks.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class EventDTO {
    private Long id;
    private String name;
    private Set<UserDTO> users;

    public EventDTO(Long id, String name, Set<UserDTO> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }
}
