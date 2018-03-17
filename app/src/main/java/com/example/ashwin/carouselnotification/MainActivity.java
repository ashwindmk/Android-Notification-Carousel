package com.example.ashwin.carouselnotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.example.ashwin.carouselnotification.NotificationBuilder.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void carousel(View view) {
        NotificationBuilder.carouselNotify(this, 2, LEFT);
    }

}
