package com.example.jadaa.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jadaa.MainActivity;
import com.example.jadaa.MyOrderDetailsActivity;
import com.example.jadaa.R;
import com.example.jadaa.User;
import com.example.jadaa.ViewPostActivity;
import com.example.jadaa.models.ModelPost;
import com.example.jadaa.models.soldBooks;
import com.example.jadaa.myCustomersDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCustomers extends RecyclerView.Adapter<AdapterCustomers.MyHolder>{

   // String uid,pTitle,pDescription,pAuthor,pEdition,pImage,pPrice,pStatus,pCollege,pDate,pTime,pPublisher;
    Context context;
    List<soldBooks> postList;
    private TextView email, phone, Name, btn;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private EditText nameEditText, passwordEditText, confirmPassEditText;
    private EditText phoneEditText, emailEditText;
    // Toolbar toolbar;
    // private String email ;
    // private static final String USERS = "users";



    public AdapterCustomers(Context context, List<soldBooks> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //inflate layout row_posts.xml
        View view = LayoutInflater.from(context).inflate(R.layout.row_customers,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int i) {

         //get data
        final String name ,phone1 ,email1; ///////////////////////////////////
        final String BookPrice = postList.get(i).getBookPrice();
        final String BookTitle = postList.get(i).getBookTitle();
        final String delivered = postList.get(i).getDelivered();
        final String inTransit = postList.get(i).getInTransit();
        final String pId = postList.get(i).getpId();
        final String processing = postList.get(i).getProcessing();
        final String purchaseDate = postList.get(i).getPurchaseDate();
        final String purchaseTime = postList.get(i).getPurchaseTime();
        final String purchaserID = postList.get(i).getPurchaserID();
        final String sellerID = postList.get(i).getSellerID();
        final String shipped = postList.get(i).getShipped();
        final String uri = postList.get(i).getUri();
        final String orderConfirmation = postList.get(i).getOrderConfirmation();
        final String BookAuthor = postList.get(i).getBookAuthor();
        final String BookEdition= postList.get(i).getBookEdition();

        myHolder.Torder_ID.setText(pId);

        myHolder.TBookTitle.setText(BookTitle);
        myHolder.TbookAuther.setText(BookAuthor);
        myHolder.TbookEdition.setText("Edition "+BookEdition);
        //myHolder.Torder_ID.setText(pId);

        if (BookPrice.equals("0"))
            myHolder.TbookPrice.setText("Book is free");
        else
            myHolder.TbookPrice.setText(BookPrice + " SAR");

        try {
            Picasso.get().load(uri).into(myHolder.TbookImage);
        } catch (Exception e) {
        }

        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users").child(purchaserID);

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

             if (dataSnapshot.getValue() != null) {
                 User user = dataSnapshot.getValue(User.class);

                 String name =user.getFullName() ;
                 String phone1 = user.getPhone() ;

                 myHolder.TpurchaserName.setText(user.getFullName());
                 myHolder.TpurchaserPhone.setText(user.getPhone());
                 // System.out.println(post);

             }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        /*
        //--------------view profile-----------------
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference =  FirebaseDatabase.getInstance().getReference("users").child(purchaserID);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    //get Data
                     name =""+ ds.child("fullName").getValue();
                     phone1 = "" + ds.child("phone").getValue();

                    // String email1 = "" + ds.child("email").getValue();
                    //set data
                    myHolder.TpurchaserName.setText(name);
                    myHolder.TpurchaserPhone.setText(phone1);
                    // email.setText(email1);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

*/

        myHolder.Torder_detail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewSingle = new Intent(context, myCustomersDetails.class);
                viewSingle.putExtra("pId", pId);
                viewSingle.putExtra("purchaseDate", purchaseDate);
                viewSingle.putExtra("purchaseTime", purchaseTime);
                viewSingle.putExtra("processing", processing);
                viewSingle.putExtra("shipped", shipped);
                viewSingle.putExtra("inTransit", inTransit);
                viewSingle.putExtra("delivered", delivered);
                viewSingle.putExtra("uri", uri);
                viewSingle.putExtra("BookTitle", BookTitle);
                viewSingle.putExtra("BookPrice", BookPrice);
                viewSingle.putExtra("BookAuthor", BookAuthor);
                viewSingle.putExtra("BookEdition", BookEdition);
                viewSingle.putExtra("purchaserID", purchaserID);
                //   viewSingle.putExtra("purchaserName", name);

              //  viewSingle.putExtra("purchaserPhone", phone1);

                context.startActivity(viewSingle);
            }
        });



        myHolder.Torder_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewSingle = new Intent(context, myCustomersDetails.class);
                viewSingle.putExtra("pId", pId);
                viewSingle.putExtra("purchaseDate", purchaseDate);
                viewSingle.putExtra("purchaseTime", purchaseTime);
                viewSingle.putExtra("processing", processing);
                viewSingle.putExtra("shipped", shipped);
                viewSingle.putExtra("inTransit", inTransit);
                viewSingle.putExtra("delivered", delivered);
                viewSingle.putExtra("uri", uri);
                viewSingle.putExtra("BookTitle", BookTitle);
                viewSingle.putExtra("BookPrice", BookPrice);
                viewSingle.putExtra("BookAuthor", BookAuthor);
                viewSingle.putExtra("BookEdition", BookEdition);
                viewSingle.putExtra("purchaserID", purchaserID);
             //   viewSingle.putExtra("purchaserName", name);
              //  viewSingle.putExtra("purchaserPhone", phone1);
                context.startActivity(viewSingle);
            }
        });

        //  myHolder.TpurchaserName.setText(purchaserID);


    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


    //view holder class
    class MyHolder extends RecyclerView.ViewHolder{

        TextView Torder_ID;
        //views from row_posts.xml
        ImageView processingT ,shippedT,in_transitT,deliveredT;
        ImageView TbookImage ,Torder_detail2;
        TextView order_status ,TbookAuther,TbookEdition,order_date ,TBookTitle ,TbookPrice,Tconfirm_order,Torder_detail,TpurchaserName,TpurchaserPhone;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            Torder_ID =itemView.findViewById(R.id.order_ID);
            TpurchaserName = itemView.findViewById(R.id.purchaserName);
            TpurchaserPhone = itemView.findViewById(R.id.purchaserPhone);
            TbookAuther  = itemView.findViewById(R.id.auther_name);
            TbookEdition = itemView.findViewById(R.id.edition);
            TbookImage = itemView.findViewById(R.id.bookImage);
            Torder_detail2 = itemView.findViewById(R.id.order_detail2);
            //order_status = itemView.findViewById(R.id.order_status);
            //order_date = itemView.findViewById(R.id.order_date);
            TBookTitle = itemView.findViewById(R.id.BookTitle);
            TbookPrice = itemView.findViewById(R.id.bookPrice);
            Tconfirm_order = itemView.findViewById(R.id.confirm_order);
            Torder_detail = itemView.findViewById(R.id.order_detail);


        }
    }
}//adapter class
