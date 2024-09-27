package com.healthmed.domain;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Patient {

    private Long id;
    private String name;
    private String cpf;
    private String email;

    public Patient() {
    }

    public Patient(Long id, String name, String cpf, String email) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
    }

}
