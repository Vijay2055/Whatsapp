package com.vz.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.vz.whatsapp.Models.Users;
import com.vz.whatsapp.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    ActivitySignUpBinding binding;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //hiding the action bar
        getSupportActionBar().hide();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //initializing database
        database=FirebaseDatabase.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Creating account");
        progressDialog.setMessage("We are creating your account");

        binding.btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword
                        (binding.etEmail.getText().toString(),binding.etPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                                    Users user=new Users(binding.etUserName.getText().toString(),
                                    binding.etPassword.getText().toString(),binding.etEmail.getText().toString());

                            //getting the id of the user
                            String id=task.getResult().getUser().getUid();
                            //saving the data into our database
                            database.getReference().child("Users").child(id).setValue(user);

                            Toast.makeText(SignUpActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignUpActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        binding.tvHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}