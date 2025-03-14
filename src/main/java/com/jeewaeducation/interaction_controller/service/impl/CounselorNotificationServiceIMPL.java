package com.jeewaeducation.interaction_controller.service.impl;

import com.jeewaeducation.interaction_controller.dto.counselorNotification.CounselorNotificationDTO;
import com.jeewaeducation.interaction_controller.dto.counselorNotification.CounselorNotificationSaveDTO;
import com.jeewaeducation.interaction_controller.entity.CounselorNotification;
import com.jeewaeducation.interaction_controller.exception.NotFoundException;
import com.jeewaeducation.interaction_controller.repo.CounselorNotificationRepo;
import com.jeewaeducation.interaction_controller.service.CounselorNotificationService;
import com.jeewaeducation.interaction_controller.utility.mappers.CounselorNotificationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CounselorNotificationServiceIMPL implements CounselorNotificationService {

    private CounselorNotificationRepo counselorNotificationRepo;
//    private ModelMapper modelMapper;
    private CounselorNotificationMapper counselorNotificationMapper;

    @Override
    public  String saveCounselorNotification(CounselorNotificationSaveDTO counselorNotificationSaveDTO) {
        if(counselorNotificationRepo.existsByCounselorIdAndStudentIdAndSeenFalse(counselorNotificationSaveDTO.getCounselorId(), counselorNotificationSaveDTO.getStudentId())) {
            return "Counselor Notification already exists";
        }

        CounselorNotification counselorNotification = new  CounselorNotification();
        counselorNotification.setCounselorId(counselorNotificationSaveDTO.getCounselorId());
        counselorNotification.setStudentId(counselorNotificationSaveDTO.getStudentId());
        counselorNotification.setCreatedAt(LocalDateTime.now());
        counselorNotification.setSeen(false);
        counselorNotificationRepo.findById(counselorNotification.getId()).ifPresent(e -> {
            throw new NotFoundException("Counselor Notification already exists");
        });
        counselorNotificationRepo.save(counselorNotification);
        return "Counselor Notification with Id: " + counselorNotification.getId() + " Saved";
    }


    @Override
    public List<CounselorNotificationDTO> getUnseenCounselorNotificationsById(int counselorId) {
        List<CounselorNotification> counselorNotification = counselorNotificationRepo.findByCounselorIdAndSeenFalse(counselorId);
        if (counselorNotification.isEmpty()) {
            throw new NotFoundException("No notifications found for counselor with ID: " + counselorId);
        }
        return counselorNotificationMapper.entityListToDtoList(counselorNotification);
    }

    @Override
    public void markNotificationAsSeen(int id) {
        CounselorNotification notification = counselorNotificationRepo.findById(id).orElse(null);
        if (notification != null) {
            notification.setSeen(true);
            counselorNotificationRepo.save(notification);
        }
    }
}
