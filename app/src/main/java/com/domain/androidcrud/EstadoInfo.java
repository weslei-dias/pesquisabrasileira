package com.domain.androidcrud;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstadoInfo {
    private String area_km2;
    private String codigo_ibge;
    private String nome;
}
