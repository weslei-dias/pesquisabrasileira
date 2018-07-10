package com.domain.androidcrud;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
public class Cliente {
    private Integer id;
    private String dataPesquisa;
    private String nomeEntrevistador;
    private String nomeEntrevistado;
    private String sexo;
    private String cidade;
    private String cep;
    private String bairro;
    private String numero;
    private String estado;
    private String rua;
    private String complemento;
    private String telefone;
    private String email;
    private String idade;
    private String localPesquisa;
    private String ocupacao;
    private String escolaridade;
    private String areaGraduacao;
    private String opcaoPos;
    private String pretencaoInicioPos;
    private String paticiparSorteio;
    private String inicioPos;
    private String outroLocal;
    private String outraArea;

}
