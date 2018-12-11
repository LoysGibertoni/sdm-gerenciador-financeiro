package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Account.class, parentColumns = "id", childColumns = "fromAccountId"),
        @ForeignKey(entity = Account.class, parentColumns = "id", childColumns = "toAccountId")
})
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    public Long id;
    public String description;
    public Double value;
    public String type;
    public Long fromAccountId;
    public Long toAccountId;

    public Transaction() {
    }
}
