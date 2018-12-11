package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.dao;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {

    @Query("SELECT * FROM `transaction`")
    List<Transaction> get();

    @Insert
    void insert(@NonNull Transaction... transactions);

    @Update
    void update(@NonNull Transaction... transactions);

    @Delete
    void delete(@NonNull Transaction... actransactionscount);
}
