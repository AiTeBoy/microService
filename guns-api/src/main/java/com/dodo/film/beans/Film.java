package com.dodo.film.beans;

import lombok.Data;

import java.io.Serializable;

@Data
public class Film implements Serializable {
    private static final long serialVersionUID = 25832258972528974L;
    private Integer boxNum;
    private Integer expectNum;
    private String filmCats;
    private String filmId;
    private String filmLength;
    private String filmName;
    private String filmScore;
    private Integer filmType;
    private String imgAddress;
    private String score;
    private String showTime;

    public Film() {
    }

    public Film(Integer boxNum, Integer expectNum, String filmCats, String filmId, String filmLength, String filmName, String filmScore, Integer filmType, String imgAddress, String score, String showTime) {
        this.boxNum = boxNum;
        this.expectNum = expectNum;
        this.filmCats = filmCats;
        this.filmId = filmId;
        this.filmLength = filmLength;
        this.filmName = filmName;
        this.filmScore = filmScore;
        this.filmType = filmType;
        this.imgAddress = imgAddress;
        this.score = score;
        this.showTime = showTime;
    }
}
