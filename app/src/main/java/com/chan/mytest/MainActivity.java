package com.chan.mytest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialDialogs;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    EditText txtName, txtAddress, txtMobile, txtEmail;
    Button btnSubmit,btnNext;
    DatabaseHandler db ;

    String name, address,mobile,email;
    final int PICK_IMAGE_REQUEST = 999;
    private Bitmap bitmap;
    private Uri filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        imageView = findViewById(R.id.imageView);
        txtName   = findViewById(R.id.name);
        txtAddress = findViewById(R.id.address);
        txtMobile = findViewById(R.id.mobile);
        txtEmail = findViewById(R.id.email);

        btnSubmit = findViewById(R.id.button);
        btnNext = findViewById(R.id.button2);

        btnSubmit.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            try {
                if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                    super.onActivityResult(requestCode, resultCode, data);
                    filePath = data.getData();
                   // InputStream inputStream = getContentResolver().openInputStream(filePath);
                    // bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                    imageView.setImageBitmap(bitmap);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.button:
                saveData();
                break;
            case R.id.button2:
                startActivity(new Intent(getApplicationContext(), User_List.class));
                break;
        }
    }

    private void saveData() {
        name = txtName.getText().toString();
        address = txtAddress.getText().toString();
        mobile = txtMobile.getText().toString();
        email = txtEmail.getText().toString();

        if(isValid() && isValidMobile(mobile)){
            if(imageView.getDrawable() != null && bitmap != null){
                Toast.makeText(this, "Valid",Toast.LENGTH_LONG).show();
                byte[] photo = imageViewToByte(imageView);
                // db.insertData(new USER(name,bitmap));
                db.insertData(name, mobile, email, address, photo);
                txtName.setText("");
                txtAddress.setText("");
                txtMobile.setText("");
                txtEmail.setText("");
                imageView.setImageResource(R.mipmap.ic_launcher);
            }else{
                Toast.makeText(this, "Upload image/photo",Toast.LENGTH_LONG).show();
            }
        }else{
            txtMobile.setError("Enter valid mobile");
        }

    }

    private boolean isValid() {
        email = txtEmail.getText().toString();
        if(txtName.getText().toString().isEmpty()){
            txtName.setError("Enter  name");
            return false;
        }else if(txtAddress.getText().toString().isEmpty()){
            txtAddress.setError("Enter  address");
            return false;
        }else if(txtMobile.getText().toString().isEmpty()){
            txtMobile.setError("Enter  mobile");
            return false;
        }else if(txtEmail.getText().toString().isEmpty()){
            txtEmail.setError("Enter email");
            return false;
        }else if(!isValidEmailId(email)){
            txtEmail.setError("Enter valid email");
            return false;
        }else{
            return true;
        }

    }


    private boolean isValidMobile(String mobile) {
         if(mobile.length() == 10){
             return true;
        }else{
            return  false;
        }
    }

    private boolean isValidEmailId(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public static byte[] imageViewToByte(ImageView image) {

        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
