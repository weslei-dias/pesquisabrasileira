package com.domain.androidcrud.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.domain.androidcrud.request.APIService;
import com.domain.androidcrud.PesquisaAdapter;
import com.domain.androidcrud.dao.PesquisaDao;
import com.domain.androidcrud.R;
import com.domain.androidcrud.Util;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuSair:
                SharedPreferences myPreferences =
                        PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor myEditor = myPreferences.edit();
                myEditor.clear();
                myEditor.commit();
                acessarLoginActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void acessarLoginActivity() {
        Intent intent = new Intent(MainActivity.this,
                LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private RecyclerView recyclerView;
    private PesquisaAdapter clienteAdapter;
    private void configurarRecycler() {
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        PesquisaDao dao = new PesquisaDao(this);
        clienteAdapter = new PesquisaAdapter(dao.getTodasPesquisas());
        recyclerView.setAdapter(clienteAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
    }
}
