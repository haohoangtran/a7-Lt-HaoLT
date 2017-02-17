package com.example.tranh.pomodoro.activities;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tranh.pomodoro.R;
import com.example.tranh.pomodoro.settings.Setting;
import com.example.tranh.pomodoro.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimerActivity extends AppCompatActivity {

    private static final String TAG = TimerActivity.class.toString();

    final Setting SETTING = Util.getSetting();
    @BindView(R.id.pb_time)
    ProgressBar pbTime;
    @BindView(R.id.tv_countdown)
    TextView tv_Countdown;
    @BindView(R.id.ib_pause)
    ImageButton ibPause;
    @BindView(R.id.ib_stop)
    ImageButton ibStop;
    private boolean isPause;
    private boolean isStop;
    private CountDownTimer countDownTimer;
    private long timeUntilFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(R.string.timer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        startTimer(SETTING.getTimeBreak() * 60 * 1000 + 1000, 1000);//cộng 1000 vì nó chạy nó bỏ mất 1s
        addListenner();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addListenner() {
        ibPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStop) {
                    if (!isPause) {
                        isPause = true;
                        countDownTimer.cancel();
                        ibPause.setImageResource(R.drawable.ic_pause_white_24px);
                    } else {
                        isPause = false;
                        startTimer(timeUntilFinished, 1000);
                        countDownTimer.start();

                        ibPause.setImageResource(R.drawable.ic_play_arrow_black_24px);
                    }
                }
            }
        });
        ibStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.onFinish();
                countDownTimer.cancel();
                // có nên xóa data không!
                isStop=true;
            }
        });
    }

    private void startTimer(long millisInFuture, long countDownInterval) {
        Log.e(TAG, String.format("onCreate: %s", SETTING.getTimeBreak()));

        countDownTimer = new CountDownTimer(millisInFuture,countDownInterval) {
            public void onTick(long millisUntilFinished) {
                long secondRemaining = millisUntilFinished / 1000;
                int minute = (int) (secondRemaining / 60);
                int second = (int) (secondRemaining - (minute * 60));
                timeUntilFinished=millisUntilFinished;
                tv_Countdown.setText(String.format("%02d:%02d", minute, second));
                pbTime.setMax(SETTING.getTimeBreak() * 60);
                pbTime.setProgress((int) secondRemaining);
            }

            public void onFinish() {
                tv_Countdown.setText(R.string.done);
                tv_Countdown.setTextSize(60);
                pbTime.setProgress(0);
                pbTime.setVisibility(View.INVISIBLE);
            }

        };
        countDownTimer.start();
    }


}
