package com.domain.androidcrud;

import android.app.Activity;
import android.content.RestrictionEntry;
import android.graphics.Color;
import android.util.Patterns;
import android.widget.Spinner;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.custom.CustomErrorReset;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidation;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidationCallback;

public class ValidadorCampos {
    public static final String NOT_EMPTY = "(?m)^\\s*\\S+[\\s\\S]*$";

    public static AwesomeValidation validarCamposObrigatorios(Activity activity)
    {
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(activity, R.id.txtNomeEntrevistador,
                NOT_EMPTY, R.string.campo_obrigatorio);
        awesomeValidation.addValidation(activity, R.id.txtNomeEntrevistado,
                NOT_EMPTY, R.string.campo_obrigatorio);
        awesomeValidation.addValidation(activity, R.id.txtUnidadeEntrevistador,
                NOT_EMPTY, R.string.campo_obrigatorio);
        awesomeValidation.addValidation(activity, R.id.txtEmail,
                Patterns.EMAIL_ADDRESS, R.string.campo_obrigatorio);
        awesomeValidation.addValidation(activity, R.id.txtTelefone,
                NOT_EMPTY, R.string.campo_obrigatorio);

        validarCamposEndereco(activity, awesomeValidation);
        validarSppinner(activity, awesomeValidation, R.id.spnAreaGraduacao, "Selecione ...");
        validarSppinner(activity, awesomeValidation, R.id.spnIdade, "Selecione ...");
        validarSppinner(activity, awesomeValidation, R.id.spnLocalPesquisa, "Selecione ...");
        validarSppinner(activity, awesomeValidation, R.id.spnOcupacao, "Selecione ...");
        validarSppinner(activity, awesomeValidation, R.id.spnGostariaPos, "Selecione ...");
        validarSppinner(activity, awesomeValidation, R.id.spnSorteio, "Selecione ...");
        validarSppinner(activity, awesomeValidation, R.id.spnGostariaGraduacao, "Selecione ...");
        validarSppinner(activity, awesomeValidation, R.id.spnEscolaridade, "Selecione ...");

        return awesomeValidation;
    }

    private static void validarSppinner(Activity activity, AwesomeValidation awesomeValidation,
                                        Integer idSpinner, final String spinnerEmptyValue) {
        awesomeValidation.addValidation(activity, idSpinner, new CustomValidation() {
            @Override
            public boolean compare(ValidationHolder validationHolder) {
                if (((Spinner) validationHolder.getView()).getSelectedItem().toString().equals(spinnerEmptyValue)) {
                    return false;
                } else {
                    return true;
                }
            }
        }, new CustomValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(validationHolder.getErrMsg());
                textViewError.setTextColor(Color.RED);
            }
        }, new CustomErrorReset() {
            @Override
            public void reset(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(null);
                textViewError.setTextColor(Color.BLACK);
            }
        }, R.string.campo_obrigatorio);
    }

    private static void validarCamposEndereco(Activity activity, AwesomeValidation awesomeValidation) {
        awesomeValidation.addValidation(activity, R.id.txtZipCode,
                NOT_EMPTY, R.string.campo_obrigatorio);
        awesomeValidation.addValidation(activity, R.id.et_street,
                NOT_EMPTY, R.string.campo_obrigatorio);
        awesomeValidation.addValidation(activity, R.id.et_neighbor,
                NOT_EMPTY, R.string.campo_obrigatorio);
        awesomeValidation.addValidation(activity, R.id.et_city,
                NOT_EMPTY, R.string.campo_obrigatorio);
        awesomeValidation.addValidation(activity, R.id.et_number,
                NOT_EMPTY, R.string.campo_obrigatorio);
        awesomeValidation.addValidation(activity, R.id.tv_zip_code_search,
                NOT_EMPTY, R.string.campo_obrigatorio);
        validarSppinner(activity, awesomeValidation, R.id.sp_state, "Estado");

    }
}
