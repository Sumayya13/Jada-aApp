package com.example.jadaa.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jadaa.R;
import com.example.jadaa.ViewMyPostActivity;
import com.example.jadaa.ViewPostActivity;
import com.example.jadaa.models.ModelPost;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPosts extends RecyclerView.Adapter<AdapterPosts.MyHolder>{

   // String uid,pTitle,pDescription,pAuthor,pEdition,pImage,pPrice,pStatus,pCollege,pDate,pTime,pPublisher;
    Context context;
    List<ModelPost> postList;


    public AdapterPosts(Context context, List<ModelPost> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //inflate layout row_posts.xml
        View view = LayoutInflater.from(context).inflate(R.layout.row_posts,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
         //get data
        final String uid = postList.get(i).getUid();
        final String pId = postList.get(i).getpId();
        final String pTitle = postList.get(i).getBookTitle();
        final String pDescription = postList.get(i).getBookDescription();
        final String pAuthor = postList.get(i).getBookAuthor();
        final String pEdition = postList.get(i).getBookEdition();
        final String pImage = (postList.get(i).getBookImage());
        final String pPrice = postList.get(i).getBookPrice();
        final String pStatus = postList.get(i).getBookStatus();
        final String pCollege = postList.get(i).getCollege();
        final String pDate = postList.get(i).getPostDate();
        final String pTime = postList.get(i).getPostTime();
        final String pPublisher = postList.get(i).getPublisher();
        //set data
        myHolder.uNameTv.setText(pPublisher);
        myHolder.pTimeTv.setText(pDate+" "+pTime);
        myHolder.pTitle.setText(pTitle);


        if (pPrice.equals("0"))
            myHolder.pDescriptionTv.setText("Book is free");
        else
            myHolder.pDescriptionTv.setText(pPrice+" SAR");


       //  myHolder.pImageIv.setImageURI(Uri.parse(pImage));

        //set post image
        //String url = pImage;
        myHolder.pTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // move data to ViewPostActivity page to view
                Intent viewSingle = new Intent(context, ViewPostActivity.class);
                viewSingle.putExtra("pTitle", pTitle);
                viewSingle.putExtra("pImage",pImage);
                viewSingle.putExtra("pDescription", pDescription);
                viewSingle.putExtra("pAuthor", pAuthor);
                viewSingle.putExtra("pEdition", pEdition);
                viewSingle.putExtra("pPrice", pPrice);
                viewSingle.putExtra("pStatus", pStatus);
                viewSingle.putExtra("pCollege", pCollege);
                viewSingle.putExtra("pDate", pDate);
                viewSingle.putExtra("pTime", pTime);
                viewSingle.putExtra("pPublisher", pPublisher);
                viewSingle.putExtra("uid", uid);
                viewSingle.putExtra("pId", pId);

                context.startActivity(viewSingle);
            }
        });

        myHolder.pImageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // move data to ViewPostActivity page to view
                Intent viewSingle = new Intent(context, ViewPostActivity.class);
                viewSingle.putExtra("pTitle", pTitle);
                viewSingle.putExtra("pImage",pImage);
                viewSingle.putExtra("pDescription", pDescription);
                viewSingle.putExtra("pAuthor", pAuthor);
                viewSingle.putExtra("pEdition", pEdition);
                viewSingle.putExtra("pPrice", pPrice);
                viewSingle.putExtra("pStatus", pStatus);
                viewSingle.putExtra("pCollege", pCollege);
                viewSingle.putExtra("pDate", pDate);
                viewSingle.putExtra("pTime", pTime);
                viewSingle.putExtra("pPublisher", pPublisher);
                viewSingle.putExtra("uid", uid);
                viewSingle.putExtra("pId", pId);

                context.startActivity(viewSingle);
            }
        });


        myHolder.pDescriptionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // move data to ViewPostActivity page to view
                Intent viewSingle = new Intent(context, ViewPostActivity.class);
                viewSingle.putExtra("pTitle", pTitle);
                viewSingle.putExtra("pImage", pImage);
                viewSingle.putExtra("pDescription", pDescription);
                viewSingle.putExtra("pAuthor", pAuthor);
                viewSingle.putExtra("pEdition", pEdition);
                viewSingle.putExtra("pPrice", pPrice);
                viewSingle.putExtra("pStatus", pStatus);
                viewSingle.putExtra("pCollege", pCollege);
                viewSingle.putExtra("pDate", pDate);
                viewSingle.putExtra("pTime", pTime);
                viewSingle.putExtra("pPublisher", pPublisher);
                viewSingle.putExtra("uid", uid);
                viewSingle.putExtra("pId", pId);

                context.startActivity(viewSingle);
            }
        });


        try{
            //  Picasso.get().load(pImage).into(myHolder.pImageIv);
            Picasso.get().load(pImage).into(myHolder.pImageIv);
           // myHolder.pImageIv.setImageURI(Uri.parse(pImage));
        }
        catch (Exception e){
        }





    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


    //view holder class
    class MyHolder extends RecyclerView.ViewHolder{

        //views from row_posts.xml
        ImageView uPictureIv, pImageIv;
        TextView uNameTv, pTimeTv, pTitle, pDescriptionTv;
        ImageButton moreBtn;
        Button favoriteBtn, commentBtn, shareBtn;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //init views
            uPictureIv = itemView.findViewById(R.id.uPictureIv);
            pImageIv = itemView.findViewById(R.id.pImageIv);
            uNameTv = itemView.findViewById(R.id.uNameTv);
            pTimeTv = itemView.findViewById(R.id.pTimeTv);
            pTitle = itemView.findViewById(R.id.pTitle);
            pDescriptionTv = itemView.findViewById(R.id.pDescriptionTv);
            moreBtn = itemView.findViewById(R.id.moreBtn);


        }
    }
}//adapter class
