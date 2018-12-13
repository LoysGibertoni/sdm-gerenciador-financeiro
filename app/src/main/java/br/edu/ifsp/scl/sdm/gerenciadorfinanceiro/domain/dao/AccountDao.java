package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import java.util.List;

import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model.Account;

@Dao
public interface AccountDao {

    @Query("SELECT a.id as id," +
            " a.description as description," +
            " a.initialBalance as initialBalance," +
            " (a.initialBalance + COALESCE(SUM(CASE WHEN t.destinationAccountId = a.id THEN t.value ELSE NULL END), 0) - COALESCE(SUM(CASE WHEN t.originAccountId = a.id THEN t.value ELSE NULL END), 0)) AS balance" +
            " FROM account a" +
            " LEFT JOIN `transaction` t ON ((a.id = t.originAccountId OR a.id = t.destinationAccountId) AND t.date <= DATE('now'))" +
            " GROUP BY a.id")
    List<Account> get();

    @Query("SELECT a.id as id," +
            " a.description as description," +
            " a.initialBalance as initialBalance," +
            " (a.initialBalance + COALESCE(SUM(CASE WHEN t.destinationAccountId = a.id THEN t.value ELSE NULL END), 0) - COALESCE(SUM(CASE WHEN t.originAccountId = a.id THEN t.value ELSE NULL END), 0)) AS balance" +
            " FROM account a" +
            " LEFT JOIN `transaction` t ON ((a.id = t.originAccountId OR a.id = t.destinationAccountId) AND t.date <= DATE('now'))" +
            " WHERE a.id = :id")
    Account get(long id);

    @Insert
    long insert(@NonNull Account account);

    @Delete
    void delete(@NonNull Account account);
}
