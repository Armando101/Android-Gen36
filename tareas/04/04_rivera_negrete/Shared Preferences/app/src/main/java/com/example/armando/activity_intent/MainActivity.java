package com.example.armando.activity_intent;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.app.Activity;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements OnClickListener{

    Button btn1, btn2, btn3;
    int ncol;
    RelativeLayout f;
    int nucol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Iniciar();
    }

    public void Iniciar() {
    btn1 = (Button)findViewById(R.id.google);
    btn2 = (Button)findViewById(R.id.activity);
    btn1.setOnClickListener(this);
    btn2.setOnClickListener(this);
    btn3 = (Button)findViewById(R.id.color);
    btn3.setOnClickListener(this);
    f= findViewById(R.id.fondo);

    cargar();
    mostrar();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.google:
                Uri uri = Uri.parse("https://www.google.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.activity:
                Intent i = new Intent(this,Activity2.class);
                startActivity(i);
                break;
            case R.id.color:
                colo();
                guardar();
                break;
        }
    }

    public void colo() {
        int color = Color.argb(100,51,51,255);
        ncol = color;
        f.setBackgroundColor(ncol);
    }

    public void guardar(){
        SharedPreferences sh = getSharedPreferences("preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putInt("Color", ncol);
        editor.apply();
        Toast.makeText (this, "cambios guardados", Toast.LENGTH_SHORT).show();
    }

    public void cargar() {
        SharedPreferences sh = getSharedPreferences("preferences", MODE_PRIVATE);
        int c = Color.argb(50,102,255,255);
        nucol= sh.getInt("Color", c);
    }

    public void mostrar() {
        f.setBackgroundColor(nucol);
    }
}
