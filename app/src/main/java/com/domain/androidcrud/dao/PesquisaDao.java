package com.domain.androidcrud.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.domain.androidcrud.db.DbGateway;
import com.domain.androidcrud.model.Pesquisa;

import java.util.ArrayList;
import java.util.List;

public class PesquisaDao {
    private final String PESQUISA = "pesquisa";
    private DbGateway gw;

    public PesquisaDao(Context ctx) {
        gw = DbGateway.getInstance(ctx);
    }

    public boolean excluir(int id) {
        return gw.getDb().delete(PESQUISA, "id=?", new String[]{id + ""}) > 0;
    }

    public boolean salvar(ContentValues cv) {
        return salvar(0, cv);
    }

    public boolean salvar(int id, ContentValues cv) {
        if (id > 0) {
            return gw.getDb().update(PESQUISA, cv, "id=?",
                    new String[]{id + ""}) > 0;
        } else {
            return gw.getDb().insert(PESQUISA, null, cv) > 0;
        }
    }

    public Pesquisa getPesquisaComMesmoEmail(String email, String nome)
    {
        Cursor cursor = gw.getDb().rawQuery("SELECT * FROM pesquisa where email='" + email +
                "' and nome_entrevistado='" + nome + "'",null);
        Pesquisa cliente = null;

        while (cursor.moveToNext()) {
            String isLead = cursor.getString(cursor.getColumnIndex("gerou_lead"));
            Boolean gerouLead = isLead != null && Integer.valueOf(isLead) == 1;
            String numeroLead = cursor.getString(cursor.getColumnIndex("id_lead"));

            cliente = new Pesquisa();
            if (gerouLead){
                cliente.setIdLead(numeroLead);
            }
        }
        cursor.close();

        return cliente;
    }

    public List<Pesquisa> getTodasPesquisas() {
        List<Pesquisa> pesquisas = new ArrayList<>();
        Cursor cursor = gw.getDb()
                .rawQuery("Select * from pesquisa order by data_pesquisa desc", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String dataPesquisa = cursor.getString(cursor.getColumnIndex("data_pesquisa"));
            String nomeEntrevistador = cursor.getString(cursor.getColumnIndex("nome_entrevistador"));
            String unidadeEntrevista = cursor.getString(cursor.getColumnIndex("unidade_entrevista"));
            String nomeEntrevistado = cursor.getString(cursor.getColumnIndex("nome_entrevistado"));
            String sexo = cursor.getString(cursor.getColumnIndex("sexo"));
            String cidade = cursor.getString(cursor.getColumnIndex("cidade"));
            String cep = cursor.getString(cursor.getColumnIndex("cep"));
            String bairro = cursor.getString(cursor.getColumnIndex("bairro"));
            String numero = cursor.getString(cursor.getColumnIndex("numero"));
            String estado = cursor.getString(cursor.getColumnIndex("estado"));
            String rua = cursor.getString(cursor.getColumnIndex("rua"));
            String complemento = cursor.getString(cursor.getColumnIndex("complemento"));
            String telefone = cursor.getString(cursor.getColumnIndex("telefone"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String idade = cursor.getString(cursor.getColumnIndex("idade"));
            String localPesquisa = cursor.getString(cursor.getColumnIndex("local_pesquisa"));
            String ocupacao = cursor.getString(cursor.getColumnIndex("ocupacao"));
            String escolaridade = cursor.getString(cursor.getColumnIndex("escolaridade"));
            String areaGraduacao = cursor.getString(cursor.getColumnIndex("area_graduacao"));
            String opcaoPos = cursor.getString(cursor.getColumnIndex("opcao_pos"));
            String qualPos = cursor.getString(cursor.getColumnIndex("qual_pos"));
            String pretencaoInicioPos = cursor.getString(cursor.getColumnIndex("pretencao_inicio_pos"));
            String paticiparSorteio = cursor.getString(cursor.getColumnIndex("paticipar_sorteio"));
            String inicioPos = cursor.getString(cursor.getColumnIndex("inicio_pos"));
            String outroLocal = cursor.getString(cursor.getColumnIndex("outro_local"));
            String outraArea = cursor.getString(cursor.getColumnIndex("outra_area"));
            String tempoConclusaoGraduacao = cursor.getString(cursor.getColumnIndex("tempo_conclusao_graduacao"));
            String desejaGraduacao = cursor.getString(cursor.getColumnIndex("deseja_graduacao"));
            String inicioPrimeiraGraduacao = cursor.getString(cursor.getColumnIndex("inicio_primeira_graduacao"));
            String inicioSegundaGraduacao = cursor.getString(cursor.getColumnIndex("inicio_segunda_graduacao"));

            String isLead = cursor.getString(cursor.getColumnIndex("gerou_lead"));
            Boolean gerouLead = isLead != null && Integer.valueOf(isLead) == 1;
            String numeroLead = cursor.getString(cursor.getColumnIndex("id_lead"));
            String status = cursor.getString(cursor.getColumnIndex("status_lead"));
            Integer unidade = cursor.getColumnIndex("unidade");

            pesquisas.add(new Pesquisa(id, dataPesquisa, nomeEntrevistador, unidadeEntrevista, nomeEntrevistado, sexo, cidade, cep,
                    bairro, numero, estado, rua, complemento, telefone, email, idade, localPesquisa,
                    ocupacao, escolaridade, areaGraduacao, opcaoPos, qualPos, pretencaoInicioPos, paticiparSorteio,
                    inicioPos, outroLocal, outraArea, tempoConclusaoGraduacao, desejaGraduacao,
                    inicioPrimeiraGraduacao, inicioSegundaGraduacao, gerouLead, numeroLead, status, unidade));
        }
        cursor.close();
        return pesquisas;
    }

    public Pesquisa retornarUltimo() {
        Cursor cursor = gw.getDb().rawQuery("SELECT * FROM pesquisa ORDER BY ID DESC", null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String dataPesquisa = cursor.getString(cursor.getColumnIndex("data_pesquisa"));
            String nomeEntrevistador = cursor.getString(cursor.getColumnIndex("nome_entrevistador"));
            String unidadeEntrevista = cursor.getString(cursor.getColumnIndex("unidade_entrevista"));
            String nomeEntrevistado = cursor.getString(cursor.getColumnIndex("nome_entrevistado"));
            String sexo = cursor.getString(cursor.getColumnIndex("sexo"));
            String cidade = cursor.getString(cursor.getColumnIndex("cidade"));
            String cep = cursor.getString(cursor.getColumnIndex("cep"));
            String bairro = cursor.getString(cursor.getColumnIndex("bairro"));
            String numero = cursor.getString(cursor.getColumnIndex("numero"));
            String estado = cursor.getString(cursor.getColumnIndex("estado"));
            String rua = cursor.getString(cursor.getColumnIndex("rua"));
            String complemento = cursor.getString(cursor.getColumnIndex("complemento"));
            String telefone = cursor.getString(cursor.getColumnIndex("telefone"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String idade = cursor.getString(cursor.getColumnIndex("idade"));
            String localPesquisa = cursor.getString(cursor.getColumnIndex("local_pesquisa"));
            String ocupacao = cursor.getString(cursor.getColumnIndex("ocupacao"));
            String escolaridade = cursor.getString(cursor.getColumnIndex("escolaridade"));
            String areaGraduacao = cursor.getString(cursor.getColumnIndex("area_graduacao"));
            String opcaoPos = cursor.getString(cursor.getColumnIndex("opcao_pos"));
            String qualPos = cursor.getString(cursor.getColumnIndex("qual_pos"));
            String pretencaoInicioPos = cursor.getString(cursor.getColumnIndex("pretencao_inicio_pos"));
            String paticiparSorteio = cursor.getString(cursor.getColumnIndex("paticipar_sorteio"));
            String inicioPos = cursor.getString(cursor.getColumnIndex("inicio_pos"));
            String outroLocal = cursor.getString(cursor.getColumnIndex("outro_local"));
            String outraArea = cursor.getString(cursor.getColumnIndex("outra_area"));
            String tempoConclusaoGraduacao = cursor.getString(cursor.getColumnIndex("tempo_conclusao_graduacao"));
            String desejaGraduacao = cursor.getString(cursor.getColumnIndex("deseja_graduacao"));
            String inicioPrimeiraGraduacao = cursor.getString(cursor.getColumnIndex("inicio_primeira_graduacao"));
            String inicioSegundaGraduacao = cursor.getString(cursor.getColumnIndex("inicio_segunda_graduacao"));
            Integer unidade = cursor.getColumnIndex("unidade");


            String isLead = cursor.getString(cursor.getColumnIndex("gerou_lead"));
            Boolean gerouLead = isLead != null && Integer.valueOf(isLead) == 1;
            String numeroLead = cursor.getString(cursor.getColumnIndex("id_lead"));
            String status = cursor.getString(cursor.getColumnIndex("status_lead"));
            cursor.close();
            return new Pesquisa(id, dataPesquisa, nomeEntrevistador, unidadeEntrevista, nomeEntrevistado, sexo, cidade, cep,
                    bairro, numero, estado, rua, complemento, telefone, email, idade, localPesquisa,
                    ocupacao, escolaridade, areaGraduacao, opcaoPos, qualPos, pretencaoInicioPos, paticiparSorteio,
                    inicioPos, outroLocal, outraArea, tempoConclusaoGraduacao, desejaGraduacao,
                    inicioPrimeiraGraduacao, inicioSegundaGraduacao, gerouLead, numeroLead, status, unidade);
        }

        return null;
    }

    public Pesquisa getPesquisa(int id) {
        Cursor cursor = gw.getDb().rawQuery("SELECT * FROM pesquisa where id=" + id,
                null);
        Pesquisa pesquisa = null;
        while (cursor.moveToNext()) {
            String dataPesquisa = cursor.getString(cursor.getColumnIndex("data_pesquisa"));
            String nomeEntrevistador = cursor.getString(cursor.getColumnIndex("nome_entrevistador"));
            String unidadeEntrevista = cursor.getString(cursor.getColumnIndex("unidade_entrevista"));
            String nomeEntrevistado = cursor.getString(cursor.getColumnIndex("nome_entrevistado"));
            String sexo = cursor.getString(cursor.getColumnIndex("sexo"));
            String cidade = cursor.getString(cursor.getColumnIndex("cidade"));
            String cep = cursor.getString(cursor.getColumnIndex("cep"));
            String bairro = cursor.getString(cursor.getColumnIndex("bairro"));
            String numero = cursor.getString(cursor.getColumnIndex("numero"));
            String estado = cursor.getString(cursor.getColumnIndex("estado"));
            String rua = cursor.getString(cursor.getColumnIndex("rua"));
            String complemento = cursor.getString(cursor.getColumnIndex("complemento"));
            String telefone = cursor.getString(cursor.getColumnIndex("telefone"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String idade = cursor.getString(cursor.getColumnIndex("idade"));
            String localPesquisa = cursor.getString(cursor.getColumnIndex("local_pesquisa"));
            String ocupacao = cursor.getString(cursor.getColumnIndex("ocupacao"));
            String escolaridade = cursor.getString(cursor.getColumnIndex("escolaridade"));
            String areaGraduacao = cursor.getString(cursor.getColumnIndex("area_graduacao"));
            String opcaoPos = cursor.getString(cursor.getColumnIndex("opcao_pos"));
            String qualPos = cursor.getString(cursor.getColumnIndex("qual_pos"));
            String pretencaoInicioPos = cursor.getString(cursor.getColumnIndex("pretencao_inicio_pos"));
            String paticiparSorteio = cursor.getString(cursor.getColumnIndex("paticipar_sorteio"));
            String inicioPos = cursor.getString(cursor.getColumnIndex("inicio_pos"));
            String outroLocal = cursor.getString(cursor.getColumnIndex("outro_local"));
            String outraArea = cursor.getString(cursor.getColumnIndex("outra_area"));
            String tempoConclusaoGraduacao = cursor.getString(cursor.getColumnIndex("tempo_conclusao_graduacao"));
            String desejaGraduacao = cursor.getString(cursor.getColumnIndex("deseja_graduacao"));
            String inicioPrimeiraGraduacao = cursor.getString(cursor.getColumnIndex("inicio_primeira_graduacao"));
            String inicioSegundaGraduacao = cursor.getString(cursor.getColumnIndex("inicio_segunda_graduacao"));

            String isLead = cursor.getString(cursor.getColumnIndex("gerou_lead"));
            Boolean gerouLead = isLead != null && Integer.valueOf(isLead) == 1;
            String numeroLead = cursor.getString(cursor.getColumnIndex("id_lead"));
            String status = cursor.getString(cursor.getColumnIndex("status_lead"));
            Integer unidade = cursor.getColumnIndex("unidade");

            cursor.close();
            pesquisa = new Pesquisa(id, dataPesquisa, nomeEntrevistador, unidadeEntrevista, nomeEntrevistado, sexo, cidade, cep,
                    bairro, numero, estado, rua, complemento, telefone, email, idade, localPesquisa,
                    ocupacao, escolaridade, areaGraduacao, opcaoPos, qualPos, pretencaoInicioPos, paticiparSorteio,
                    inicioPos, outroLocal, outraArea, tempoConclusaoGraduacao, desejaGraduacao,
                    inicioPrimeiraGraduacao, inicioSegundaGraduacao, gerouLead, numeroLead, status, unidade);
        }
        cursor.close();
        return pesquisa;
    }
}
