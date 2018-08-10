package com.domain.androidcrud;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import com.domain.androidcrud.activity.CadastroActivity;
import com.domain.androidcrud.request.AddressRequest;

public class ZipCodeListener implements TextWatcher {
    private Context context;

    public ZipCodeListener( Context context ){
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String zipCode = editable.toString();

        if( zipCode.length() == 8 ){
            new AddressRequest( (CadastroActivity) context ).execute();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
}
