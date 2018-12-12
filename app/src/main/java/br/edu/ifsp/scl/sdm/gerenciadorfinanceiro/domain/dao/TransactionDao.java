package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import java.util.List;

import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model.Transaction;

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
