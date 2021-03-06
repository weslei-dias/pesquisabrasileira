package com.domain.androidcrud;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class PesquisaHolder extends RecyclerView.ViewHolder {

    public TextView nomeCliente;
    public TextView dataPesquisa;
    public TextView gerouLead;
    public TextView numeroLead;
    public ImageButton btnEditar;
    public ImageButton btnExcluir;

    public PesquisaHolder(@NonNull View itemView) {
        super(itemView);
        nomeCliente = (TextView) itemView.findViewById(R.id.nomeCliente);
        dataPesquisa = (TextView) itemView.findViewById(R.id.dataPesquisa);
        gerouLead = itemView.findViewById(R.id.gerou_prospect);
        numeroLead = itemView.findViewById(R.id.num_prospect);
        btnEditar = (ImageButton) itemView.findViewById(R.id.btnEdit);
        btnExcluir = (ImageButton) itemView.findViewById(R.id.btnDelete);
    }
}
