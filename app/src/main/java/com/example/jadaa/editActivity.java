package com.example.jadaa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class editActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String curruntuserid;

    EditText ename, ephone, eemail;
    TextView btn;
    FirebaseUser database;
    FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference ;
    private FirebaseUser user;
    private EditText nameEditText;
    private EditText phoneEditText, emailEditText;
    private EditText Name,phone,email;
    //private String email, password, confirmPass, fullName, phone;
    private static final Pattern PHONE_PATTERN= Pattern.compile("^[+]?[0-9]{10,13}$");

    DatabaseReference updateData = FirebaseDatabase.getInstance()
            .getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
public static final String TAG ="TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        /*---------------------delete app bar ------------------------*/
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // move to previous page using toolbar
        getSupportActionBar().setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nameEditText = findViewById(R.id.editname);
        phoneEditText = findViewById(R.id.editnumber);
        emailEditText = findViewById(R.id.editemail);


        Intent data = getIntent();
        String fullName4 = data.getStringExtra("fullName");
        String email4 = data.getStringExtra("email");
        String phone4 = data.getStringExtra("phone");

        nameEditText.setText(fullName4);
        phoneEditText.setText(phone4);
        emailEditText.setText(email4);



        mAuth=FirebaseAuth.getInstance();
        curruntuserid=mAuth.getCurrentUser().getUid();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);



        databaseReference = FirebaseDatabase.getInstance()
                .getReference("users");


        /*
        // show old data
        Query query = databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //get Data
                    String name = "" + ds.child("fullName").getValue();
                    String phone1 = "" + ds.child("phone").getValue();
                    String email1 = "" + ds.child("email").getValue();

                    //set data
                    nameEditText.setText(name);
                    phoneEditText.setText(phone1);
                    emailEditText.setText(email1);
                }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

         */




            //  int width = dm.widthPixels;
       // int height = dm.heightPixels;

     //  getWindow().setLayout((int) (width * .9), (int) (height * .6));


    }//onClick
    public void Save(View view) {



        btn= findViewById(R.id.updatebtn);
        ename= (EditText)findViewById(R.id.editname);
        ephone= (EditText)findViewById(R.id.editnumber);
        eemail= (EditText)findViewById(R.id.editemail);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1=ename.getText().toString().trim();
                String phone1=ephone.getText().toString().trim();
                String email1=eemail.getText().toString().trim();

                validateEmail(email1);
                validateName(name1);
                validatePhone(phone1);
                isEmpty(email1,name1,phone1);
                if(isEmpty(email1,phone1,name1)){


                    AlertDialog.Builder alertDialogBilder = new AlertDialog.Builder(
                            editActivity.this);
                    alertDialogBilder.setTitle("message");
                    alertDialogBilder.setMessage("Please enter a vaild inputs")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int id) {

                           /* btn = findViewById(R.id.updatebtn);
                            ename = findViewById(R.id.editname);
                            ephone = findViewById(R.id.editnumber);
                            eemail = findViewById(R.id.editemail);*/


                                }
                            });


                    AlertDialog alertDialog = alertDialogBilder.create();
                    alertDialog.show();

                }else if (!validateEmail(email1) || !validateName(name1) || !validatePhone(phone1) ){
                    return;
              }

                Map<String, Object> updates = new HashMap<String,Object>();

                updates.put("fullName", name1);
                updates.put("phone", phone1);
                updates.put("email", email1);
                updateData.updateChildren(updates);


            }
        });




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



    private boolean isEmpty(String string1,String string2,String string3) {
        if (string1.isEmpty()&&string2.isEmpty()&&string3.isEmpty()) {

            Toast.makeText(editActivity.this, "empty", Toast.LENGTH_SHORT).show();
            return true;
        }else return false;}

}