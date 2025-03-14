package com.jeewaeducation.interaction_controller.dto.counselorSession;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounselorSessionSaveDTO {
    private String description;
    private int counselorId;
    private int studentId;
    private Date startTime;
    private Date endTime;
}
