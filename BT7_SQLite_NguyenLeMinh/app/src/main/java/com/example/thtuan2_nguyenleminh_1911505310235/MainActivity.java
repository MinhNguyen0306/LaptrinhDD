package com.example.thtuan2_nguyenleminh_1911505310235;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.app.Notification;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.thtuan2_nguyenleminh_1911505310235.SQLite.DBHelper;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.close();
    }
}