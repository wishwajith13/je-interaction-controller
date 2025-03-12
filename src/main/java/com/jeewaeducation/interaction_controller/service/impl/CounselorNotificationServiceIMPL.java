package com.jeewaeducation.interaction_controller.service.impl;

import com.jeewaeducation.interaction_controller.dto.counselorNotification.CounselorNotificationDTO;
import com.jeewaeducation.interaction_controller.dto.counselorNotification.CounselorNotificationSaveDTO;
import com.jeewaeducation.interaction_controller.entity.CounselorNotification;
import com.jeewaeducation.interaction_controller.exception.NotFoundException;
import com.jeewaeducation.interaction_controller.repo.CounselorNotificationRepo;
import com.jeewaeducation.interaction_controller.service.CounselorNotificationService;
import com.jeewaeducation.interaction_controller.utility.mappers.CounselorNotificationMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CounselorNotificationServiceIMPL implements CounselorNotificationService {
    private CounselorNotificationRepo counselorNotificationRepo;
    private ModelMapper modelMapper;
    private CounselorNotificationMapper counselorNotificationMapper;

    @Override
    public  String saveCounselorNotification(CounselorNotificationSaveDTO counselorNotificationSaveDTO) {
        CounselorNotification counselorNotification = new  CounselorNotification();
        counselorNotification.setCounselorId(counselorNotificationSaveDTO.getCounselorId());
        counselorNotification.setMessage(counselorNotificationSaveDTO.getMessage());
        counselorNotification.setStudentId(counselorNotificationSaveDTO.getStudentId());
        counselorNotification.setCreatedAt(LocalDateTime.now());
        counselorNotification.setSeen(false);
//        counselorNotification.setId(0);
        counselorNotificationRepo.findById(counselorNotification.getId()).ifPresent(e -> {
            throw new NotFoundException("Counselor Notification already exists");
        });
        counselorNotificationRepo.save(counselorNotification);
        return "Counselor Notification with Id: " + counselorNotification.getId() + " Saved";
    }


    @Override
    public List<CounselorNotificationDTO> getUnseenCounselorNotificationsById(int counselorId) {
        List<CounselorNotification> counselorNotification = counselorNotificationRepo.findByCounselorIdAndSeenFalse(counselorId);
//        List<CounselorNotification> counselorNotification = counselorNotificationRepo.findByCounselorId(counselorId);
        if (counselorNotification.isEmpty()) {
            throw new NotFoundException("No notifications found for counselor with ID: " + counselorId);
        }
        return counselorNotificationMapper.entityListToDtoList(counselorNotification);
    }
}
