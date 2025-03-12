package com.jeewaeducation.interaction_controller.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class CounselorNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "counselor_id" ,nullable = false)
    private int counselorId;
    @Column(name = "student_id" ,nullable = false)
    private int studentId;
    @Column(name = "seen" ,nullable = false)
    private boolean seen;
    @Column(name = "created_at" ,nullable = false)
    private LocalDateTime createdAt;

}
