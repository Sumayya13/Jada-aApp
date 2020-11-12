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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jadaa.MyOrderActivity;
import com.example.jadaa.MyOrderDetailsActivity;
import com.example.jadaa.R;
import com.example.jadaa.ViewPostActivity;
import com.example.jadaa.models.ModelPost;
import com.example.jadaa.models.soldBooks;
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

public class AdapterMyOrder extends RecyclerView.Adapter<AdapterMyOrder.MyHolder>{

   // String uid,pTitle,pDescription,pAuthor,pEdition,pImage,pPrice,pStatus,pCollege,pDate,pTime,pPublisher;
    Context context;
    List<soldBooks> postList;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public AdapterMyOrder(Context context, List<soldBooks> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //inflate layout row_posts.xml
        View view = LayoutInflater.from(context).inflate(R.layout.row_myorder,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int i) {
        //get data

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



        // بأعرض آخر حالة للطلب
        if (delivered.equals("1")) {
            myHolder.orderstatusT.setText("Delivered");

        }
        else if (inTransit.equals("1")) {
            myHolder.orderstatusT.setText("In Transit");
        }
        else if (shipped.equals("1")) {
            myHolder.orderstatusT.setText("Shipped");
        }
        else
        if (processing.equals("1")) {
            myHolder.orderstatusT.setText("Processing");
        }else  myHolder.orderstatusT.setText("ordered");




/*

        if (orderConfirmation.equals("true")) {
            myHolder.confirm_order.setClickable(false);
            myHolder.confirm_order.setVisibility(View.INVISIBLE);
        }


        if(delivered.equals("1")) {
            myHolder.confirm_order.setClickable(false);
            myHolder.confirm_order.setVisibility(View.VISIBLE);
        }

          if (delivered.equals("0"))// not delivered yet

        {
            myHolder.confirm_order.setVisibility(View.INVISIBLE);
            myHolder.confirm_order.setClickable(false);

        }
*/

        //set data
        myHolder.BookTitle.setText(BookTitle);
        myHolder.order_date.setText(purchaseDate + " " + purchaseTime);


        if (BookPrice.equals("0"))
            myHolder.bookPrice.setText("Book is free");
        else
            myHolder.bookPrice.setText(BookPrice + " SAR");

        try {
            Picasso.get().load(uri).into(myHolder.bookImage);
        } catch (Exception e) {
        }




        // بأعرض اللون للحالة الطلب

        if (delivered.equals("1"))
        {
            myHolder.deliveredT.setImageDrawable(context.getResources().getDrawable(R.drawable.delivered__status));
        }else myHolder.deliveredT.setVisibility(View.INVISIBLE); // not delivered yet


        if (inTransit.equals("1"))
        {
            myHolder.in_transitT.setImageDrawable(context.getResources().getDrawable(R.drawable.intransit_status));
        }else myHolder.in_transitT.setVisibility(View.INVISIBLE); // not in Transit yet


            if (shipped.equals("1"))  // not in transit yet
        {
            myHolder.shippedT.setImageDrawable(context.getResources().getDrawable(R.drawable.shipped__status));
        }else myHolder.shippedT.setVisibility(View.INVISIBLE); // not in Transit yet



        if (processing.equals("1"))  // not shipped yet
        {
            myHolder.processingT.setImageDrawable(context.getResources().getDrawable(R.drawable.processing_status));
        }else    myHolder.processingT.setVisibility(View.INVISIBLE);// not in Transit yet

/*


        // بأعرض اللون للحالة الطلب

        if (delivered.equals("1"))
        {
            myHolder.deliveredT.setImageDrawable(context.getResources().getDrawable(R.drawable.delivered__status));
        }else myHolder.deliveredT.setImageDrawable(context.getResources().getDrawable(R.drawable.gray_new)); // not delivered yet


        if (inTransit.equals("1"))
        {
            myHolder.in_transitT.setImageDrawable(context.getResources().getDrawable(R.drawable.intransit_status));
        }else myHolder.in_transitT.setImageDrawable(context.getResources().getDrawable(R.drawable.gray_new)); // not in Transit yet


            if (shipped.equals("1"))  // not in transit yet
        {
            myHolder.shippedT.setImageDrawable(context.getResources().getDrawable(R.drawable.shipped__status));
        }else myHolder.shippedT.setImageDrawable(context.getResources().getDrawable(R.drawable.gray_new)); // not in Transit yet



        if (processing.equals("1"))  // not shipped yet
        {
            myHolder.processingT.setImageDrawable(context.getResources().getDrawable(R.drawable.processing_status));
        }else    myHolder.processingT.setImageDrawable(context.getResources().getDrawable(R.drawable.gray_new));// not in Transit yet


*/

/*

        // بأعرض اللون للحالة الطلب
        if (processing.equals("0"))  // not shipped yet
        {

            myHolder.processingT.setVisibility(View.INVISIBLE);
            myHolder.confirm_order.setVisibility(View.INVISIBLE);
            myHolder.confirm_order.setClickable(false);
            myHolder.order_status.setText("order");

        }
        if (shipped.equals("0"))  // not shipped yet
        {
            myHolder.shippedT.setVisibility(View.INVISIBLE);
            myHolder.confirm_order.setVisibility(View.INVISIBLE);
            myHolder.confirm_order.setClickable(false);

        }
        else myHolder.shippedT.setVisibility(View.VISIBLE);

        if (inTransit.equals("0"))  // not in Transit yet
            myHolder.in_transitT.setVisibility(View.INVISIBLE);
        else myHolder.in_transitT.setVisibility(View.VISIBLE);

        if (delivered.equals("0"))// not delivered yet

        {
            myHolder.deliveredT.setVisibility(View.INVISIBLE);
            myHolder.confirm_order.setVisibility(View.INVISIBLE);
            myHolder.confirm_order.setClickable(false);

        }
        else myHolder.shippedT.setVisibility(View.VISIBLE);



*/








        myHolder.confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (orderConfirmation.equals("false")) { // صفر يعني لم يتم تأكيد الطلب بعد
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
                    ref.child(pId).child("orderConfirmation").setValue("true");

                    myHolder.confirm_order.setVisibility(View.INVISIBLE);
                    myHolder.confirm_order.setClickable(false);


                    Toast.makeText(context,"The order has been confirmed",Toast.LENGTH_LONG).show();
                //    Toast.makeText(AdapterMyOrder.class, "Cancel", Toast.LENGTH_SHORT).show();
                }
            }
        });



        myHolder.order_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent viewSingle = new Intent(context, MyOrderDetailsActivity.class);

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

                context.startActivity(viewSingle);

                }

        });


// لازم اغيرها بحيث اجيب سناب شوت واشوف القيمة
        Query fquery = FirebaseDatabase.getInstance().getReference("soldBooks").child(pId).orderByChild("delivered").equalTo("0");
        fquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myHolder.confirm_order.setVisibility(View.INVISIBLE);
                myHolder.confirm_order.setClickable(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


    //view holder class
    class MyHolder extends RecyclerView.ViewHolder{

        //views from row_posts.xml
        ImageView processingT ,shippedT,in_transitT,deliveredT;
        ImageView bookImage ,order_detail2;
        TextView order_status , order_date ,BookTitle ,bookPrice,confirm_order,order_detail,orderstatusT ;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //init views
            processingT = itemView.findViewById(R.id.processing);
            shippedT = itemView.findViewById(R.id.shipped);
            in_transitT = itemView.findViewById(R.id.in_transit);
            deliveredT = itemView.findViewById(R.id.delivered);
            bookImage = itemView.findViewById(R.id.bookImage);
            order_detail2 = itemView.findViewById(R.id.order_detail2);
            order_date = itemView.findViewById(R.id.order_date);
            BookTitle = itemView.findViewById(R.id.BookTitle);
            bookPrice = itemView.findViewById(R.id.bookPrice);
            confirm_order = itemView.findViewById(R.id.confirm_order);
            order_detail = itemView.findViewById(R.id.order_detail);
            orderstatusT = itemView.findViewById(R.id.orderstatus);

        }
    }
}//adapter class
