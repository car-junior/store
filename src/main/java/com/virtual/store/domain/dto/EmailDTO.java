package com.virtual.store.domain.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class EmailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Email Obrigatório")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
