package com.example.viktor.math_counter.Activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viktor.math_counter.GameTypeOperation.DivideOperation;
import com.example.viktor.math_counter.GameTypeOperation.MinusOperation;
import com.example.viktor.math_counter.GameTypeOperation.MultiplyOperation;
import com.example.viktor.math_counter.GameTypeOperation.PlusOperation;
import com.example.viktor.math_counter.GameTypeOperation.RandomOperation;
import com.example.viktor.math_counter.GameTypeOperation.StrategyGameType;
import com.example.viktor.math_counter.R;
import com.example.viktor.math_counter.Util.TopScore;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static com.example.viktor.math_counter.Activity.OptionsScreenActivity.APP_PREFERENCES_NICKNAME;
import static com.example.viktor.math_counter.Activity.OptionsScreenActivity.APP_RANDOM2;
import static com.example.viktor.math_counter.Activity.OptionsScreenActivity.APP_TYPE;
import static com.example.viktor.math_counter.Activity.OptionsScreenActivity.GLOBAL_KEY_PREFERENCES;
import static com.example.viktor.math_counter.Activity.OptionsScreenActivity.LEVEL;

public class GameScreenActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressBar progressBar;
    private TextView tvTime;
    private TextView tvScore;
    private TextView tvSample;
    private TextView tvAnswer;
    private Handler handler;
    private ConstraintLayout constraintLayout;


    private Button btnExit;
    private Button btnPause;

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btn0;
    private Button btnOk;
    private Button btnCe;

    String mOperationType;


    int mScore = 0;
    String sharedScoreString = null;
    String jsonString = null;


    int val1;
    int val2;

    private final String PLUS = "plus";
    private final String MINUS = "minus";
    private final String DIVIDE = "divide";
    private final String MULTIPLY = "multiply";

    public static final String Load_Level = "Easy";// cold start
    public static final String Load_Random = "True";//// cold start
    public static final String Load_SINE = "LOAD_Sine";
    public static final String Load_NAME = "LOAD_Nane";
    public static final String Load_Score = "LOAD_Score";
    public int sOperationLevel = 100;// cold start
//    SharedPreferences mSettingsSine;
//    SharedPreferences mSettingsName;
//    SharedPreferences mPreferencesScore;
//    SharedPreferences mVolume;
//    SharedPreferences mPreferencesLevel;///
//    SharedPreferences mPreferencesRandom;///


    SharedPreferences mGlobalSettings;
    SharedPreferences.Editor editor;


    String mSharedOperationType;
    String mSharedOperationName;
    String mSharedOperationLevel;
    String mSharedOperationrandom;


    String volume = "8";
    Boolean ReturnIsPush = false;
    Boolean BackPressIsPush = false;
    public AudioManager audioManager = null;
    public static final String VOLUME = "8";

    StrategyGameType strategyGameType;

    int result;
    Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        animation = AnimationUtils.loadAnimation(this, R.anim.alpha);

        loadPref();

        initView();

        setListeners();

        // Toast.makeText(this, Load_Score, Toast.LENGTH_SHORT).show();/////


        if (mSharedOperationType == null) {
            Toast.makeText(this, "Game type is empty! Defaut operation will be plus.", Toast.LENGTH_SHORT).show();
            mOperationType = PLUS;
        } else {
            mOperationType = mSharedOperationType;
        }
        setGameLevel();
        // Toast.makeText(this, "" + sOperationLevel, Toast.LENGTH_SHORT).show();
        initStrategyGameType();


        getSample();


        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                tvTime.setText("" + (l / 1000));
                progressBar.setProgress((int) l / 600);

            }

            @Override
            public void onFinish() {
                tvTime.setText("stop");

            }
        }.start();


    }

    private void setTextView(TextView v, String s) {

        v.setText(s);

    }

    private void initStrategyGameType() {
        if (mSharedOperationrandom.equals("TRUE")) {
            strategyGameType = new RandomOperation(this);


        } else {
            switch (mOperationType) {
                case PLUS: {
                    strategyGameType = new PlusOperation(this);
                    break;
                }
                case MINUS: {
                    strategyGameType = new MinusOperation(this);

                    break;
                }
                case DIVIDE: {
                    strategyGameType = new DivideOperation(this);

                    break;
                }
                case MULTIPLY: {
                    strategyGameType = new MultiplyOperation(this);

                    break;
                }
                default: {
                    strategyGameType = new PlusOperation(this);//////////////////////////////////////////////////////////////////
                    break;
                }
            }

        }


    }

    private void setGameLevel() {

        if (mOperationType.equals(PLUS) || mOperationType.equals(MINUS)) {
            //for PLUS "+"
            switch (mSharedOperationLevel) {

                case "Easy": {
                    sOperationLevel = 100;
                    break;
                }

                case "Medium": {
                    sOperationLevel = 150;

                    break;
                }
                case "Hard": {
                    sOperationLevel = 200;
                    break;
                }


            }

        } else if (mOperationType.equals(DIVIDE) || mOperationType.equals(MULTIPLY)) {

            switch (mSharedOperationLevel) {

                case "Easy": {
                    sOperationLevel = 50;
                    break;
                }

                case "Medium": {
                    sOperationLevel = 100;

                    break;
                }
                case "Hard": {
                    sOperationLevel = 150;
                    break;
                }


            }

        }
        if (mSharedOperationrandom.equals("TRUE")) {
            switch (mSharedOperationLevel) {

                case "Easy": {
                    sOperationLevel = 10;
                    break;
                }

                case "Medium": {
                    sOperationLevel = 15;

                    break;
                }
                case "Hard": {
                    sOperationLevel = 20;
                    break;
                }


            }
        }


    }

    private void loadPref() {


        mGlobalSettings = getSharedPreferences(GLOBAL_KEY_PREFERENCES, MODE_PRIVATE);
        mSharedOperationLevel = mGlobalSettings.getString(LEVEL, Load_Level);//указываем ключ (другого маинАктивити(MainActivity) по которому мы будем доставать значения и переменную куда будем сохранять

        mSharedOperationrandom = mGlobalSettings.getString(APP_RANDOM2, Load_Random);//указываем ключ (другого маинАктивити(MainActivity) по которому мы будем доставать значения и переменную куда будем сохранять

        mSharedOperationType = mGlobalSettings.getString(APP_TYPE, Load_SINE);


        mSharedOperationName = mGlobalSettings.getString(APP_PREFERENCES_NICKNAME, Load_NAME);

        String vol = mGlobalSettings.getString(VOLUME, VOLUME);
        volume = vol;

        sharedScoreString = mGlobalSettings.getString(Load_Score, sharedScoreString); //getString(Load_Score,"");


    }

    private void getSample() {

        String expression = null;

        if (mOperationType == null || mOperationType.equals("")) {
            Toast.makeText(this, "operationType is null!", Toast.LENGTH_SHORT).show();
            return;
        }


        Map<String, String> map = strategyGameType.getAlgoritm(sOperationLevel);


        expression = map.get("keyExpression");
        result = Integer.parseInt(map.get("keyResult"));

        setTextSample(expression);


    }

    private void setTextSample(String expression) {

        if (tvSample == null) {
            Toast.makeText(this, "answer field is null!", Toast.LENGTH_SHORT).show();
            return;
        }

        tvSample.setText(expression + " = ");

    }

    private void setListeners() {

        btnPause.setOnClickListener(this);
        btnExit.setOnClickListener(this);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn0.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        btnCe.setOnClickListener(this);
    }

    private void initView() {
        progressBar = findViewById(R.id.progressBar);///

        tvTime = findViewById(R.id.tvTime);/////

        tvScore = findViewById(R.id.tvScore);
        tvSample = findViewById(R.id.tvSample);
        tvAnswer = findViewById(R.id.tvAnswer);

        btnExit = findViewById(R.id.btnExit_activity_game);
        btnPause = findViewById(R.id.btnPause_activity_game);
        constraintLayout = findViewById(R.id.LoyautGame);
        constraintLayout.setBackgroundResource(R.drawable.fon);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn0 = findViewById(R.id.btn0);
        btnOk = findViewById(R.id.btnOk);
        btnCe = findViewById(R.id.btnCE);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn1: {
                setTextAnswer("1");
                break;
            }
            case R.id.btn2: {
                setTextAnswer("2");

                break;
            }

            case R.id.btn3: {
                setTextAnswer("3");
                break;
            }

            case R.id.btn4: {
                setTextAnswer("4");
                break;
            }

            case R.id.btn5: {
                setTextAnswer("5");
                break;
            }

            case R.id.btn6: {
                setTextAnswer("6");
                break;
            }

            case R.id.btn7: {
                setTextAnswer("7");
                break;
            }

            case R.id.btn8: {
                setTextAnswer("8");
                break;
            }

            case R.id.btn9: {
                setTextAnswer("9");
                break;
            }

            case R.id.btn0: {
                setTextAnswer("0");
                break;
            }

            case R.id.btnOk: {
                if (tvAnswer.getText().equals("")) {

                    Toast.makeText(this, "You did not enter a number", Toast.LENGTH_SHORT).show();
                } else {

                    check();
                    tvAnswer.setText("");
                }


                break;
            }

            case R.id.btnCE: {
                if (tvAnswer == null) {
                    Toast.makeText(this, "answer field is null!", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    tvAnswer.setText("");
                break;
            }
            case R.id.btnExit_activity_game: {
                ReturnIsPush = true;

                onBackPressed();
                break;
            }


        }
    }

    private void check() {


        int currentAnswer = Integer.parseInt(getTextAnswer());

        if (result == currentAnswer) {
            //  Toast.makeText(this, "Right", Toast.LENGTH_SHORT).show();

            handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message message) {
                    // tvAnswer.setBackgroundColor(Color.WHITE);
                    tvAnswer.setBackgroundResource(R.drawable.fongameloyaut);
                    return false;
                }
            });
            handler.sendEmptyMessageDelayed(1, 500);

            tvAnswer.setBackgroundColor(Color.GREEN);


            switch (mSharedOperationLevel) {

                case "Easy": {
                    mScore = mScore + 10;
                    break;
                }

                case "Medium": {
                    mScore = mScore + 15;

                    break;
                }
                case "Hard": {
                    mScore = mScore + 20;
                    break;
                }
                default: {
                    //  Toast.makeText(this, "EROR is not working with cold start but works ", Toast.LENGTH_SHORT).show();////При холодном сарте можно его увидеть Ничего страшного нет
                    mScore = mScore + 10;
                    break;
                }


            }
            //  mScore = mScore + 10;
            setTextView(tvScore, String.valueOf(mScore));
        } else {

            //  Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();

            handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message message) {
                    // tvAnswer.setBackgroundColor(Color.WHITE);
                    tvAnswer.setBackgroundResource(R.drawable.fongameloyaut);
                    return false;
                }
            });
            handler.sendEmptyMessageDelayed(1, 500);

            tvAnswer.setBackgroundColor(Color.RED);


        }

        getSample();
    }

    private void setTextAnswer(String s) {

        if (tvAnswer == null) {
            Toast.makeText(this, "answer field is null!", Toast.LENGTH_SHORT).show();
            return;
        }

        String prevAnswer = tvAnswer.getText().toString();

        tvAnswer.setText(prevAnswer + s);


    }

    private String getTextAnswer() {
        return tvAnswer.getText().toString();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }


    @Override
    protected void onStop() {
        super.onStop();
        if (ReturnIsPush == false & BackPressIsPush == false) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, /////Позвязать бекпрес и ретурн fals true как в маин активити
                    0, 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, Integer.valueOf(volume), 0);////------

    }

    @Override
    public void onBackPressed() {
        BackPressIsPush = true;
        SaveData();
        super.onBackPressed();

    }

    private void SaveData() {
        // loadPref();
        String score = tvScore.getText().toString();
        if (score.equals("0")) {
            return;
        }

        editor = mGlobalSettings.edit();
       // editor.clear();


        String stringJsonScore = sharedScoreString;


        if (stringJsonScore == null) {

            List<TopScore> topScores = new ArrayList<>();
            TopScore topScore = new TopScore();
            topScore.setName(mSharedOperationName);
            topScore.setScore(Integer.parseInt(score));

            topScores.add(topScore);

            try {
                jsonString = writeToJson(topScores);
            } catch (IOException e) {
                e.printStackTrace();
            }

            editor.putString(Load_Score, jsonString);
        } else {
            //POJO realisation
            List<TopScore> topScoreList = null;
            try {
                topScoreList = readJson(stringJsonScore);
            } catch (IOException e) {
                e.printStackTrace();
            }
            checkNewScoreWithExistScores(topScoreList, score);
            editor.putString(Load_Score, jsonString);

//                ArrayList<String> list = new ArrayList<>();
//                list.addAll(stringScore);
//                if (!list.contains(score)) {
//
//                    list.remove(list.size() - 1);
//                    list.add(score+";"+mSharedOperationName);
//                } else {
//                    list.add(score+";"+mSharedOperationName);
//                }
//
//                stringScore.clear();
//                stringScore.addAll(list);
//                editor.putStringSet(Load_Score, stringScore);


        }

        editor.apply();


    }

    // @TargetApi(Build.VERSION_CODES.N)
    private void checkNewScoreWithExistScores(List<TopScore> topScores, String newScore) {

        //algoritm sravneniya
        int newScoreInt = Integer.parseInt(newScore);

        for (int i = 0; i < topScores.size(); i++) {/////////////////////////////////////////////////////////////////////////////
            if (topScores.get(i).getScore() == newScoreInt) {
                try {
                    jsonString = writeToJson(topScores);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            topScores.sort(new Comparator<TopScore>() {
                @Override
                public int compare(TopScore o1, TopScore o2) {
                    return o1.getScore() - o2.getScore();

                }
            });
        } else {//Build.VERSION.SDK_INT < Build.VERSION_CODES.N
            for (int i = 0; i < topScores.size(); i++) {

                for (int j = 0; j < topScores.size() - 1; j++) {

                    if (topScores.get(j).getScore() > topScores.get(j + 1).getScore()) {

                        TopScore tmp = topScores.get(j);
                        TopScore tmp1 = topScores.get(j + 1);
                        topScores.set(j + 1, tmp);
                        topScores.set(j, tmp1);
                    }
                }
            }
        }

        if (topScores.size() < 5) {

            TopScore topScore = new TopScore();
            topScore.setName(mSharedOperationName);
            topScore.setScore(newScoreInt);

            topScores.add(topScore);
            try {
                jsonString = writeToJson(topScores);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {


            TopScore minScore = topScores.get(0);


            if (minScore.getScore() < newScoreInt) {

                minScore.setScore(newScoreInt);
                minScore.setName(mSharedOperationName);


            }

            try {
                jsonString = writeToJson(topScores);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }

    private String writeToJson(List<TopScore> topScoreList) throws IOException {


        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = new JsonWriter(stringWriter);


        writer.beginArray();

        for (int i = 0; i < topScoreList.size(); i++) {

            writer.beginObject();

            writer.name("name");
            writer.value(topScoreList.get(i).getName());
            writer.name("score");
            writer.value(topScoreList.get(i).getScore());

            writer.endObject();

        }
        writer.endArray();

        writer.close();
        stringWriter.close();

        return stringWriter.toString();


    }

    private List<TopScore> readJson(String stringJson) throws IOException {

        StringReader stringReader = new StringReader(stringJson);
        JsonReader reader = new JsonReader(stringReader);

        List<TopScore> topScores = new ArrayList<>();

        TopScore topScore = null;

        reader.beginArray();
        while (reader.hasNext()) {

            topScore = new TopScore();
            readObject(reader, topScore);

            topScores.add(topScore);
        }
        reader.endArray();


        return topScores;
    }

    private void readObject(JsonReader reader, TopScore topScore) throws IOException {

        reader.beginObject();
        while (reader.hasNext()) {

            String keyName = reader.nextName();

            if (keyName.equals("name")) {


                String currentName = reader.nextString();
                topScore.setName(currentName);


            } else if (keyName.equals("score")) {

                int currentScore = reader.nextInt();

                topScore.setScore(currentScore);

            }
        }

        reader.endObject();
    }
}


