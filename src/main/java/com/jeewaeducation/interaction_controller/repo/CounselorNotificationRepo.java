package com.jeewaeducation.interaction_controller.repo;

import com.jeewaeducation.interaction_controller.entity.CounselorNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface CounselorNotificationRepo extends JpaRepository<CounselorNotification,Integer> {

    List<CounselorNotification> findByCounselorIdAndSeenFalse(int counselorId);
}
