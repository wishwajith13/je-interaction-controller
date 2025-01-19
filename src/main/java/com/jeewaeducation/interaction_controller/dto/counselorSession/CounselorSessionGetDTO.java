package com.jeewaeducation.interaction_controller.dto.counselorSession;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounselorSessionGetDTO {
    private int sessionId;
    private String date;
    private String description;
    private int counselorId;
    private int studentId;
}
