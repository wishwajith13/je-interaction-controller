package com.jeewaeducation.interaction_controller.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
    private String date;
    @Column(name="description")
    private String description;
    @Column(name="counselor")
    private String counselor;
    @Column(name="student")
    private String student;
}
