package com.dumv.appdoctruyentranh.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dumv.appdoctruyentranh.api.ApiGetUser;
import com.dumv.appdoctruyentranh.api.RetrofitClient;
import com.dumv.appdoctruyentranh.databinding.ActivityLoginBinding;
import com.dumv.appdoctruyentranh.model.User;
import com.dumv.appdoctruyentranh.util.StoreUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!StoreUtil.get(getApplicationContext(), "user").isEmpty() &&
                !StoreUtil.get(getApplicationContext(), "pass").isEmpty()) {
            binding.edtUsername.setText(StoreUtil.get(getApplicationContext(), "user"));
            binding.edtPassword.setText(StoreUtil.get(getApplicationContext(), "pass"));
            binding.chkRemember.setChecked(true);
        }

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompositeDisposable compositeDisposable = new CompositeDisposable();
                Disposable disposable = RetrofitClient.getClient()
                        .create(ApiGetUser.class)
                        .getUser()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(new Consumer<List<User>>() {
                            @Override
                            public void accept(List<User> users) throws Exception {
                                int dem = 0;
                                for (User user : users) {
                                    if (user.getUsername().equals(binding.edtUsername.getText().toString()) &&
                                            user.getPassword().equals(binding.edtPassword.getText().toString())) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);

                                        if (binding.chkRemember.isChecked()) {
                                            StoreUtil.save(getApplicationContext(), "id", user.getId() + "");
                                            StoreUtil.save(getApplicationContext(), "user", user.getUsername());
                                            StoreUtil.save(getApplicationContext(), "pass", user.getPassword());
                                        }
                                        break;
                                    } else {
                                        dem++;
                                    }
                                }

                                if (dem == users.size()) {
                                    Toast.makeText(getApplicationContext(), "Vui lòng kiểm tra tài khoản và mật khẩu !!!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                compositeDisposable.add(disposable);

            }
        });

        binding.txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}