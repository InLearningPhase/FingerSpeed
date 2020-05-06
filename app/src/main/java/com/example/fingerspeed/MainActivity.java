package com.example.fingerspeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView timerTextView;
    private TextView thousandTextView;
    private Button tapButton;

    private CountDownTimer countDownTimer;

    private long iniCount = 10000;
    private int timeInterval = 1000;
    private int remainTime = 10;

    private int aThousand = 10;

    private final String REMAIN_TIME_KEY = "remaining time key";
    private final String THOUSAND_KEY = "thousand key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.txtTimer);
        thousandTextView = findViewById(R.id.txtThousand);
        tapButton = findViewById(R.id.btnTap);

        thousandTextView.setText(aThousand + "");

        if (savedInstanceState != null) {

            remainTime = savedInstanceState.getInt(REMAIN_TIME_KEY);
            aThousand = savedInstanceState.getInt(THOUSAND_KEY);

            restoreGame();

        }

        tapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                aThousand--;
                thousandTextView.setText(Integer.toString(aThousand));

                if (remainTime > 0 && aThousand <= 0) {

                    if (countDownTimer != null) {

                        countDownTimer.cancel();
                        countDownTimer = null;
                    }
                    showAlert("Game Finished", "You Won!");

                }
            }
        });

        if (savedInstanceState == null) {

            countDownTimer = new CountDownTimer(iniCount, timeInterval) {

                @Override
                public void onTick(long millisUntilFinished) {

                    remainTime = (int) millisUntilFinished / 1000;
                    timerTextView.setText(remainTime + "");

                }

                @Override
                public void onFinish() {

                    showAlert("Game Finished", "You lost!");

                }
            };
            countDownTimer.start();

        }
    }

    private void resetGame() {

        if (countDownTimer != null) {

            countDownTimer.cancel();
            countDownTimer = null;
        }
        aThousand = 10;
        thousandTextView.setText(Integer.toString(aThousand));

        timerTextView.setText(Integer.toString(remainTime));

        countDownTimer = new CountDownTimer(iniCount, timeInterval) {

            @Override
            public void onTick(long millisUntilFinished) {

                remainTime = (int) millisUntilFinished / 1000;
                timerTextView.setText(remainTime + "");

            }

            @Override
            public void onFinish() {

                showAlert("Game Finished", "You lost!");

            }
        };
        countDownTimer.start();

    }

    private void showAlert(String title, String msg) {

        // Toast.makeText(MainActivity.this, "You Won!", Toast.LENGTH_SHORT).show();
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)

                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        resetGame();

                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();

                    }
                })
                .show();
        alertDialog.setCancelable(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putInt(REMAIN_TIME_KEY, remainTime);
        outState.putInt(THOUSAND_KEY, aThousand);
        countDownTimer.cancel();

    }

    private void restoreGame() {

        int restoreRemainTime = remainTime;
        int restoreThousand = aThousand;

        timerTextView.setText(restoreRemainTime + "");
        thousandTextView.setText(restoreThousand + "");

        countDownTimer = new CountDownTimer((long)remainTime*1000, timeInterval) {
            @Override
            public void onTick(long millisUntilFinished) {

                remainTime = (int)millisUntilFinished / 1000;
                timerTextView.setText(remainTime + "");

            }

            @Override
            public void onFinish() {

                showAlert("Game Finished", "You lost!");

            }
        };
        countDownTimer.start();
    }
}
