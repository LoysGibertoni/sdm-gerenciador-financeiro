package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation.account;

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
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.databinding.FragmentAccountsBinding;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.AppDatabase;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model.Account;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation.common.BindableItemAdapter;

public class AccountsFragment extends Fragment {

    private FragmentAccountsBinding mBinding;
    private BindableItemAdapter<Account> mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_accounts, container, false);
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
        mAdapter = new BindableItemAdapter<>(R.layout.item_account);
        mAdapter.setItems(AppDatabase.getInstance().getAccountDao().get());
        mBinding.rvAccounts.setAdapter(mAdapter);
    }

    private void bindListeners() {
        mBinding.fabAdd.setOnClickListener(v -> this.startActivityForResult(new Intent(getContext(), AccountAddActivity.class),
                AccountAddActivity.REQUEST_CODE));
        mAdapter.setOnItemClickListener(account -> this.startActivityForResult(new Intent(getContext(), AccountDetailsActivity.class)
                .putExtra(AccountDetailsActivity.EXTRA_ACCOUNT, account), AccountDetailsActivity.REQUEST_CODE));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Caso receba resultado de alguma das activities iniciadas
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AccountAddActivity.REQUEST_CODE) {
                // Adiciona o item ao adapter
                mAdapter.addItem((Account) data.getSerializableExtra(AccountAddActivity.EXTRA_ACCOUNT));
            } else if (requestCode == AccountDetailsActivity.REQUEST_CODE) {
                // Remove o item do adapter
                mAdapter.removeItem((Account) data.getSerializableExtra(AccountDetailsActivity.EXTRA_ACCOUNT));
            }
        }
    }
}
