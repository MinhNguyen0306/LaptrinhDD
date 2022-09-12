package com.example.bt3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListAvtivity extends AppCompatActivity {
    int index;
    ArrayList<String> list;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_avtivity);

        ListView lv = (ListView) findViewById(R.id.listView);
        EditText text = (EditText) findViewById(R.id.txtName);
        Button btnThem = (Button) findViewById(R.id.btnThem);
        Button btnSua = (Button) findViewById(R.id.btnSua);
        Button btnXoa = (Button) findViewById(R.id.btnXoa);

        list = new ArrayList<String>();
        list.add("Java");
        list.add("PHP");
        list.add("C#");
        list.add("Python");
        list.add("Ruby");
        list.add("Golang");
        ArrayAdapter adapter = new ArrayAdapter(ListAvtivity.this, android.R.layout.simple_list_item_1, list);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ListAvtivity.this, list.get(i), Toast.LENGTH_SHORT).show();
                name = list.get(i);
                text.setText(name);
                index = i;
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputSubject = text.getText().toString();
                list.add(inputSubject);
                adapter.notifyDataSetChanged();
                Toast.makeText(ListAvtivity.this, "Da them" + inputSubject, Toast.LENGTH_SHORT).show();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updateSubject = text.getText().toString();
                list.remove(index);
                list.add(index, updateSubject);
                adapter.notifyDataSetChanged();
                Toast.makeText(ListAvtivity.this, "Da sua", Toast.LENGTH_SHORT).show();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String delSubject = text.getText().toString();
                list.remove(index);
                adapter.notifyDataSetChanged();
                Toast.makeText(ListAvtivity.this, "Da Xoa " + delSubject, Toast.LENGTH_SHORT).show();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                name = list.get(index).toString();
                doOpenChildActivity(name);
                return false;
            }
        });
    }


    public void doOpenChildActivity(String name)
    {
        Intent intent = new Intent(ListAvtivity.this, SecondActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Subject title", name);
        intent.putExtras(bundle);
        ListAvtivity.this.startActivity(intent);
    }
}