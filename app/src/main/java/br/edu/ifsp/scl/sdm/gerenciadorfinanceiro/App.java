package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro;

import android.app.Application;

import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.domain.AppDatabase;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Ao inciar o app, instancia o banco de dados
        AppDatabase.createInstance(this);
    }
}
