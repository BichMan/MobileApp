package com.dumv.appdoctruyentranh.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dumv.appdoctruyentranh.api.ApiDangKy;
import com.dumv.appdoctruyentranh.api.ApiGetUser;
import com.dumv.appdoctruyentranh.api.RetrofitClient;
import com.dumv.appdoctruyentranh.databinding.ActivityRegisterBinding;
import com.dumv.appdoctruyentranh.model.ResponseBodyDTO;
import com.dumv.appdoctruyentranh.model.User;
import com.dumv.appdoctruyentranh.view.MainActivity;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoTen = binding.edtHoTen.getText().toString();
                String user = binding.edtUser.getText().toString();
                String pass = binding.edtPass.getText().toString();

                if (hoTen.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Không bỏ trống !!!", Toast.LENGTH_SHORT).show();
                } else {
                    CompositeDisposable compositeDisposable = new CompositeDisposable();
                    Disposable disposable = RetrofitClient.getClient()
                            .create(ApiDangKy.class)
                            .insertUser(binding.edtHoTen.getText().toString(),
                                    binding.edtUser.getText().toString(),
                                    binding.edtPass.getText().toString())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.newThread())
                            .subscribe(new Consumer<ResponseBodyDTO>() {
                                @Override
                                public void accept(ResponseBodyDTO responseBodyDTO) throws Exception {
                                    if (responseBodyDTO.isSuccess()) {
                                        Toast.makeText(getApplicationContext(), "Thành công !!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    throwable.printStackTrace();
                                }
                            });
                    Toast.makeText(getApplicationContext(), "Đăng ký thành công !!!", Toast.LENGTH_SHORT).show();
                    compositeDisposable.add(disposable);

                }
            }
        });
        binding.txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}