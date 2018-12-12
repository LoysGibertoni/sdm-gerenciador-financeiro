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

        this.bindData();
        this.bindListeners();
    }

    private void bindData() {
        mAdapter = new BindableItemAdapter<>(R.layout.item_account);
        mAdapter.setItems(AppDatabase.getInstance().getAccountDao().get());
        mBinding.rvAccounts.setAdapter(mAdapter);
    }

    private void bindListeners() {
        mBinding.fabAdd.setOnClickListener(v -> this.startDetailsActivity(null));
        mAdapter.setOnItemClickListener(this::startDetailsActivity);
    }

    private void startDetailsActivity(Account account) {
        final Intent intent = new Intent(getContext(), AccountDetailsActivity.class);
        if (account != null) {
            intent.putExtra(AccountDetailsActivity.EXTRA_ACCOUNT, account);
        }
        startActivityForResult(intent, AccountDetailsActivity.REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == AccountDetailsActivity.REQUEST_CODE) {
            mAdapter.setItems(AppDatabase.getInstance().getAccountDao().get());
        }
    }
}
