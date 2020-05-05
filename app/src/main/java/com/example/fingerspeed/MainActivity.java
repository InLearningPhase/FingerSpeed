package com.example.fingerspeed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView timerTextView;
    private TextView thousandTextView;
    private Button tapButton;

    private CountDownTimer countDownTimer;

    private long iniCount = 60000;
    private int timeInterval = 1000;
    private int remainTime = 60;

    private int aThousand = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.txtTimer);
        thousandTextView = findViewById(R.id.txtThousand);
        tapButton = findViewById(R.id.btnTap);
        thousandTextView.setText(aThousand + "");

        tapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                aThousand--;
                thousandTextView.setText(aThousand + "");
                if (remainTime > 0 && aThousand <= 0) {
                    Toast.makeText(MainActivity.this, "You Won!", Toast.LENGTH_SHORT).show();
                }
            }
        });

            countDownTimer = new CountDownTimer(iniCount, timeInterval) {

            @Override
            public void onTick(long millisUntilFinished) {
                remainTime = (int) millisUntilFinished / 1000;
                timerTextView.setText(remainTime + "");
            }

            @Override
            public void onFinish() {

                Toast.makeText(MainActivity.this, "Countdown Finished", Toast.LENGTH_SHORT).show();
            }
        };
        countDownTimer.start();
    }
}
