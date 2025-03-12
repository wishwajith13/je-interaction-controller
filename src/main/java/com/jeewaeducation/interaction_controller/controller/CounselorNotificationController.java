package com.jeewaeducation.interaction_controller.controller;

import com.jeewaeducation.interaction_controller.dto.counselorNotification.CounselorNotificationDTO;
import com.jeewaeducation.interaction_controller.entity.CounselorNotification;
import com.jeewaeducation.interaction_controller.repo.CounselorNotificationRepo;
import com.jeewaeducation.interaction_controller.service.CounselorNotificationService;
import com.jeewaeducation.interaction_controller.utility.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/counselor-notification")
public class CounselorNotificationController {
    @Autowired
    private CounselorNotificationService counselorNotificationService;
    private final CounselorNotificationRepo counselorNotificationRepo;

    public CounselorNotificationController(CounselorNotificationRepo counselorNotificationRepo) {
        this.counselorNotificationRepo = counselorNotificationRepo;
    }

    @GetMapping("/unseen/{id}")
    private ResponseEntity<StandardResponse> getUnseenCounselorNotifications(@PathVariable int id) {
        List<CounselorNotificationDTO> message = counselorNotificationService.getUnseenCounselorNotificationsById(id);
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @PostMapping("/mark-seen/{id}")
    public void markNotificationAsSeen(@PathVariable int id) {
        CounselorNotification notification = counselorNotificationRepo.findById(id).orElse(null);
        if (notification != null) {
            notification.setSeen(true);
            counselorNotificationRepo.save(notification);
        }
    }
}
