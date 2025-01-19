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
@RequestMapping("api/v1/counselor_session")
@CrossOrigin
public class CounselorSessionController {

    @Autowired
    private CounselorSessionService counselorSessionService;

    @PostMapping(
            path = {"/save"}
    )
    private ResponseEntity<StandardResponse> saveCounselorSession(@RequestBody CounselorSessionSaveDTO counselorSessionSaveDTO){
        String message = counselorSessionService.saveCounselorSession(counselorSessionSaveDTO);
        return new ResponseEntity<StandardResponse>(new StandardResponse(201,"Success",message), HttpStatus.CREATED);
    }

    @PostMapping(
            path = {"/update/{id}"}
    )
    private ResponseEntity<StandardResponse> updateCounselorSession(@RequestBody CounselorSessionSaveDTO counselorSessionSaveDTO, @PathVariable int id){
        String message = counselorSessionService.updateCounselorSession(counselorSessionSaveDTO, id);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200,"Success",message), HttpStatus.OK);
    }

    @PostMapping(
            path = {"/delete/{id}"}
    )
    private ResponseEntity<StandardResponse> deleteCounselorSession(@PathVariable int id){
        String message = counselorSessionService.deleteCounselorSession(id);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200,"Success",message), HttpStatus.OK);
    }

    @PostMapping(
            path = {"/getall"}
    )
    private ResponseEntity<StandardResponse> getAllCounselorSessions(){
        List<CounselorSessionGetDTO> message = counselorSessionService.getAllCounselorSessions();
        return new ResponseEntity<StandardResponse>(new StandardResponse(200,"Success",message), HttpStatus.OK);
    }

    @PostMapping(
            path = {"/get/{id}"}
    )
    private ResponseEntity<StandardResponse> getCounselorSession(@PathVariable int id){
        CounselorSessionGetDTO message = counselorSessionService.getCounselorSession(id);
        return new ResponseEntity<StandardResponse>(new StandardResponse(200,"Success",message), HttpStatus.OK);
    }
}
