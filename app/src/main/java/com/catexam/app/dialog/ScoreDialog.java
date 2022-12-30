package com.catexam.app.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.catexam.app.R;
import com.catexam.app.activity.QuizActivity;
import com.catexam.app.databinding.DialogScoreBinding;
import com.catexam.app.util.HideStatus;


public class ScoreDialog extends Dialog {
    DialogScoreBinding binding;
    private HideStatus hideStatus;
    QuizActivity context;
    String score, questionCount, wrongAns;
    int un_attempt;

    public ScoreDialog(@NonNull QuizActivity context, String score, String questionCount, String wrongAns, int un_attempt) {
        super(context, R.style.Theme_AppCompat_Dialog);
        this.context = context;
        this.score = score;
        this.questionCount = questionCount;
        this.wrongAns = wrongAns;
        this.un_attempt = un_attempt;
        init();
    }

    void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_score, null, false);
        setContentView(binding.getRoot());
        hideStatus = new HideStatus(getWindow(), true);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.setClickListener(new EventHandler(getContext()));

        binding.tvScore.setText(score);
        binding.tvCount.setText(questionCount);
        binding.tvUnattempted.setText("Un Attempted:"+String.valueOf(un_attempt));
        binding.tvWrongAns.setText(wrongAns);

        show();

    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void closePage() {
            context.finish();
            dismiss();
        }


    }


}