package com.example.cuocduakythu_nguyenleminh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView txtDiem;
    ImageButton btnPlay;
    CheckBox cbxPika, cbxSongoku, cbxLuf;
    SeekBar sbrPika, sbrSon, sbrLuf;
    int soDiem = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();

        sbrPika.setEnabled(false);
        sbrSon.setEnabled(false);
        sbrLuf.setEnabled(false);

        CountDownTimer countDownTimer = new CountDownTimer(60000,300) {
            @Override
            public void onTick(long l) {
                int num = 4;
                Random random = new Random();
                int one = random.nextInt(num) + 1;
                int two = random.nextInt(num) + 1;
                int three = random.nextInt(num) + 1;

                if (sbrPika.getProgress() >= sbrPika.getMax()){
                    Toast.makeText(MainActivity.this, "Pikachu win", Toast.LENGTH_LONG).show();
                    if(cbxPika.isChecked()){
                        soDiem += 10;
                        Toast.makeText(MainActivity.this, "ban doan chinh xac", Toast.LENGTH_LONG).show();
                    }else{
                        soDiem -= 5;
                        Toast.makeText(MainActivity.this, "ban doan sai roi", Toast.LENGTH_LONG).show();
                    }
                    txtDiem.setText(String.valueOf(soDiem));
                    this.cancel();
                    enableCheckBox();
                    btnPlay.setVisibility(View.VISIBLE);
                }
                if (sbrSon.getProgress() >= sbrSon.getMax()){
                    Toast.makeText(MainActivity.this, "Songoku win", Toast.LENGTH_LONG).show();
                    if(cbxSongoku.isChecked()){
                        soDiem += 10;
                        Toast.makeText(MainActivity.this, "ban doan chinh xac", Toast.LENGTH_LONG).show();
                    }else{
                        soDiem -= 5;
                        Toast.makeText(MainActivity.this, "ban doan sai roi", Toast.LENGTH_LONG).show();
                    }
                    txtDiem.setText(String.valueOf(soDiem));
                    this.cancel();
                    enableCheckBox();
                    btnPlay.setVisibility(View.VISIBLE);
                }
                if (sbrLuf.getProgress() >= sbrLuf.getMax()){
                    Toast.makeText(MainActivity.this, "Pikachu win", Toast.LENGTH_LONG).show();
                    if(cbxLuf.isChecked()){
                        soDiem += 10;
                        Toast.makeText(MainActivity.this, "ban doan chinh xac", Toast.LENGTH_LONG).show();
                    }else{
                        soDiem -= 5;
                        Toast.makeText(MainActivity.this, "ban doan sai roi", Toast.LENGTH_LONG).show();
                    }
                    txtDiem.setText(String.valueOf(soDiem));
                    this.cancel();
                    enableCheckBox();
                    btnPlay.setVisibility(View.VISIBLE);
                }

                sbrPika.setProgress(sbrPika.getProgress() + one);
                sbrSon.setProgress(sbrSon.getProgress() + two);
                sbrLuf.setProgress(sbrLuf.getProgress() + three);
            }

            @Override
            public void onFinish() {

            }
        };

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbxPika.isChecked() || cbxSongoku.isChecked() || cbxLuf.isChecked()){
                    sbrPika.setProgress(0);
                    sbrSon.setProgress(0);
                    sbrLuf.setProgress(0);
                    btnPlay.setVisibility(View.INVISIBLE);
                    disableCheckBox();
                    countDownTimer.start();
                }else{
                    Toast.makeText(MainActivity.this, "Vui long dat cuoc truoc khi choi", Toast.LENGTH_LONG).show();
                }
            }
        });

        cbxPika.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cbxSongoku.setChecked(false);
                    cbxLuf.setChecked(false);
                }
            }
        });
        cbxSongoku.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cbxPika.setChecked(false);
                    cbxLuf.setChecked(false);
                }
            }
        });

        cbxLuf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cbxSongoku.setChecked(false);
                    cbxPika.setChecked(false);
                }
            }
        });
    }

    public void AnhXa(){
        txtDiem = (TextView) findViewById(R.id.txtDiem);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        cbxPika = (CheckBox) findViewById(R.id.cbxPikachu);
        cbxSongoku = (CheckBox) findViewById(R.id.cbxSongoku);
        cbxLuf = (CheckBox) findViewById(R.id.cbxLuffy);
        sbrPika = (SeekBar) findViewById(R.id.sbrPikachu);
        sbrSon = (SeekBar) findViewById(R.id.sbrSongoku);
        sbrLuf = (SeekBar) findViewById(R.id.sbrLuffy);
    }

    public void enableCheckBox(){
        cbxPika.setEnabled(true);
        cbxSongoku.setEnabled(true);
        cbxLuf.setEnabled(true);
    }

    public void disableCheckBox(){
        cbxPika.setEnabled(false);
        cbxSongoku.setEnabled(false);
        cbxLuf.setEnabled(false);
    }
}