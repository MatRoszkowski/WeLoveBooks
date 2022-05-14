package com.mateuszroszkowski.WeLoveBooks.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.mateuszroszkowski.WeLoveBooks.model.RoleName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    private Set<RoleName> roles;

    @Builder
    public UserDTO(Long id, String username, String password, Set<RoleName> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
