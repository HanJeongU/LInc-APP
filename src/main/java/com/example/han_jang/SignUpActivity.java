package com.example.han_jang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private  static  final String TAG="SignUpActivity";
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();


        findViewById(R.id.signUpButton).setOnClickListener(onClickListener);
        findViewById(R.id.gotoLoginButton).setOnClickListener(onClickListener);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
    View.OnClickListener onClickListener = new View.OnClickListener(){

        public  void onClick(View v){
            switch (v.getId()){
                case R.id.signUpButton:
                    signUp();
                    break;
                case R.id.gotoLoginButton:
                    startLoginActivity();

                    break;
            }
        }
    };

    private void signUp(){
        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.passwordCheckEditText)).getText().toString();


        if(email.length()>0&& password.length()>0 && passwordCheck.length()>0) {
            if (password.equals(passwordCheck)) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast("??????????????? ?????????????????????.");
                                    //UI
                                } else {
                                    if (task.getException() != null) {
                                        startToast(task.getException().toString());
                                        //UI
                                    }
                                }

                                // ...
                            }
                        });

            } else {
                startToast("??????????????? ???????????? ????????????.");

            }
        } else{
            startToast("????????? ?????? ??????????????? ????????? ?????????.");
        }

    }
    private void startToast(String msg){
        Toast.makeText(this,  msg, Toast.LENGTH_SHORT).show();
    }

    private void startLoginActivity(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);

    }
}