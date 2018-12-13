package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation.transaction;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.R;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.databinding.ActivityTransactionDetailsBinding;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.AppDatabase;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model.Transaction;

public class TransactionDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_TRANSACTION = "TransactionDetailsActivity.Transaction";

    public static final int REQUEST_CODE = 4;

    private ActivityTransactionDetailsBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_transaction_details);

        // Atribui os dados necessários e cria o listener do botão
        mBinding.setTransaction((Transaction) getIntent().getSerializableExtra(EXTRA_TRANSACTION));
        mBinding.btRemove.setOnClickListener(v -> this.remove());
    }

    private void remove() {
        // Remove a transação
        final Transaction transaction = mBinding.getTransaction();
        AppDatabase.getInstance().getTransactionDao().delete(transaction);

        // Seta o resultado OK indicando que a transação foi removida
        setResult(RESULT_OK, new Intent().putExtra(EXTRA_TRANSACTION, transaction));
        finish();
    }
}
