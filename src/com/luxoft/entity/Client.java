package com.luxoft.entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class Client {
    private String name;
    private String email;
    private String password;
}
