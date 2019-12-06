package com.dodo.user.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserParams implements Serializable {
    private static final long serialVersionUID = 25832250897125289L;
    private Integer id;
    private String username;
    private String password;
    private String mobile;
    private String email;
    private String address;
}
