package com.domain.androidcrud.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pesquisa {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("dataPesquisa")
    @Expose
    private String dataPesquisa;
    @SerializedName("nomeEntrevistador")
    @Expose
    private String nomeEntrevistador;
    @SerializedName("unidadeEntrevista")
    @Expose
    private String unidadeEntrevista;
    @SerializedName("nomeEntrevistado")
    @Expose
    private String nomeEntrevistado;
    @SerializedName("sexo")
    @Expose
    private String sexo;
    @SerializedName("cidade")
    @Expose
    private String cidade;
    @SerializedName("cep")
    @Expose
    private String cep;
    @SerializedName("bairro")
    @Expose
    private String bairro;
    @SerializedName("numero")
    @Expose
    private String numero;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("rua")
    @Expose
    private String rua;
    @SerializedName("complemento")
    @Expose
    private String complemento;
    @SerializedName("telefone")
    @Expose
    private String telefone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("idade")
    @Expose
    private String idade;
    @SerializedName("localPesquisa")
    @Expose
    private String localPesquisa;
    @SerializedName("ocupacao")
    @Expose
    private String ocupacao;
    @SerializedName("escolaridade")
    @Expose
    private String escolaridade;
    @SerializedName("areaGraduacao")
    @Expose
    private String areaGraduacao;
    @SerializedName("opcaoPos")
    @Expose
    private String opcaoPos;
    @SerializedName("qualPos")
    @Expose
    private String qualPos;
    @SerializedName("pretencaoInicioPos")
    @Expose
    private String pretencaoInicioPos;
    @SerializedName("paticiparSorteio")
    @Expose
    private String paticiparSorteio;
    @SerializedName("inicioPos")
    @Expose
    private String inicioPos;
    @SerializedName("outroLocal")
    @Expose
    private String outroLocal;
    @SerializedName("outraArea")
    @Expose
    private String outraArea;
    @SerializedName("tempoConclusaoGraduacao")
    @Expose
    private String tempoConclusaoGraduacao;

    @SerializedName("desejaGraduacao")
    @Expose
    private String desejaGraduacao ;
    @SerializedName("inicioPrimeiraGraduacao")
    @Expose
    private String inicioPrimeiraGraduacao;
    @SerializedName("inicioSegundaGraduacao")
    @Expose
    private String inicioSegundaGraduacao;

    private Boolean gerouLead;

    private String idLead;

    @SerializedName("statusLead")
    @Expose
    private String statusLead;

    @SerializedName("unidade")
    @Expose
    private String unidade;

    @SerializedName("idWeb")
    @Expose
    private Integer idWeb;


    public Pesquisa()
    {

    }
}
