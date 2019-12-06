package com.dodo.film.beans;

import lombok.Data;

import java.io.Serializable;

@Data
public class Banner implements Serializable {
    private static final long serialVersionUID = 258225089712528974L;
    private String bannerAddress;
    private String bannerId;
    private String bannerUrl;

    public Banner() {
    }

    public Banner(String bannerAddress, String bannerId, String bannerUrl) {
        this.bannerAddress = bannerAddress;
        this.bannerId = bannerId;
        this.bannerUrl = bannerUrl;
    }
}
