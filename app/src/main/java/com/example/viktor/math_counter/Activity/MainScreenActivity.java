package com.example.viktor.math_counter.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.viktor.math_counter.Fragments.BlankFragmentTop;
import com.example.viktor.math_counter.R;
import com.example.viktor.math_counter.Service.MyService;
import com.example.viktor.math_counter.Util.Util;

import java.util.ArrayList;

import static com.example.viktor.math_counter.Activity.GameScreenActivity.Load_Score;
import static com.example.viktor.math_counter.Activity.OptionsScreenActivity.APP_PREFERENCES_NICKNAME;
import static com.example.viktor.math_counter.Activity.OptionsScreenActivity.GLOBAL_KEY_PREFERENCES;
import static com.example.viktor.math_counter.Activity.OptionsScreenActivity.VOLUME;


public class MainScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnOptions;
    private Button mBtnPlay;
    private Button mBtnExit;
    private Button mBtnTop;
    Boolean OptionsIsPush = false;
    Boolean PlayIsPush = false;
    public ConstraintLayout constraintLayout;
    public static ConstraintLayout MainConstraintLayout;

    private TextView tvPlaerName;
    private String saveNickName;
    private String saveVolume = "8";

    private Util util;
    private String stringScore;

    BlankFragmentTop top;


    AudioManager a = null;

//    private SharedPreferences mPreferencesVolume;
//    private SharedPreferences mPreferencesScore;
//    private SharedPreferences mPreferencesName;

    SharedPreferences mGlobalSettings;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screnn);

        initView();


        setListeners();

        util = new Util();

        startService(new Intent(MainScreenActivity.this, MyService.class));
        loadPref();

        a = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        a.setStreamVolume(AudioManager.STREAM_MUSIC, Integer.valueOf(saveVolume), 0);


        constraintLayout.setVisibility(View.INVISIBLE);

    }

    private void setListeners() {
        mBtnOptions.setOnClickListener(this);
        mBtnPlay.setOnClickListener(this);
        mBtnExit.setOnClickListener(this);
        mBtnTop.setOnClickListener(this);
    }

    private void initView() {
        mBtnOptions = findViewById(R.id.btnOptions);
        mBtnPlay = findViewById(R.id.btnPlay);
        mBtnTop = findViewById(R.id.btnTop);
        mBtnExit = findViewById(R.id.btnExit);
        tvPlaerName = findViewById(R.id.textViewMain);
        constraintLayout = findViewById(R.id.Constrain_Layout);
        MainConstraintLayout = findViewById(R.id.MainConstrainLoyaut);
        MainConstraintLayout.setBackgroundResource(R.drawable.fon);

    }

    private void loadPref() {

        mGlobalSettings = getSharedPreferences(GLOBAL_KEY_PREFERENCES, MODE_PRIVATE);//указыванм имя ключа (другого маинАктивити(MainActivity)) и MODE_PRIVATE


        saveNickName = mGlobalSettings.getString(APP_PREFERENCES_NICKNAME, "");//указываем ключ (другого маинАктивити(MainActivity) по которому мы будем доставать значения и переменную куда будем сохранять

        if (!saveNickName.equals("")) {
            tvPlaerName.setText(saveNickName);
        } else {
            Toast.makeText(this, "Field player name is empty, fill it!", Toast.LENGTH_SHORT).show();
        }

        saveVolume = mGlobalSettings.getString(VOLUME, saveVolume);

        stringScore = mGlobalSettings.getString(Load_Score, null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOptions: {

                OptionsIsPush = true;
                util.openActivity(MainScreenActivity.this, OptionsScreenActivity.class);
                break;

            }

            case R.id.btnPlay: {

                PlayIsPush = true;
                util.openActivity(MainScreenActivity.this, GameScreenActivity.class);
                break;

            }

            case R.id.btnExit: {


                moveTaskToBack(true);
                stopService(new Intent(MainScreenActivity.this, MyService.class));
                onBackPressed();
                break;

            }
            case R.id.btnTop: {

                if (stringScore == null) {

                    return;
                }
                for (int i = 0; i < MainConstraintLayout.getChildCount(); i++) {
                    MainConstraintLayout.getChildAt(i).setEnabled(false);
                }
                //ArrayList<String> list = new ArrayList<>();
                //list.addAll(stringScore);


                top = BlankFragmentTop.newInstance(stringScore);

                getSupportFragmentManager()
                        .beginTransaction()
                        // .add(R.id.fl_top, top)
                        .replace(R.id.Constrain_Layout, top)
                        .commit();

                constraintLayout.setVisibility(View.VISIBLE);

                // Toast.makeText(this, stringScore.toString(), Toast.LENGTH_SHORT).show();

                break;


            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    protected void onStop() {
        super.onStop();
        if (OptionsIsPush == false & PlayIsPush == false) {

            stopService(new Intent(MainScreenActivity.this, MyService.class));
        }


    }

    @Override
    public void onBackPressed() {

        stopService(new Intent(MainScreenActivity.this, MyService.class));
        super.onBackPressed();

    }

    @Override
    protected void onResume() {
        super.onResume();
        OptionsIsPush = false;

        loadPref();
        startService(new Intent(MainScreenActivity.this, MyService.class));


    }
}
