package com.jeewaeducation.interaction_controller.service.impl;

import com.jeewaeducation.interaction_controller.dto.counselorSession.CounselorSessionGetDTO;
import com.jeewaeducation.interaction_controller.dto.counselorSession.CounselorSessionSaveDTO;
import com.jeewaeducation.interaction_controller.entity.CounselorSession;
import com.jeewaeducation.interaction_controller.exception.DuplicateKeyException;
import com.jeewaeducation.interaction_controller.exception.NotFoundException;
import com.jeewaeducation.interaction_controller.repo.CounselorSessionRepo;
import com.jeewaeducation.interaction_controller.utility.mappers.CounselorSessionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CounselorSessionServiceIMPLTest {

    //service class needs to be tested
    @InjectMocks
    private CounselorSessionServiceIMPL counselorSessionServiceIMPL;

    //dependency of service class
    @Mock
    private CounselorSessionRepo counselorSessionRepo;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private CounselorSessionMapper counselorSessionMapper;

    @Test
    public void saveSession_WhenSessionSaved_ReturnString(){
        //Arrange
        CounselorSessionSaveDTO counselorSessionSaveDTO = Mockito.mock(CounselorSessionSaveDTO.class);

        CounselorSession counselorSession = new CounselorSession(
                0,
                counselorSessionSaveDTO.getDate(),
                counselorSessionSaveDTO.getDescription(),
                counselorSessionSaveDTO.getCounselor(),
                counselorSessionSaveDTO.getStudent()
        );

        CounselorSession savedCounselorSession = new CounselorSession(
                0,
                counselorSessionSaveDTO.getDate(),
                counselorSessionSaveDTO.getDescription(),
                counselorSessionSaveDTO.getCounselor(),
                counselorSessionSaveDTO.getStudent()
        );

        //Mocking the behavior of the dependencies
        when(modelMapper.map(counselorSessionSaveDTO, CounselorSession.class))
                .thenReturn(counselorSession);
        when(counselorSessionRepo.findById(0))
                .thenReturn(java.util.Optional.empty());
        when(counselorSessionRepo.save(counselorSession))
                .thenReturn(savedCounselorSession);

        //Act
        String result = counselorSessionServiceIMPL
                .saveCounselorSession(counselorSessionSaveDTO);

        //Assert
        assertEquals("Counselor Session with Id: 0 Saved", result);

        verify(modelMapper, times(1))
                .map(counselorSessionSaveDTO, CounselorSession.class);
        verify(counselorSessionRepo, times(1))
                .findById(0);
        verify(counselorSessionRepo, times(1))
                .save(counselorSession);
    }

    @Test
    public void saveSession_WhenSessionExists_ThrowDuplicateKeyException(){
        //Arrange
        CounselorSessionSaveDTO counselorSessionSaveDTO = Mockito.mock(CounselorSessionSaveDTO.class);

        CounselorSession counselorSession = new CounselorSession(
                0,
                counselorSessionSaveDTO.getDate(),
                counselorSessionSaveDTO.getDescription(),
                counselorSessionSaveDTO.getCounselor(),
                counselorSessionSaveDTO.getStudent()
        );

        //Mocking the behavior of the dependencies
        when(modelMapper.map(counselorSessionSaveDTO, CounselorSession.class))
                .thenReturn(counselorSession);
        when(counselorSessionRepo.findById(0))
                .thenReturn(java.util.Optional.of(counselorSession));

        //Act
        //Assert
        DuplicateKeyException exception = assertThrows(
                DuplicateKeyException.class,
                () -> counselorSessionServiceIMPL.saveCounselorSession(counselorSessionSaveDTO)
        );

        assertEquals("Counselor Session already exists", exception.getMessage());

        verify(modelMapper, times(1))
                .map(counselorSessionSaveDTO, CounselorSession.class);
        verify(counselorSessionRepo, times(1))
                .findById(0);
        verify(counselorSessionRepo, times(0))
                .save(counselorSession);
    }

    @Test
    public void getAllSessions_WhenSessionsExist_ReturnListOfSessions(){
        //Arrange
        CounselorSession counselorSession1 = mock(CounselorSession.class);
        CounselorSession counselorSession2 = mock(CounselorSession.class);

        List<CounselorSession> counselorSessions = Arrays.asList(counselorSession1, counselorSession2);

        List<CounselorSessionGetDTO> counselorSessionGetDTOS = Arrays.asList(
                new CounselorSessionGetDTO(
                        counselorSession1.getSessionId(),
                        counselorSession1.getDate(),
                        counselorSession1.getDescription(),
                        counselorSession1.getCounselor(),
                        counselorSession1.getStudent()
                ),
                new CounselorSessionGetDTO(
                        counselorSession2.getSessionId(),
                        counselorSession2.getDate(),
                        counselorSession2.getDescription(),
                        counselorSession2.getCounselor(),
                        counselorSession2.getStudent()
                )
        );

        //Mocking the behavior of the dependencies
        when(counselorSessionRepo.findAll())
                .thenReturn(counselorSessions);
        when(counselorSessionMapper.entityListToDtoList(counselorSessions))
                .thenReturn(counselorSessionGetDTOS);

        //Act
        List<CounselorSessionGetDTO> result = counselorSessionServiceIMPL.getAllCounselorSessions();

        //Assert
        assertEquals(counselorSessionGetDTOS, result);
        assertNotNull(result);

        verify(counselorSessionRepo,times(1))
                .findAll();
        verify(counselorSessionMapper,times(1))
                .entityListToDtoList(counselorSessions);

    }

    @Test
    public void getAllSessions_WhenNoSessionsExist_ThrowNotFoundException(){
        //Arrange
        List<CounselorSession> counselorSessions = new ArrayList<>();

        //Mocking the behavior of the dependencies
        when(counselorSessionRepo.findAll())
                .thenReturn(counselorSessions);

        //Act
        //Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> counselorSessionServiceIMPL.getAllCounselorSessions()
        );

        assertEquals("No Counselor Sessions found", exception.getMessage());

        verify(counselorSessionRepo,times(1))
                .findAll();
        verify(counselorSessionMapper,times(0))
                .entityListToDtoList(counselorSessions);
    }

    @Test
    public void getSession_WhenSessionExists_ReturnSession() {
        //Arrange
        CounselorSession counselorSession = mock(CounselorSession.class);

        CounselorSessionGetDTO counselorSessionGetDTO = new CounselorSessionGetDTO(
                counselorSession.getSessionId(),
                counselorSession.getDate(),
                counselorSession.getDescription(),
                counselorSession.getCounselor(),
                counselorSession.getStudent()
        );

        //Mocking the behavior of the dependencies
        when(counselorSessionRepo.findById(0))
                .thenReturn(java.util.Optional.of(counselorSession));
        when(modelMapper.map(counselorSession, CounselorSessionGetDTO.class))
                .thenReturn(counselorSessionGetDTO);

        //Act
        CounselorSessionGetDTO result = counselorSessionServiceIMPL
                .getCounselorSession(0);

        //Assert
        assertEquals(counselorSessionGetDTO, result);
        assertNotNull(result);
        verify(counselorSessionRepo, times(1))
                .findById(0);
        verify(modelMapper, times(1))
                .map(counselorSession, CounselorSessionGetDTO.class);

    }

    @Test
    public void getSession_WhenSessionDoesNotExist_ThrowNotFoundException() {
        //Arrange
        //Mocking the behavior of the dependencies
        when(counselorSessionRepo.findById(0))
                .thenReturn(java.util.Optional.empty());

        //Act
        //Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> counselorSessionServiceIMPL.getCounselorSession(0)
        );

        assertEquals("Counselor Session not found with ID: 0", exception.getMessage());

        verify(counselorSessionRepo, times(1))
                .findById(0);

        verify(modelMapper, times(0))
                .map(any(), any());

    }

    @Test
    public void updateSession_WhenSessionExists_ReturnString(){
        //Arrange
        CounselorSessionSaveDTO counselorSessionSaveDTO = Mockito.mock(CounselorSessionSaveDTO.class);

        CounselorSession counselorSession = new CounselorSession(
                0,
                counselorSessionSaveDTO.getDate(),
                counselorSessionSaveDTO.getDescription(),
                counselorSessionSaveDTO.getCounselor(),
                counselorSessionSaveDTO.getStudent()
        );

        CounselorSession updatedCounselorSession = new CounselorSession(
                0,
                "2021-09-09",
                "Updated Description",
                "Updated Counselor",
                "Updated Student"
        );

        //Mocking the behavior of the dependencies
        when(counselorSessionRepo.findById(0))
                .thenReturn(java.util.Optional.of(counselorSession));
        when(modelMapper.map(counselorSessionSaveDTO, CounselorSession.class))
                .thenReturn(counselorSession);
        when(counselorSessionRepo.save(counselorSession))
                .thenReturn(updatedCounselorSession);

        //Act
        String result = counselorSessionServiceIMPL
                .updateCounselorSession(counselorSessionSaveDTO, 0);

        //Assert
        assertEquals("Counselor Session with Id: "+counselorSession.getSessionId()+" Updated", result);
        verify(modelMapper, times(1))
                .map(counselorSessionSaveDTO, CounselorSession.class);
        verify(counselorSessionRepo, times(1))
                .findById(0);
        verify(counselorSessionRepo, times(1))
                .save(counselorSession);
        }

    @Test
    public void updateSession_WhenSessionDoesNotExist_ThrowNotFoundException() {
        //Arrange
        int sessionId = 0;
        CounselorSessionSaveDTO counselorSessionSaveDTO = Mockito.mock(CounselorSessionSaveDTO.class);

        //Mocking the behavior of the dependencies
        when(counselorSessionRepo.findById(0))
                .thenReturn(java.util.Optional.empty());

        //Act
        //Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> counselorSessionServiceIMPL.updateCounselorSession(counselorSessionSaveDTO, 0)
        );

        assertEquals("Counselor Session not found with ID: "+sessionId, exception.getMessage());

        verify(modelMapper, times(0))
                .map(any(), any());
        verify(counselorSessionRepo, times(1))
                .findById(0);
        verify(counselorSessionRepo, times(0))
                .save(any());
    }

    @Test
    public void deleteSession_WhenSessionExists_ReturnString() {
        //Arrange
        int sessionId = 0;
        CounselorSession counselorSession = mock(CounselorSession.class);

        //Mocking the behavior of the dependencies
        when(counselorSessionRepo.findById(sessionId))
                .thenReturn(java.util.Optional.of(counselorSession));
        doNothing()
                .when(counselorSessionRepo)
                .deleteById(sessionId);

        //Act
        String result = counselorSessionServiceIMPL.deleteCounselorSession(sessionId);

        //Assert
        assertEquals("Counselor Session with Id: " + sessionId + " Deleted", result);
        verify(counselorSessionRepo, times(1))
                .findById(0);
        verify(counselorSessionRepo, times(1))
                .deleteById(0);
    }

    @Test
    public void deleteSession_WhenSessionDoesNotExist_ThrowNotFoundException() {
        //Arrange
        int sessionId = 0;

        //Mocking the behavior of the dependencies
        when(counselorSessionRepo.findById(sessionId))
                .thenReturn(java.util.Optional.empty());

        //Act
        //Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> counselorSessionServiceIMPL.deleteCounselorSession(sessionId)
        );

        assertEquals("Counselor Session not found with ID: " + sessionId, exception.getMessage());

        verify(counselorSessionRepo, times(1))
                .findById(0);
        verify(counselorSessionRepo, times(0))
                .deleteById(0);

    }

}