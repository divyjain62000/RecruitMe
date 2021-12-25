package com.recruitme.service.mail.dto;

import com.recruitme.service.dto.DataTransferObject;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class MailDTO extends DataTransferObject {

    private List<String> to;
    private String subject;
    private String body;
    Map<String,Object> emailMap;
}
