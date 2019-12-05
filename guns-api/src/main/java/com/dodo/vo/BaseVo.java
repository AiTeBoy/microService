package com.dodo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseVo implements Serializable {
    private static final long serialVersionUID = 2583225089712528974L;
    private Integer status;
    private Object data;
    private String msg;

    public BaseVo() {
    }

    public BaseVo(Integer status, Object data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }
}
