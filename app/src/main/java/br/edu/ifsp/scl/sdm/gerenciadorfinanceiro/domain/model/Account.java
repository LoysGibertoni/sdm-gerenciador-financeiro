package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Account implements Serializable, Comparable<Account> {

    @PrimaryKey(autoGenerate = true)
    public Long id;
    public String description;
    public Double initialBalance;
    public Double balance;

    public Account() {
    }

    @NonNull
    @Override
    public String toString() {
        return this.description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int compareTo(@NonNull Account o) {
        return (int) (this.id - o.id);
    }
}
