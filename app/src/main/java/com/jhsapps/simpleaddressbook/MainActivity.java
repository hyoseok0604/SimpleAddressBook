package com.jhsapps.simpleaddressbook;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    EditText et;
    TextView tv;

    ArrayList<Item> baseData;
    ArrayList<Item> shownData;

    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lv);
        et = findViewById(R.id.et_search);
        tv = findViewById(R.id.tv);

        baseData = generateData(/*20*/);
        shownData = new ArrayList<>(baseData);

        adapter = new CustomAdapter(this, shownData);
        lv.setAdapter(adapter);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                search(text);
            }
        });

        checkSearchResult();
    }

    private ArrayList<Item> generateData(/*int count*/) {
        ArrayList<Item> data = new ArrayList<>();
//        for(int i = 0 ; i < count ; i++){
//            data.add(new Item("이름" + i, String.format("010-%04d-%04d", i / 1000, i % 1000)));
//        }
        data.add(new Item("장효석", "010-7311-3209"));
        data.add(new Item("홍길동", "010-2010-2413"));
        data.add(new Item("김민수", "010-4213-7241"));
        data.add(new Item("김가은", "010-7894-1276"));
        data.add(new Item("안진수", "010-1412-3274"));
        data.add(new Item("황예찬", "010-7243-8325"));
        data.add(new Item("김민준", "010-7324-7443"));
        data.add(new Item("최윤수", "010-1206-2130"));
        data.add(new Item("조아라", "010-6234-6341"));
        data.add(new Item("김승현", "010-3421-6234"));
        data.add(new Item("박준성", "010-5143-6034"));
        data.add(new Item("박정민", "010-5123-6524"));
        data.add(new Item("정유진", "010-1513-9345"));
        data.add(new Item("이광수", "010-6234-1532"));
        return data;
    }

    private void search(String text){
        shownData.clear();

        for(Item s: baseData){
            if(s.name.contains(text) || s.number.replaceAll("-", "").contains(text)){
                shownData.add(s);
            }
        }

        checkSearchResult();

        adapter.notifyDataSetChanged();
    }

    private void checkSearchResult(){
        lv.setVisibility(View.INVISIBLE);
        tv.setVisibility(View.INVISIBLE);
        if (shownData.size() == 0){
            tv.setVisibility(View.VISIBLE);
        }else{
            lv.setVisibility(View.VISIBLE);
        }
    }
}