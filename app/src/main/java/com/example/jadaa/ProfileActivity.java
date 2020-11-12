package com.example.jadaa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.jadaa.FavoriteActivity;
import com.example.jadaa.HomeActivity;
import com.example.jadaa.MainActivity;
import com.example.jadaa.MyOrderActivity;
import com.example.jadaa.MyPostActivity;
import com.example.jadaa.R;
import com.example.jadaa.editActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
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
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener   {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;


    //_______________ view profile_____________
    private TextView email, phone, Name, btn;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private EditText nameEditText, passwordEditText, confirmPassEditText;
    private EditText phoneEditText, emailEditText;
   // Toolbar toolbar;
String name ,phone1 ,email1; ///////////////////////////////////
    // private String email ;
    // private static final String USERS = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        /*---------------------delete app bar ------------------------*/
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


// to undo to previose
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");

        /*---------------------Hooks------------------------*/
        navigationView = findViewById(R.id.nav_view );
        drawerLayout = findViewById(R.id.drawer_layout );
        toolbar = findViewById(R.id.toolbar);



        /*---------------------Hooks------------------------*/
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        Name = findViewById(R.id.name);
        btn = findViewById(R.id.EditButton);


        //--------------view profile-----------------
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");


        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //get Data
                    name = "" + ds.child("fullName").getValue();
                    phone1 = "" + ds.child("phone").getValue();
                    email1 = "" + ds.child("email").getValue();

                    //set data
                    Name.setText(name.toString());
                    phone.setText(phone1);
                    email.setText(email1);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //////////////////////////////////////////////////

                Intent profileIntent = new Intent(getApplicationContext(), editActivity.class);
                profileIntent.putExtra("fullName", name);
                profileIntent.putExtra("phone", phone1);
                profileIntent.putExtra("email", email1);
                startActivity(profileIntent);
            }
        });


        /*---------------------Navigation------------------------*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_Profile);

        /*---------------------Navigation------------------------*/
   /*   navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        // to change color when click on item
        navigationView.setNavigationItemSelectedListener(this);
        // send this page
        navigationView.setCheckedItem(R.id.nav_Profile);
*/




    }//onCreate

/*
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home: break;
            case R.id.nav_Profile:
                Intent profile = new Intent(ProfileActivity.this, ProfileActivity.class);
                startActivity(profile); break;
            case R.id.nav_MyPost:
                Intent myPost = new Intent(ProfileActivity.this, MyPostActivity.class);
                startActivity(myPost); break;
            case R.id.nav_order:
                Intent myOrder = new Intent(ProfileActivity.this, MyOrderActivity.class);
                startActivity(myOrder); break;
            case R.id.nav_heart:
                Intent favorite = new Intent(ProfileActivity.this, FavoriteActivity.class);
                startActivity(favorite); break;
            case R.id.nav_out:{
                android.app.AlertDialog.Builder alertDialogBilder = new AlertDialog.Builder(this);
                alertDialogBilder.setTitle("Log out");
                alertDialogBilder.setMessage("Are you sure you want to log out?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // close the dialog
                            }
                        })
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {

                                FirebaseAuth.getInstance().signOut();
                                finish();
                                Intent logout = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(logout);
                            }
                        });


                AlertDialog alertDialog = alertDialogBilder.create();
                alertDialog.show();break;
            }}
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }



 */

    /*---------------------to Open or close Navigation ------------------------*/
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Intent profile = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(profile); break;
            case R.id.nav_Profile: break;
            case R.id.nav_MyPost:
                Intent myPost = new Intent(ProfileActivity.this, MyPostActivity.class);
                startActivity(myPost); break;
            case R.id.nav_order:  Intent profile1 = new Intent(ProfileActivity.this, MyOrderActivity.class);
                startActivity(profile1); break;
            case R.id.nav_heart:
                Intent heart = new Intent(ProfileActivity.this, FavoriteActivity.class);
                startActivity(heart); break;
            case R.id.nav_paople:
                Intent myCustomers = new Intent(ProfileActivity.this, MyCustomersActivity.class);
                startActivity(myCustomers); break;
            case R.id.nav_out:
                android.app.AlertDialog.Builder alertDialogBilder = new AlertDialog.Builder(this);
                alertDialogBilder.setTitle("Log out");
                alertDialogBilder.setMessage("Are you sure you want to log out?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // close the dialog
                            }
                        })
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {

                                FirebaseAuth.getInstance().signOut();
                                finish();
                                Intent logout = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(logout);
                            }
                        });


                AlertDialog alertDialog = alertDialogBilder.create();
                alertDialog.show();break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}