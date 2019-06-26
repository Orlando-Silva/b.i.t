package com.example.bit.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.CompoundButton;

import com.example.bit.DAL.Entities.User;
import com.example.bit.DAL.Repositories.UserRepository;
import com.example.bit.R;
import com.example.bit.View.IntentExtras.Constants;
import com.example.bit.databinding.ActivityConfigurationBinding;

public class ConfigurationActivity extends AppCompatActivity {

    User user;
    UserRepository mUserRepository;
    ActivityConfigurationBinding mBindingContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        mBindingContent = DataBindingUtil.setContentView(this, R.layout.activity_configuration);
        recoverUserFromIntent();
        mUserRepository = new UserRepository(getApplication());
        user = mUserRepository.get(user.getId());
        mBindingContent.sendEmailOption.setChecked(user.isReceivesEmailOnWithdraw());
        mBindingContent.sendEmailOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                user.setReceivesEmailOnWithdraw(isChecked);
                mUserRepository.update(user);
            }
        });

    }

    private void recoverUserFromIntent() {
        user = (User) getIntent().getSerializableExtra(Constants.USER_INTENT);
    }
}
