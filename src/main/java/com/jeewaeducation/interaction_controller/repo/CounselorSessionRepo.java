package com.jeewaeducation.interaction_controller.repo;

import com.jeewaeducation.interaction_controller.entity.CounselorSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface CounselorSessionRepo extends JpaRepository<CounselorSession, Integer> {

    List<CounselorSession> findByCounselorId(int counselorId);

    List<CounselorSession> findByCounselorIdAndSessionId(int counselorId, int sessionId);

    List<CounselorSession> findByCounselorIdAndStudentId(int counselorId, int studentId);

    List<CounselorSession> findByStudentId(int studentId);
}
