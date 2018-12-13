package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation.statement;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.R;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.databinding.FragmentStatementsBinding;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.AppDatabase;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.dao.TransactionDao;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model.Account;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model.Transaction;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation.common.BindableItemAdapter;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation.transaction.TransactionDetailsActivity;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation.util.ValidationUtils;

public class StatementsFragment extends Fragment {

    private FragmentStatementsBinding mBinding;
    private BindableItemAdapter<Transaction> mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_statements, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Atribui os dados necessários e cria os listeners de click
        this.setAdapters();
        this.setDatePickers();
        this.bindListeners();
    }

    private void setAdapters() {
        // Cria o adapter de transações e o atibui ao RecyclerView
        mAdapter = new BindableItemAdapter<>(R.layout.item_transaction);
        mBinding.rvTransactions.setAdapter(mAdapter);

        // Cria o adapter de contas e o atibui ao Spinner
        final ArrayAdapter<Account> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, AppDatabase.getInstance().getAccountDao().get());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.spAccount.setAdapter(adapter);
    }

    private void bindListeners() {
        mBinding.rgFilterType.setOnCheckedChangeListener((group, checkedId) -> {
            mBinding.gpAccount.setVisibility(checkedId == mBinding.rbAccount.getId() ? View.VISIBLE : View.GONE);
            mBinding.gpTransactionOperation.setVisibility(checkedId == mBinding.rbTransactionOperation.getId() ? View.VISIBLE : View.GONE);
            mBinding.gpTransactionType.setVisibility(checkedId == mBinding.rbTransactionType.getId() ? View.VISIBLE : View.GONE);
        });
        mAdapter.setOnItemClickListener(account -> this.startActivityForResult(new Intent(getContext(), TransactionDetailsActivity.class)
                .putExtra(TransactionDetailsActivity.EXTRA_TRANSACTION, account), TransactionDetailsActivity.REQUEST_CODE));
        mBinding.btApply.setOnClickListener(v -> this.apply());
    }

    private void setDatePickers() {
        final Calendar calendar = Calendar.getInstance();

        final DatePickerDialog fromDatePicker = new DatePickerDialog(Objects.requireNonNull(getContext()), (view, year, month, dayOfMonth) ->
                mBinding.etFrom.setText(String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year)),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        mBinding.etFrom.setOnClickListener(v -> fromDatePicker.show());

        final DatePickerDialog untilDatePicker = new DatePickerDialog(Objects.requireNonNull(getContext()), (view, year, month, dayOfMonth) ->
                mBinding.etUntil.setText(String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year)),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        mBinding.etUntil.setOnClickListener(v -> untilDatePicker.show());
    }

    private void apply() {
        // Verifica se todos os campos foram preenchidos
        if (!ValidationUtils.validateRequiredFields(mBinding.rgFilterType, mBinding.spAccount,
                mBinding.tilFrom, mBinding.tilUntil, mBinding.rgOperation, mBinding.spType)) {
            Snackbar.make(mBinding.getRoot(), R.string.fill_all_fields, Snackbar.LENGTH_LONG).show();
            return;
        }

        final TransactionDao transactionDao = AppDatabase.getInstance().getTransactionDao();
        if (mBinding.rbAccount.isChecked()) {
            final long accountId = ((Account) mBinding.spAccount.getSelectedItem()).id;
            final String fromDate = this.getDate(Objects.requireNonNull(mBinding.etFrom.getText()).toString());
            final String toDate = this.getDate(Objects.requireNonNull(mBinding.etUntil.getText()).toString());
            mAdapter.setItems(transactionDao.get(accountId, fromDate, toDate));
        } else if (mBinding.rbTransactionOperation.isChecked()) {
            if (mBinding.rbCredit.isChecked()) {
                mAdapter.setItems(transactionDao.getCredits());
            } else if (mBinding.rbDebit.isChecked()) {
                mAdapter.setItems(transactionDao.getDebits());
            } else if (mBinding.rbTransfer.isChecked()) {
                mAdapter.setItems(transactionDao.getTransfers());
            }
        } else if (mBinding.rbTransactionType.isChecked()) {
            mAdapter.setItems(transactionDao.get(mBinding.spType.getSelectedItem().toString()));
        }
    }

    private String getDate(@NonNull String formattedDate) {
        final List<String> split = Arrays.asList(formattedDate.split("/"));
        Collections.reverse(split);
        return TextUtils.join("-", split);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Caso receba resultado da activity de detalhes
        if (resultCode == Activity.RESULT_OK && requestCode == TransactionDetailsActivity.REQUEST_CODE) {
            // Remove o item do adapter
            mAdapter.removeItem((Transaction) data.getSerializableExtra(TransactionDetailsActivity.EXTRA_TRANSACTION));
        }
    }
}
