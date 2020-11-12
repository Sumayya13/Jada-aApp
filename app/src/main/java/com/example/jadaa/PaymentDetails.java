package com.example.jadaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetails extends AppCompatActivity {


    TextView txtId, txtAmount , txtStatus , moveMyOrder ,editProfile ,homeTv ;
    ImageView imageMyOrder,imageprofile , homeIv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        /*---------------------delete app bar ------------------------*/
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        txtId = (TextView)findViewById(R.id.txtId);
        txtAmount = (TextView)findViewById(R.id.txtAmount);
        txtStatus = (TextView)findViewById(R.id.txtStatus);
        editProfile = (TextView)findViewById(R.id.editProfile);
        moveMyOrder = (TextView)findViewById(R.id.moveMyOrder);
        imageMyOrder = (ImageView)findViewById(R.id.imageMyOrder);
        imageprofile = (ImageView)findViewById(R.id.imageprofile);
        homeIv =(ImageView)findViewById(R.id.homeIv);
        homeTv = (TextView)findViewById(R.id.homeTv);

        homeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move_To_home = new Intent(PaymentDetails.this, HomeActivity.class);
                startActivity(move_To_home );
            }
        });
        homeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move_To_home = new Intent(PaymentDetails.this, HomeActivity.class);
                startActivity(move_To_home );
            }
        });

        imageprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move_To_editProfil = new Intent(PaymentDetails.this, ProfileActivity.class);
                startActivity(move_To_editProfil);
            }
        });


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move_To_editProfil = new Intent(PaymentDetails.this, ProfileActivity.class);
                startActivity(move_To_editProfil);
            }
        });

        imageMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MyOrderActivity = new Intent(PaymentDetails.this, MyOrderActivity.class);
                startActivity(MyOrderActivity);
            }
        });

        moveMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MyOrderActivity = new Intent(PaymentDetails.this, MyOrderActivity.class);
                startActivity(MyOrderActivity);
            }
        });



        // get Intent
        Intent intent = getIntent() ;

        try {

            // استقبل الاوبجكت الللي ارسلته في صفحة فيو بوست بعد الدفع
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response") ,intent.getStringExtra("PaymentAmount"));

        }
        catch (JSONException e) {
            e.printStackTrace();
        }











    }// on craeete



    public void showDetails(JSONObject response, String paymentAmount){

        try {
            txtId.setText(response.getString("id"));
            txtStatus.setText(response.getString("state"));
            txtAmount.setText("$"+paymentAmount);


        } catch (JSONException e) {

        }
    }










    }// class