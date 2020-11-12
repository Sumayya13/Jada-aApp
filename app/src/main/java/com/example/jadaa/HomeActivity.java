package com.example.jadaa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.jadaa.adapters.AdapterPosts;
import com.example.jadaa.models.ModelPost;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;

    RecyclerView recyclerView;
    List<ModelPost> postList;
    AdapterPosts adapterPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hom);

        /*---------------------delete app bar ------------------------*/
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /*---------------------Recycle view ------------------------*/
        // recyclerView = View.findViewById(R.id.post_list);
        recyclerView = findViewById(R.id.post_list);
        // LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
        //show news =t posts first , for this load from last
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        //set layout to recyclerview
        recyclerView.setLayoutManager(layoutManager);

        //init  post list
        postList = new ArrayList<>();
        loadPosts();



        /*---------------------Hooks------------------------*/
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view );
        drawerLayout = findViewById(R.id.drawer_layout );
        setSupportActionBar(toolbar);


        /*---------------------Navigation------------------------*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        // to change color when click on item
        navigationView.setNavigationItemSelectedListener(this);
        // send this page
        navigationView.setCheckedItem(R.id.nav_home);




        // if want to hide item of navigation
        /* menu = navigationView.getMenu();
        menu.findItem(R.id.nav_out).setVisible(false);
        menu.findItem(R.id.nav_Profile).setVisible(false); */

        /*---------------------to move to add post page ------------------------*/
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move_To_Add_Post = new Intent(HomeActivity.this, AddPostActivity.class);
                startActivity(move_To_Add_Post);
            }
        });




    }// on create

    private void loadPosts() {
     final FirebaseUser thisUser = FirebaseAuth.getInstance().getCurrentUser();
        // path of all posts
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        //get all from this
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot ds: snapshot.getChildren() ){
                    ModelPost modelPost = ds.getValue(ModelPost.class);
                    if (!modelPost.getUid().equals(thisUser.getUid()) && !modelPost.getBookStatus().equals("SOLD") ) {
                        postList.add(modelPost);
                        //adapter
                        adapterPosts = new AdapterPosts(HomeActivity.this, postList);
                        //set adapter to recycler view
                        recyclerView.setAdapter(adapterPosts);
                        adapterPosts.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //in case of error
                Toast.makeText(HomeActivity.this,""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    //method search later
    private void searchPosts(final String searchQuery){
        final FirebaseUser thisUser = FirebaseAuth.getInstance().getCurrentUser();// to show other post
        // path of all posts
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        //get all from this
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot ds: snapshot.getChildren() ){
                    ModelPost modelPost = ds.getValue(ModelPost.class);


                    if(!modelPost.getUid().equals(thisUser.getUid()) ){
                    if(modelPost.getBookTitle().toLowerCase().contains(searchQuery.toLowerCase())||
                            modelPost.getBookDescription().toLowerCase().contains(searchQuery.toLowerCase())||
                            modelPost.getBookPrice().toLowerCase().contains(searchQuery.toLowerCase())||
                            modelPost.getBookAuthor().toLowerCase().contains(searchQuery.toLowerCase())||
                            modelPost.getCollege().toLowerCase().contains(searchQuery.toLowerCase())||
                            modelPost.getPostDate().toLowerCase().contains(searchQuery.toLowerCase())||
                            modelPost.getPostTime().toLowerCase().contains(searchQuery.toLowerCase())||
                            modelPost.getPublisher().toLowerCase().contains(searchQuery.toLowerCase())||
                            modelPost.getBookEdition().toLowerCase().contains(searchQuery.toLowerCase())||
                            (searchQuery.toLowerCase().equals("free")&& modelPost.getBookPrice().toLowerCase().equals("0"))

                    ){
                        postList.add(modelPost);
                    }}

                    //adapter
                    adapterPosts = new AdapterPosts(HomeActivity.this,postList);
                    //set adapter to recycler view
                    recyclerView.setAdapter(adapterPosts);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //in case of error
                Toast.makeText(HomeActivity.this,""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.serch_view);
        SearchView searchView =(SearchView) MenuItemCompat.getActionView(item);
        //search listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //call when user press search button
                if(!TextUtils.isEmpty(query)){
                    searchPosts(query);
                }
                else{
                    loadPosts();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //called as ans when user press any letter
                if(!TextUtils.isEmpty(newText)){
                    searchPosts(newText);
                }
                else{
                    loadPosts();
                }

                return false;
            }
        });
        return true;


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

    @Override      // to move to page when click
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home: break;
            case R.id.nav_Profile:
                Intent profile = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(profile); break;
            case R.id.nav_MyPost:
                Intent myPost = new Intent(HomeActivity.this, MyPostActivity.class);
                startActivity(myPost); break;
            case R.id.nav_order:
                Intent myOrder = new Intent(HomeActivity.this, MyOrderActivity.class);
                startActivity(myOrder); break;
            case R.id.nav_heart:
                Intent favorite = new Intent(HomeActivity.this, FavoriteActivity.class);
                startActivity(favorite); break;
            case R.id.nav_paople:
                Intent myCustomers = new Intent(HomeActivity.this, MyCustomersActivity.class);
            startActivity(myCustomers); break;
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

}