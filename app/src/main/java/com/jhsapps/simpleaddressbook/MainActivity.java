package com.jhsapps.simpleaddressbook;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.kennyc.bottomsheet.BottomSheetListener;
import com.kennyc.bottomsheet.BottomSheetMenuDialogFragment;

import java.util.ArrayList;
import java.util.regex.Pattern;

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
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new BottomSheetMenuDialogFragment.Builder(MainActivity.this)
                        .setSheet(R.menu.main_menu)
                        .setTitle("메뉴")
                        .object(position)
                        .setListener(new BottomSheetListener() {
                            @SuppressWarnings("NullableProblems")
                            @Override
                            public void onSheetShown(BottomSheetMenuDialogFragment bottomSheetMenuDialogFragment, Object o) {
                            }

                            @SuppressWarnings("NullableProblems")
                            @Override
                            public void onSheetItemSelected(BottomSheetMenuDialogFragment bottomSheetMenuDialogFragment, MenuItem menuItem, Object o) {
                                int position = (int) o;
                                switch(menuItem.getItemId()){
                                    case R.id.edit:
                                        edit(position);
                                        break;
                                    case R.id.delete:
                                        remove(position);
                                        break;
                                    case R.id.insert:
                                        insert();
                                        break;
                                }
                            }

                            @SuppressWarnings("NullableProblems")
                            @Override
                            public void onSheetDismissed(BottomSheetMenuDialogFragment bottomSheetMenuDialogFragment, Object o, int i) {
                            }
                        })
                        .show(getSupportFragmentManager());
                return false;
            }
        });

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

    private void edit(final int position){
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_edit, null);

        final EditText et_name = v.findViewById(R.id.et_name);
        final EditText et_number = v.findViewById(R.id.et_number);

        final Item item = shownData.get(position);

        et_name.setText(item.name);
        et_number.setText(item.number);

        new AlertDialog.Builder(this)
                .setTitle("수정")
                .setView(v)
                .setPositiveButton("완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(patternMatchingNumber(et_number.getText().toString())){
                            item.name = et_name.getText().toString();
                            item.number = et_number.getText().toString();

                            search(et.getText().toString()); // 변경된 데이터가 검색 결과에서 반영되도록
                        }else{
                            Toast.makeText(MainActivity.this, "전화번호 형식을 맞춰주세요", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void remove(int position){
        baseData.remove(shownData.get(position));
        shownData.remove(position);

        adapter.notifyDataSetChanged();
    }

    private void insert(){
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_edit, null);

        final EditText et_name = v.findViewById(R.id.et_name);
        final EditText et_number = v.findViewById(R.id.et_number);

        new AlertDialog.Builder(this)
                .setTitle("추가")
                .setView(v)
                .setPositiveButton("완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(patternMatchingNumber(et_number.getText().toString())){
                            baseData.add(new Item(et_name.getText().toString(), et_number.getText().toString()));

                            search(et.getText().toString()); // 추가한 데이터가 검색할 경우 보이도록
                        }else{
                            Toast.makeText(MainActivity.this, "전화번호 형식을 맞춰주세요", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private boolean patternMatchingNumber(String str) {
        return Pattern.matches("^\\d{2,3}-\\d{3,4}-\\d{4}$", str);
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