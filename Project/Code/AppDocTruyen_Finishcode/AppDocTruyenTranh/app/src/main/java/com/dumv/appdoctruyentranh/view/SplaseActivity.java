package com.dumv.appdoctruyentranh.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.dumv.appdoctruyentranh.databinding.ActivitySplaseBinding;

public class SplaseActivity extends AppCompatActivity {

    ActivitySplaseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                SplaseActivity.this.finish();
                binding.progressBar.setVisibility(View.GONE);
            }
        }, 2500);

    }
}