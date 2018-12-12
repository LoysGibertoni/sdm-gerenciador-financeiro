package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation.account;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.R;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.databinding.ActivityAccountDetailsBinding;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.AppDatabase;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.dao.AccountDao;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model.Account;

public class AccountDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_ACCOUNT = "AccountDetailsActivity.Account";

    public static final int REQUEST_CODE = 1;

    private ActivityAccountDetailsBinding mBinding;
    private AccountDao mAccountDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Seta o conteúdo dessa activity e obtém o AccountDao para salvar ou remover a conta futuramente
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_account_details);
        mAccountDao = AppDatabase.getInstance().getAccountDao();

        // Atribui os dados necessários e cria os listeners dos botões
        this.bindData();
        this.bindListeners();
    }

    private void bindData() {
        if (getIntent().hasExtra(EXTRA_ACCOUNT)) {
            // Se recebeu uma conta via Bundle, utiliza-a na view
            mBinding.setAccount((Account) getIntent().getSerializableExtra(EXTRA_ACCOUNT));
        } else {
            // Se não recebeu, cria uma nova
            mBinding.setAccount(new Account());
        }
    }

    private void bindListeners() {
        mBinding.btSave.setOnClickListener(v -> this.save());
        mBinding.btRemove.setOnClickListener(v -> this.remove());
    }

    private void save() {
        // Insere ou atualiza a conta
        final Account account = mBinding.getAccount();
        if (account.id == null) {
            mAccountDao.insert(account);
        } else {
            mAccountDao.update(account);
        }

        // Seta o resultado OK indicando que houve alteração e passa a conta atualizada
        setResult(RESULT_OK, new Intent().putExtra(EXTRA_ACCOUNT, account));
        finish();
    }

    private void remove() {
        // Remove a conta, caso ela já exista no banco
        final Account account = mBinding.getAccount();
        if (account.id != null) {
            mAccountDao.delete(account);
            // Seta o resultado OK indicando que houve alteração, mas não passa a conta, já que ela foi removida
            setResult(RESULT_OK);
        } else {
            // Seta o resultado CANCELED indicando que a inserção foi cancelada
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}
