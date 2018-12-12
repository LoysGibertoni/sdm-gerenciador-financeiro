package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import java.util.List;

import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model.Account;

@Dao
public interface AccountDao {

    @Query("SELECT a.id as id, a.description as description, a.initialBalance as initialBalance, a.initialBalance AS balance FROM account a" +
            " LEFT JOIN `transaction` c ON a.id = c.toAccountId" +
            " LEFT JOIN `transaction` d ON a.id = c.fromAccountId" +
            " GROUP BY a.id")
    List<Account> get();

    @Insert
    void insert(@NonNull Account... account);

    @Update
    void update(@NonNull Account... account);

    @Delete
    void delete(@NonNull Account... account);
}
