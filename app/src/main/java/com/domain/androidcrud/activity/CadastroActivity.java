package com.domain.androidcrud.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.domain.androidcrud.PesquisaAdapter;
import com.domain.androidcrud.R;
import com.domain.androidcrud.Util;
import com.domain.androidcrud.ValidadorCampos;
import com.domain.androidcrud.ZipCodeListener;
import com.domain.androidcrud.dao.PesquisaDao;
import com.domain.androidcrud.dao.UserDao;
import com.domain.androidcrud.model.Endereco;
import com.domain.androidcrud.model.Pesquisa;
import com.domain.androidcrud.model.User;
import com.domain.androidcrud.request.APIService;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CadastroActivity extends AppCompatActivity {

    private EditText etZipCode;
    private Util util;
    private ProgressBar progressBar;
    private APIService mAPIService;
    private AwesomeValidation awesomeValidation;
    private PesquisaAdapter pesquisaAdapter;
    private  User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_cadastro);
        pesquisaAdapter = new PesquisaAdapter(new ArrayList<Pesquisa>());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_content);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setDadosEntrevistador();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        etZipCode = (EditText) findViewById(R.id.txtZipCode);
        etZipCode.addTextChangedListener(new ZipCodeListener(this));

        awesomeValidation = ValidadorCampos.validarCamposObrigatorios(this);

        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        String retorno = "";
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

        //verifica se começou agora ou se veio de uma edição
        Intent intent = getIntent();
        preencherDadosSeEdicao(intent);

        Spinner spinnerOpcaoPos = findViewById(R.id.spnGostariaPos);
        spinnerOpcaoPos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                TextInputLayout layoutOutro = findViewById(R.id.ll_pos_realizada);
                LinearLayout layoutIniciarPos = findViewById(R.id.ll_inicio_pos);
                if (selectedItemText.equals("Sim gostaria")) {
                    layoutOutro.setVisibility(View.VISIBLE);
                    layoutIniciarPos.setVisibility(View.VISIBLE);
                } else {
                    layoutOutro.setVisibility(View.GONE);
                    layoutIniciarPos.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerLocal = findViewById(R.id.spnLocalPesquisa);
        spinnerLocal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                TextInputLayout layoutOutro = findViewById(R.id.ll_outroLocal);
                if (selectedItemText.equals("Outro")) {
                    layoutOutro.setVisibility(View.VISIBLE);
                } else {
                    layoutOutro.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerGraduacao = findViewById(R.id.spnGostariaGraduacao);
        spinnerGraduacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);

                TextInputLayout layoutPrimGraduacao = findViewById(R.id.ll_inicio_prim_graduacao);
                TextInputLayout layoutSegGraduacao = findViewById(R.id.ll_inicio_second_graduacao);
                if (selectedItemText.equals("Quero fazer a 1ª graduação")) {
                    layoutSegGraduacao.setVisibility(View.GONE);
                    layoutPrimGraduacao.setVisibility(View.VISIBLE);
                } else {
                    layoutPrimGraduacao.setVisibility(View.GONE);
                }
                if (selectedItemText.equals("Quero fazer a 2ª graduação")) {
                    layoutPrimGraduacao.setVisibility(View.GONE);
                    layoutSegGraduacao.setVisibility(View.VISIBLE);
                } else {
                    layoutSegGraduacao.setVisibility(View.GONE);
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
                TextInputLayout layoutFinalGraduacao = findViewById(R.id.ll_final_graduacao);
                if (selectedItemText.equals("Outra")) {
                    layoutFinalGraduacao.setVisibility(View.GONE);
                    layoutOutro.setVisibility(View.VISIBLE);
                } else {
                    layoutOutro.setVisibility(View.GONE);
                }
                if (selectedItemText.equals("Em andamento")) {
                    layoutOutro.setVisibility(View.GONE);
                    layoutFinalGraduacao.setVisibility(View.VISIBLE);
                } else {
                    layoutFinalGraduacao.setVisibility(View.GONE);
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
                if (selectedItemText.equals("Em x meses")) {
                    layoutOutro.setVisibility(View.VISIBLE);
                } else {
                    layoutOutro.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Salvando...");
        pd.setMessage("Aguarde um momento");
        pd.setCancelable(true);
        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final EditText txtNomeEntrevistado = findViewById(R.id.txtNomeEntrevistado);
                final EditText txtCep = findViewById(R.id.txtZipCode);
                final EditText txtRua = findViewById(R.id.et_street);
                final EditText txtComplemento = findViewById(R.id.et_complement);
                final EditText txtNumero = findViewById(R.id.et_number);
                final EditText txtBairro = findViewById(R.id.et_neighbor);
                final EditText txtCidade = findViewById(R.id.et_city);

                final Spinner spnEstado = findViewById(R.id.sp_state);
                final EditText txtTelefone = findViewById(R.id.et_phone_number);
                final EditText txtEmail = findViewById(R.id.txtEmail);

                final RadioGroup rgSexo = findViewById(R.id.rgSexo);
                final Spinner spnIdade = findViewById(R.id.spnIdade);
                final Spinner spnLocalPesquisa = findViewById(R.id.spnLocalPesquisa);
                final Spinner spnOcupacao = findViewById(R.id.spnOcupacao);
                final Spinner spnEscolaridade = findViewById(R.id.spnEscolaridade);
                final Spinner spnArea = findViewById(R.id.spnAreaGraduacao);
                final Spinner spnOpcaoPos = findViewById(R.id.spnGostariaPos);
                final Spinner spnInicioPos = findViewById(R.id.spnInicioPos);
                final Spinner spnParticiparSorteio = findViewById(R.id.spnSorteio);
                final Spinner spnGraduacao = findViewById(R.id.spnGostariaGraduacao);

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
                if (localPesquisa.equals("Outro")) {
                    EditText txtOutroLocal = findViewById(R.id.txtOutroLocal);
                    outroLocal = txtOutroLocal.getText().toString();
                }
                String ocupacao = spnOcupacao.getSelectedItem().toString();
                String escolaridade = spnEscolaridade.getSelectedItem().toString();
                String areaGraduacao = spnArea.getSelectedItem().toString();
                String outraArea = null;
                if (areaGraduacao.equals("Outra")) {
                    EditText txtOutraArea = findViewById(R.id.txtOutroArea);
                    outraArea = txtOutraArea.getText().toString();
                }

                String tempoFinalizarGraduacao = null;
                if (areaGraduacao.equals("Em andamento")) {
                    EditText txtFinalGraduacao = findViewById(R.id.txtFinalGraduacao);
                    tempoFinalizarGraduacao = txtFinalGraduacao.getText().toString();
                }
                String opcaoPos = spnOpcaoPos.getSelectedItem().toString();
                String qualPos = null;
                if (opcaoPos.equals("Sim gostaria")) {
                    EditText txtQualPos = findViewById(R.id.txtQualPos);
                    qualPos = txtQualPos.getText().toString();
                }
                String pretencaoInicioPos = spnInicioPos.getSelectedItem().toString();
                String inicioPos = null;
                if (pretencaoInicioPos.equals("Em x meses")) {
                    EditText txtInicio = findViewById(R.id.txtMesesInicioPos);
                    inicioPos = txtInicio.getText().toString();
                }

                String gostariaGraduacao = spnGraduacao.getSelectedItem().toString();
                String inicioPrimeiraGraduacao = null;
                String inicioSegundaGraduacao = null;
                if (gostariaGraduacao.equals("Quero fazer a 1ª graduação")) {
                    EditText txtPrimGrad = findViewById(R.id.txtInicioPrimGraduacao);
                    inicioPrimeiraGraduacao = txtPrimGrad.getText().toString();

                } else if (gostariaGraduacao.equals("Quero fazer a 2ª graduação")) {
                    EditText txtSegGrad = findViewById(R.id.txtInicioSegGraduacao);
                    inicioSegundaGraduacao = txtSegGrad.getText().toString();
                }

                String paticiparSorteio = spnParticiparSorteio.getSelectedItem().toString();
                String sexo = rgSexo.getCheckedRadioButtonId() == R.id.rbMasculino ? "M" : "F";


                boolean isFormValido = validarPreenchimentoCampos(nomeEntrevistado,
                        cep, numero, rua, cidade, telefone, email, estado, idade,
                        localPesquisa, ocupacao, escolaridade, areaGraduacao,
                        gostariaGraduacao, paticiparSorteio);

                final PesquisaDao dao = new PesquisaDao(getBaseContext());

                boolean sucesso;

                SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String dataAtual = "";
                Calendar hoje = Calendar.getInstance();
                dataAtual = formatoData.format(hoje.getTime());
                final ContentValues cv = new ContentValues();
                if (pesquisaEditada == null){
                    cv.put("data_pesquisa", dataAtual);
                }else{
                    cv.put("id", pesquisaEditada.getId());
                }
                cv.put("nome_entrevistador", user.getNome());
                cv.put("unidade_entrevista", user.getUnidade());
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
                        .replace(")", "")
                        .replace("-", "")
                        .replace(" ", ""));
                cv.put("email", email);
                cv.put("idade", idade);
                cv.put("local_pesquisa", localPesquisa);
                cv.put("ocupacao", ocupacao);
                cv.put("escolaridade", escolaridade);
                cv.put("area_graduacao", areaGraduacao);
                cv.put("opcao_pos", opcaoPos);
                cv.put("qual_pos", qualPos);
                cv.put("pretencao_inicio_pos", pretencaoInicioPos);
                cv.put("paticipar_sorteio", paticiparSorteio);
                cv.put("inicio_pos", inicioPos);
                cv.put("outro_local", outroLocal);
                cv.put("outra_area", outraArea);
                cv.put("tempo_conclusao_graduacao", tempoFinalizarGraduacao);
                cv.put("deseja_graduacao", gostariaGraduacao);
                cv.put("inicio_primeira_graduacao", inicioPrimeiraGraduacao);
                cv.put("inicio_segunda_graduacao", inicioSegundaGraduacao);

                if (isFormValido) {

                    Pesquisa pesquisaIgual = dao.getPesquisaComMesmoEmail(cv.getAsString("email"),
                            cv.getAsString("nome_entrevistado"));

                        if (pesquisaEditada != null) {
                            salvarPesquisaEdicao(view, txtNomeEntrevistado,
                                    txtCep, txtRua, txtComplemento, txtNumero, txtBairro,
                                    txtCidade, spnEstado, txtTelefone, txtEmail, rgSexo,
                                    spnIdade, spnLocalPesquisa, spnOcupacao, spnEscolaridade,
                                    spnArea, spnOpcaoPos, spnInicioPos, spnParticiparSorteio,
                                    spnGraduacao, dao, cv, pd);
                        } else {
                            if (pesquisaIgual != null){
                                StringBuilder msgPesquisaIgual = new StringBuilder();
                                msgPesquisaIgual.append("Já existe uma pesquisa para este entrevistado.");
                                if (pesquisaIgual.getIdLead() != null){
                                    msgPesquisaIgual.append("Lead de id: " + pesquisaIgual.getIdLead());
                                }
                                msgPesquisaIgual.append("Verifique o nome e o email do entrevistador e tente novamente");
                                Snackbar.make(view, msgPesquisaIgual.toString() , Snackbar.LENGTH_LONG)
                                        .setDuration(5000)
                                        .setAction("Action", null).show();
                            }else {
                                sucesso = dao.salvar(cv);
                                eviarPesquisaParaGeracaoProspect(view, txtNomeEntrevistado,
                                        txtCep, txtRua, txtComplemento, txtNumero, txtBairro, txtCidade,
                                        spnEstado, txtTelefone, txtEmail, rgSexo, spnIdade, spnLocalPesquisa,
                                        spnOcupacao, spnEscolaridade, spnArea, spnOpcaoPos, spnInicioPos,
                                        spnParticiparSorteio, spnGraduacao, dao, sucesso, cv, pd);
                            }
                        }


                } else {
                    Snackbar.make(view, "Existem campos não preenchidos. Verifique e tente novamente.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    private void setDadosEntrevistador() {
        final UserDao userDao = new UserDao(getBaseContext());
        SharedPreferences myPreferences =
                PreferenceManager.getDefaultSharedPreferences(CadastroActivity.this);
        String value = myPreferences.getString("username", "");
        user = userDao.getUsuarioComUsername(value);

        TextView txtNomeLIdero = findViewById(R.id.txtNomeLidero);
        txtNomeLIdero.setText(user.getNome());

        TextView txtUnidadeLidero = findViewById(R.id.txtUnidadeLidero);
        txtUnidadeLidero.setText(user.getUnidade());
    }

    private void salvarPesquisaEdicao(View view, EditText txtNomeEntrevistado, EditText txtCep,
                                      EditText txtRua, EditText txtComplemento, EditText txtNumero,
                                      EditText txtBairro, EditText txtCidade, Spinner spnEstado,
                                      EditText txtTelefone, EditText txtEmail, RadioGroup rgSexo,
                                      Spinner spnIdade, Spinner spnLocalPesquisa, Spinner spnOcupacao,
                                      Spinner spnEscolaridade, Spinner spnArea, Spinner spnOpcaoPos,
                                      Spinner spnInicioPos, Spinner spnParticiparSorteio,
                                      Spinner spnGraduacao, PesquisaDao dao, ContentValues cv,
                                      ProgressDialog pd) {
        boolean sucesso;
        sucesso = dao.salvar(pesquisaEditada.getId(), cv);
        eviarPesquisaParaGeracaoProspect(view, txtNomeEntrevistado,
                txtCep, txtRua, txtComplemento, txtNumero, txtBairro, txtCidade,
                spnEstado, txtTelefone, txtEmail, rgSexo, spnIdade, spnLocalPesquisa,
                spnOcupacao, spnEscolaridade, spnArea, spnOpcaoPos, spnInicioPos,
                spnParticiparSorteio, spnGraduacao, dao, sucesso, cv, pd);
    }

    private void eviarPesquisaParaGeracaoProspect(final View view,
                                                  final EditText txtNomeEntrevistado,
                                                  final EditText txtCep, final EditText txtRua,
                                                  final EditText txtComplemento, final EditText txtNumero,
                                                  final EditText txtBairro, final EditText txtCidade,
                                                  final Spinner spnEstado, final EditText txtTelefone,
                                                  final EditText txtEmail, final RadioGroup rgSexo,
                                                  final Spinner spnIdade, final Spinner spnLocalPesquisa,
                                                  final Spinner spnOcupacao, final Spinner spnEscolaridade,
                                                  final Spinner spnArea, final Spinner spnOpcaoPos,
                                                  final Spinner spnInicioPos, final Spinner spnParticiparSorteio,
                                                  final Spinner spnGraduacao, final PesquisaDao dao, boolean sucesso,
                                                  final ContentValues cv, final ProgressDialog pd) {
        if (sucesso) {
            final Pesquisa pesquisa = dao.retornarUltimo();

            pd.show();

            mAPIService = new Retrofit.Builder()
                    .baseUrl("http://18.216.169.97:9441/popbe/")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(APIService.class);

            mAPIService.savePost("Token " +
                    user.getToken() ,pesquisa)
                    .subscribeOn(Schedulers.newThread())
                    .takeUntil(Observable.timer(120, TimeUnit.SECONDS))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Pesquisa>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            pd.hide();
                            Snackbar.make(view, "Erro ao salvar a pesquisa! " + e.getMessage()
                                    , Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                        @Override
                        public void onNext(Pesquisa pesquisa) {
                            if (pesquisa.getOpcaoPos().equals("Sim gostaria")) {
                                pd.setTitle("Gerando Lead ...");
                            }

                            cv.clear();
                            cv.put("gerou_lead", pesquisa.getGerouLead() ? 1 : 0);
                            cv.put("unidade", pesquisa.getUnidade());
                            cv.put("status_lead", pesquisa.getStatusLead());
                            if (pesquisa.getGerouLead()) {
                                cv.put("id_lead", pesquisa.getIdLead());
                            }
                            dao.salvar(pesquisa.getId(), cv);

                            if (pesquisaEditada != null) {
                                pesquisaAdapter.atualizarCliente(pesquisa);
                                pesquisaEditada = null;
                            } else {
                                pesquisaAdapter.adicionarPesquisa(pesquisa);
                            }


                            txtNomeEntrevistado.setText("");
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
                            spnGraduacao.setSelection(0);
                            pd.hide();
                            Snackbar.make(view, "Pesquisa salva com sucesso", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            Intent intent = new Intent(CadastroActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

        } else {
            Snackbar.make(view, "Erro ao salvar a pesquisa! Verifique o preenchimento " +
                    "de todos os campo e tente novamente!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private boolean validarPreenchimentoCampos(
                                               String nomeEntrevistado, String cep, String numero,
                                               String rua, String cidade, String telefone,
                                               String email, String estado, String idade,
                                               String localPesquisa, String ocupacao,
                                               String escolaridade, String areaGraduacao,
                                               String pretencaoInicioPos, String paticiparSorteio) {
        awesomeValidation.validate();
        if (    StringUtils.isEmpty(nomeEntrevistado) ||
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
        if (intent.hasExtra("pesquisa")) {
            Integer idCliente = (Integer) intent.getSerializableExtra("pesquisa");
            pesquisaEditada = new PesquisaDao(this).getPesquisa(idCliente);
            TextView txtDataPesquisa = findViewById(R.id.txtDataPesquisa);
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
            Spinner spnGraduacao = findViewById(R.id.spnGostariaGraduacao);

            txtDataPesquisa.setText(pesquisaEditada.getDataPesquisa());
            txtNomeEntrevistado.setText(pesquisaEditada.getNomeEntrevistado());
            txtCep.setText(pesquisaEditada.getCep());
            txtRua.setText(pesquisaEditada.getRua());
            txtComplemento.setText(pesquisaEditada.getComplemento());
            txtNumero.setText(pesquisaEditada.getNumero());
            txtBairro.setText(pesquisaEditada.getBairro());
            txtCidade.setText(pesquisaEditada.getCidade());
            txtTelefone.setText(pesquisaEditada.getTelefone());
            txtEmail.setText(pesquisaEditada.getEmail());
            spnEstado.setSelection(getIndex(spnEstado, pesquisaEditada.getEstado()));
            spnIdade.setSelection(getIndex(spnIdade, pesquisaEditada.getIdade()));
            spnLocalPesquisa.setSelection(getIndex(spnLocalPesquisa, pesquisaEditada.getLocalPesquisa()));
            if (spnLocalPesquisa.getSelectedItem().toString().equals("Outro")) {
                EditText txtOutroLocal = findViewById(R.id.txtOutroLocal);
                txtOutroLocal.setText(pesquisaEditada.getOutroLocal());
            }
            spnOcupacao.setSelection(getIndex(spnOcupacao, pesquisaEditada.getOcupacao()));
            spnEscolaridade.setSelection(getIndex(spnEscolaridade, pesquisaEditada.getEscolaridade()));
            spnArea.setSelection(getIndex(spnArea, pesquisaEditada.getAreaGraduacao()));
            if (spnArea.getSelectedItem().toString().equals("Outra")) {
                EditText txtOutraArea = findViewById(R.id.txtOutroArea);
                txtOutraArea.setText(pesquisaEditada.getOutraArea());
            }
            spnOpcaoPos.setSelection(getIndex(spnOpcaoPos, pesquisaEditada.getOpcaoPos()));
            if (spnOpcaoPos.getSelectedItem().toString().equals("Sim gostaria")) {
                EditText txtQualPos = findViewById(R.id.txtQualPos);
                txtQualPos.setText(pesquisaEditada.getQualPos());
            }
            spnInicioPos.setSelection(getIndex(spnInicioPos, pesquisaEditada.getPretencaoInicioPos()));
            if (spnInicioPos.getSelectedItem().toString().equals("Em x meses")) {
                EditText txtInicio = findViewById(R.id.txtMesesInicioPos);
                txtInicio.setText(pesquisaEditada.getInicioPos());
            }

            spnGraduacao.setSelection(getIndex(spnGraduacao, pesquisaEditada.getDesejaGraduacao()));

            if (spnGraduacao.getSelectedItem().equals("Quero fazer a 1ª graduação")) {
                EditText txtPrimGrad = findViewById(R.id.txtInicioPrimGraduacao);
                txtPrimGrad.setText(pesquisaEditada.getInicioPrimeiraGraduacao());

            } else if (spnGraduacao.getSelectedItem().equals("Quero fazer a 2ª graduação")) {
                EditText txtSegGrad = findViewById(R.id.txtInicioSegGraduacao);
                txtSegGrad.setText(pesquisaEditada.getInicioSegundaGraduacao());
            }
            spnParticiparSorteio.setSelection(getIndex(spnParticiparSorteio, pesquisaEditada.getPaticiparSorteio()));
            if (pesquisaEditada.getSexo() != null) {
                RadioButton rb;
                if (pesquisaEditada.getSexo().equals("M")) {
                    rb = (RadioButton) findViewById(R.id.rbMasculino);
                    rb.setChecked(true);
                } else {
                    rb = (RadioButton) findViewById(R.id.rbFeminino);
                    rb.setChecked(true);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Endereco.REQUEST_ZIP_CODE_CODE
                && resultCode == RESULT_OK) {

            etZipCode.setText(data.getStringExtra(Endereco.ZIP_CODE_KEY));
        }
    }

    private String getZipCode() {
        return etZipCode.getText().toString();
    }

    public String getUriRequest() {
        return "https://viacep.com.br/ws/" + getZipCode() + "/json/";
    }

    public void lockFields(boolean isToLock) {
        util.lockFields(isToLock);
    }


    public void searchZipCode(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, Endereco.REQUEST_ZIP_CODE_CODE);
    }

    public void setAddressFields(Endereco address) {
        setField(R.id.et_street, address.getLogradouro());
//        setField( R.id.et_complement, address.getComplemento() );
        setField(R.id.et_neighbor, address.getBairro());
        setField(R.id.et_city, address.getLocalidade());
        setSpinner(R.id.sp_state, R.array.estados, address.getUf());

        if (address == null) {
            Snackbar.make(getCurrentFocus(),
                    "Não foi possível recuperar o cep informado", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    private void setField(int fieldId, String data) {
        ((EditText) findViewById(fieldId)).setText(data);
    }

    private void setSpinner(int fieldId, int arrayId, String uf) {
        Spinner spinner = (Spinner) findViewById(fieldId);
        String[] states = getResources().getStringArray(arrayId);

        for (int i = 0; i < states.length; i++) {
            if (states[i].endsWith("(" + uf + ")")) {
                spinner.setSelection(i);
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
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menuSair:
                SharedPreferences myPreferences =
                        PreferenceManager.getDefaultSharedPreferences(CadastroActivity.this);
                SharedPreferences.Editor myEditor = myPreferences.edit();
                myEditor.clear();
                myEditor.commit();
                acessarLoginActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void acessarLoginActivity() {
        Intent intent = new Intent(CadastroActivity.this,
                LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private Pesquisa pesquisaEditada = null;

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString()
                    .equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }
}
