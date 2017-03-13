package com.example.mtree.popitup;

import android.annotation.TargetApi;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.Window;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;
        import java.io.InputStream;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Random;
        import java.util.concurrent.ThreadLocalRandom;

        import static com.example.mtree.popitup.R.id.button1;
        import static com.example.mtree.popitup.R.id.button2;
        import static com.example.mtree.popitup.R.id.button3;
        import static com.example.mtree.popitup.R.id.button4;

public class Question2Activity extends AppCompatActivity implements View.OnClickListener {

    private String code;
    private JSONArray json_questions;

    private String question;
    private String[] choices;
    private String correct_ans;

    private TextView tvTitle;
    private TextView tvQuestion;
    private List<Button> buttons;

    private static final int[] buttonId = {button1, button2, button3, button4};

    ArrayList<Integer> list;

    private int number = 0;
    private Vibrator v;

    private int score;
    private int round;

    private Intent intent;
    boolean doubleBackToExitPressedOnce = false;

    private MediaPlayer correct1;
    private MediaPlayer wrong1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);

        Intent intent = getIntent();
        code = intent.getStringExtra("code");

        initInstance();
    }

    private void initInstance() {
        SharedPreferences prefs = this.getSharedPreferences("dummy", Context.MODE_PRIVATE);;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("mission3", true);
        editor.apply();

        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        correct1 = MediaPlayer.create(this, R.raw.correct1);
        wrong1 = MediaPlayer.create(this, R.raw.wrong1);

        score = 0;
        round = 0;

        list = new ArrayList<Integer>();

        setTitle();
        getQuestion();
        setQuestion();
    }

    View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case button1: caseToCheck(0); break;
                case button2: caseToCheck(1); break;
                case button3: caseToCheck(2); break;
                case button4: caseToCheck(3); break;
                default: break;
            }
        }
    };

    private void caseToCheck(int id) {
        if ( checkAnswer(buttons.get(id).getText().toString()) == 0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                buttons.get(id).setBackground(getResources().getDrawable(R.drawable.rounded_disable_corner_choice));
            }
            else {
            }
            buttons.get(id).setEnabled(false);
        }
    }

    private void correctDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (round < 6){
                setTitle();
                getQuestion();
                setQuestion();
                getWindow().getDecorView().findViewById(android.R.id.content).invalidate();
                round++;
                }

                else {
                    new AlertDialog.Builder(Question2Activity.this)
                            .setMessage(getString(R.string.end_question2))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    intent = new Intent(Question2Activity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

            }
        });

        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.activity_correct, null);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.show();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                ImageView image = (ImageView) dialog.findViewById(R.id.correct_ans);
                Bitmap icon = BitmapFactory.decodeResource(Question2Activity.this.getResources(),
                        R.drawable.correct_ans);
                float imageWidthInPX = (float)image.getWidth();

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                        Math.round(imageWidthInPX * (float)icon.getHeight() / (float)icon.getWidth()));
                image.setLayoutParams(layoutParams);
            }
        });

    }

    private void wrongDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setNegativeButton(R.string.try_again, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.activity_wrong, null);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.show();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                ImageView image = (ImageView) dialog.findViewById(R.id.wrong_ans);
                Bitmap icon = BitmapFactory.decodeResource(Question2Activity.this.getResources(),
                        R.drawable.wrong_ans);
                float imageWidthInPX = (float)image.getWidth();

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                        Math.round(imageWidthInPX * (float)icon.getHeight() / (float)icon.getWidth()));
                image.setLayoutParams(layoutParams);
            }
        });

    }

    private void setTitle() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        tvTitle.setBackgroundColor(color);

        number++;
        String txtNumber = String.format("%02d", number);
        tvTitle.setText(txtNumber);

    }

    private void setQuestion() {

        buttons = new ArrayList<Button>(buttonId.length);
        for (int id : buttonId) {
            final Button button = (Button) findViewById(id);
            button.setOnClickListener(btnClick);
            buttons.add(button);
        }

        tvQuestion.setText(question);

        for (int i = 0; i < buttonId.length; i++) {
            buttons.get(i).setText(choices[i]);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                buttons.get(i).setBackground(getResources().getDrawable(R.drawable.rounded_corner_choice));
                buttons.get(i).setTextColor(0xFFFFFFFF);
                buttons.get(i).setEnabled(true);
            }
        }
    }

    private void getQuestion() {
        try {

            JSONObject obj = new JSONObject(loadJSONFromAsset());
            json_questions = obj.getJSONArray("questions");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        randomQuestion();
        shuffleChoices(choices);
    }

    private void randomQuestion() {
//        int randomIndex = new Random().nextInt(length);

        int randomIndex = randomIndex();
        try {

            JSONObject randomQuestion = json_questions.getJSONObject(randomIndex);
            question = randomQuestion.getString("question");

            JSONArray json_choices = randomQuestion.getJSONArray("choices");
            choices = new String[json_choices.length()];

            for (int i = 0; i < json_choices.length(); i++) {
                JSONObject choice = json_choices.getJSONObject(i);
                choices[i] = choice.getString("choice");
                if (choice.getString("isTrue").equals("true")) {
                    correct_ans = choice.getString("choice");
                }
            }

        } catch (JSONException e) {

            e.printStackTrace();

        }

    }

    private int randomIndex() {
        Random rand = new Random();
        int index = rand.nextInt(json_questions.length());

        while (list.contains(index)) {
            index = rand.nextInt(json_questions.length());
        }

        list.add(index);

        return index;
    }

    private int checkAnswer(String ans) {
        if (correct_ans.equals(ans)) {
            correct1.start();
            correctDialog();
            return 1;
        } else {
            v.vibrate(200);
            wrong1.start();
            wrongDialog();
            return 0;
        }
    }

    @TargetApi(21)
    private void shuffleChoices(String[] chs) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = chs.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = chs[index];
            chs[index] = chs[i];
            chs[i] = a;
        }
    }

    private String loadJSONFromAsset() {
        String json = null;

        try {

            InputStream is = Question2Activity.this.getAssets().open("questions2.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {

            ex.printStackTrace();
            return null;
        }

        return json;

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.exit, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
