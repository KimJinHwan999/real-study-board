package com.example.board.dto;

import java.security.PrivateKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RSA {
    private PrivateKey privateKey;
    private String modulus;
    private String exponent;

   

}
