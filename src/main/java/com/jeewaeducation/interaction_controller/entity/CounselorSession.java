package com.jeewaeducation.interaction_controller.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="counselor_session")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CounselorSession {
    @Id
    @Column(name="session_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int sessionId;
    @Column(name="date")
    private Date date;
    @Column(name="description")
    private String description;
    @Column(name="counselor_id")
    private String counselorId;
    @Column(name="student_id")
    private String studentId;
}
