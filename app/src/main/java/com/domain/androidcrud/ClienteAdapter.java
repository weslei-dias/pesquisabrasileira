package com.domain.androidcrud;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteHolder> {

    private List<Cliente> clientes;

    public ClienteAdapter(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @NonNull
    @Override
    public ClienteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ClienteHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ClienteHolder clienteHolder, final int i) {
        clienteHolder.nomeCliente.setText(clientes.get(i).getNomeEntrevistado());
        clienteHolder.dataPesquisa.setText(clientes.get(i).getDataPesquisa());
        clienteHolder.gerouProspect.setText(
                clientes.get(i).getGerouProspect() ? "Gerou Propospect : Sim" : "Gerou Propospect : Não");
        if (clientes.get(i).getGerouProspect()){
            clienteHolder.numeroProspect.setVisibility(View.VISIBLE);
            clienteHolder.numeroProspect.setText("Número da prospect: " +
                    clientes.get(i).getNumeroProspect());
        }
        clienteHolder.btnEditar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),
                        CadastroActivity.class);

                Cliente cliente = clientes.get(i);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("cliente", cliente.getId());
                v.getContext().startActivity(intent);

            }
        });

        clienteHolder.btnExcluir.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir esta pesquisa?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Cliente cliente = clientes.get(i);
                                ClienteDao dao = new ClienteDao(view.getContext());
                                boolean sucesso = dao.excluir(cliente.getId());
                                if(sucesso) {
                                    removerCliente(cliente);
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
        return clientes != null ? clientes.size() : 0;
    }

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
        notifyItemInserted(getItemCount());
    }

    public void atualizarCliente(Cliente cliente) {

        for (int i = 0; i < clientes.size(); i++) {
            Cliente cli = clientes.get(i);
            if (cli.getId().equals(cliente.getId())){
                clientes.set(i, cliente);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void removerCliente(Cliente cliente){
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cli = clientes.get(i);
            if (cli.getId().equals(cliente.getId())){
                clientes.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    private Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (CadastroActivity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
}
