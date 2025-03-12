package com.jeewaeducation.interaction_controller.service;

import com.jeewaeducation.interaction_controller.dto.counselorNotification.CounselorNotificationDTO;
import com.jeewaeducation.interaction_controller.dto.counselorNotification.CounselorNotificationSaveDTO;

import java.util.List;

public interface CounselorNotificationService {

    String saveCounselorNotification(CounselorNotificationSaveDTO counselorNotificationSaveDTO);

    List<CounselorNotificationDTO> getUnseenCounselorNotificationsById(int id);
}
