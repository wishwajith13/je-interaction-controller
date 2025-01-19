package com.jeewaeducation.interaction_controller.dto.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventGetDto {
    private int id;
    private String title;
    private String date;
    private String time;
}
