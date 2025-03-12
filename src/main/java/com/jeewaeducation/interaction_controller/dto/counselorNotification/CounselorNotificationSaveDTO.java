package com.jeewaeducation.interaction_controller.dto.counselorNotification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounselorNotificationSaveDTO {
    private int counselorId;
    private int studentId;
    private String message;
}
