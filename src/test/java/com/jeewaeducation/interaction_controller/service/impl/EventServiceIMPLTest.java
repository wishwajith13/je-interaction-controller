package com.jeewaeducation.interaction_controller.service.impl;

import com.jeewaeducation.interaction_controller.dto.event.EventGetDto;
import com.jeewaeducation.interaction_controller.dto.event.EventSaveDto;
import com.jeewaeducation.interaction_controller.entity.Event;
import com.jeewaeducation.interaction_controller.exception.DuplicateKeyException;
import com.jeewaeducation.interaction_controller.exception.NotFoundException;
import com.jeewaeducation.interaction_controller.repo.EventRepo;
import com.jeewaeducation.interaction_controller.utility.mappers.EventMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceIMPLTest {

    //service class needs to be tested
    @InjectMocks
    private EventServiceIMPL eventServiceIMPL;

    //dependency of service class
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private EventRepo eventRepo;
    @Mock
    private EventMapper eventMapper;

    @Test
    public void saveEvent_WhenEventSaved_ReturnString(){
        //Arrange
        EventSaveDto eventSaveDto = new EventSaveDto();
        eventSaveDto.setTitle("Event 1");
        eventSaveDto.setDate("2025-02-10");
        eventSaveDto.setTime("10.00 AM");

        Event event = new Event();
        event.setTitle("Event 1");
        event.setDate("2025-02-10");
        event.setTime("10.00 AM");

        Event savedEvent = new Event();
        savedEvent.setId(0);
        savedEvent.setTitle("Event 1");
        savedEvent.setDate("2025-02-10");
        savedEvent.setTime("10.00 AM");



        when(modelMapper.map(eventSaveDto, Event.class))
                .thenReturn(event);
        when(eventRepo.findById(0))
                .thenReturn(Optional.empty());
        when(eventRepo.save(event))
                .thenReturn(savedEvent);

        //Act
        String result = eventServiceIMPL.saveEvent(eventSaveDto);

        //Assert
        assertEquals(eventSaveDto.getTitle(), savedEvent.getTitle());
        assertEquals(eventSaveDto.getDate(), savedEvent.getDate());
        assertEquals(eventSaveDto.getTime(), savedEvent.getTime());
        assertEquals("Event with Id: " + savedEvent.getId() + " Saved", result);

        verify(modelMapper, times(1))
                .map(eventSaveDto, Event.class);
        verify(eventRepo, times(1))
                .save(event);
    }

    @Test
    public void saveEvent_WhenEventIdExists_ThrowsDuplicateKeyException(){
        //Arrange
        EventSaveDto eventSaveDto = new EventSaveDto();
        eventSaveDto.setTitle("Event 1");
        eventSaveDto.setDate("2025-02-10");
        eventSaveDto.setTime("10.00 AM");

        Event event = new Event();
        event.setId(1);
        event.setTitle("Event 1");
        event.setDate("2025-02-10");
        event.setTime("10.00 AM");

        //Mocking the behavior of the dependencies

        when(modelMapper.map(eventSaveDto, Event.class))
                .thenReturn(event);
        when(eventRepo.findById(1))
                .thenReturn(Optional.of(event));

        //Act
        DuplicateKeyException exception = assertThrows(
                DuplicateKeyException.class,
                () -> eventServiceIMPL.saveEvent(eventSaveDto)
        );

        //Assert
        assertEquals("Event already exists", exception.getMessage());

        verify(modelMapper, times(1))
                .map(eventSaveDto, Event.class);
        verify(eventRepo, times(0))
                .save(event);
    }

    @Test
    public void getAllEvents_WhenEventsExist_ReturnsDtoList(){

        //Arrange
        Event event1 = mock(Event.class);
        Event event2 = mock(Event.class);
        List<Event> events = Arrays.asList(event1, event2);

        List<EventGetDto> eventGetDos = Arrays.asList(
                new EventGetDto(event1.getId(),event1.getTitle(),event1.getDate(),event1.getTime()),
                new EventGetDto(event2.getId(),event2.getTitle(),event2.getDate(),event2.getTime())
        );

        //Mocking the behavior of the dependencies
        when(eventRepo.findAll())
                .thenReturn(events);
        when(eventMapper.entityListToDtoList(events))
                .thenReturn(eventGetDos);

        //Act
        List<EventGetDto> result = eventServiceIMPL.getAllEvents();

        //Assert
        verify(eventRepo, times(1))
                .findAll();
        verify(eventMapper, times(1))
                .entityListToDtoList(events);

        assertEquals(eventGetDos, result);
        assertNotNull(result);

    }

    @Test
    public void getAllEvents_WhenNoEventsExist_ThrowsNotFoundException(){
        //Arrange
        when(eventRepo.findAll())
                .thenReturn(Collections.emptyList());
        //Act
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> eventServiceIMPL.getAllEvents()
        );
        //Assert
        assertEquals("No Events Found", exception.getMessage());
        verify(eventRepo, times(1))
                .findAll();
        verify(eventMapper, times(0))
                .entityListToDtoList(anyList());
    }

    @Test
    public void getEvent_WhenEventExists_ReturnsEventDto(){

        //Arrange
        Event event = new Event();
        event.setId(1);
        event.setTitle("Event 1");
        event.setDate("2025-02-10");
        event.setTime("10.00 AM");

        EventGetDto eventGetDto = new EventGetDto();
        eventGetDto.setId(1);
        eventGetDto.setTitle("Event 1");
        eventGetDto.setDate("2025-02-10");
        eventGetDto.setTime("10.00 AM");


        //Mocking the behavior of the dependencies
        when(eventRepo.existsById(1))
                .thenReturn(true);
        when(eventRepo.findById(1))
                .thenReturn(Optional.of(event));
        when(modelMapper.map(event, EventGetDto.class))
                .thenReturn(eventGetDto);

        //Act
        EventGetDto result = eventServiceIMPL.getEvent(1);

        //Assert
        assertEquals(eventGetDto,result);
        verify(eventRepo, times(1))
                .findById(1);
        verify(modelMapper, times(1))
                .map(event, EventGetDto.class);
        verify(eventRepo, times(1))
                .existsById(1);
    }

    @Test
    public void getEvent_WhenEventDoesNotExist_ThrowsNotFoundException(){
        //Arrange
        int eventId = 999;
        when(eventRepo.existsById(eventId))
                .thenReturn(false);
        //Act
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> eventServiceIMPL.getEvent(eventId)
        );
        //Assert
        assertEquals("Event Not Found", exception.getMessage());
        verify(eventRepo, times(1))
                .existsById(eventId);
        verify(eventRepo, times(0))
                .findById(eventId);
    }

    @Test
    public void updateEvent_WhenEventExists_ReturnsString(){

        //Arrange
        EventSaveDto eventSaveDto = new EventSaveDto();
        eventSaveDto.setTitle("Event updated 1");
        eventSaveDto.setDate("2025-02-10");
        eventSaveDto.setTime("10.00 AM");

        Event event = new Event();
        event.setId(1);
        event.setTitle("Event updated 1");
        event.setDate("2025-02-10");
        event.setTime("10.00 AM");

        //Mocking the behavior of the dependencies
        when(eventRepo.findById(1))
                .thenReturn(Optional.of(event));
        when(modelMapper.map(eventSaveDto, Event.class))
                .thenReturn(event);
        when(eventRepo.save(event))
                .thenReturn(event);

        //Act
        String result = eventServiceIMPL.updateEvent(eventSaveDto, 1);
        //Assert
        assertEquals("Event with Id: " + event.getId() + " Updated", result);
        verify(eventRepo, times(1))
                .findById(1);
        verify(modelMapper, times(1))
                .map(eventSaveDto, Event.class);
        verify(eventRepo, times(1))
                .save(event);

    }

    @Test
    public void updateEvent_WhenEventDoesNotExist_ThrowsNotFoundException(){
        //Arrange
        int eventId = 999;
        EventSaveDto eventSaveDto = new EventSaveDto();
        eventSaveDto.setTitle("Event updated 1");
        eventSaveDto.setDate("2025-02-10");
        eventSaveDto.setTime("10.00 AM");

        //Mocking the behavior of the dependencies
        when(eventRepo.findById(eventId))
                .thenReturn(Optional.empty());
        //Act
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> eventServiceIMPL.updateEvent(eventSaveDto, eventId)
        );
        //Assert
        assertEquals("Event Not Found", exception.getMessage());
        verify(eventRepo, times(1))
                .findById(eventId);
        verify(eventRepo, times(0))
                .save(any(Event.class));
        verify(modelMapper, times(0))
                .map(any(EventSaveDto.class), any());
    }

    @Test
    public void deleteEvent_WhenEventDeleted_ReturnsString(){
        //Arrange
        int eventId = 1;

        //Mocking the behavior of the dependencies
        when(eventRepo.existsById(eventId))
                .thenReturn(true);
        doNothing()
                .when(eventRepo)
                .deleteById(eventId);

        //Act
        String result = eventServiceIMPL.deleteEvent(eventId);

        //Assert
        assertEquals("Event with ID"+eventId+"has been Deleted", result);
        verify(eventRepo, times(1))
                .deleteById(eventId);
        verify(eventRepo, times(1))
                .existsById(eventId);
    }

    @Test
    public void deleteEvent_WhenEventDoesNotExist_ThrowsNotFoundException(){
        //Arrange
        int eventId = 999;
        when(eventRepo.existsById(eventId))
                .thenReturn(false);
        //Act
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> eventServiceIMPL.deleteEvent(eventId)
        );
        //Assert
        assertEquals("Event Not Found", exception.getMessage());
        verify(eventRepo, never())
                .deleteById(anyInt());
        verify(eventRepo, times(1))
                .existsById(eventId);
    }
}