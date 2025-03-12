package com.jeewaeducation.interaction_controller.service;

import com.jeewaeducation.interaction_controller.dto.counselorNotification.CounselorNotificationDTO;
import com.jeewaeducation.interaction_controller.dto.counselorNotification.CounselorNotificationSaveDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CounselorNotificationService {

    String saveCounselorNotification(CounselorNotificationSaveDTO counselorNotificationSaveDTO);

    List<CounselorNotificationDTO> getUnseenCounselorNotificationsById(int id);

    void markNotificationAsSeen(int id);
}
