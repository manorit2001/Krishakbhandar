package com.example.mapstest.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapstest.FarmerActivity;
import com.example.mapstest.R;
import com.example.mapstest.RetailersActivity;
import com.example.mapstest.UserHelper;
import com.example.mapstest.WarehouseActivity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

//    private LoginViewModel loginViewModel;
    private Boolean validuser=new Boolean(false);
    private Boolean validpass=new Boolean(false);
    private String[] UserEmails = new String[] {"farmer@gmail.com","retailer@gmail.com","warehouse@gmail.com"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
//                .get(LoginViewModel.class);
        UserHelper db=new UserHelper(this);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final TextView error = (TextView) findViewById(R.id.error);

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE)
                    loginButton.performClick();

                return false;
            }
        });


        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Pattern p= Patterns.EMAIL_ADDRESS;
                Matcher m=p.matcher(s);
//                Toast.makeText(LoginActivity.this, Boolean.toString(m.matches()), Toast.LENGTH_SHORT).show();
                if(m.matches())
                {
                    validuser=true;
                    if(validpass)
                    {
//                        error.setVisibility(View.INVISIBLE);
                        loginButton.setEnabled(true);
                    }
//                    else
//                    {
//                        error.setText("Password less than 5 characters!");
//                        error.setVisibility(View.VISIBLE);
//                        loginButton.setEnabled(false);
//                    }
                }
                else{
                    validuser=false;
                    usernameEditText.setError("Email not Valid!");
//                    error.setText("Email not Valid!");
//                    error.setVisibility(View.VISIBLE);
                    loginButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>5){
                    validpass=true;
                    if(validuser){
//                        error.setVisibility(View.INVISIBLE);
                        loginButton.setEnabled(true);
                    }
//                    else
//                    {
//                        error.setText("Email not Valid!");
//                        error.setVisibility(View.VISIBLE);
//                        loginButton.setEnabled(false);
//                    }
                }
                else{
                    validpass=false;
                    passwordEditText.setError("Password less than 5 characters!");
//                    error.setText("Password less than 5 characters!");
//                    error.setVisibility(View.VISIBLE);
                    loginButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if(db.UserExist(usernameEditText.getText().toString())) {
                    error.setVisibility(View.INVISIBLE);
                    if(db.UserPassExist(usernameEditText.getText().toString(),passwordEditText.getText().toString())) {
//                    Toast.makeText(getApplicationContext(),
//                            "Redirecting...",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent();
                        intent.putExtra("email",usernameEditText.getText().toString());
                        switch (db.get_type(usernameEditText.getText().toString(),passwordEditText.getText().toString()))
                        {
                            case "Farmer":
                                intent.setClass(LoginActivity.this, FarmerActivity.class);
                            case "Retailer":
                                intent.setClass(LoginActivity.this, RetailersActivity.class);
                            case "Warehouse":
                                intent.setClass(LoginActivity.this, WarehouseActivity.class);
                        }
                        startActivity(intent);
                    }
                    else
                    {
                        error.setText("Incorrect Password!");
                        error.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Sign Up",Toast.LENGTH_SHORT).show();
                    signup(usernameEditText.getText().toString(),passwordEditText.getText().toString());
                }*/ ///TODO:WHEN DATABASE IS BUILD UNCOMMENT


                if(UserExists(usernameEditText.getText().toString())) {
                    error.setVisibility(View.INVISIBLE);
                    if(UserPassExists(usernameEditText.getText().toString(),passwordEditText.getText().toString())) {
                        Toast.makeText(getApplicationContext(),"Redirecting...",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent();
                        intent.putExtra("email",usernameEditText.getText().toString());
                        switch (get_type(usernameEditText.getText().toString()))
                        {
                            case "farmer":
                                intent.setClass(LoginActivity.this, FarmerActivity.class);
                            case "retailer":
                                intent.setClass(LoginActivity.this, RetailersActivity.class);
                            case "warehouse":
                                intent.setClass(LoginActivity.this, WarehouseActivity.class);
                        }
                        startActivity(intent);
                    }
                    else
                    {
                        error.setText("Incorrect Password!");
                        error.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Sign Up",Toast.LENGTH_SHORT).show();
                    signup(usernameEditText.getText().toString(),passwordEditText.getText().toString());
                }
            }
        });

//        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
//            @Override
//            public void onChanged(@Nullable LoginFormState loginFormState) {
//                if (loginFormState == null) {
//                    return;
//                }
//                loginButton.setEnabled(loginFormState.isDataValid());
//                if (loginFormState.getUsernameError() != null)
//                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
//                }
//                if (loginFormState.getPasswordError() != null) {
//                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
//                }
//            }
//        });
//
//        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
//            @Override
//            public void onChanged(@Nullable LoginResult loginResult) {
//                if (loginResult == null) {
//                    return;
//                }
//                loadingProgressBar.setVisibility(View.GONE);
//                if (loginResult.getError() != null) {
//                    showLoginFailed(loginResult.getError());
//                }
//                if (loginResult.getSuccess() != null) {
//                    updateUiWithUser(loginResult.getSuccess());
//                }
//                setResult(Activity.RESULT_OK);
//
//                //Complete and destroy login activity once successful
//                finish();
//            }
//        });
//
//        TextWatcher afterTextChangedListener = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // ignore
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // ignore
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());
//            }
//        };
//        usernameEditText.addTextChangedListener(afterTextChangedListener);
//        passwordEditText.addTextChangedListener(afterTextChangedListener);
//        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    loginViewModel.login(usernameEditText.getText().toString(),
//                            passwordEditText.getText().toString());
//                }
//                return false;
//            }
//        });
//
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadingProgressBar.setVisibility(View.VISIBLE);
//                loginViewModel.login(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());
//            }
//        });
    }

    private void signup(String name, String passwd)
    {
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("pass",passwd);
        startActivity(intent);
    }
    private boolean UserExists(String user)
    {
        boolean exist = false;
        for(int i=0;i<3;i++)
        {
            if(user.equals(UserEmails[i]))
                exist=true;
        }
        return exist;
    }

    private boolean UserPassExists(String user,String passwd)
    {
        boolean exist=false;
        if(passwd.equals("password"))
            exist=true;
        return exist;
    }
    private String get_type(String user)
    {
        Pattern p=Pattern.compile("(.*)@");
        Matcher m=p.matcher(user);
        if(m.find())
            return m.group(0);
        else
            return "";
    }

}
