package com.example.pruebatecnica_blaspiris;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UserInfoActivty extends AppCompatActivity {

    Button addContacts;
    ToggleButton generateQR;
    ImageView image;
    TextView name,fullname,gender,address,email,city,country,phone,cell,birthday;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_activty);

        Intent i = getIntent();
        user = (User)i.getSerializableExtra("user");

        addContacts=findViewById(R.id.addContacts);
        generateQR=findViewById(R.id.generateQR);

        image=findViewById(R.id.image);
        name=findViewById(R.id.name);
        fullname=findViewById(R.id.fullname);
        gender=findViewById(R.id.gender);
        address=findViewById(R.id.address);
        city=findViewById(R.id.city);
        country=findViewById(R.id.country);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        cell=findViewById(R.id.cell);
        birthday=findViewById(R.id.birthday);

        try {
            InputStream is = (InputStream) new URL(user.getPhoto()).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            image.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        name.setText(user.getNameSurname());
        fullname.setText(user.getAllName());
        gender.setText(user.getGender());
        address.setText(user.getAddress());
        city.setText(user.getAllCity());
        country.setText(user.getCountry());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());
        cell.setText(user.getPhone2());
        birthday.setText(user.getDateAge());

        addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser(user);
            }
        });

        generateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(generateQR.isChecked()){
                    generateQR(user);
                }else{
                    setImage(user);
                }

            }
        });
    }

    //SAVE USER IN CONTACTS
    private void saveUser(User user) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, user.getName()+" "+user.getSurname());
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, user.getPhone());
        intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, user.getPhone2());
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, user.getEmail());
        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, user.getAddress());
        startActivity(intent);
    }

    //GENERATE QR
    private void generateQR(User user) {
        String str =
                "BEGIN:VCARD\n" +
                        "VERSION:3.0\r\n" +
                        "N:" + user.getSurname() + ";" + user.getName() + ";;Mr;\r\n" +
                        "FN:" + "Mr." + user.getName() + " " + user.getSurname() + "\r\n" +
                        "EMAIL:" + user.getEmail() + "\r\n" +
                        "TEL;CELL:" + user.getPhone() +"\r\n" +
                        "END:VCARD\r\n";
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(str, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            image.setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    //SET IMAGE
    private void setImage(User user) {
        try {
            InputStream is = (InputStream) new URL(user.getPhoto()).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            image.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}