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
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.navAccounts:
                fragment = new AccountsFragment();
                break;
            case R.id.navTransactions:
                break;
            case R.id.navStatements:
                break;
        }

        if (fragment != null) {
            this.replaceFragment(fragment);
            return true;
        } else {
            return false;
        }
    }

    public void replaceFragment(@NonNull Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, fragment)
                .commitAllowingStateLoss();
    }
}
