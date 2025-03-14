package com.jeewaeducation.interaction_controller.service.impl;

import com.jeewaeducation.interaction_controller.dto.counselorSession.CounselorSessionGetDTO;
import com.jeewaeducation.interaction_controller.dto.counselorSession.CounselorSessionSaveDTO;
import com.jeewaeducation.interaction_controller.entity.CounselorSession;
import com.jeewaeducation.interaction_controller.exception.DuplicateKeyException;
import com.jeewaeducation.interaction_controller.exception.NotFoundException;
import com.jeewaeducation.interaction_controller.repo.CounselorSessionRepo;
import com.jeewaeducation.interaction_controller.service.CounselorSessionService;
import com.jeewaeducation.interaction_controller.utility.mappers.CounselorSessionMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CounselorSessionServiceIMPL implements CounselorSessionService {

    private CounselorSessionRepo counselorSessionRepo;
    private ModelMapper modelMapper;
    private CounselorSessionMapper counselorSessionMapper;


    @Override
    public String saveCounselorSession(CounselorSessionSaveDTO counselorSessionSaveDTO) {
        CounselorSession counselorSession = new CounselorSession();
        counselorSession.setDescription(counselorSessionSaveDTO.getDescription());
        counselorSession.setCounselorId(counselorSessionSaveDTO.getCounselorId());
        counselorSession.setStudentId(counselorSessionSaveDTO.getStudentId());
        counselorSession.setStartTime(counselorSessionSaveDTO.getStartTime());
        counselorSession.setEndTime(counselorSessionSaveDTO.getEndTime());
        counselorSessionRepo.findById(counselorSession.getSessionId()).ifPresent(e -> {
            throw new DuplicateKeyException("Counselor Session already exists");
        });
        counselorSessionRepo.save(counselorSession);
        return "Counselor Session with Id: " + counselorSession.getSessionId() + " Saved";
    }

    @Override
    public String updateCounselorSession(CounselorSessionSaveDTO counselorSessionSaveDTO, int sessionId) {
        counselorSessionRepo.findById(sessionId).orElseThrow(() -> new NotFoundException("Counselor Session not found with ID: " + sessionId));
        CounselorSession counselorSession = modelMapper.map(counselorSessionSaveDTO, CounselorSession.class);
        counselorSession.setSessionId(sessionId);
        counselorSessionRepo.save(counselorSession);
        return "Counselor Session with Id: " + counselorSession.getSessionId() + " Updated";
    }

    @Override
    public String deleteCounselorSession(int sessionId) {
        counselorSessionRepo.findById(sessionId).orElseThrow(
                () -> new NotFoundException("Counselor Session not found with ID: " + sessionId)
        );
        counselorSessionRepo.deleteById(sessionId);
        return "Counselor Session with Id: " + sessionId + " Deleted";
    }

    @Override
    public CounselorSessionGetDTO getCounselorSession(int sessionId) {
        CounselorSession counselorSession = counselorSessionRepo.findById(sessionId).
                orElseThrow(() -> new NotFoundException("Counselor Session not found with ID: " + sessionId));
        return modelMapper.map(counselorSession, CounselorSessionGetDTO.class);
    }

    @Override
    public List<CounselorSessionGetDTO> getAllCounselorSessions() {
        List<CounselorSession> counselorSessions = counselorSessionRepo.findAll();
        if (counselorSessions.isEmpty()) {
            throw new NotFoundException("No Counselor Sessions found");
        }
        return counselorSessionMapper.entityListToDtoList(counselorSessions);
    }

    @Override
    public List<CounselorSessionGetDTO> getCounselorSessionsByCounselor(int counselorId) {
        List<CounselorSession> counselorSessions = counselorSessionRepo.findByCounselorId(counselorId);
        if (counselorSessions.isEmpty()) {
            throw new NotFoundException("No Counselor Sessions found for Counselor ID: " + counselorId);
        }
        return counselorSessionMapper.entityListToDtoList(counselorSessions);
    }

    @Override
    public List<CounselorSessionGetDTO> getCounselorSessionsByCounselorAndStudent(int counselorId, int studentId) {
        List<CounselorSession> counselorSessions = counselorSessionRepo.findByCounselorIdAndStudentId(counselorId, studentId);
        if (counselorSessions.isEmpty()) {
            throw new NotFoundException("No Counselor Sessions found for Counselor ID: " + counselorId + " and Student ID: " + studentId);
        }
        return counselorSessionMapper.entityListToDtoList(counselorSessions);
    }
}
