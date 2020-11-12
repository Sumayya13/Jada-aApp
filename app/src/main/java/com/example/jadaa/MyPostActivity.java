package com.example.jadaa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.jadaa.adapters.AdapterMyPosts;
import com.example.jadaa.adapters.AdapterPosts;
import com.example.jadaa.models.ModelMyPost;
import com.example.jadaa.models.ModelPost;
import com.google.android.material.navigation.NavigationView;
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
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyPostActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    LinearLayout linearLayout;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    RecyclerView recyclerView;
    List<ModelMyPost> postList;
    AdapterMyPosts adapterPosts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);


        /*---------------------delete app bar ------------------------*/
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /*---------------------Hooks------------------------*/
        navigationView = findViewById(R.id.nav_view );
        drawerLayout = findViewById(R.id.drawer_layout );
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        linearLayout = findViewById(R.id.NoOrder);


        /*---------------------Recycle view ------------------------*/
          //recyclerView = findViewById(R.id.post_list);
        recyclerView = findViewById(R.id.post_list);
        // LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(MyPostActivity.this);
        //show news =t posts first , for this load from last
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        //set layout to recyclerview
        recyclerView.setLayoutManager(layoutManager);

        //init  post list
        postList = new ArrayList<>();
        loadPosts();


        /*---------------------Navigation------------------------*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        // to change color when click on item
        navigationView.setNavigationItemSelectedListener(this);
        // send this page
        navigationView.setCheckedItem(R.id.nav_MyPost);




    }//onCreate



    private void loadPosts() {

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        // path of all posts
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");

        Query query = ref.orderByChild("uid").equalTo(user.getUid());
        //get all from this
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot ds: snapshot.getChildren() ){

                    linearLayout.setVisibility(View.INVISIBLE);
                    ModelMyPost modelMyPost = ds.getValue(ModelMyPost.class);
                    postList.add(modelMyPost);
                    //adapter
                    adapterPosts = new AdapterMyPosts(MyPostActivity.this, postList);
                    //set adapter to recycler view
                    recyclerView.setAdapter(adapterPosts);
                    adapterPosts.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //in case of error
                Toast.makeText(MyPostActivity.this,""+error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    ModelMyPost modelmyPost = ds.getValue(ModelMyPost.class);


                    if(modelmyPost.getUid().equals(thisUser.getUid())){
                        if(modelmyPost.getBookTitle().toLowerCase().contains(searchQuery.toLowerCase())||
                                modelmyPost.getBookDescription().toLowerCase().contains(searchQuery.toLowerCase())||
                                modelmyPost.getBookPrice().toLowerCase().contains(searchQuery.toLowerCase())||
                                modelmyPost.getBookAuthor().toLowerCase().contains(searchQuery.toLowerCase())||
                                modelmyPost.getCollege().toLowerCase().contains(searchQuery.toLowerCase())||
                                modelmyPost.getPostDate().toLowerCase().contains(searchQuery.toLowerCase())||
                                modelmyPost.getPostTime().toLowerCase().contains(searchQuery.toLowerCase())||
                                modelmyPost.getPublisher().toLowerCase().contains(searchQuery.toLowerCase())||
                                modelmyPost.getBookEdition().toLowerCase().contains(searchQuery.toLowerCase())||
                                (searchQuery.toLowerCase().equals("free")&& modelmyPost.getBookPrice().toLowerCase().equals("0"))

                        ){
                            postList.add(modelmyPost);
                        }}

                    //adapter
                    adapterPosts = new AdapterMyPosts(MyPostActivity.this,postList);
                    //set adapter to recycler view
                    recyclerView.setAdapter(adapterPosts);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //in case of error
                Toast.makeText(MyPostActivity.this,""+error.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Intent profile = new Intent(MyPostActivity.this, HomeActivity.class);
                startActivity(profile); break;
            case R.id.nav_Profile:
                Intent profile1 = new Intent(MyPostActivity.this, ProfileActivity.class);
                startActivity(profile1); break;
            case R.id.nav_MyPost: break;
            case R.id.nav_order:
                Intent myOrder = new Intent(MyPostActivity.this, MyOrderActivity.class);
                startActivity(myOrder); break;
            case R.id.nav_heart:
                Intent heart = new Intent(MyPostActivity.this, FavoriteActivity.class);
                startActivity(heart); break;
            case R.id.nav_paople:
                Intent myCustomers = new Intent(MyPostActivity.this, MyCustomersActivity.class);
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