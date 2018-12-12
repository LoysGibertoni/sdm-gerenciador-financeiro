package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation.util;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.widget.TextView;

public class BindingAdapters {

    @BindingAdapter("android:text")
    public static void setText(TextView view, Double value) {
        view.setText(value == null ? null : String.valueOf(value));
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static double getText(TextView view) {
        try {
            return Double.parseDouble(view.getText().toString());
        } catch (NumberFormatException ex) {
            return 0.0;
        }
    }
}
