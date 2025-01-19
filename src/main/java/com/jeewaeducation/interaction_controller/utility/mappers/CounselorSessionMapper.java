package com.jeewaeducation.interaction_controller.utility.mappers;

import com.jeewaeducation.interaction_controller.dto.counselorSession.CounselorSessionGetDTO;
import com.jeewaeducation.interaction_controller.entity.CounselorSession;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CounselorSessionMapper {
    List<CounselorSessionGetDTO> entityListToDtoList(List<CounselorSession> counselorSessions);

}
