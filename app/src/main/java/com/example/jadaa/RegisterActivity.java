package com.example.jadaa;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameEditText, passwordEditText, confirmPassEditText;
    private EditText phoneEditText, emailEditText;
    private ImageView picImageView;
    private TextView join;
    private TextView registerButton;
    private TextView registerTextView;
    private TextView loginTextView;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static final String USERS = "users";
    private String TAG = "RegisterActivity";
    private User user;
    private String email, password, confirmPass, fullName, phone;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" + "(?=.*[0-9])" + "(?=.*[a-z])" +
            "(?=.*[A-Z])" + "(?=.*[a-zA-Z])" + "(?=.*[@#$%^&+=*./-_?!])" + "(?=\\S+$)" + ".{8,}" + "$");
    private static final Pattern PHONE_PATTERN= Pattern.compile("^[+]?[0-9]{10,13}$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*---------------------delete app bar ------------------------*/
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        nameEditText = findViewById(R.id.fullname_edittext);
        phoneEditText = findViewById(R.id.phone_edittext);
        passwordEditText = findViewById(R.id.enterpass_edittext);
        confirmPassEditText = findViewById(R.id.confirmpass_edittext);
        emailEditText = findViewById(R.id.email_edittext);
        registerButton = findViewById(R.id.register_button);
        loginTextView = findViewById(R.id.login_textview);
        registerTextView = findViewById(R.id.register_textview);
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USERS);
        mAuth = FirebaseAuth.getInstance();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEditText.getText().toString().trim();
                password = passwordEditText.getText().toString().trim();
                confirmPass= confirmPassEditText.getText().toString().trim();
                fullName = nameEditText.getText().toString().trim();
                phone = phoneEditText.getText().toString().trim();


              /*  if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter email and password",
                            Toast.LENGTH_LONG).show();
                    return;
                } */

               validateEmail(email);
                validatePassword(password);
                validateName(fullName);
                validatePhone(phone);
                validateConfirmPass(confirmPass);
                //checkEnteredData();
                if (!validateEmail(email) || !validatePassword(password) || !validateName(fullName)
                        || !validatePhone(phone) || !validateConfirmPass(confirmPass)) {
                    return;
                }

                //final FirebaseUser thisUser = FirebaseAuth.getInstance().getCurrentUser();
                user = new User(email, password, fullName, phone);
                registerUser();
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    public boolean validateEmail(String text){
        if (text.isEmpty()) {
            emailEditText.setError("Email field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            emailEditText.setError("Please enter a valid email address!");
            return false;
        } else {
            emailEditText.setError(null);
            return true;
        }
    }

    public boolean validatePassword(String text){
        if (text.isEmpty()) {
            passwordEditText.setError("Password field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(text).matches()) {
            passwordEditText.setError("Weak Password\n"+"- Use at least 8 characters\n" +
                    "- Use upper and lower case characters\n"+"- Use 1 or more numbers\n"+ "- Use 1 or more special characters");
            return false;
        } else {
            passwordEditText.setError(null);
            return true;
        }
    }

    public boolean validateConfirmPass(String text){
        if(text.isEmpty()){
            confirmPassEditText.setError("This field is required \n Please enter your password again");
            return false;
        } else if(!password.equals(text)){
            confirmPassEditText.setError("This doesn't match your password \n Please enter the correct password");
            return false;
        } else{
            confirmPassEditText.setError(null);
            return true;
        }
    }

    private boolean validateName(String text) {
        if (text.isEmpty()) {
            nameEditText.setError("Username field can't be empty");
            return false;
        } else if (text.length() > 20) {
            nameEditText.setError("Username is too long");
            return false;
        } else {
            nameEditText.setError(null);
            return true;
        }
    }
    public boolean validatePhone(String text){
        if (text.isEmpty()) {
            phoneEditText.setError("Phone number field can't be empty");
            return false;
        } else if (!PHONE_PATTERN.matcher(text).matches()) {
            phoneEditText.setError("Incorrect phone format");
            return false;
        } else {
            phoneEditText.setError(null);
            return true;
        }
    }



    public void registerUser(){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            user.sendEmailVerification();
                            Toast.makeText(RegisterActivity.this, "Successful registration. \n Verification email will be sent to "
                                    + user.getEmail(), Toast.LENGTH_LONG).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser currentUser){
        String keyId = mDatabase.push().getKey();
        assert keyId != null;
        mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user); //adding user info to database
        Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(loginIntent);
    }

}
