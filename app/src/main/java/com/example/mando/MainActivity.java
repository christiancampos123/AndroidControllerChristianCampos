package com.example.mando;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements Runnable{

    JoyStickClass joistick1,joistick2;
    RelativeLayout joistick1_layout, joistick2_layout;
    private int color;
    //ImageView image_joystick, image_border;

    String ip, red, green, blue;
    int port;
    private Socket sock;
    private PrintWriter out;
    private BufferedReader in;
    private int colors;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        this.ip = getIntent().getExtras().getString("ip");
        this.port = getIntent().getExtras().getInt("port");
        this.colors = getIntent().getExtras().getInt("colors");


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        this.connect();

        new Thread(this).start();

        //we create both joystick in onCreate
        this.newJoistick1();
        this.newJoistick2();

    }

    //2 independent joisticks created

    public void newJoistick1(){


        joistick1_layout = (RelativeLayout)findViewById(R.id.joistick1_layout);

        joistick1 = new JoyStickClass(getApplicationContext()
                , joistick1_layout, R.drawable.image_button);
        joistick1.setStickSize(150, 150);
        joistick1.setLayoutSize(500, 500);
        joistick1.setLayoutAlpha(150);
        joistick1.setStickAlpha(100);
        joistick1.setOffset(90);
        joistick1.setMinimumDistance(50);

        joistick1_layout.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                joistick1.drawStick(arg1);
                if(arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {

                    int direction = joistick2.get8Direction();
                    if(direction == JoyStickClass.STICK_UP) {
                        //textView5.setText("Direction : Up");
                        sendCommand("mvuu");
                    } else if(direction == JoyStickClass.STICK_UPRIGHT) {
                        //textView5.setText("Direction : Up Right");
                        sendCommand("mvur");
                    } else if(direction == JoyStickClass.STICK_RIGHT) {
                        //textView5.setText("Direction : Right");
                        sendCommand("mvrr");
                    } else if(direction == JoyStickClass.STICK_DOWNRIGHT) {
                        //textView5.setText("Direction : Down Right");
                        sendCommand("mvdr");
                    } else if(direction == JoyStickClass.STICK_DOWN) {
                        //textView5.setText("Direction : Down");
                        sendCommand("mvdd");
                    } else if(direction == JoyStickClass.STICK_DOWNLEFT) {
                        //textView5.setText("Direction : Down Left");
                        sendCommand("mvdl");
                    } else if(direction == JoyStickClass.STICK_LEFT) {
                        //textView5.setText("Direction : Left");
                        sendCommand("mvll");
                    } else if(direction == JoyStickClass.STICK_UPLEFT) {
                        //textView5.setText("Direction : Up Left");
                        sendCommand("nonope");
                    } else if(direction == JoyStickClass.STICK_NONE) {
                        //textView5.setText("Direction : Center");
                    }
                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
//parado
                }
                return true;
            }
        });
    }

    public void newJoistick2(){


        joistick2_layout = (RelativeLayout)findViewById(R.id.joistick2_layout);

        joistick2 = new JoyStickClass(getApplicationContext()
                , joistick2_layout, R.drawable.image_button);
        joistick2.setStickSize(150, 150);
        joistick2.setLayoutSize(500, 500);
        joistick2.setLayoutAlpha(150);
        joistick2.setStickAlpha(100);
        joistick2.setOffset(90);
        joistick2.setMinimumDistance(50);

        joistick2_layout.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                joistick2.drawStick(arg1);
                if(arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {

                    int direction = joistick2.get8Direction();
                    if(direction == JoyStickClass.STICK_UP) {
                        //textView5.setText("Direction : Up");
                        sendCommand("stuu");
                    } else if(direction == JoyStickClass.STICK_UPRIGHT) {
                        //textView5.setText("Direction : Up Right");
                        sendCommand("stur");
                    } else if(direction == JoyStickClass.STICK_RIGHT) {
                        //textView5.setText("Direction : Right");
                        sendCommand("strr");
                    } else if(direction == JoyStickClass.STICK_DOWNRIGHT) {
                        //textView5.setText("Direction : Down Right");
                        sendCommand("stdr");
                    } else if(direction == JoyStickClass.STICK_DOWN) {
                        //textView5.setText("Direction : Down");
                        sendCommand("stdd");
                    } else if(direction == JoyStickClass.STICK_DOWNLEFT) {
                        //textView5.setText("Direction : Down Left");
                        sendCommand("stdl");
                    } else if(direction == JoyStickClass.STICK_LEFT) {
                        //textView5.setText("Direction : Left");
                        sendCommand("stll");
                    } else if(direction == JoyStickClass.STICK_UPLEFT) {
                        //textView5.setText("Direction : Up Left");
                        sendCommand("stul");
                    } else if(direction == JoyStickClass.STICK_NONE) {
                        //textView5.setText("Direction : Center");
                        //simply no shoot son no send command
                    }
                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
//parado
                }
                return true;
            }
        });
    }

    private void sendCommand(String line) {
        try {
            out.println(line);
        } catch (Exception error) {
            this.finish();
        }
    }

    private String getText() {

        String text = null;

        try {
            text = this.in.readLine();
        } catch (IOException error) {

        }

        return text;

    }

    private boolean processText(String text) {

        boolean entered = false;
        String command = "NUL";

        try {
            command = text.toLowerCase();
        } catch (Exception error) {

        }

        // Precess BYE command
        if (!entered && command.equals("bye")) {

            processBye();
            entered = true;
        }

        if (!entered) {

        }

        return entered;

    }

    private void processBye() {


        this.finish();

    }

    public void connect(){

        try {

            sock = new Socket(ip, port);
            out = new PrintWriter(sock.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
            out.println("connection%controller%" + colors);

        } catch (Exception e) {

            this.finish();

        }
    }

    @Override
    public void run() {

        while (true) {
            this.processText(this.getText());
        }

    }
}


