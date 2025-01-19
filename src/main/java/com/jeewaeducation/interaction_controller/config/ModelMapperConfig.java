package com.jeewaeducation.interaction_controller.config;

import com.jeewaeducation.interaction_controller.dto.counselorSession.CounselorSessionSaveDTO;
import com.jeewaeducation.interaction_controller.entity.CounselorSession;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig
{
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<CounselorSessionSaveDTO, CounselorSession>() {
            @Override
            protected void configure() {
                map().setCounselorId(source.getCounselorId());
                map().setStudentId(source.getStudentId());
                // Add other mappings as needed
            }
        });
        return modelMapper;
    }
}
