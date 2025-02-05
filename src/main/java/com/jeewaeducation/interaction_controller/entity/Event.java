package com.jeewaeducation.interaction_controller.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String time;
}
