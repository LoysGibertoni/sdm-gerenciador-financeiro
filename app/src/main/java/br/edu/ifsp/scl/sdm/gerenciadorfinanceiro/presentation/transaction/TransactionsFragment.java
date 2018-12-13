package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation.transaction;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.R;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.databinding.FragmentTransactionsBinding;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.AppDatabase;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model.Transaction;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation.common.BindableItemAdapter;

public class TransactionsFragment extends Fragment {

    private FragmentTransactionsBinding mBinding;
    private BindableItemAdapter<Transaction> mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_transactions, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Atribui os dados necessários e cria os listeners de click
        this.bindData();
        this.bindListeners();
    }

    private void bindData() {
        mAdapter = new BindableItemAdapter<>(R.layout.item_transaction);
        mAdapter.setItems(AppDatabase.getInstance().getTransactionDao().get());
        mBinding.rvTransactions.setAdapter(mAdapter);
    }

    private void bindListeners() {
        mBinding.fabAdd.setOnClickListener(v -> this.startActivityForResult(new Intent(getContext(), TransactionAddActivity.class),
                TransactionAddActivity.REQUEST_CODE));
        mAdapter.setOnItemClickListener(account -> this.startActivityForResult(new Intent(getContext(), TransactionDetailsActivity.class)
                .putExtra(TransactionDetailsActivity.EXTRA_TRANSACTION, account), TransactionDetailsActivity.REQUEST_CODE));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Caso receba resultado de alguma das activities iniciadas
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == TransactionAddActivity.REQUEST_CODE) {
                // Adiciona o item ao adapter
                mAdapter.addItem((Transaction) data.getSerializableExtra(TransactionAddActivity.EXTRA_TRANSACTION));
            } else if (requestCode == TransactionDetailsActivity.REQUEST_CODE) {
                // Remove o item do adapter
                mAdapter.removeItem((Transaction) data.getSerializableExtra(TransactionDetailsActivity.EXTRA_TRANSACTION));
            }
        }
    }
}
