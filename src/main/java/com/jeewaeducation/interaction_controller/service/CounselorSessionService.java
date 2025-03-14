package com.jeewaeducation.interaction_controller.service;

import com.jeewaeducation.interaction_controller.dto.counselorSession.CounselorSessionGetDTO;
import com.jeewaeducation.interaction_controller.dto.counselorSession.CounselorSessionSaveDTO;

import java.util.List;

public interface CounselorSessionService {

    String saveCounselorSession(CounselorSessionSaveDTO counselorSessionSaveDTO);

    String updateCounselorSession(CounselorSessionSaveDTO counselorSessionSaveDTO, int sessionId);

    String deleteCounselorSession(int sessionId);

    CounselorSessionGetDTO getCounselorSession(int sessionId);

    List<CounselorSessionGetDTO> getCounselorSessionsByCounselor(int counselorId);

    List<CounselorSessionGetDTO> getCounselorSessionsByCounselorAndStudent(int counselorId, int studentId);

    List<CounselorSessionGetDTO> getAllCounselorSessions();
}
