package com.domain.androidcrud;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Endereco {
    public static final int REQUEST_ZIP_CODE_CODE = 566;
    public static final String ZIP_CODE_KEY = "zip_code_key";
    private String bairro;
    private String cep;
    private String logradouro;
    private String localidade;
    private String uf;
    private String complemento;
    private String unidade;
    private String ibge;
    private String gia;
}
