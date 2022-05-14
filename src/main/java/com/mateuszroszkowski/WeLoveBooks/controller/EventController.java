package com.mateuszroszkowski.WeLoveBooks.controller;

import com.mateuszroszkowski.WeLoveBooks.dto.EventDTO;
import com.mateuszroszkowski.WeLoveBooks.service.EventService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService){this.eventService=eventService;}

    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable Long id){
        return eventService.getEventDTOById(id);
    }

    @PostMapping
    public EventDTO createEvent(@RequestBody EventDTO eventDTO){
        return eventService.createEvent(eventDTO);
    }

    @PatchMapping("/{id}")
    public EventDTO updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO){
        return eventService.updateEvent(id, eventDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id){
        eventService.deleteEvent(id);
    }
}
