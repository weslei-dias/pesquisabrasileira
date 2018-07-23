package com.domain.androidcrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class ClienteDao {
    private final String TABLE_CLIENTES = "popbe";
    private DbGateway gw;

    public ClienteDao(Context ctx) {
        gw = DbGateway.getInstance(ctx);
    }

    public boolean excluir(int id){
        return gw.getDb().delete(TABLE_CLIENTES, "id=?", new String[]{ id + "" }) > 0;
    }

    public boolean salvar(ContentValues cv) {
        return salvar(0, cv);
    }

        public boolean salvar(int id, ContentValues cv) {
        if (id > 0){
            return gw.getDb().update(TABLE_CLIENTES, cv, "id=?",
                    new String[]{id + ""}) > 0;
        }else {
            return gw.getDb().insert(TABLE_CLIENTES, null, cv) > 0;
        }
    }

    public List<Cliente> getTodosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        Cursor cursor = gw.getDb()
                .rawQuery("Select * from popbe", null);
        while(cursor.moveToNext()){
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

            clientes.add(new Cliente(id, dataPesquisa, nomeEntrevistador, unidadeEntrevista, nomeEntrevistado, sexo, cidade, cep,
                    bairro, numero, estado, rua, complemento, telefone, email, idade, localPesquisa,
                    ocupacao, escolaridade,areaGraduacao, opcaoPos, qualPos, pretencaoInicioPos, paticiparSorteio,
                    inicioPos, outroLocal, outraArea, tempoConclusaoGraduacao, desejaGraduacao, inicioPrimeiraGraduacao, inicioSegundaGraduacao));
        }
        cursor.close();
        return clientes;
    }

    public Cliente retornarUltimo(){
        Cursor cursor = gw.getDb().rawQuery("SELECT * FROM popbe ORDER BY ID DESC", null);
        if(cursor.moveToFirst()){
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
            cursor.close();
            return new Cliente(id, dataPesquisa, nomeEntrevistador, unidadeEntrevista, nomeEntrevistado, sexo, cidade, cep,
                    bairro, numero, estado, rua, complemento, telefone, email, idade, localPesquisa,
                    ocupacao, escolaridade,areaGraduacao, opcaoPos, qualPos, pretencaoInicioPos, paticiparSorteio,
                    inicioPos, outroLocal, outraArea, tempoConclusaoGraduacao, desejaGraduacao, inicioPrimeiraGraduacao, inicioSegundaGraduacao);
        }

        return null;
    }

    public Cliente getCliente(int id){
        Cursor cursor = gw.getDb().rawQuery("SELECT * FROM popbe where id=" + id,
                null);
        Cliente cliente = null;
        while(cursor.moveToNext()){
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
            cursor.close();
            cliente = new Cliente(id, dataPesquisa, nomeEntrevistador, unidadeEntrevista, nomeEntrevistado, sexo, cidade, cep,
                    bairro, numero, estado, rua, complemento, telefone, email, idade, localPesquisa,
                    ocupacao, escolaridade,areaGraduacao, opcaoPos, qualPos, pretencaoInicioPos, paticiparSorteio,
                    inicioPos, outroLocal, outraArea, tempoConclusaoGraduacao, desejaGraduacao, inicioPrimeiraGraduacao, inicioSegundaGraduacao);
        }
        cursor.close();
        return cliente;
    }
}
