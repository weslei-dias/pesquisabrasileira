package com.domain.androidcrud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.domain.androidcrud.activity.CadastroActivity;
import com.domain.androidcrud.dao.PesquisaDao;
import com.domain.androidcrud.model.Pesquisa;

import java.util.List;

public class PesquisaAdapter extends RecyclerView.Adapter<PesquisaHolder> {

    private List<Pesquisa> pesquisas;

    public PesquisaAdapter(List<Pesquisa> pesquisas) {
        this.pesquisas = pesquisas;
    }

    @NonNull
    @Override
    public PesquisaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PesquisaHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PesquisaHolder pesquisaHolder, final int i) {
        pesquisaHolder.nomeCliente.setText(pesquisas.get(i).getNomeEntrevistado());
        pesquisaHolder.dataPesquisa.setText(pesquisas.get(i).getDataPesquisa());
        pesquisaHolder.gerouLead.setText(
                pesquisas.get(i).getGerouLead() ? "Gerou Lead : Sim" : "Gerou Lead : Não");
        if (pesquisas.get(i).getGerouLead()){
            pesquisaHolder.numeroLead.setVisibility(View.VISIBLE);
            pesquisaHolder.numeroLead.setText("ID do lead: " +
                    pesquisas.get(i).getIdLead());
        }
        pesquisaHolder.btnEditar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),
                        CadastroActivity.class);

                Pesquisa pesquisa = pesquisas.get(i);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("pesquisa", pesquisa.getId());
                v.getContext().startActivity(intent);

            }
        });

        pesquisaHolder.btnExcluir.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir esta pesquisa?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Pesquisa pesquisa = pesquisas.get(i);
                                PesquisaDao dao = new PesquisaDao(view.getContext());
                                boolean sucesso = dao.excluir(pesquisa.getId());
                                if(sucesso) {
                                    removerPesquisa(pesquisa);
                                    Snackbar.make(view, "Pesquisa excluída!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }else{
                                    Snackbar.make(view, "Erro ao excluir a pesquisa!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pesquisas != null ? pesquisas.size() : 0;
    }

    public void adicionarPesquisa(Pesquisa pesquisa) {
        pesquisas.add(pesquisa);
        notifyItemInserted(getItemCount());
    }

    public void atualizarCliente(Pesquisa pesquisa) {

        for (int i = 0; i < pesquisas.size(); i++) {
            Pesquisa cli = pesquisas.get(i);
            if (cli.getId().equals(pesquisa.getId())){
                pesquisas.set(i, pesquisa);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void removerPesquisa(Pesquisa pesquisa){
        for (int i = 0; i < pesquisas.size(); i++) {
            Pesquisa cli = pesquisas.get(i);
            if (cli.getId().equals(pesquisa.getId())){
                pesquisas.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }
}
