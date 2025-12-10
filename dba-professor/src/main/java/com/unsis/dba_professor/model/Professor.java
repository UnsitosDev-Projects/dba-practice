package com.unsis.dba_professor.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Professor implements Serializable {
    private String id;
    private String name;
    private String department;
    private String email;
}
