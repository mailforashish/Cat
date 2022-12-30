package com.catexam.app.activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
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
    private long backPressedTime;
    int answer;
    int mSelectedId;
    DatabaseHelper mDatabaseHelper;
    boolean opt1, opt2, opt3, opt4;

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
        textColorDefaultRb = binding.tvOptionOne.getTextColors();
        textColorDefaultCd = binding.textViewCountdown.getTextColors();
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
            opt1 = true;
            answer = 1;
            binding.tvOptionOne.setBackground(getResources().getDrawable(R.drawable.option_gray));
            binding.tvOptionTwo.setBackground(getResources().getDrawable(R.drawable.option_bg));
            binding.tvOptionThree.setBackground(getResources().getDrawable(R.drawable.option_bg));
            binding.tvOptionFour.setBackground(getResources().getDrawable(R.drawable.option_bg));
        }

        public void optionTwo() {
            opt2 = true;
            answer = 2;
            binding.tvOptionTwo.setBackground(getResources().getDrawable(R.drawable.option_gray));
            binding.tvOptionOne.setBackground(getResources().getDrawable(R.drawable.option_bg));
            binding.tvOptionThree.setBackground(getResources().getDrawable(R.drawable.option_bg));
            binding.tvOptionFour.setBackground(getResources().getDrawable(R.drawable.option_bg));
        }

        public void optionThree() {
            opt3 = true;
            answer = 3;
            binding.tvOptionThree.setBackground(getResources().getDrawable(R.drawable.option_gray));
            binding.tvOptionTwo.setBackground(getResources().getDrawable(R.drawable.option_bg));
            binding.tvOptionOne.setBackground(getResources().getDrawable(R.drawable.option_bg));
            binding.tvOptionFour.setBackground(getResources().getDrawable(R.drawable.option_bg));
        }

        public void optionFour() {
            opt4 = true;
            answer = 4;
            binding.tvOptionFour.setBackground(getResources().getDrawable(R.drawable.option_gray));
            binding.tvOptionTwo.setBackground(getResources().getDrawable(R.drawable.option_bg));
            binding.tvOptionThree.setBackground(getResources().getDrawable(R.drawable.option_bg));
            binding.tvOptionOne.setBackground(getResources().getDrawable(R.drawable.option_bg));
        }

        public void btnConfirm() {
            if (!answered) {
                if (opt1 || opt2 || opt3 || opt4) {
                    CheckAnswer();
                } else {
                    Toast.makeText(QuizActivity.this, "Please Select an Answer", Toast.LENGTH_SHORT).show();
                }
            } else {
                showNextQuestion();
            }
        }

        public void btnSolution() {
            binding.textViewQuestion.setText(currentQuestion.getExplaination());
        }
    }

    private void showNextQuestion() {
        binding.tvOptionOne.setTextColor(textColorDefaultRb);
        binding.tvOptionTwo.setTextColor(textColorDefaultRb);
        binding.tvOptionThree.setTextColor(textColorDefaultRb);
        binding.tvOptionFour.setTextColor(textColorDefaultRb);
        opt4 = false;
        opt2 = false;
        opt3 = false;
        opt1 = false;

        binding.tvOptionOne.setBackgroundDrawable(getResources().getDrawable(R.drawable.option_bg));
        binding.tvOptionTwo.setBackgroundDrawable(getResources().getDrawable(R.drawable.option_bg));
        binding.tvOptionThree.setBackgroundDrawable(getResources().getDrawable(R.drawable.option_bg));
        binding.tvOptionFour.setBackgroundDrawable(getResources().getDrawable(R.drawable.option_bg));

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);
            binding.textViewQuestion.setText(currentQuestion.getQuestion());
            binding.tvOptionOne.setText(currentQuestion.getOption_1());
            binding.tvOptionTwo.setText(currentQuestion.getOption_2());
            binding.tvOptionThree.setText(currentQuestion.getOption_3());
            binding.tvOptionFour.setText(currentQuestion.getOption_4());
            questionCounter++;
            binding.textViewQuestionCount.setText("Question :" + questionCounter + "/" + questionCountTotal);
            answered = false;
            binding.buttonConfirmNext.setText("confirm");
            binding.buttonExplain.setVisibility(View.GONE);
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
        binding.textViewCountdown.setText(timeFormatted);
        if (timeLeftInMillis < 20000) {
            binding.textViewCountdown.setTextColor(Color.RED);
        } else {
            binding.textViewCountdown.setTextColor(textColorDefaultCd);
        }
    }

    private void CheckAnswer() {
        answered = true;
        countDownTimer.cancel();
        if (answer == currentQuestion.getAnswer()) {
            score++;
            binding.textViewScore.setText("Score:" + score);
        } else {
            unscore++;
            binding.textViewWrAnswer.setText("Wrong :" + unscore);
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
                binding.textViewQuestion.setText(" Answer 1 is Correct");
                break;
            case 2:
                binding.tvOptionTwo.setTextColor(Color.GREEN);
                binding.textViewQuestion.setText(" Answer 2 is Correct");
                break;
            case 3:
                binding.tvOptionThree.setTextColor(Color.GREEN);
                binding.textViewQuestion.setText(" Answer 3 is Correct");
                break;
            case 4:
                binding.tvOptionFour.setTextColor(Color.GREEN);
                binding.textViewQuestion.setText(" Answer 4 is Correct");
                break;
        }
        if (questionCounter < questionCountTotal) {
            binding.buttonConfirmNext.setText("Next");
            binding.buttonExplain.setVisibility(View.VISIBLE);
        } else {
            binding.buttonConfirmNext.setText("ShowResult");
        }
        if (binding.buttonConfirmNext.getText().toString().equals("ShowResult")) {
            showResult();
        }
    }

    private void showResult() {
        String score = binding.textViewScore.getText().toString();
        String questionCount = binding.textViewQuestionCount.getText().toString();
        String wrongAns = binding.textViewWrAnswer.getText().toString();
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