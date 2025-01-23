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
    private String date;
    private String counselor;
    private String student;
}
