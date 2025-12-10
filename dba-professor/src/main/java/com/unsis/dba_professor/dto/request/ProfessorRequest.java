package com.unsis.dba_professor.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ProfessorRequest {
    private String name;
    private String department;
    private String email;
}
