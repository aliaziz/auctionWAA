package com.teams_mars.admin_module.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//(Data Transfer Object)
@AllArgsConstructor
@Setter
@Getter
@ToString
public class FieldErroDTO {
    private String field;
    private String message;

}
