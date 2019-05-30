package com.example.bit.View;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.bit.DAL.Entities.User;
import com.example.bit.R;
import com.example.bit.databinding.ActivityHomeBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        bindingContent.response.setText("Olá" + user.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mainmenu, menu);

        MenuItem item = menu.findItem(R.id.settingsIcon);

        Spinner spinner = (Spinner) item.getActionView();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String[] options = getResources().getStringArray(R.array.Options);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Options, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return true;
    }

}
