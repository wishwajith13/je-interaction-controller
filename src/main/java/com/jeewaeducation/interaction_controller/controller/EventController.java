package com.jeewaeducation.interaction_controller.controller;


import com.jeewaeducation.interaction_controller.dto.event.EventGetDto;
import com.jeewaeducation.interaction_controller.dto.event.EventSaveDto;
import com.jeewaeducation.interaction_controller.service.EventService;
import com.jeewaeducation.interaction_controller.utility.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<StandardResponse> saveEvent(@RequestBody EventSaveDto eventSaveDto) {
        String message = eventService.saveEvent(eventSaveDto);
        return new ResponseEntity<>(new StandardResponse(201, "success", message), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse> getEvents(@PathVariable int id) {
        EventGetDto event = eventService.getEvent(id);
        return new ResponseEntity<>(new StandardResponse(200, "success", event), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<StandardResponse> getAllEvents() {
        return new ResponseEntity<>(new StandardResponse(200, "success", eventService.getAllEvents()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StandardResponse> deleteEvent(@PathVariable int id) {
        String message = eventService.deleteEvent(id);
        return new ResponseEntity<>(new StandardResponse(200, "success", message), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardResponse> updateEvent(@RequestBody EventSaveDto eventSaveDto, @PathVariable int id) {
        String message = eventService.updateEvent(eventSaveDto, id);
        return new ResponseEntity<>(new StandardResponse(201, "updated", message), HttpStatus.ACCEPTED);
    }
}


