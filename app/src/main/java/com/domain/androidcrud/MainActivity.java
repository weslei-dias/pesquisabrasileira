package com.domain.androidcrud;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText etZipCode;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etZipCode = (EditText) findViewById(R.id.et_zip_code);
        etZipCode.addTextChangedListener( new ZipCodeListener(this) );

        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        String retorno="";
        Calendar hoje = Calendar.getInstance();
        retorno = formatoData.format(hoje.getTime());
        TextView fieldDate = (TextView) findViewById(R.id.txtDataPesquisa);
                fieldDate.setText(retorno);

        Spinner spStates = (Spinner) findViewById(R.id.sp_state);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this,
                        R.array.estados,
                        android.R.layout.simple_spinner_item);
        spStates.setAdapter(adapter);

        util = new Util(this,
                R.id.et_zip_code,
                R.id.et_street,
                R.id.et_complement,
                R.id.et_neighbor,
                R.id.et_city,
                R.id.sp_state,
                R.id.et_number,
                R.id.tv_zip_code_search);


        //verifica se começou agora ou se veio de uma edição
        Intent intent = getIntent();
//        if(intent.hasExtra("cliente")){
//            findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
//            findViewById(R.id.includecadastro).setVisibility(View.VISIBLE);
//            findViewById(R.id.fab).setVisibility(View.INVISIBLE);
//            Integer idCliente = (Integer) intent.getSerializableExtra("cliente");
//            clienteEditado = new ClienteDao(this).getCliente(idCliente);
//            EditText txtNome = (EditText)findViewById(R.id.txtNome);
//            Spinner spnEstado = (Spinner)findViewById(R.id.spnEstado);
//            CheckBox chkVip = (CheckBox)findViewById(R.id.chkVip);
//
//            txtNome.setText(clienteEditado.getNome());
//            chkVip.setChecked(clienteEditado.isVip());
//            spnEstado.setSelection(getIndex(spnEstado, clienteEditado.getUf()));
//            if(clienteEditado.getSexo() != null){
//                RadioButton rb;
//                if(clienteEditado.getSexo().equals("M"))
//                    rb = (RadioButton)findViewById(R.id.rbMasculino);
//                else
//                    rb = (RadioButton)findViewById(R.id.rbFeminino);
//                rb.setChecked(true);
//            }
//        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               findViewById(R.id.includemain)
                       .setVisibility(View.INVISIBLE);
               findViewById(R.id.includecadastro)
                       .setVisibility(View.VISIBLE);
               findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            }
        });

        Button btnCancelar = (Button)findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.includemain).setVisibility(View.VISIBLE);
                findViewById(R.id.includecadastro).setVisibility(View.INVISIBLE);
                findViewById(R.id.fab).setVisibility(View.VISIBLE);
            }
        });

        Button btnSalvar = (Button)findViewById(R.id.btnSalvar);
//        btnSalvar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditText txtNome = findViewById(R.id.txtNome);
//                Spinner spnEstado = findViewById(R.id.spnEstado);
//                RadioGroup rgSexo = findViewById(R.id.rgSexo);
//                CheckBox chkVip = findViewById(R.id.chkVip);
//
//                String nome = txtNome.getText().toString();
//                String uf = spnEstado.getSelectedItem().toString();
//                boolean vip = chkVip.isChecked();
//                String sexo = rgSexo.getCheckedRadioButtonId() == R.id.rbMasculino ? "M" : "F";
//
//                ClienteDao dao = new ClienteDao(getBaseContext());
//                boolean sucesso;
//
//                if (clienteEditado != null){
//                    sucesso = dao.salvar(clienteEditado.getId(), nome, sexo, uf, vip);
//                }else {
//                    sucesso = dao.salvar(nome, sexo, uf, vip);
//                }
//
//                if (sucesso){
//                    Cliente cliente = dao.retornarUltimo();
//
//                    if (clienteEditado != null){
//                        clienteAdapter.atualizarCliente(cliente);
//                        clienteEditado = null;
//                    }else {
//                        clienteAdapter.adicionarCliente(cliente);
//                    }
//                    txtNome.setText("");
//                    rgSexo.setSelected(false);
//                    spnEstado.setSelection(0);
//                    chkVip.setChecked(false);
//                    Snackbar.make(view, "Salvo com sucesso", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                    findViewById(R.id.includemain).setVisibility(View.VISIBLE);
//                    findViewById(R.id.includecadastro).setVisibility(View.INVISIBLE);
//                    findViewById(R.id.fab).setVisibility(View.VISIBLE);
//                }else {
//                    Snackbar.make(view, "Erro ao salvar, consulte os logs!", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }
//
//            }
//        });

        configurarRecycler();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == Endereco.REQUEST_ZIP_CODE_CODE
                && resultCode == RESULT_OK ){

            etZipCode.setText( data.getStringExtra( Endereco.ZIP_CODE_KEY ) );
        }
    }

    private String getZipCode(){
        return etZipCode.getText().toString();
    }

    public String getUriRequest(){
        return "https://viacep.com.br/ws/"+getZipCode()+"/json/";
    }

    public void lockFields( boolean isToLock ){
        util.lockFields( isToLock );
    }


    public void searchZipCode( View view ){
        Intent intent = new Intent( this, MainActivity.class );
        startActivityForResult(intent, Endereco.REQUEST_ZIP_CODE_CODE);
    }

    public void setAddressFields( Endereco address){
        setField( R.id.et_street, address.getLogradouro() );
        setField( R.id.et_complement, address.getComplemento() );
        setField( R.id.et_neighbor, address.getBairro() );
        setField( R.id.et_city, address.getLocalidade() );
        setSpinner( R.id.sp_state, R.array.estados, address.getUf());
    }

    private void setField( int fieldId, String data ){
        ((EditText) findViewById( fieldId )).setText( data );
    }

    private void setSpinner( int fieldId, int arrayId, String uf ){
        Spinner spinner = (Spinner) findViewById( fieldId );
        String[] states = getResources().getStringArray(arrayId);

        for( int i = 0; i < states.length; i++ ){
            if( states[i].endsWith("("+uf+")") ){
                spinner.setSelection( i );
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

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

    private Cliente clienteEditado = null;
    private int getIndex(Spinner spinner, String myString){
        int index = 0;
        for (int i=0; i< spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString()
                    .equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
}
