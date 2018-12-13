package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation.account;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.R;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.databinding.ActivityAccountDetailsBinding;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.AppDatabase;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model.Account;

public class AccountDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_ACCOUNT = "AccountDetailsActivity.Account";

    public static final int REQUEST_CODE = 2;

    private ActivityAccountDetailsBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_account_details);

        // Atribui os dados necessários e cria o listener do botão
        mBinding.setAccount((Account) getIntent().getSerializableExtra(EXTRA_ACCOUNT));
        mBinding.btRemove.setOnClickListener(v -> this.remove());
    }

    private void remove() {
        // Remove a conta
        final Account account = mBinding.getAccount();
        AppDatabase.getInstance().getAccountDao().delete(account);

        // Seta o resultado OK indicando que a conta foi removida
        setResult(RESULT_OK, new Intent().putExtra(EXTRA_ACCOUNT, account));
        finish();
    }
}
