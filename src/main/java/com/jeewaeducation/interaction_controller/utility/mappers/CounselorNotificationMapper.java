package com.jeewaeducation.interaction_controller.utility.mappers;

import com.jeewaeducation.interaction_controller.dto.counselorNotification.CounselorNotificationDTO;
import com.jeewaeducation.interaction_controller.entity.CounselorNotification;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CounselorNotificationMapper {
    List<CounselorNotificationDTO> entityListToDtoList(List<CounselorNotification> counselorNotifications);
}
