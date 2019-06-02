package com.example.bit.View;

import android.os.Bundle;
import android.app.Activity;

import com.example.bit.R;
import com.example.bit.databinding.ActivityDepositsBinding;

import androidx.databinding.DataBindingUtil;

public class DepositActivity extends Activity {

    ActivityDepositsBinding bindingContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposits);
        bindingContent = DataBindingUtil.setContentView(this, R.layout.activity_deposits);

    }

}
