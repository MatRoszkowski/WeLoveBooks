package com.mateuszroszkowski.WeLoveBooks.service;

import com.mateuszroszkowski.WeLoveBooks.dto.EventDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.mapper.EventMapper;
import com.mateuszroszkowski.WeLoveBooks.model.Event;
import com.mateuszroszkowski.WeLoveBooks.repository.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventService(EventRepository eventRepository, EventMapper eventMapper){
        this.eventRepository=eventRepository;
        this.eventMapper=eventMapper;
    }

    public EventDTO createEvent(EventDTO eventDTO){
        Event event = new Event();

        event.setName(eventDTO.getName());
        event = eventRepository.save(event);

        return eventMapper.map(event);
    }

    public EventDTO getEventDTOById(Long id){
        Event event = getEventById(id);
        return eventMapper.map(event);
    }

    public Event getEventById(Long id){
        Event event = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
        return event;
    }

    public void deleteEvent(Long id) {
        Event event = getEventById(id);
        event.getUsers().forEach(user -> user.getEvents().remove(event));
        eventRepository.delete(getEventById(id));
    }

    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Event event = getEventById(id);
        if(event.getName()!=null) {
            event.setName(eventDTO.getName());
        }

        return eventMapper.map(eventRepository.save(event));
    }
}
