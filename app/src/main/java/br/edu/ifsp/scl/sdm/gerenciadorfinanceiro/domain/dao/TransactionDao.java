package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import java.util.List;

import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model.Transaction;

@Dao
public interface TransactionDao {

    @Query("SELECT * FROM `transaction`")
    List<Transaction> get();

    @Query("SELECT * FROM `transaction`" +
            " WHERE (originAccountId = :accountId OR destinationAccountId = :accountId)" +
            " AND date >= :fromDate AND date <= :toDate")
    List<Transaction> get(long accountId, @NonNull String fromDate, @NonNull String toDate);

    @Query("SELECT * FROM `transaction`" +
            " WHERE originAccountId IS NULL" +
            " AND destinationAccountId IS NOT NULL")
    List<Transaction> getCredits();

    @Query("SELECT * FROM `transaction`" +
            " WHERE originAccountId IS NOT NULL" +
            " AND destinationAccountId IS NULL")
    List<Transaction> getDebits();

    @Query("SELECT * FROM `transaction`" +
            " WHERE originAccountId IS NOT NULL" +
            " AND destinationAccountId IS NOT NULL")
    List<Transaction> getTransfers();

    @Query("SELECT * FROM `transaction`" +
            " WHERE type = :type")
    List<Transaction> get( @NonNull String type);

    @Insert
    long insert(@NonNull Transaction transactions);

    @Delete
    void delete(@NonNull Transaction transactions);
}
