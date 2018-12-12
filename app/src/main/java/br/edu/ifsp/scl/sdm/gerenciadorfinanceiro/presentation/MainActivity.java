package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.R;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.databinding.ActivityMainBinding;
import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation.account.AccountsFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.nvNavigation.setOnNavigationItemSelectedListener(this);
        binding.nvNavigation.setSelectedItemId(R.id.navAccounts);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navAccounts:
                this.replaceFragment(new AccountsFragment());
                return true;
            case R.id.navTransactions:
                return true;
            case R.id.navStatements:
                return true;
        }
        return false;
    }

    public void replaceFragment(@NonNull Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, fragment)
                .commitAllowingStateLoss();
    }
}
