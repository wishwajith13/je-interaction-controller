package com.jeewaeducation.interaction_controller.service;

import com.jeewaeducation.interaction_controller.dto.event.EventGetDto;
import com.jeewaeducation.interaction_controller.dto.event.EventSaveDto;

import java.util.List;

public interface EventService {
    String saveEvent(EventSaveDto eventSaveDTO);

    String deleteEvent(int eventId);

    EventGetDto getEvent(int eventId);

    List<EventGetDto> getAllEvents();

    String updateEvent(EventSaveDto eventSaveDto, int id);
}
