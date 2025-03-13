package com.jeewaeducation.interaction_controller.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;


@Entity
@Table(name="counselor_session")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CounselorSession extends BaseEntity{
    @Id
    @Column(name="session_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int sessionId;
    @Column(name="description")
    private String description;
    @Column(name="counselor_id")
    private int counselorId;
    @Column(name="student_id")
    private int studentId;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
}
