package com.domain.androidcrud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.domain.androidcrud.request.APIService;
import com.domain.androidcrud.ClienteAdapter;
import com.domain.androidcrud.dao.ClienteDao;
import com.domain.androidcrud.R;
import com.domain.androidcrud.Util;

public class MainActivity extends AppCompatActivity {

    private EditText etZipCode;
    private Util util;
    private ProgressBar progressBar;
    private APIService mAPIService;
    private AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configurarRecycler();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.includemain)
                        .setVisibility(View.GONE);
                findViewById(R.id.includecadastro)
                        .setVisibility(View.VISIBLE);
                findViewById(R.id.fab).setVisibility(View.GONE);

                Intent intent = new Intent(MainActivity.this,
                        CadastroActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);

    }

    private RecyclerView recyclerView;
    private ClienteAdapter clienteAdapter;
    private void configurarRecycler() {
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ClienteDao dao = new ClienteDao(this);
        clienteAdapter = new ClienteAdapter(dao.getTodosClientes());
        recyclerView.setAdapter(clienteAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
    }
}
