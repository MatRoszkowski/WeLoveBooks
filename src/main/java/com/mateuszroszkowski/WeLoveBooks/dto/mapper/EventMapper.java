package com.mateuszroszkowski.WeLoveBooks.dto.mapper;

import com.mateuszroszkowski.WeLoveBooks.dto.EventDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.UserDTO;
import com.mateuszroszkowski.WeLoveBooks.model.Event;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class EventMapper implements Mapper<Event, EventDTO> {
    private final UserMapper userMapper;

    public EventMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public EventDTO map(Event event){
        Set<UserDTO> usersDTO = new HashSet<>();
        event.getUsers().forEach(u -> usersDTO.add(userMapper.map(u)));
        return new EventDTO(event.getId(), event.getName(), usersDTO);
    }
}
