package com.jeewaeducation.interaction_controller.service.impl;

import com.jeewaeducation.interaction_controller.dto.counselorSession.CounselorSessionSaveDTO;
import com.jeewaeducation.interaction_controller.entity.CounselorSession;
import com.jeewaeducation.interaction_controller.repo.CounselorSessionRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class CounselorSessionServiceIMPLTest {

    @Mock
    private CounselorSessionRepo counselorSessionRepo;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private CounselorSessionServiceIMPL counselorSessionServiceIMPL;

    @Test
    void saveCounselorSession() {
        CounselorSession counselorSession = new CounselorSession();
        counselorSession.setSessionId(1);
        counselorSession.setDate("2025-01-12");
        counselorSession.setDescription("Software Engineers");

        Mockito.when(modelMapper.map(Mockito.any(CounselorSessionSaveDTO.class), Mockito.eq(CounselorSession.class))).thenReturn(counselorSession);
        Mockito.when(counselorSessionRepo.save(Mockito.any(CounselorSession.class))).thenReturn(counselorSession);

        CounselorSessionSaveDTO counselorSessionSaveDTO = new CounselorSessionSaveDTO();
        counselorSessionSaveDTO.setDate("2025-01-12");
        counselorSessionSaveDTO.setDescription("Software Engineers");

        String result = counselorSessionServiceIMPL.saveCounselorSession(counselorSessionSaveDTO);
        Assertions.assertEquals("Counselor Session with Id: "+ 1 + " Saved", result);
        System.out.println("First test passed");
    }

    @Test
    void updateCounselorSession() {
    }

    @Test
    void deleteCounselorSession() {
    }

    @Test
    void getCounselorSession() {
    }

    @Test
    void getAllCounselorSessions() {
    }
}