package com.example.viktor.math_counter.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viktor.math_counter.R;


public class OptionsScreenActivity extends AppCompatActivity implements View.OnClickListener {
    AlertDialog.Builder adSave;
    Context context;

    boolean checkSetings = false;
    String checkSetingsY_N = "";

    Boolean PushbuttonReturnToMainScreen = false;
    private ConstraintLayout constraintLayout;


    Button setDefault;
    private SeekBar volumeSeekbar = null;
    public AudioManager audioManager = null;
    Button buttonReturnToMainScreen;
    Button buttonSave;
    EditText editTexPlayer;
    TextView tvType;
    CheckBox RandomCheckBox;

    RadioGroup radioGroup;
    public String level = "Easy";
    // public String level = "Medium";//////
    boolean checkRandom = false;

    Boolean ReturnIsPush = false;
    Boolean BackPressIsPush = false;


    public String checkRandom2 = "T";

    public static final String LEVEL = "key_level";//
    public static final String APP_PREFERENCES_NICKNAME = "";
    public static final String APP_RANDOM = "KEY_Random";
    public static final String APP_RANDOM2 = "True";
    public static final String VOLUME = "8";
    String volume = "8";
    Integer volumeNoSave = 8;

    public String loadName;//////////////////////////////////////////////////
    public String loaLevel;
    public String loadType;
    public boolean loadRandom;
    public boolean loadVoll = false;

//

    public static final String APP_TYPE = "Type";
    public String Sine_Game = "plus";
    public int select = 0;

    Spinner spinner;


//
//    SharedPreferences preferencesName;
//    SharedPreferences mSettings;
//    SharedPreferences mSettings2;
//    SharedPreferences mVolume;
//    SharedPreferences mSettingsType;


    SharedPreferences mGlobalSettings;
    SharedPreferences.Editor editor;
    public static final String GLOBAL_KEY_PREFERENCES = "key_shared_preferences";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_screen);


        buttonReturnToMainScreen = findViewById(R.id.buttonReturnToMainScreen);
        buttonSave = findViewById(R.id.buttonSave);
        editTexPlayer = findViewById(R.id.editTexPlayer);
        radioGroup = findViewById(R.id.radioGroup);
        RandomCheckBox = findViewById(R.id.RandmCheckBox);
        setDefault = findViewById(R.id.set_default_options);
        constraintLayout = findViewById(R.id.ConstrainLoyautOptions);
        constraintLayout.setBackgroundResource(R.drawable.fon);

        tvType = findViewById(R.id.tvType);

        spinner = findViewById(R.id.spinner);


        buttonReturnToMainScreen.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        setDefault.setOnClickListener(this);
        radioGroup.check(R.id.radioButtonEasy);


        LoadData();
        loadName = String.valueOf(editTexPlayer.getText());/////////////////////////////
        loaLevel = String.valueOf(level);
        loadType = Sine_Game;


        volumeNoSave = Integer.valueOf(volume);////=========
        loadRandom = checkRandom;


        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.Sine, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);
        SetRandomSelection(Sine_Game);

        spinner.setSelection(select);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {

                switch (selectedItemPosition) {
                    case 0: {
                        Sine_Game = "plus";
                        break;
                    }
                    case 1: {
                        Sine_Game = "minus";
                        break;
                    }
                    case 2: {
                        Sine_Game = "multiply";
                        break;
                    }
                    case 3: {
                        Sine_Game = "divide";
                        break;
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        RandomCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if (compoundButton.isChecked()) {


                    checkRandom = true;
                    checkRandom2 = "TRUE";


                    Toast.makeText(getApplicationContext(), checkRandom2, Toast.LENGTH_SHORT).show();

                } else {
                    checkRandom = false;
                    checkRandom2 = "FALSE";
                    ;

                    Toast.makeText(getApplicationContext(), checkRandom2, Toast.LENGTH_SHORT).show();

                }
            }
        });


//// lisiner dla chrcBox
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButtonEasy: {
                        level = "Easy";


                        // question = true;
                        break;
                    }
                    case R.id.radioButtonMedium: {
                        level = "Medium";

                        // question = true;
                        break;

                    }
                    case R.id.radioButtonHard: {
                        level = "Hard";
                        //   question = true;
                        break;
                    }
                }

            }
        });
        LevelSet(level);
        RandomSet(checkRandom);


        initControls();


    }


    private void initControls() {
        try {
            volumeSeekbar = (SeekBar) findViewById(R.id.seekBar);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            //volumeSeekbar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(Integer.valueOf(volume));/////-------


            volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                    volume = String.valueOf(progress);
                    loadVoll = true;


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void SetRandomSelection(String random_game) {


        switch (random_game) {
            case "plus": {
                select = 0;
                break;

            }
            case "minus": {
                select = 1;
                break;

            }
            case "multiply": {
                select = 2;
                break;

            }
            case "divide": {
                select = 3;
                break;

            }


        }

    }

    private void RandomSet(boolean checkRandom) {

        if (checkRandom == false) {
            RandomCheckBox.setChecked(false);

//
        } else {
            RandomCheckBox.setChecked(true);

//
        }
    }

    private void LevelSet(String level) {


        if (level.equals("Easy")) {
            radioGroup.check(R.id.radioButtonEasy);

        }
        if (level.equals("Medium")) {
            radioGroup.check(R.id.radioButtonMedium);

        }
        if (level.equals("Hard")) {
            radioGroup.check(R.id.radioButtonHard);
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonReturnToMainScreen: {
                PushbuttonReturnToMainScreen = true;//////////////////////////////////////////////
                ReturnIsPush = true;
                if (!loadName.equals(String.valueOf(editTexPlayer.getText())) || !loaLevel.equals(level)
                        || !loadType.equals(Sine_Game) || loadRandom != checkRandom || loadVoll == true) {
                    //Question();
                    onBackPressed();

                } else {
                    super.onBackPressed();

                }


                if (editTexPlayer.getText().equals("")) {
                    Toast.makeText(this, "Field player name is empty, fill it!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // onBackPressed();


                break;
            }
            case R.id.buttonSave: {
                SaveData();
                checkSetings = true;


                break;
            }
            case R.id.set_default_options: {
                SetDefault();


                break;
            }
            default: {
                Toast.makeText(this, "This button isn't create!", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    private void Question() {

        if (checkSetings == false) {
            context = OptionsScreenActivity.this;
            String title = "Save Setings";
            String message = "Save Setings?";
            String button1String = "no";
            String button2String = "yes";

            adSave = new AlertDialog.Builder(context);
            adSave.setTitle(title);  // заголовок
            adSave.setMessage(message); // сообщение
            adSave.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                    volumeSeekbar.setProgress(volumeNoSave);////=======
                    // APP_IntentMain();
                    onBackPressedCustom();


                }

            });

            adSave.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    // APP_IntentMain();
                    onBackPressedCustom();
                    SaveData();
                }
            });
            adSave.setCancelable(true);
            adSave.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    Toast.makeText(context, "You did not select anything.",
                            Toast.LENGTH_LONG).show();
                }
            });

            adSave.show();


        } else {
            onBackPressedCustom();
        }


    }


    private void SetDefault() {
        editTexPlayer.setText(" ");
        level = "Easy";
        radioGroup.check(R.id.radioButtonEasy);
        RandomCheckBox.setChecked(false);
        spinner.setSelection(0);
        volumeSeekbar.setProgress(8);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoadData();


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

        if (!loadName.equals(String.valueOf(editTexPlayer.getText())) || !loaLevel.equals(level)
                || !loadType.equals(Sine_Game) || loadRandom != checkRandom || loadVoll == true) {
            Question();


        } else {
            onBackPressedCustom();

        }


    }

    private void onBackPressedCustom() {
        super.onBackPressed();
    }

    private void SaveData() {

        //main sharedpreferenceinstance
        mGlobalSettings = getSharedPreferences(GLOBAL_KEY_PREFERENCES, MODE_PRIVATE);
        editor = mGlobalSettings.edit();


        String strNickName = editTexPlayer.getText().toString();

        editor.putString(APP_PREFERENCES_NICKNAME, strNickName);
        // Toast.makeText(this, checkRandom2, Toast.LENGTH_SHORT).show();
        String strLevel = level;
        editor.putString(LEVEL, strLevel);

        boolean strRandom = checkRandom;
        editor.putBoolean(APP_RANDOM, strRandom);

        String strType = checkRandom2;
        editor.putString(APP_RANDOM2, strType);

        String strSine = Sine_Game;
        editor.putString(APP_TYPE, strSine);

        String strVoll = volume;
        editor.putString(VOLUME, strVoll);


        editor.apply();


        Toast.makeText(OptionsScreenActivity.this, "Options have been save!", Toast.LENGTH_SHORT).show();


    }

    private void LoadData() {

        mGlobalSettings = getSharedPreferences(GLOBAL_KEY_PREFERENCES, MODE_PRIVATE);//указыванм имя ключа (APP_PREFERENCES_NICKNAME = KEY_Nickname (это ключ)) и MODE_PRIVATE


        String saveNickName = mGlobalSettings.getString(APP_PREFERENCES_NICKNAME, APP_PREFERENCES_NICKNAME);//указываем ключ по которому мы будем доставать значения а второе значение мы не указываем так как сохранять нам его не надо (мв присвоили значение переменной saveNickName )
        editTexPlayer.setText(saveNickName);


        String saveLevel = mGlobalSettings.getString(LEVEL, level);//указываем ключ по которому мы будем доставать значения а второе значение мы не указываем так как сохранять нам его не надо (мв присвоили значение переменной saveNickName )
        level = saveLevel;


        boolean saveRandom = mGlobalSettings.getBoolean(APP_RANDOM, false);
        checkRandom = saveRandom;


        String saveRandom1 = mGlobalSettings.getString(APP_RANDOM2, APP_RANDOM2);//указываем ключ по которому мы будем доставать значения а второе значение мы не указываем так как сохранять нам его не надо (мв присвоили значение переменной saveNickName )
        checkRandom2 = saveRandom1;


        String Sine = mGlobalSettings.getString(APP_TYPE, APP_TYPE);//указываем ключ по которому мы будем доставать значения а второе значение мы не указываем так как сохранять нам его не надо (мв присвоили значение переменной saveNickName )

        Sine_Game = Sine;
        tvType.setText(Sine);


        String vol = mGlobalSettings.getString(VOLUME, VOLUME);
        volume = vol;

    }
}