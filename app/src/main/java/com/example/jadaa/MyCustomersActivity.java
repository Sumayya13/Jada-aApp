package com.example.jadaa;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.jadaa.adapters.AdapterCustomers;
import com.example.jadaa.adapters.AdapterMyOrder;
import com.example.jadaa.adapters.AdapterPosts;
import com.example.jadaa.models.ModelPost;
import com.example.jadaa.models.soldBooks;
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
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MyCustomersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;

    int is_has_order = 0;  // عشان اشيك هل عنده طلبات او لا

    RecyclerView recyclerView;
    List<soldBooks> postList;
    AdapterCustomers adapterPosts;

    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_customers);


        /*---------------------delete app bar ------------------------*/
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        /*---------------------Hooks------------------------*/
        navigationView = findViewById(R.id.nav_view );
        drawerLayout = findViewById(R.id.drawer_layout );
        toolbar = findViewById(R.id.toolbar);
        linearLayout = findViewById(R.id.NoOrder);
        setSupportActionBar(toolbar);


        /*---------------------Recycle view ------------------------*/
        // recyclerView = View.findViewById(R.id.post_list);
        recyclerView = findViewById(R.id.post_list);
        // LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MyCustomersActivity.this);
        //show news =t posts first , for this load from last
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        //set layout to recyclerview
        recyclerView.setLayoutManager(layoutManager);

        //init  post list
        postList = new ArrayList<>();
        loadPosts();

     /*
        if (is_has_order == 0) {
            linearLayout.setVisibility(View.VISIBLE);
        }
*/


        /*---------------------Navigation------------------------*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_paople);




    }// on create


    private void loadPosts() {

        final FirebaseUser thisUser = FirebaseAuth.getInstance().getCurrentUser();
        // path of all posts
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
        //get all from this
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot ds: snapshot.getChildren() ){
                    soldBooks modelPost = ds.getValue(soldBooks.class);
                    if (modelPost.getSellerID().equals(thisUser.getUid()) ) {
                        linearLayout.setVisibility(View.INVISIBLE);
                        postList.add(modelPost);
                        //adapter
                        adapterPosts = new AdapterCustomers(MyCustomersActivity.this, postList);
                        //set adapter to recycler view
                        recyclerView.setAdapter(adapterPosts);
                        adapterPosts.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //in case of error
                Toast.makeText(MyCustomersActivity.this,""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }




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
                Intent profile = new Intent(MyCustomersActivity.this, HomeActivity.class);
                startActivity(profile); break;
            case R.id.nav_Profile:
                Intent profile1 = new Intent(MyCustomersActivity.this, ProfileActivity.class);
                startActivity(profile1); break;
            case R.id.nav_MyPost:
                Intent myPost = new Intent(MyCustomersActivity.this, MyPostActivity.class);
                startActivity(myPost); break;
            case R.id.nav_order:
                Intent myOrder = new Intent(MyCustomersActivity.this, MyOrderActivity.class);
                startActivity(myOrder); break;
            case R.id.nav_heart:
                Intent heart = new Intent(MyCustomersActivity.this, FavoriteActivity.class);
                startActivity(heart); break;
            case R.id.nav_paople: break;
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