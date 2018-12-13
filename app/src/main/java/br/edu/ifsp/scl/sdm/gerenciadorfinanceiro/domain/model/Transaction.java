package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.AppDatabase;

@Entity(foreignKeys = {
        @ForeignKey(entity = Account.class, parentColumns = "id", childColumns = "originAccountId"),
        @ForeignKey(entity = Account.class, parentColumns = "id", childColumns = "destinationAccountId")
    },
    indices = {
        @Index("originAccountId"),
        @Index("destinationAccountId")
    }
)
public class Transaction implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long id;
    public String description;
    public Double value;
    public String type;
    public Long originAccountId;
    public Long destinationAccountId;
    public String date;

    public Transaction() {
    }

    public Account getOriginAccount() {
        if (this.originAccountId != null) {
            return AppDatabase.getInstance().getAccountDao().get(this.originAccountId);
        }
        return null;
    }

    public Account getDestinationAccount() {
        if (this.destinationAccountId != null) {
            return AppDatabase.getInstance().getAccountDao().get(this.destinationAccountId);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }
}
