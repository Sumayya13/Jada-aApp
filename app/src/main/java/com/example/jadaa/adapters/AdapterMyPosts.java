package com.example.jadaa.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jadaa.AddPostActivity;
import com.example.jadaa.HomeActivity;
import com.example.jadaa.MainActivity;
import com.example.jadaa.MyPostActivity;
import com.example.jadaa.R;
import com.example.jadaa.ViewMyPostActivity;
import com.example.jadaa.ViewPostActivity;
import com.example.jadaa.models.ModelMyPost;
import com.example.jadaa.models.ModelPost;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterMyPosts extends RecyclerView.Adapter<AdapterMyPosts.MyHolder>{

    // String uid,pTitle,pDescription,pAuthor,pEdition,pImage,pPrice,pStatus,pCollege,pDate,pTime,pPublisher;
    Context context;
    List<ModelMyPost> postList;

    public AdapterMyPosts(Context context, List<ModelMyPost> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //inflate layout row_posts.xml
        View view = LayoutInflater.from(context).inflate(R.layout.row_my_posts,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {
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
            myHolder.pDescriptionTv.setText("  "+pPrice+" SAR");


        //  myHolder.pImageIv.setImageURI(Uri.parse(pImage));


        myHolder.pImageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // move data to ViewPostActivity page to view
                Intent viewSingle = new Intent(context, ViewMyPostActivity.class);
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
                viewSingle.putExtra("pPublisher",pPublisher);
                viewSingle.putExtra("uid", uid);
                viewSingle.putExtra("pId", pId);

                context.startActivity(viewSingle);
            }
        });


        myHolder.pDescriptionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // move data to ViewPostActivity page to view
                Intent viewSingle = new Intent(context, ViewMyPostActivity.class);
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
        //set post image
        //String url = pImage;
        myHolder.pTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // move data to ViewPostActivity page to view
                Intent viewSingle = new Intent(context, ViewMyPostActivity.class);
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
                viewSingle.putExtra("pPublisher",pPublisher);
                viewSingle.putExtra("uid", uid);
                viewSingle.putExtra("pId", pId);

                context.startActivity(viewSingle);
            }
        });



        try{


            //  Picasso.get().load(pImage).into(myHolder.pImageIv);
            Picasso.get().load(postList.get(i).getBookImage()).into(myHolder.pImageIv);
            // myHolder.pImageIv.setImageURI(Uri.parse(pImage));
        }
        catch (Exception e){
        }



// strt delete post it's work
        myHolder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                android.app.AlertDialog.Builder alertDialogBilder = new AlertDialog.Builder(context);
                alertDialogBilder.setTitle("delete post");
                alertDialogBilder.setMessage("Are you sure you want to delete this post?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // close the dialog
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {


                                Query fquery = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("pId").equalTo(pId);
                                fquery.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        for (DataSnapshot ds :dataSnapshot.getChildren()){

                                            ds.getRef().removeValue();// remove value from firebase where pid matches
                                        }
                                        Toast.makeText(context,"Deleted successfully",Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });




                            }
                        });


                AlertDialog alertDialog = alertDialogBilder.create();
                alertDialog.show();




            }



        });// end delete post

        myHolder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edit icon is clicked
                // start AddPostActivity with key "editPost" and the id of the post clicked
                Intent intent = new Intent(context, AddPostActivity.class);
                intent.putExtra("key","editPost");
                intent.putExtra("editPostId", pId);
                context.startActivity(intent);
            }
        });




/*
        // strt delete post
        myHolder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog pd = new ProgressDialog(context);
                pd.setMessage("Deleting...");

                StorageReference picRef = FirebaseStorage.getInstance().getReferenceFromUrl(pImage);
                picRef.delete()

                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //image deleted

                                Query fquery = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("pId").equalTo(pId);
                                fquery.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        for (DataSnapshot ds :dataSnapshot.getChildren()){

                                            ds.getRef().removeValue();// remove value from firebase where pid matches
                                        }
                                        Toast.makeText(context,"Deleted successfully",Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }


                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed
                        pd.dismiss();
                        Toast.makeText(context,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });// end delete post


*/




    }//onCreate

    @Override
    public int getItemCount() {
        return postList.size();
    }


    //view holder class
    class MyHolder extends RecyclerView.ViewHolder{

        //views from row_posts.xml
        ImageView uPictureIv, pImageIv;
        TextView uNameTv, pTimeTv, pTitle, pDescriptionTv;
        ImageButton moreBtn, editBtn;
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
            editBtn = itemView.findViewById(R.id.editBtn);



        }
    }
}//adapter class
