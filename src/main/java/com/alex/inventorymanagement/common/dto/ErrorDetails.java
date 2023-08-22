package com.alex.inventorymanagement.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {

    private Date timeStamp;
    private String message;
    private String details;

}
