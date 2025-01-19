package com.jeewaeducation.interaction_controller.service.impl;

import com.jeewaeducation.interaction_controller.dto.counselorSession.CounselorSessionGetDTO;
import com.jeewaeducation.interaction_controller.dto.counselorSession.CounselorSessionSaveDTO;
import com.jeewaeducation.interaction_controller.entity.CounselorSession;
import com.jeewaeducation.interaction_controller.exception.DuplicateKeyException;
import com.jeewaeducation.interaction_controller.exception.NotFoundException;
import com.jeewaeducation.interaction_controller.repo.CounselorSessionRepo;
import com.jeewaeducation.interaction_controller.service.CounselorSessionService;
import com.jeewaeducation.interaction_controller.utility.mappers.CounselorSessionMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CounselorSessionServiceIMPL implements CounselorSessionService {

    @Autowired
    private CounselorSessionRepo counselorSessionRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CounselorSessionMapper counselorSessionMapper;


    @Override
    public String saveCounselorSession(CounselorSessionSaveDTO counselorSessionSaveDTO) {
        CounselorSession counselorSession = new CounselorSession();
        counselorSession.setSessionId(0);
        counselorSession.setDate(counselorSessionSaveDTO.getDate());
        counselorSession.setCounselorId(counselorSessionSaveDTO.getCounselorId());
        counselorSession.setStudentId(counselorSessionSaveDTO.getStudentId());
//        CounselorSession counselorSession = modelMapper.map(counselorSessionSaveDTO, CounselorSession.class);
//        counselorSession.setCounselorId(0);
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
//        counselorSession.setSessionId(sessionId);
        counselorSessionRepo.save(counselorSession);
        return "Counselor Session with Id: " + counselorSession.getSessionId() + " Updated";
    }

    @Override
    public String deleteCounselorSession(int sessionId) {
        counselorSessionRepo.findById(sessionId).orElseThrow(() -> new NotFoundException("Counselor Session not found with ID: " + sessionId));
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
//        return counselorSessions.stream().map(counselorSession -> modelMapper.map(counselorSession, CounselorSessionGetDTO.class)).toList();
    }
}
