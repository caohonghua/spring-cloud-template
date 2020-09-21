package org.caohh.server.web.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String username;
    private String password;
    private String img = "/images/61.gif";

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
