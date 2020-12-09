package com.teams_mars.admin_module.dto;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDTO {
    private String errorType;

    public ValidationErrorDTO(String errorType) {
        this.errorType = errorType;
    }

    private List<FieldErroDTO> fieldErroDTOList = new ArrayList<>();

    public void addFieldError(String field, String message){
        FieldErroDTO error = new FieldErroDTO(field,message);
        fieldErroDTOList.add(error);
    }
}
