package com.acm.casemanagement.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@XmlRootElement
public class ErrorResponse implements Serializable {
    @XmlElement
    private int status;
    @XmlElement
    private String message;
    @XmlElement
    private long timestamp;


}


