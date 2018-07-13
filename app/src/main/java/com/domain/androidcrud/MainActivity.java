package com.domain.androidcrud;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private EditText etZipCode;
    private Util util;
    private ProgressBar progressBar;
    private APIService mAPIService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etZipCode = (EditText) findViewById(R.id.txtZipCode);
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
                R.id.txtZipCode,
                R.id.et_street,
                R.id.et_complement,
                R.id.et_neighbor,
                R.id.et_city,
                R.id.sp_state,
                R.id.et_number,
                R.id.tv_zip_code_search);

        mAPIService = ApiUtils.getAPIService();

        //verifica se começou agora ou se veio de uma edição
        Intent intent = getIntent();
        preencherDadosSeEdicao(intent);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               findViewById(R.id.includemain)
                       .setVisibility(View.GONE);
               findViewById(R.id.includecadastro)
                       .setVisibility(View.VISIBLE);
               findViewById(R.id.fab).setVisibility(View.GONE);
            }
        });

        Spinner spinnerLocal = findViewById(R.id.spnLocalPesquisa);
        spinnerLocal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                TextInputLayout layoutOutro = findViewById(R.id.ll_outroLocal);
                if (selectedItemText.equals("Outro")){
                    layoutOutro.setVisibility(View.VISIBLE);
                }else {
                    layoutOutro.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerOutraArea = findViewById(R.id.spnAreaGraduacao);
        spinnerOutraArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                TextInputLayout layoutOutro = findViewById(R.id.ll_outraArea);
                if (selectedItemText.equals("Outra")){
                    layoutOutro.setVisibility(View.VISIBLE);
                }else {
                    layoutOutro.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner spinnerInicioPos = findViewById(R.id.spnInicioPos);
        spinnerInicioPos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                TextInputLayout layoutOutro = findViewById(R.id.ll_mesesInicioPos);
                if (selectedItemText.equals("Em x meses")){
                    layoutOutro.setVisibility(View.VISIBLE);
                }else {
                    layoutOutro.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText txtNomeEntrevistador = findViewById(R.id.txtNomeEntrevistador);
                EditText txtUnidadeEntrevistador = findViewById(R.id.txtUnidadeEntrevistador);
                EditText txtNomeEntrevistado = findViewById(R.id.txtNomeEntrevistado);
                EditText txtCep = findViewById(R.id.txtZipCode);
                EditText txtRua = findViewById(R.id.et_street);
                EditText txtComplemento = findViewById(R.id.et_complement);
                EditText txtNumero = findViewById(R.id.et_number);
                EditText txtBairro = findViewById(R.id.et_neighbor);
                EditText txtCidade = findViewById(R.id.et_city);

                Spinner spnEstado = findViewById(R.id.sp_state);
                EditText txtTelefone = findViewById(R.id.et_phone_number);
                EditText txtEmail = findViewById(R.id.txtEmail);

                RadioGroup rgSexo = findViewById(R.id.rgSexo);
                Spinner spnIdade = findViewById(R.id.spnIdade);
                Spinner spnLocalPesquisa = findViewById(R.id.spnLocalPesquisa);
                Spinner spnOcupacao = findViewById(R.id.spnOcupacao);
                Spinner spnEscolaridade = findViewById(R.id.spnEscolaridade);
                Spinner spnArea = findViewById(R.id.spnAreaGraduacao);
                Spinner spnOpcaoPos = findViewById(R.id.spnGostariaPos);
                Spinner spnInicioPos = findViewById(R.id.spnInicioPos);
                Spinner spnParticiparSorteio = findViewById(R.id.spnSorteio);

                String nomeEntrevistador = txtNomeEntrevistador.getText().toString();
                String unidadeEntrevistador = txtUnidadeEntrevistador.getText().toString();
                String nomeEntrevistado = txtNomeEntrevistado.getText().toString();
                String cep = txtCep.getText().toString();
                String bairro = txtBairro.getText().toString();
                String numero = txtNumero.getText().toString();
                String rua = txtRua.getText().toString();
                String complemento = txtComplemento.getText().toString();
                String cidade = txtCidade.getText().toString();
                String telefone = txtTelefone.getText().toString().replace("(", "");
                String email = txtEmail.getText().toString();
                String estado = spnEstado.getSelectedItem().toString();
                String idade = spnIdade.getSelectedItem().toString();
                String localPesquisa = spnLocalPesquisa.getSelectedItem().toString();
                String outroLocal = null;
                if (localPesquisa.equals("Outro")){
                    EditText txtOutroLocal = findViewById(R.id.txtOutroLocal);
                    outroLocal = txtOutroLocal.getText().toString();
                }
                String ocupacao = spnOcupacao.getSelectedItem().toString();
                String escolaridade = spnEscolaridade.getSelectedItem().toString();
                String areaGraduacao = spnArea.getSelectedItem().toString();
                String outraArea = null;
                if (areaGraduacao.equals("Outra")){
                    EditText txtOutraArea = findViewById(R.id.txtOutroArea);
                    outraArea = txtOutraArea.getText().toString();
                }
                String opcaoPos = spnOpcaoPos.getSelectedItem().toString();
                String pretencaoInicioPos = spnInicioPos.getSelectedItem().toString();
                String inicioPos = null;
                if (pretencaoInicioPos.equals("Em x meses")){
                    EditText txtInicio = findViewById(R.id.txtMesesInicioPos);
                    inicioPos = txtInicio.getText().toString();
                }

                String paticiparSorteio = spnParticiparSorteio.getSelectedItem().toString();
                String sexo = rgSexo.getCheckedRadioButtonId() == R.id.rbMasculino ? "M" : "F";


                boolean isFormValido = validarPreenchimentoCampos(view, nomeEntrevistador, nomeEntrevistado,
                        cep, numero, rua, cidade, telefone, email, estado, idade,
                        localPesquisa, ocupacao, escolaridade, areaGraduacao,
                        pretencaoInicioPos, paticiparSorteio);

                ClienteDao dao = new ClienteDao(getBaseContext());
                boolean sucesso;

                SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String dataAtual="";
                Calendar hoje = Calendar.getInstance();
                dataAtual = formatoData.format(hoje.getTime());
                ContentValues cv = new ContentValues();
                cv.put("data_pesquisa", dataAtual);
                cv.put("nome_entrevistador", nomeEntrevistador);
                cv.put("unidade_entrevista", unidadeEntrevistador);
                cv.put("nome_entrevistado", nomeEntrevistado);
                cv.put("sexo", sexo);
                cv.put("cidade", cidade);
                cv.put("cep", cep);
                cv.put("bairro", bairro);
                cv.put("numero", numero);
                cv.put("estado", estado);
                cv.put("rua", rua);
                cv.put("complemento", complemento);
                cv.put("telefone", telefone
                        .replace(")","")
                        .replace("-","")
                        .replace(" ", ""));
                cv.put("email", email);
                cv.put("idade", idade);
                cv.put("local_pesquisa", localPesquisa);
                cv.put("ocupacao", ocupacao);
                cv.put("escolaridade", escolaridade);
                cv.put("area_graduacao", areaGraduacao);
                cv.put("opcao_pos", opcaoPos);
                cv.put("pretencao_inicio_pos", pretencaoInicioPos);
                cv.put("paticipar_sorteio", paticiparSorteio);
                cv.put("inicio_pos", inicioPos);
                cv.put("outro_local", outroLocal);
                cv.put("outra_area", outraArea);

                if (isFormValido){
                    if (clienteEditado != null){
                        sucesso = dao.salvar(clienteEditado.getId(), cv);
                    }else {
                        sucesso = dao.salvar(cv);
                    }

                    if (sucesso){
                        Cliente cliente = dao.retornarUltimo();

                        if (clienteEditado != null){
                            clienteAdapter.atualizarCliente(cliente);
                            clienteEditado = null;
                        }else {
                            clienteAdapter.adicionarCliente(cliente);
                        }

                        sendPost(cliente);


                        txtNomeEntrevistado.setText("");
                        txtUnidadeEntrevistador.setText("");
                        txtNomeEntrevistador.setText("");
                        txtCep.setText("");
                        txtRua.setText("");
                        txtComplemento.setText("");
                        txtNumero.setText("");
                        txtBairro.setText("");
                        txtCidade.setText("");
                        txtTelefone.setText("");
                        txtEmail.setText("");
                        rgSexo.setSelected(false);
                        spnEstado.setSelection(0);
                        spnIdade.setSelection(0);
                        spnLocalPesquisa.setSelection(0);
                        spnOcupacao.setSelection(0);
                        spnEscolaridade.setSelection(0);
                        spnArea.setSelection(0);
                        spnOpcaoPos.setSelection(0);
                        spnInicioPos.setSelection(0);
                        spnParticiparSorteio.setSelection(0);
                        Snackbar.make(view, "Pesquisa salva com sucesso", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        findViewById(R.id.includemain).setVisibility(View.VISIBLE);
                        findViewById(R.id.includecadastro).setVisibility(View.GONE);
                        findViewById(R.id.fab).setVisibility(View.VISIBLE);
                    }else {
                        Snackbar.make(view, "Erro ao salvar a pesquisa! Verifique o preenchimento " +
                                "de todos os campo e tente novamente!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }else{
                    Snackbar.make(view, "Existem campos não preenchidos. Verifique e tente novamente.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        configurarRecycler();
    }

    private boolean validarPreenchimentoCampos(View view, String nomeEntrevistador, String nomeEntrevistado, String cep, String numero, String rua, String cidade, String telefone, String email, String estado, String idade, String localPesquisa, String ocupacao, String escolaridade, String areaGraduacao, String pretencaoInicioPos, String paticiparSorteio) {
        if (StringUtils.isEmpty(nomeEntrevistador) ||
                StringUtils.isEmpty(nomeEntrevistado) ||
                StringUtils.isEmpty(cep) ||
                StringUtils.isEmpty(rua) ||
                StringUtils.isEmpty(numero) ||
                StringUtils.isEmpty(cidade) ||
                StringUtils.isEmpty(estado) ||
                StringUtils.isEmpty(telefone) ||
                StringUtils.isEmpty(email) ||
                escolaridade.startsWith("Selecione") ||
                localPesquisa.startsWith("Selecione") ||
                ocupacao.startsWith("Selecione") ||
                idade.startsWith("Selecione") ||
                areaGraduacao.startsWith("Selecione") ||
                pretencaoInicioPos.startsWith("Selecione") ||
                paticiparSorteio.startsWith("Selecione")) {
            return false;
        }
        return true;
    }


    private void preencherDadosSeEdicao(Intent intent) {
        if(intent.hasExtra("cliente")){
            findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
            findViewById(R.id.includecadastro).setVisibility(View.VISIBLE);
            findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            Integer idCliente = (Integer) intent.getSerializableExtra("cliente");
            clienteEditado = new ClienteDao(this).getCliente(idCliente);
            TextView txtDataPesquisa = findViewById(R.id.txtDataPesquisa);
            EditText txtNomeEntrevistador = findViewById(R.id.txtNomeEntrevistador);
            EditText txtUnidadeEntrevistador = findViewById(R.id.txtUnidadeEntrevistador);
            EditText txtNomeEntrevistado = findViewById(R.id.txtNomeEntrevistado);
            TextInputEditText txtCep = (TextInputEditText) findViewById(R.id.txtZipCode);
            EditText txtRua = findViewById(R.id.et_street);
            EditText txtComplemento = findViewById(R.id.et_complement);
            EditText txtNumero = findViewById(R.id.et_number);
            EditText txtBairro = findViewById(R.id.et_neighbor);
            EditText txtCidade = findViewById(R.id.et_city);

            Spinner spnEstado = findViewById(R.id.sp_state);
            EditText txtTelefone = findViewById(R.id.et_phone_number);
            EditText txtEmail = findViewById(R.id.txtEmail);

            RadioGroup rgSexo = findViewById(R.id.rgSexo);
            Spinner spnIdade = findViewById(R.id.spnIdade);
            Spinner spnLocalPesquisa = findViewById(R.id.spnLocalPesquisa);
            Spinner spnOcupacao = findViewById(R.id.spnOcupacao);
            Spinner spnEscolaridade = findViewById(R.id.spnEscolaridade);
            Spinner spnArea = findViewById(R.id.spnAreaGraduacao);
            Spinner spnOpcaoPos = findViewById(R.id.spnGostariaPos);
            Spinner spnInicioPos = findViewById(R.id.spnInicioPos);
            Spinner spnParticiparSorteio = findViewById(R.id.spnSorteio);

            txtDataPesquisa.setText(clienteEditado.getDataPesquisa());
            txtNomeEntrevistado.setText(clienteEditado.getNomeEntrevistado());
            txtUnidadeEntrevistador.setText(clienteEditado.getUnidadeEntrevista());
            txtNomeEntrevistador.setText(clienteEditado.getNomeEntrevistador());
            txtCep.setText(clienteEditado.getCep());
            txtRua.setText(clienteEditado.getRua());
            txtComplemento.setText(clienteEditado.getComplemento());
            txtNumero.setText(clienteEditado.getNumero());
            txtBairro.setText(clienteEditado.getBairro());
            txtCidade.setText(clienteEditado.getCidade());
            txtTelefone.setText(clienteEditado.getTelefone());
            txtEmail.setText(clienteEditado.getEmail());
            spnEstado.setSelection(getIndex(spnEstado, clienteEditado.getEstado()));
            spnIdade.setSelection(getIndex(spnIdade, clienteEditado.getIdade()));
            spnLocalPesquisa.setSelection(getIndex(spnLocalPesquisa, clienteEditado.getLocalPesquisa()));
            if (spnLocalPesquisa.getSelectedItem().toString().equals("Outro")){
                EditText txtOutroLocal = findViewById(R.id.txtOutroLocal);
                txtOutroLocal.setText(clienteEditado.getOutroLocal());
            }
            spnOcupacao.setSelection(getIndex(spnOcupacao, clienteEditado.getOcupacao()));
            spnEscolaridade.setSelection(getIndex(spnEscolaridade, clienteEditado.getEscolaridade()));
            spnArea.setSelection(getIndex(spnArea, clienteEditado.getAreaGraduacao()));
            if (spnArea.getSelectedItem().toString().equals("Outra")){
                EditText txtOutraArea = findViewById(R.id.txtOutroArea);
                txtOutraArea.setText(clienteEditado.getOutraArea());
            }
            spnOpcaoPos.setSelection(getIndex(spnOpcaoPos, clienteEditado.getOpcaoPos()));
            spnInicioPos.setSelection(getIndex(spnInicioPos, clienteEditado.getPretencaoInicioPos()));
            if (spnInicioPos.getSelectedItem().toString().equals("Em x meses")){
                EditText txtInicio = findViewById(R.id.txtMesesInicioPos);
                txtInicio.setText(clienteEditado.getInicioPos());
            }
            spnParticiparSorteio.setSelection(getIndex(spnParticiparSorteio, clienteEditado.getPaticiparSorteio()));
            if(clienteEditado.getSexo() != null){
                RadioButton rb;
                if(clienteEditado.getSexo().equals("M")) {
                    rb = (RadioButton) findViewById(R.id.rbMasculino);
                    rb.setChecked(true);
                }
                else {
                    rb = (RadioButton) findViewById(R.id.rbFeminino);
                    rb.setChecked(true);
                }
            }
        }
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
//        setField( R.id.et_complement, address.getComplemento() );
        setField( R.id.et_neighbor, address.getBairro() );
        setField( R.id.et_city, address.getLocalidade() );
        setSpinner( R.id.sp_state, R.array.estados, address.getUf());

        if (address == null){
            Snackbar.make(getCurrentFocus(),
                    "Não foi possível recuperar o cep informado", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

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

    public void sendPost(Cliente cliente) {

        // RxJava
        mAPIService.savePost(cliente).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Cliente>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Cliente post) {
                        showResponse(post.toString());
                    }
                });
    }

    public void showResponse(String response) {
        System.out.println(response);
    }
}
