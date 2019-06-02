package com.example.bit.View;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bit.DAL.Entities.User;
import com.example.bit.R;
import com.example.bit.databinding.ActivityHomeBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding bindingContent;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bindingContent = DataBindingUtil.setContentView(this, R.layout.activity_home);

        setSupportActionBar(bindingContent.homeToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        user = (User) getIntent().getSerializableExtra("User");
        bindingContent.response.setText("Olá " + user.getName() + "!");
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
                break;
            case R.id.action_configurations:
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
