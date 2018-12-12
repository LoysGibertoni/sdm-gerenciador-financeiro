package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Account implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long id;
    public String description;
    public Double initialBalance;
    public Double balance;

    public Account() {
    }
}
