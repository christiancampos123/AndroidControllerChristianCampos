package com.example.mando;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Connect extends AppCompatActivity {

    private int port, red, blue, green;
    private String ip, redS, greenS, blueS;
    private EditText editIP, editPORT;
    private TextView redTextView, greenTextView, blueTextView;
    private RelativeLayout painter;
    private Draw p;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect);

        color=1;
        this.red = 255;
        this.green = 255;
        this.blue = 255;

        SharedPreferences prefs = getSharedPreferences("Ship", Context.MODE_PRIVATE);
        this.ip = prefs.getString("ip", "IP");
        this.port = prefs.getInt("port", 0);


        this.redS = prefs.getString("redS", "255");
        this.greenS = prefs.getString("greenS", "255");
        this.blueS = prefs.getString("blueS", "255");
        //this.name = prefs.getString("name", "Nombre");

        try {
            this.red = Integer.parseInt(this.redS);
        } catch (NumberFormatException error) {
            this.red = 255;
            //this.redS = "128";
        }

        try {
            this.green = Integer.parseInt(this.greenS);
        } catch (NumberFormatException error) {
            this.green = 255;
            //this.greenS = "128";
        }

        try {
            this.blue = Integer.parseInt(this.blueS);
        } catch (NumberFormatException error) {
            this.blue = 255;
            //this.blueS = "128";
            }

            this.editIP = (EditText) findViewById(R.id.IP);
            this.editPORT = (EditText) findViewById(R.id.PORT);

            editIP.setText(this.ip);


            if (port == 0) {
                editPORT.setText("PORT");
            } else {
                editPORT.setText(Integer.toString(this.port));//change the integer to a String
            }



        painter = (RelativeLayout) findViewById(R.id.painter);
        this.justDrawIt();



    }

    private void justDrawIt() {

        this.p = new Draw(this, this.red, this.green, this.blue);
        painter.addView(p);

    }

    public void red(View v) {
        this.red = 255;
        this.redS=Integer.toString(255);
        this.green=0;
        this.greenS=Integer.toString(0);
        this.blue=0;
        this.blueS=Integer.toString(0);
        this.justDrawIt();
        this.color=1;
    }

    public void blue(View v) {
        this.red = 0;
        this.redS=Integer.toString(0);
        this.green=0;
        this.greenS=Integer.toString(0);
        this.blue=255;
        this.blueS=Integer.toString(255);
        this.justDrawIt();
        this.color=2;
    }

    public void green(View v) {
        this.red = 0;
        this.redS=Integer.toString(0);
        this.green=255;
        this.greenS=Integer.toString(255);
        this.blue=0;
        this.blueS=Integer.toString(0);
        this.justDrawIt();
        this.color=3;
    }

    public void white(View v) {
        this.red = 255;
        this.redS=Integer.toString(255);
        this.green=255;
        this.greenS=Integer.toString(255);
        this.blue=255;
        this.blueS=Integer.toString(255);
        this.justDrawIt();
        this.color=6;
    }

    public void lightGrey(View v) {
        this.red = 155;
        this.redS=Integer.toString(155);
        this.green=155;
        this.greenS=Integer.toString(155);
        this.blue=155;
        this.blueS=Integer.toString(155);
        this.justDrawIt();
        this.color=5;
    }

    public void darkgrey(View v) {
        this.red = 60;
        this.redS=Integer.toString(60);
        this.green=60;
        this.redS=Integer.toString(60);
        this.blue=60;
        this.redS=Integer.toString(60);
        this.justDrawIt();
        this.color=4;
    }


    public void connect(View v){

        try {

            ip = this.editIP.getText().toString();
            port = Integer.parseInt(this.editPORT.getText().toString());


            SharedPreferences prefs = getSharedPreferences("ship", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("ip", ip);
            editor.putInt("port", port);
            editor.putString("red", redS);
            editor.putString("green", greenS);
            editor.putString("blue", blueS);
            editor.commit();

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("ip", ip);
            intent.putExtra("port", port);
            intent.putExtra("colors", color);

            startActivity(intent);

        } catch (Exception e) {

            System.out.println(e);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
