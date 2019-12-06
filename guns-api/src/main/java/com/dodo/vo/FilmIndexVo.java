package com.dodo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmIndexVo implements Serializable {
    private static final long serialVersionUID = 258322508971252897L;
    private Object data;
    private String imgPre;
    private String msg;
    private String nowPage;
    private Integer status;
    private String totalPage;
}
