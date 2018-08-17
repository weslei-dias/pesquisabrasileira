package com.domain.androidcrud.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.domain.androidcrud.R;
import com.domain.androidcrud.dao.UserDao;
import com.domain.androidcrud.model.User;
import com.domain.androidcrud.request.APIService;
import com.domain.androidcrud.request.ApiUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAPIService = ApiUtils.getAPIServiceVetrol();

        Button btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                EditText txtUsername = findViewById(R.id.txtUsername);
                EditText txtPassword = findViewById(R.id.txtPassword);
                final String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                pd.setTitle("Acessando...");
                pd.setMessage("Aguarde um momento");
                pd.setCancelable(true);
                pd.show();
                mAPIService.login(username, password).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<User>() {
                            @Override
                            public void onCompleted() {
                                pd.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                pd.dismiss();
                                Snackbar.make(getCurrentFocus(), "Username ou senha inválidos. " +
                                                "Verifique e tente novamente"
                                        , Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }

                            @Override
                            public void onNext(User user) {
                                UserDao userDao = new UserDao(getApplicationContext());
                                ContentValues cv = new ContentValues();
                                cv.put("username", username);
                                cv.put("token", user.getToken());
                                cv.put("nome", user.getNome());
                                cv.put("unidade", user.getUnidade());

                                User userJaRegistrado = userDao.getUsuarioComUsername(username);
                                boolean salvou = userDao.salvar(userJaRegistrado.getId(), cv);

                                if (salvou){

                                    SharedPreferences myPreferences =
                                            PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                    SharedPreferences.Editor myEditor = myPreferences.edit();
                                    myEditor.putString("username", username);
                                    myEditor.commit();

                                    Snackbar.make(getCurrentFocus(), "Seja bem vindo " + user.getNome()
                                            , Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    acessarMainActivity();
                                } else {
                                    Snackbar.make(getCurrentFocus(), "Username ou senha inválidos. " +
                                                    "Verifique e tente novamente"
                                            , Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        });

            }
        });
    }

    private void acessarMainActivity() {
        Intent intent = new Intent(LoginActivity.this,
                MainActivity.class);
        startActivity(intent);
        finish();
    }
}
