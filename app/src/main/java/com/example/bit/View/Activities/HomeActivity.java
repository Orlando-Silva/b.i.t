package com.example.bit.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.bit.DAL.Entities.User;
import com.example.bit.R;
import com.example.bit.View.Fragments.ActionBarFragment;
import com.example.bit.View.IntentExtras.Constants;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Página Inicial");
        user = (User) getIntent().getSerializableExtra(Constants.USER_INTENT);
        ((TextView)findViewById(R.id.response)).setText("Olá " + user.getName() + "!");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i = null;
        switch (id)  {
            case R.id.action_editAccount:
                i = ActionBarFragment.EditUser(this, user);
                startActivity(i);
                break;
            case R.id.action_configurations:
                break;
            case R.id.action_logout:
                i = ActionBarFragment.Logout(this);
                startActivity(i);
                break;
            case android.R.id.home:
                finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}
