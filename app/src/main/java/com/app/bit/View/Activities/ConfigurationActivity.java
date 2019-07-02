package com.app.bit.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.CompoundButton;

import com.app.bit.DAL.Entities.User;
import com.app.bit.DAL.Repositories.UserRepository;
import com.app.bit.R;
import com.app.bit.View.IntentExtras.Constants;
import com.app.bit.databinding.ActivityConfigurationBinding;

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
