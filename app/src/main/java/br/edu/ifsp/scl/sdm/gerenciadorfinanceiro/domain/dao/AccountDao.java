package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.dao;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model.Account;

import java.util.List;

@Dao
public interface AccountDao {

    @Query("SELECT * FROM account")
    List<Account> get();

    @Insert
    void insert(@NonNull Account... account);

    @Update
    void update(@NonNull Account... account);

    @Delete
    void delete(@NonNull Account... account);
}
