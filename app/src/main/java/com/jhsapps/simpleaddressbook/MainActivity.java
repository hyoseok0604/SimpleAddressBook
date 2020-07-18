package com.jhsapps.simpleaddressbook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = findViewById(R.id.lv);

        CustomAdapter adapter = new CustomAdapter(this, generateData(20));
        lv.setAdapter(adapter);
    }

    @SuppressLint("DefaultLocale")
    private ArrayList<Item> generateData(int count) {
        ArrayList<Item> data = new ArrayList<>();
        for(int i = 0 ; i < count ; i++){
            data.add(new Item("이름" + i, String.format("010-%04d-%04d", i / 1000, i % 1000)));
        }
        return data;
    }
}