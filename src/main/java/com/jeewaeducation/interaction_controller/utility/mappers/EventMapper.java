package com.jeewaeducation.interaction_controller.utility.mappers;

import com.jeewaeducation.interaction_controller.dto.event.EventGetDto;
import com.jeewaeducation.interaction_controller.entity.Event;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    List<EventGetDto> entityListToDtoList(List<Event> events);
}
