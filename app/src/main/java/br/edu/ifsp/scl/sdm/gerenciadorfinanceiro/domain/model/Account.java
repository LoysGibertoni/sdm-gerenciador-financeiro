package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Account {

    @PrimaryKey(autoGenerate = true)
    public Long id;
    public String description;
    public Double initialBalance;
    @Ignore
    public Double balance;

    public Account() {
    }
}
