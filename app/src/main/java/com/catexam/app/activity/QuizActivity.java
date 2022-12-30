package com.catexam.app.activity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.catexam.app.databinding.ActivityQuizBinding;
import com.catexam.app.dialog.ScoreDialog;
import com.catexam.app.response.DatabaseModel;
import com.catexam.app.R;
import com.catexam.app.util.DatabaseHelper;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {
    ActivityQuizBinding binding;

    private static final long COUNTDOWN_IN_MILLIS = 90000;

    private int unattempted;

    private TextView Qu_score;
    private TextView button_Ok;


    private TextView text_view_unattempted;

    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView wrongAnswer;
    private TextView textViewQuestionCount;
    private TextView textViewCountDown;

    private Button buttonConfirmNext;
    private Button buttonExplain;

    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private List<DatabaseModel> questionList;

    private int questionCounter;
    private int questionCountTotal;
    private DatabaseModel currentQuestion;

    private int score;
    private boolean answered;

    private int unscore;
    private int attempted;
    private int id;

    private long backPressedTime;

    //declair id  variable for access db
    //private int id;
    int answer;
    int mSelectedId;
    DatabaseHelper mDatabaseHelper;
    boolean r1, r2, r3, r4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        binding.setClickListener(new EventHandler(this));


        mDatabaseHelper = new DatabaseHelper(this);
        mSelectedId = getIntent().getIntExtra("id", 0);
        questionList = mDatabaseHelper.getQuestionsTable(mSelectedId);

        text_view_unattempted = findViewById(R.id.text_view_unattempted);
        textViewQuestion = findViewById(R.id.text_view_question);
        textViewScore = findViewById(R.id.text_view_score);
        wrongAnswer = findViewById(R.id.text_view_Wr_answer);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        textViewCountDown = findViewById(R.id.text_view_countdown);


        buttonConfirmNext = findViewById(R.id.button_confirm_next);
        buttonExplain = findViewById(R.id.button_Explain);

        textColorDefaultRb = binding.tvOptionOne.getTextColors();
        textColorDefaultCd = textViewCountDown.getTextColors();

        DatabaseHelper DbHelper = new DatabaseHelper(this);
        //first time set id according to db
        // questionList = DbHelper.getAllQuestions(9);
        // questionList = DbHelper.getAllQuestions(id);

        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);
        showNextQuestion();


    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void optionOne() {
            r1 = true;
            answer = 1;
            binding.tvOptionOne.setBackground(getResources().getDrawable(R.drawable.option_gray));
            binding.tvOptionTwo.setBackground(getResources().getDrawable(R.drawable.option_bg));
            binding.tvOptionThree.setBackground(getResources().getDrawable(R.drawable.option_bg));
            binding.tvOptionFour.setBackground(getResources().getDrawable(R.drawable.option_bg));

        }

        public void optionTwo() {
            r2 = true;
            answer = 2;
            binding.tvOptionTwo.setBackground(getResources().getDrawable(R.drawable.option_gray));
            binding.tvOptionOne.setBackground(getResources().getDrawable(R.drawable.option_bg));
            binding.tvOptionThree.setBackground(getResources().getDrawable(R.drawable.option_bg));
            binding.tvOptionFour.setBackground(getResources().getDrawable(R.drawable.option_bg));
        }

        public void optionThree() {
            r3 = true;
            answer = 3;
            binding.tvOptionThree.setBackground(getResources().getDrawable(R.drawable.option_gray));
            binding.tvOptionTwo.setBackground(getResources().getDrawable(R.drawable.option_bg));
            binding.tvOptionOne.setBackground(getResources().getDrawable(R.drawable.option_bg));
            binding.tvOptionFour.setBackground(getResources().getDrawable(R.drawable.option_bg));
        }

        public void optionFour() {
            r4 = true;
            answer = 4;
            binding.tvOptionFour.setBackground(getResources().getDrawable(R.drawable.option_gray));
            binding.tvOptionTwo.setBackground(getResources().getDrawable(R.drawable.option_bg));
            binding.tvOptionThree.setBackground(getResources().getDrawable(R.drawable.option_bg));
            binding.tvOptionOne.setBackground(getResources().getDrawable(R.drawable.option_bg));
        }

        public void btnConfirm() {
            if (!answered) {
                if (r1 || r2 || r3 || r4) {
                    CheckAnswer();
                } else {
                    Toast.makeText(QuizActivity.this, "Please Select an Answer", Toast.LENGTH_SHORT).show();
                }
            } else {
                showNextQuestion();
            }
        }

        public void btnSolution() {
            textViewQuestion.setText(currentQuestion.getExplaination());
        }

    }

    private void showNextQuestion() {
        binding.tvOptionOne.setTextColor(textColorDefaultRb);
        binding.tvOptionTwo.setTextColor(textColorDefaultRb);
        binding.tvOptionThree.setTextColor(textColorDefaultRb);
        binding.tvOptionFour.setTextColor(textColorDefaultRb);
        r4 = false;
        r2 = false;
        r3 = false;
        r1 = false;

        binding.tvOptionOne.setBackgroundDrawable(getResources().getDrawable(R.drawable.option_bg));
        binding.tvOptionTwo.setBackgroundDrawable(getResources().getDrawable(R.drawable.option_bg));
        binding.tvOptionThree.setBackgroundDrawable(getResources().getDrawable(R.drawable.option_bg));
        binding.tvOptionFour.setBackgroundDrawable(getResources().getDrawable(R.drawable.option_bg));

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);
            textViewQuestion.setText(currentQuestion.getQuestion());
            binding.tvOptionOne.setText(currentQuestion.getOption_1());
            binding.tvOptionTwo.setText(currentQuestion.getOption_2());
            binding.tvOptionThree.setText(currentQuestion.getOption_3());
            binding.tvOptionFour.setText(currentQuestion.getOption_4());
            questionCounter++;
            textViewQuestionCount.setText("Question :" + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("confirm");
            buttonExplain.setVisibility(View.GONE);
            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
        } else {
            showResult();
        }

    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                upDateCountDownText();
            }
            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                upDateCountDownText();
                CheckAnswer();
            }
        }.start();
    }

    private void upDateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int second = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, second);
        textViewCountDown.setText(timeFormatted);
        if (timeLeftInMillis < 20000) {
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(textColorDefaultCd);
        }
    }

    private void CheckAnswer() {
        answered = true;
        countDownTimer.cancel();
        if (answer == currentQuestion.getAnswer()) {
            score++;
            textViewScore.setText("Score:" + score);
        } else {
            unscore++;
            wrongAnswer.setText("Wrong :" + unscore);
        }
        showSolution();

    }

    private void showSolution() {
        binding.tvOptionOne.setTextColor(Color.RED);
        binding.tvOptionTwo.setTextColor(Color.RED);
        binding.tvOptionThree.setTextColor(Color.RED);
        binding.tvOptionFour.setTextColor(Color.RED);

        switch (currentQuestion.getAnswer()) {
            case 1:
                binding.tvOptionOne.setTextColor(Color.GREEN);
                textViewQuestion.setText(" Answer 1 is Correct");
                break;
            case 2:
                binding.tvOptionTwo.setTextColor(Color.GREEN);
                textViewQuestion.setText(" Answer 2 is Correct");
                break;
            case 3:
                binding.tvOptionThree.setTextColor(Color.GREEN);
                textViewQuestion.setText(" Answer 3 is Correct");
                break;
            case 4:
                binding.tvOptionFour.setTextColor(Color.GREEN);
                textViewQuestion.setText(" Answer 4 is Correct");
                break;
        }
        if (questionCounter < questionCountTotal) {
            buttonConfirmNext.setText("Next");
            buttonExplain.setVisibility(View.VISIBLE);
        } else {
            buttonConfirmNext.setText("ShowResult");
        }
        if (buttonConfirmNext.getText().toString().equals("ShowResult")) {
            showResult();

        }

    }

    private void showResult() {
        String score = textViewScore.getText().toString();
        String questionCount = textViewQuestionCount.getText().toString();
        String wrongAns = wrongAnswer.getText().toString();
        int un_attempt = questionCountTotal - questionCounter;
        new ScoreDialog(QuizActivity.this, score, questionCount, wrongAns, un_attempt);

    }


    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            showResult();
        } else {
            Toast.makeText(this, " Press back Again to finish ", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }


}