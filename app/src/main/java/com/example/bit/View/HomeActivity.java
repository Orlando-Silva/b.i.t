package com.example.bit.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bit.DAL.Entities.User;
import com.example.bit.R;
import com.example.bit.Workers.PendingDepositWorker;
import com.example.bit.Workers.VerifyDepositWorker;
import com.example.bit.databinding.ActivityHomeBinding;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.work.Configuration;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding bindingContent;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bindingContent = DataBindingUtil.setContentView(this, R.layout.activity_home);

        Configuration myConfig = new Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.INFO)
                .build();




        setSupportActionBar(bindingContent.homeToolbar);
        setTitle("Página inicial");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        user = (User) getIntent().getSerializableExtra("User");
        bindingContent.response.setText("Olá " + user.getName() + "!");

        bindingContent.depositsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Deposits(v);
            }
        });
    }

    public void Deposits(View view) {
        Intent i = new Intent(HomeActivity.this, DepositActivity.class);
        i.putExtra("User", user);
        startActivity(i);
        finish();
    }

    public void EditUser() {
        Intent i = new Intent(HomeActivity.this, EditUserActivity.class);
        i.putExtra("User", user);
        startActivity(i);
    }

    public void Logout() {
        Intent i = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)  {
            case R.id.action_editAccount:
                EditUser();
                break;
            case R.id.action_configurations:
                break;
            case R.id.action_logout:
                Logout();
                break;
            case android.R.id.home:
                this.finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
