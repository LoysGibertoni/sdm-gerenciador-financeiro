package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation.transaction;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.Calendar;
import java.util.Locale;

import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.R;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.databinding.ActivityTransactionAddBinding;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.AppDatabase;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model.Account;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model.Transaction;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation.util.ValidationUtils;

public class TransactionAddActivity extends AppCompatActivity {
    public static final String EXTRA_TRANSACTION = "TransactionAddActivity.Transaction";

    public static final int REQUEST_CODE = 3;

    private ActivityTransactionAddBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_transaction_add);

        // Atribui os dados necessários e cria os listeners dos botões
        mBinding.setTransaction(new Transaction());
        this.setAdapter();
        this.setDatePicker();
        mBinding.rgOperation.setOnCheckedChangeListener((group, checkedId) -> {
            mBinding.gpOriginAccount.setVisibility(checkedId != mBinding.rbCredit.getId() ? View.VISIBLE : View.GONE);
            mBinding.gpDestinationAccount.setVisibility(checkedId != mBinding.rbDebit.getId() ? View.VISIBLE : View.GONE);
        });
        mBinding.btSave.setOnClickListener(v -> this.save());
    }

    private void setAdapter() {
        // Cria o adapter a o atibui aos Spinners de conta
        final ArrayAdapter<Account> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, AppDatabase.getInstance().getAccountDao().get());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.spOriginAccount.setAdapter(adapter);
        mBinding.spDestinationAccount.setAdapter(adapter);
    }

    private void setDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            mBinding.getTransaction().date = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
            mBinding.etDate.setText(mBinding.getTransaction().getFormattedDate());
        } ,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        mBinding.etDate.setOnClickListener(v -> dialog.show());
    }

    private void save() {
        // Verifica se todos os campos foram preenchidos
        if (!ValidationUtils.validateRequiredFields(mBinding.rgOperation, mBinding.tilDescription, mBinding.spOriginAccount,
                mBinding.spDestinationAccount, mBinding.tilValue, mBinding.spType, mBinding.tilDate)) {
            Snackbar.make(mBinding.getRoot(), R.string.fill_all_fields, Snackbar.LENGTH_LONG).show();
            return;
        }

        // Insere a transação
        final Transaction transaction = mBinding.getTransaction();
        transaction.type = mBinding.spType.getSelectedItem().toString();
        if (!mBinding.rbCredit.isChecked()) {
            transaction.originAccountId = ((Account) mBinding.spOriginAccount.getSelectedItem()).id;
        }
        if (!mBinding.rbDebit.isChecked()) {
            transaction.destinationAccountId = ((Account) mBinding.spDestinationAccount.getSelectedItem()).id;
        }
        transaction.id = AppDatabase.getInstance().getTransactionDao().insert(transaction);

        // Seta o resultado OK indicando que a transação foi inserida
        setResult(RESULT_OK, new Intent().putExtra(EXTRA_TRANSACTION, transaction));
        finish();
    }
}
