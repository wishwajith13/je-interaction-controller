package com.jeewaeducation.interaction_controller.repo;

import com.jeewaeducation.interaction_controller.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


@Repository
@EnableJpaRepositories
public interface EventRepo extends JpaRepository<Event,Integer> {

}
