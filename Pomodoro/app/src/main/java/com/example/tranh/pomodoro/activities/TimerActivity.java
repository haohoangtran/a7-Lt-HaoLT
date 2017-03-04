package com.example.tranh.pomodoro.activities;

import android.app.ProgressDialog;
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
import com.example.tranh.pomodoro.database.models.Task;
import com.example.tranh.pomodoro.evenbus_event.TimerCommand;
import com.example.tranh.pomodoro.evenbus_event.TimerCommandEvent;
import com.example.tranh.pomodoro.evenbus_event.TimerTickEvent;
import com.example.tranh.pomodoro.settings.Setting;
import com.example.tranh.pomodoro.utils.Util;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.DonutProgress;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimerActivity extends AppCompatActivity {
    private Task task;


    private static final String TAG = TimerActivity.class.toString();

    final Setting SETTING = Util.getSetting();
    @BindView(R.id.pb_time)
    DonutProgress pbTime;
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
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(R.string.timer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pbTime.setText(getString(R.string.please_wait));
        pbTime.setProgress(pbTime.getMax());
        addListenner();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
                        TimerCommandEvent event = new TimerCommandEvent(TimerCommand.PAUSE_TIME);
                        EventBus.getDefault().post(event);
                        ibPause.setImageResource(R.drawable.ic_pause_white_24px);

                    } else {
                        isPause = false;
                        TimerCommandEvent event = new TimerCommandEvent(TimerCommand.RESUME_TIME);
                        EventBus.getDefault().post(event);
                        ibPause.setImageResource(R.drawable.ic_play_arrow_black_24px);
                    }
                }
            }
        });
        ibStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimerCommandEvent event = new TimerCommandEvent(TimerCommand.STOP_TIMER);
                EventBus.getDefault().post(event);
                isStop = true;
                //stop để làm gì rồi code tiếp!
            }
        });
    }

    @Subscribe
    public void onTimerTick(TimerTickEvent event) {
        if (event.getTick() == 0) {
            pbTime.setText(getString(R.string.done));
            return;
        }
        long millisUntilFinished = event.getTick();
        long secondRemaining = millisUntilFinished / 1000;
        int minute = (int) (secondRemaining / 60);
        int second = (int) (secondRemaining - (minute * 60));
        timeUntilFinished = millisUntilFinished;
        pbTime.setText(String.format("%02d:%02d", minute, second));
        pbTime.setMax(SETTING.getTimeBreak() * 60);
        pbTime.setProgress((int) secondRemaining);
    }


}
