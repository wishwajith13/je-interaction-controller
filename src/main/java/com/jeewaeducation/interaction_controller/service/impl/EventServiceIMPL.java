package com.jeewaeducation.interaction_controller.service.impl;

import com.jeewaeducation.interaction_controller.dto.event.EventGetDto;
import com.jeewaeducation.interaction_controller.dto.event.EventSaveDto;
import com.jeewaeducation.interaction_controller.entity.Event;
import com.jeewaeducation.interaction_controller.exception.DuplicateKeyException;
import com.jeewaeducation.interaction_controller.exception.NotFoundException;
import com.jeewaeducation.interaction_controller.repo.EventRepo;
import com.jeewaeducation.interaction_controller.service.EventService;
import com.jeewaeducation.interaction_controller.utility.mappers.EventMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class EventServiceIMPL implements EventService {


    private  ModelMapper modelMapper;
    private  EventRepo eventRepo;
    private EventMapper eventMapper;


    @Override
    public List<EventGetDto> getAllEvents() {
            List<Event> events = eventRepo.findAll();
            if(events.isEmpty()){
                throw new NotFoundException("No Events Found");
            }
            return eventMapper.entityListToDtoList(events);
    }

    @Override
    public String saveEvent(EventSaveDto eventSaveDTO) {
        Event event = modelMapper.map(eventSaveDTO, Event.class);
        eventRepo.findById(event.getId()).ifPresent(e -> {
            throw new DuplicateKeyException("Event already exists");
        });
        eventRepo.save(event);
        return "Event with Id: " + event.getId() + " Saved";
    }

    @Override
    public String deleteEvent(int eventId) {
        if(eventRepo.existsById(eventId)){
            eventRepo.deleteById(eventId);
            return "Event with ID"+eventId+"has been Deleted";
        }else{
            throw new NotFoundException("Event Not Found");
        }

    }

    @Override
    public EventGetDto getEvent(int eventId) {
        if(eventRepo.existsById(eventId)){
            Event event = eventRepo.findById(eventId).orElseThrow(() -> new NotFoundException("Event Not Found"));
            return modelMapper.map(event,EventGetDto.class);
        }else {
            throw new NotFoundException("Event Not Found");
        }
    }
    
    @Override
    public String updateEvent(EventSaveDto eventSaveDto, int id) {
        eventRepo.findById(id).orElseThrow(() -> new NotFoundException("Event Not Found"));
        Event event = modelMapper.map(eventSaveDto, Event.class);
        event.setId(id);
        eventRepo.save(event);
        return "Event with Id: " + event.getId() + " Updated";
    }
}
