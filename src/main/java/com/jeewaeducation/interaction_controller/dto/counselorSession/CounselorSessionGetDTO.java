package com.jeewaeducation.interaction_controller.dto.counselorSession;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounselorSessionGetDTO {
    private int sessionId;
    private String date;
    private String description;
    private String counselor;
    private String student;
}
