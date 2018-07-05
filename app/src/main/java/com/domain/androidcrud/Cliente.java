package com.domain.androidcrud;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cliente {
    private Integer id;
    private String nome;
    private String sexo;
    private String uf;
    private boolean vip;
}
