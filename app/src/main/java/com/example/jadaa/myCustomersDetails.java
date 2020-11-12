package com.example.jadaa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
public class myCustomersDetails extends AppCompatActivity {

    TextView Tvorder_ID ,TvBookTitle ,Tvauther_name ,Tvedition ,TvbookPrice ,TvpurchaserName ,TvpurchaserPhone , Tvorder_date ;
    String purchaserPhone ,purchaserName;
    ImageView IvbookImage;

    LinearLayout LLProcessing  , LLshipped ,LLinTransit, LLdelivered;
    TextView TvProcessing , Tvshipped , TvinTransit, Tvdelivered;
    ImageView IvProcessing , Ivshipped ,IvinTransit ,  Ivdelivered;

    String purchaseDate,purchaseTime, processing,shipped,inTransit,delivered,uri,BookPrice,BookTitle,BookAuthor,BookEdition,purchaserID;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_customers_details);


        /*---------------------delete app bar ------------------------*/
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // ------------------------tool bar ------------------
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


        Tvorder_ID = (TextView) findViewById(R.id.order_ID);
        IvbookImage = findViewById(R.id.bookImage);
        TvBookTitle = (TextView) findViewById(R.id.BookTitle);
        Tvauther_name = (TextView) findViewById(R.id.auther_name);
        Tvedition = (TextView) findViewById(R.id.edition);
        TvbookPrice = (TextView) findViewById(R.id.bookPrice);
        TvpurchaserName = (TextView) findViewById(R.id.purchaserName);
        TvpurchaserPhone = (TextView) findViewById(R.id.purchaserPhone);
        Tvorder_date = (TextView) findViewById(R.id.order_date);

        // ----------------------order status--------
        LLProcessing = findViewById(R.id.LLProcessing);
        LLshipped = findViewById(R.id.LLshipped);
        LLinTransit = findViewById(R.id.LLinTransit);
        LLdelivered = findViewById(R.id.LLdelivered);


        IvProcessing = findViewById(R.id.IvProcessing);
        Ivshipped = findViewById(R.id.Ivshipped);
        IvinTransit = findViewById(R.id.IvinTransit);
        Ivdelivered = findViewById(R.id.Ivdelivered);

        TvProcessing = (TextView) findViewById(R.id.TvProcessing);
        Tvshipped = (TextView) findViewById(R.id.Tvshipped);
        TvinTransit = (TextView) findViewById(R.id.TvinTransite);
        Tvdelivered = (TextView) findViewById(R.id.Tvdelivered);


        final String title, des, price, pAuth, uid, pStatus, pImage, pid;


        final Bundle extras = getIntent().getExtras();

        pid = extras.getString("pId");
        purchaseDate = extras.getString("purchaseDate");
        purchaseTime = extras.getString("purchaseTime");
        processing = extras.getString("processing");
        shipped = extras.getString("shipped");
        inTransit = extras.getString("inTransit");
        delivered = extras.getString("delivered");
        uri = extras.getString("uri");
        BookTitle = extras.getString("BookTitle");
        BookPrice = extras.getString("BookPrice");
        BookAuthor = extras.getString("BookAuthor");
        BookEdition = extras.getString("BookEdition");
        purchaserID = extras.getString("purchaserID");
        //purchaserPhone = extras.getString("purchaserPhone");
        //purchaserName = extras.getString("purchaserName");


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

                    TvpurchaserName.setText(user.getFullName());
                    TvpurchaserPhone.setText(user.getPhone());
                    // System.out.println(post);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        // لو القيمة ١ تلقائيا لونها بيطلع لاني اضفته في ال xml
        if (processing.equals("0"))
            IvProcessing.setImageDrawable(getApplicationContext().getDrawable(R.drawable.gray));

        if (shipped.equals("0"))
            Ivshipped.setImageDrawable(getApplicationContext().getDrawable(R.drawable.gray));

        if (inTransit.equals("0"))
            IvinTransit.setImageDrawable(getApplicationContext().getDrawable(R.drawable.gray));

        if (delivered.equals("0"))
            Ivdelivered.setImageDrawable(getApplicationContext().getDrawable(R.drawable.gray));




        // أغير اللون من الرصاصي
        IvProcessing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (processing.equals("0")) {
                    IvProcessing.setImageDrawable(getApplicationContext().getDrawable(R.drawable.processing_status));
                    processing = "1";
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
                    ref.child(pid).child("processing").setValue(processing);
                }
                else if (processing.equals("1") && shipped.equals("0"))  {   // لو البروسسنق واحد والشيبد ٠ اشيل الضوء
                    IvProcessing.setImageDrawable(getApplicationContext().getDrawable(R.drawable.gray));
                    processing = "0";
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
                    ref.child(pid).child("processing").setValue(processing);

                }
                    if (processing.equals("1") && shipped.equals("1")) { // لو جيت بطفي الترانزت واللي بعدها الدلفرد شغاله بطلع له مسج تقول ان لازم تشيل الدلفرد اول
                        Toast.makeText(myCustomersDetails.this, "remove shipped first", Toast.LENGTH_SHORT).show();
                }

            }
        });


        /*
        // أغير اللون من الرصاصي للبرتقالي
        Ivshipped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (shipped.equals("0")) {
                    Ivshipped.setImageDrawable(getApplicationContext().getDrawable(R.drawable.shipped__status));
                    shipped = "1";
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
                    ref.child(pid).child("shipped").setValue(shipped);
                } else {
                    Ivshipped.setImageDrawable(getApplicationContext().getDrawable(R.drawable.gray));
                    shipped = "0";
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
                    ref.child(pid).child("shipped").setValue(shipped);
                }

            }
        });




    /*
        // أغير اللون من الرصاصي الأخصر
        Ivdelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // -----------اشيك عالحالة اللي قبلها ما اقدر اسوي الحالة  الا اذا سويت الحالة اللي قبلها
                if (inTransit.equals("0") || shipped.equals("0")) {
                    Toast.makeText(myCustomersDetails.this,"Choose all status before it" , Toast.LENGTH_SHORT).show();}
                else {
                    if (delivered.equals("0")) {
                        Ivdelivered.setImageDrawable(getApplicationContext().getDrawable(R.drawable.delivered__status));
                        delivered = "1";
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
                        ref.child(pid).child("delivered").setValue(delivered);
                    } else {
                        Ivdelivered.setImageDrawable(getApplicationContext().getDrawable(R.drawable.gray));
                        delivered = "0";
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
                        ref.child(pid).child("delivered").setValue(delivered);
                    }
                }// else



            }
        });
*/

        /*
        // أغير اللون من الرصاصي الأخصر
        Ivdelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // -----------اشيك عالحالة اللي قبلها ما اقدر اسوي الحالة  الا اذا سويت الحالة اللي قبلها
                if (inTransit.equals("0") || shipped.equals("0")) {
                    Toast.makeText(myCustomersDetails.this, "Choose all status before it", Toast.LENGTH_SHORT).show();
                } else {
                    if (delivered.equals("0")) {
                        Ivdelivered.setImageDrawable(getApplicationContext().getDrawable(R.drawable.delivered__status));
                        delivered = "1";
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
                        ref.child(pid).child("delivered").setValue(delivered);
                    } else {
                        Ivdelivered.setImageDrawable(getApplicationContext().getDrawable(R.drawable.gray));
                        delivered = "0";
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
                        ref.child(pid).child("delivered").setValue(delivered);
                    }
                }// else


            }
        });

         */
/*
        // احط الصورة المناسبةلليوزر بناء على حاله الطلب
        if (delivered.equals("")) {
            track.setImageDrawable(getApplicationContext().getDrawable(R.drawable.alldelivered));
        } else {
            if (inTransit.equals("1"))
                track.setImageDrawable(getResources().getDrawable(R.drawable.allintransit));
            else if (shipped.equals("1"))
                track.setImageDrawable(getResources().getDrawable(R.drawable.allshipped));
            else
                track.setImageDrawable(getResources().getDrawable(R.drawable.allprocessing));

        }


 */


        //_____________________ set data
        Tvorder_ID.setText("#" + pid);
        TvBookTitle.setText(BookTitle);
        Tvedition.setText("Edition " + BookEdition);
        Tvauther_name.setText(BookAuthor);

        try {
            Picasso.get().load(uri).into(IvbookImage);
        } catch (Exception e) {
        }

        if (BookPrice.equals("0"))
            TvbookPrice.setText("Book is free");
        else
            TvbookPrice.setText(BookPrice + " SAR");

        //  TvpurchaserName.setText("Suzan Salem");
        // TvpurchaserPhone.setText("057836529");
        TvpurchaserName.setText(purchaserName);
        TvpurchaserPhone.setText(purchaserPhone);
        Tvorder_date.setText(purchaseDate + " " + purchaseTime);


        //TvpurchaserName.setText(pur);








/*

        // أغير اللون من الرصاصي الأصفر
        IvinTransit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inTransit.equals("0")) {
                    IvinTransit.setImageDrawable(getApplicationContext().getDrawable(R.drawable.intransit_status));
                    inTransit = "1";
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
                    ref.child(pid).child("inTransit").setValue(inTransit);
                } else {
                    IvinTransit.setImageDrawable(getApplicationContext().getDrawable(R.drawable.gray));
                    inTransit = "0";
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
                    ref.child(pid).child("inTransit").setValue(inTransit);
                }

            }
        });
*/

/*
        // أغير اللون من الرصاصي لبنفسج
        IvProcessing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

if (processing.equals("0")) {
    IvProcessing.setImageDrawable(getApplicationContext().getDrawable(R.drawable.processing_status));
    processing = "1";
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
    ref.child(pid).child("processing").setValue(processing);
}

            //لكن لازم shipped تكون طافيه اعشان اطفي pricessing
                    if (processing.equals("1") && shipped.equals("0")) {
                IvProcessing.setImageDrawable(getApplicationContext().getDrawable(R.drawable.gray));
                processing = "0";
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
                ref.child(pid).child("processing").setValue(processing);
            }

                if (processing.equals("1") && shipped.equals("1")) { // لو جيت بطفي الترانزت واللي بعدها الدلفرد شغاله بطلع له مسج تقول ان لازم تشيل الدلفرد اول
                Toast.makeText(myCustomersDetails.this, "remove shipped first", Toast.LENGTH_SHORT).show();

            }


            }
        });



 */



// --------------------------------------------------------------تم اختبار ان ترانزت
        // أغير اللون من الرصاصي للازرق
        Ivshipped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (shipped.equals("0")){// صفر ويبي يضغطها عشان تصيير واحد
                    if (processing.equals("0"))    // ضغط shipped وماهي بروسسنق
                        Toast.makeText(myCustomersDetails.this, "status should be processing first", Toast.LENGTH_SHORT).show();

                    else if (processing.equals("1"))
                    {
                        // لو shiped طافيه اشغلها
                        if (shipped.equals("0")) {
                            Ivshipped.setImageDrawable(getApplicationContext().getDrawable(R.drawable.shipped__status));
                            shipped = "1";
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
                            ref.child(pid).child("shipped").setValue(shipped);
                        }



                    }

// لو الترانز
                }else  //لكن لازم الترانزت تكون طافيه اعشان اطفي الشبد
                    if (shipped.equals("1") && inTransit.equals("0")) {
                        Ivshipped.setImageDrawable(getApplicationContext().getDrawable(R.drawable.gray));
                        shipped = "0";
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
                        ref.child(pid).child("shipped").setValue(shipped);
                    }

                if (shipped.equals("1") && inTransit.equals("1")) { // لو جيت بطفي الترانزت واللي بعدها الدلفرد شغاله بطلع له مسج تقول ان لازم تشيل الدلفرد اول
                    Toast.makeText(myCustomersDetails.this, "remove in transit first", Toast.LENGTH_SHORT).show();

                }
            }
        });



// --------------------------------------------------------------تم اختبار ان ترانزت
        // أغير اللون من الرصاصي الأخصر
        IvinTransit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inTransit.equals("0")){// صفر ويبي يضغطها فبتصير بعد التشييك واحد
                    if (shipped.equals("0"))    // ضغط transit وماهي shipped
                        Toast.makeText(myCustomersDetails.this, "status should be shipped first", Toast.LENGTH_SHORT).show();

                    else if (shipped.equals("1"))  //  ضغط الدلفرد وهي ترانزت انور الضوء (الباث الصح
                    {
                        // لو الترانزت طافيه اشغلها
                        if (inTransit.equals("0")) {
                            IvinTransit.setImageDrawable(getApplicationContext().getDrawable(R.drawable.intransit_status));
                            inTransit = "1";
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
                            ref.child(pid).child("inTransit").setValue(inTransit);
                        }



                    }

// لو الترانز
                }else  //لكن لازم الدلفرد تكون طافيه  لو شغاله الترانزت اطفيها
                if (inTransit.equals("1") && delivered.equals("0")) {
                    IvinTransit.setImageDrawable(getApplicationContext().getDrawable(R.drawable.gray));
                    inTransit = "0";
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
                    ref.child(pid).child("inTransit").setValue(inTransit);
                }

                if (inTransit.equals("1") && delivered.equals("1")) { // لو جيت بطفي الترانزت واللي بعدها الدلفرد شغاله بطلع له مسج تقول ان لازم تشيل الدلفرد اول
                    Toast.makeText(myCustomersDetails.this, "remove delivered first", Toast.LENGTH_SHORT).show();

                }
            }
        });






        // لو ضغط الدلفرد و الترانزت اللي قبلها ما انضغطت اطلع له مسج
        // ضغط الدلفرد والترانزت انضغطت حالتين
        // اما رصاصي الضوء فاخليه اصفر
        // واما اصفر واخليه رصالي






// --------------------------------------------------------------تم اختبار الدلفرد

                // أغير اللون من الرصاصي الاصفر
                Ivdelivered.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (delivered.equals("0")){// صفر ويبي يضغطها فبتصير بعد التشييك واحد
                            if (inTransit.equals("0"))    // ضغط الدلفرد وماهي ان ترانزت الخطأ
                                Toast.makeText(myCustomersDetails.this, "status should be in transit first", Toast.LENGTH_SHORT).show();

                            else if (inTransit.equals("1"))  //  ضغط الدلفرد وهي ترانزت انور الضوء (الباث الصح
                            {

                                if (delivered.equals("0")) {
                                    Ivdelivered.setImageDrawable(getApplicationContext().getDrawable(R.drawable.delivered__status));
                                    delivered = "1";
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
                                    ref.child(pid).child("delivered").setValue(delivered);
                                }


                            }



// واخزن في الداتابيس القيمه الجديدة صفر و  الدلفرد مضاءة فاشيل الضوء مباشره لو ضغط
                    }else  if (delivered.equals("1")){
                            Ivdelivered.setImageDrawable(getApplicationContext().getDrawable(R.drawable.gray));
                        delivered = "0";
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("soldBooks");
                        ref.child(pid).child("delivered").setValue(delivered);
                    }}
                });








    }// on create
}



/*

ما اقدر اضغط الدلفرد الا اذا كنت ضاغطة الترانزت
ما اقدر اشيل الترانزت الا اذا شلت الضوء عن الدلفرد


ما اقدر اضغط الترانزت الا اذا كنت ضاغطه الشيبد
ما اقدر اشيل الشيبد الا اذا شلت الضوء عن البروسسنق


ما اقدر اشيل البروسسنق  الا اذا شلت الضوء عن الشيبد




 */