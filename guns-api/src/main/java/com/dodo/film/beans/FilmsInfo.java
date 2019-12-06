package com.dodo.film.beans;


import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class FilmsInfo implements Serializable {
    private static final long serialVersionUID = 258322589712528974L;
    private List<Film> filmInfo;
    private Integer filmNum;
    private String nowPage;
    private String Page;

    public FilmsInfo() {
    }

    public FilmsInfo(List<Film> filmInfo, Integer filmNum, String nowPage, String page) {
        this.filmInfo = filmInfo;
        this.filmNum = filmNum;
        this.nowPage = nowPage;
        Page = page;
    }


}
