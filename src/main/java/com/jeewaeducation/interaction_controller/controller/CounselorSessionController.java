package com.jeewaeducation.interaction_controller.controller;

import com.jeewaeducation.interaction_controller.dto.counselorSession.CounselorSessionGetDTO;
import com.jeewaeducation.interaction_controller.dto.counselorSession.CounselorSessionSaveDTO;
import com.jeewaeducation.interaction_controller.service.CounselorSessionService;
import com.jeewaeducation.interaction_controller.utility.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/counselor-session")
public class CounselorSessionController {

    @Autowired
    private CounselorSessionService counselorSessionService;

    @PostMapping
    private ResponseEntity<StandardResponse> saveCounselorSession(@RequestBody CounselorSessionSaveDTO counselorSessionSaveDTO) {
        String message = counselorSessionService.saveCounselorSession(counselorSessionSaveDTO);
        return new ResponseEntity<>(new StandardResponse(201, "Success", message), HttpStatus.CREATED);
    }

    @PutMapping(
            path = {"/{id}"}
    )
    private ResponseEntity<StandardResponse> updateCounselorSession(@RequestBody CounselorSessionSaveDTO counselorSessionSaveDTO, @PathVariable int id) {
        String message = counselorSessionService.updateCounselorSession(counselorSessionSaveDTO, id);
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @DeleteMapping(
            path = {"/{id}"}
    )
    private ResponseEntity<StandardResponse> deleteCounselorSession(@PathVariable int id) {
        String message = counselorSessionService.deleteCounselorSession(id);
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<StandardResponse> getAllCounselorSessions() {
        List<CounselorSessionGetDTO> message = counselorSessionService.getAllCounselorSessions();
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/{id}"}
    )
    private ResponseEntity<StandardResponse> getCounselorSession(@PathVariable int id) {
        CounselorSessionGetDTO message = counselorSessionService.getCounselorSession(id);
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/counselor/{counselorId}"}
    )
    private ResponseEntity<StandardResponse> getCounselorSessionsByCounselor(@PathVariable int counselorId) {
        List<CounselorSessionGetDTO> message = counselorSessionService.getCounselorSessionsByCounselor(counselorId);
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }

    @GetMapping(
            path = {"/counselor/{counselorId}/student/{studentId}"}
    )
    private ResponseEntity<StandardResponse> getCounselorSessionsByCounselorAndStudent(@PathVariable int counselorId, @PathVariable int studentId) {
        List<CounselorSessionGetDTO> message = counselorSessionService.getCounselorSessionsByCounselorAndStudent(counselorId, studentId);
        return new ResponseEntity<>(new StandardResponse(200, "Success", message), HttpStatus.OK);
    }
}
