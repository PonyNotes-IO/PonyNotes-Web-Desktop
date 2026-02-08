package com.ruoyi.xmbj.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.xmbj.domain.XmAfUser;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class XmAfUserDTO extends XmAfUser {
    private static final long serialVersionUID = 1L;

    private Integer orderNum;

    private String productName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date productEndDate;
}
