package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;


public class SignUp extends AppCompatActivity {

    TextInputEditText textInputEditTextFullname, textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail;
    Button buttonSignup;
    TextView textViewLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textInputEditTextFullname = findViewById(R.id.fullname);
        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextEmail = findViewById(R.id.email);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonSignup = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LogIn.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname, user_name, password, email;
                fullname = String.valueOf(textInputEditTextFullname.getText());
                user_name = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                email = String.valueOf(textInputEditTextEmail.getText());

                if (!fullname.equals("") && !user_name.equals("") && !email.equals("") && !password.equals("")) {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //m???t m???ng cho t??n tr?????ng c???a tham s??? v?? m???t m???ng kh??c cho d??? li???u.
                            //Creating array for tham s???
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "user_name";
                            field[2] = "password";
                            field[3] = "email";
                            //Creating array for d??? li???u
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = user_name;
                            data[2] = password;
                            data[3] = email;
                            //T???o ?????i t?????ng cho PutData, truy???n URL, ph????ng th???c, tr?????ng, d??? li???u l??m ?????i s???.
                            PutData putData = new PutData("http://192.168.1.6/LoginRegister/signup.php", "POST", field, data);
                            // D??? li???u ???????c g???i ??i s??? c?? d???ng $_POST['fullname'] = fullname;

                            //startFetch() ????? b???t ?????u process, n?? tr??? v??? m???t gi?? tr??? boolean.
                            if (putData.startPut()) {
                                //onComplete() ????? bi???t khi n??o process ho??n t???t.
                                if (putData.onComplete()) {
                                    //End ProgressBar (Set visibility to GONE)
                                    progressBar.setVisibility(View.GONE);
                                    //N???u qu?? tr??nh ho??n t???t, h??y s??? d???ng getResult () ????? nh???n gi?? tr??? k???t qu???.
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), LogIn.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Vui l??ng ??i???n ?????y ????? th??ng tin!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}