package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation.account;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.R;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.databinding.ActivityAccountAddBinding;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.AppDatabase;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model.Account;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation.util.ValidationUtils;

public class AccountAddActivity extends AppCompatActivity {
    public static final String EXTRA_ACCOUNT = "AccountAddActivity.Account";

    public static final int REQUEST_CODE = 1;

    private ActivityAccountAddBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_account_add);

        // Atribui os dados necessários e cria o listener do botão
        mBinding.setAccount(new Account());
        mBinding.btSave.setOnClickListener(v -> this.save());
    }

    private void save() {
        // Verifica se todos os campos foram preenchidos
        if (!ValidationUtils.validateRequiredFields(mBinding.tilDescription, mBinding.tilDescription)) {
            Snackbar.make(mBinding.getRoot(), R.string.fill_all_fields, Snackbar.LENGTH_LONG).show();
            return;
        }

        // Insere a conta
        final Account account = mBinding.getAccount();
        account.id = AppDatabase.getInstance().getAccountDao().insert(account);
        account.balance = account.initialBalance;

        // Seta o resultado OK indicando que a conta foi adicionada
        setResult(RESULT_OK, new Intent().putExtra(EXTRA_ACCOUNT, account));
        finish();
    }

}