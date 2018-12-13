package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation.util;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class ValidationUtils {

    public static boolean validateRequiredFields(View... fields) {
        for (final View field : fields) {
            if (field.getVisibility() == View.VISIBLE) {
                if (field instanceof EditText && TextUtils.isEmpty(((EditText) field).getText().toString())) {
                    return false;
                } else if (field instanceof Spinner && ((Spinner) field).getSelectedItem() == null) {
                    return false;
                } else if (field instanceof RadioGroup && ((RadioGroup) field).getCheckedRadioButtonId() == View.NO_ID) {
                    return false;
                }
            }
        }
        return true;
    }
}
