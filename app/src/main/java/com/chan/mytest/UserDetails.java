package com.chan.mytest;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class UserDetails extends AppCompatActivity {
    int id ;
    DatabaseHandler db;
    String name, mobile, email, address;
    Bitmap image;
    ImageView imageView;
    TextView txtName, txtMobile, txtEmail, txtAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        id = getIntent().getIntExtra("id",0);
        db = new DatabaseHandler(this);

        imageView = findViewById(R.id.imageView2);
        txtName = findViewById(R.id.textView2);
        txtMobile = findViewById(R.id.textView5);
        txtEmail = findViewById(R.id.textView6);
        txtAddress = findViewById(R.id.textView4);
        getUserDetails(id);
    }

    private void getUserDetails(int id) {
        List<USER> uersDetails = db.getUserDetails(id);
        for(USER user: uersDetails){
            name = user.getName();
            mobile = user.getMobile();
            email = user.getEmail();
            address = user.getAddress();
            image = user.getImage();
        }
        txtName.setText("Name : "+name);
        txtMobile.setText("Mobile : "+mobile);
        txtEmail.setText("Email : "+email);
        txtAddress.setText("Address : "+address);
        imageView.setImageBitmap(image);
    }
}
