package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.dao.AccountDao;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.dao.TransactionDao;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model.Account;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.model.Transaction;

@Database(entities = {Account.class, Transaction.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String NAME = "gf-db";

    // Singleton que será instanciado ao iniciar a aplicação, já que esse é um processo custoso
    private static AppDatabase mInstance;

    public static void createInstance(@NonNull Context context) {
        // Criar a instância da base de dados
        mInstance = Room.databaseBuilder(context, AppDatabase.class, NAME).allowMainThreadQueries().build();
    }

    public static AppDatabase getInstance() {
        return mInstance;
    }

    // DAOs
    public abstract AccountDao getAccountDao();
    public abstract TransactionDao getTransactionDao();
}