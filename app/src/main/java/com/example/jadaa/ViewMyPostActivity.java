package com.example.jadaa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewMyPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_post);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // move to previous page using toolbar
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //views from row_posts.xml
        ImageView uPictureIv, pImageIv;
        TextView uNameTv, pTimeTv, pTitle, pDescriptionTv,auther_name,edition,college,bookPrice,owner;
        ImageButton moreBtn;
        Button favoriteBtn, commentBtn, shareBtn;


        //init views
        uPictureIv =findViewById(R.id.uPictureIv);
        pImageIv = findViewById(R.id.bookImage);
        uNameTv = (TextView)findViewById(R.id.uNameTv);
        pTimeTv =(TextView) findViewById(R.id.pTimeTv);
        pTitle =(TextView) findViewById(R.id.BookTitle);
        pDescriptionTv =(TextView) findViewById(R.id.description);
        auther_name =(TextView) findViewById(R.id.auther_name);
        edition =(TextView) findViewById(R.id.edition);
        college=(TextView) findViewById(R.id.college);
        bookPrice=(TextView) findViewById(R.id.bookPrice);
        // owner=(TextView) findViewById(R.id.owner);

        Bundle extras = getIntent().getExtras();
        String title;
        title =  " "+extras.getString("pTitle");
        // message.setContent(title, "text/html; charset=utf-8");


        //    if (extras != null)
        pTitle.setText(title);
        pDescriptionTv.setText(" "+extras.getString("pDescription"));
        auther_name.setText(  " by "+extras.getString("pAuthor"));
        edition.setText( " Edition "+extras.getString("pEdition"));
        String uri =extras.getString("pImage");
        //owner.setText( extras.getString("pPublisher"));

        try{
            //  Picasso.get().load(pImage).into(myHolder.pImageIv);
            Picasso.get().load(uri).into(pImageIv);
            // myHolder.pImageIv.setImageURI(Uri.parse(pImage));
        }
        catch (Exception e){
        }


        //String someHtmlMessage = "Hello this is test message <b style='color:blue;'>bold color</b>";
        //message.setContent(someHtmlMessage, "text/html; charset=utf-8");


        if ( extras.getString("pPrice").equals("0"))
            bookPrice.setText("Book is free");
        else
            bookPrice.setText(" "+extras.getString("pPrice")+" SAR");
        //
        //
        college.setText( " "+extras.getString("pCollege"));
        //   pTitle.setText( extras.getString("pPublisher"));
        // and get whatever type user account id is


    }// on Create
}

