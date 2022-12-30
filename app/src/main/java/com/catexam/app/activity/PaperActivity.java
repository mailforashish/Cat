package com.catexam.app.activity;

import android.graphics.Color;
import android.os.Bundle;

import android.view.WindowManager;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.catexam.app.R;
import com.catexam.app.adapter.PaperAdapter;
import com.catexam.app.databinding.ActivityPaperBinding;
import com.catexam.app.response.OptionList;

import java.util.ArrayList;
import java.util.List;

public class PaperActivity extends AppCompatActivity {
    ActivityPaperBinding binding;
    List<OptionList> paperList = new ArrayList<>();
    PaperAdapter paperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_paper);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);

        binding.rvPaper.setLayoutManager(new GridLayoutManager(PaperActivity.this, 2));
        paperAdapter = new PaperAdapter(PaperActivity.this, paperList);
        binding.rvPaper.setAdapter(paperAdapter);
        paperAdapter.notifyDataSetChanged();
        setData();
    }

    private void setData() {
        paperList.clear();
        OptionList list2 = new OptionList(R.drawable.pdf1,"a.pdf");
        paperList.add(list2);
        list2 = new OptionList(R.drawable.pdf2,"b.pdf");
        paperList.add(list2);
        list2 = new OptionList(R.drawable.pdf3,"c.pdf");
        paperList.add(list2);
        list2 = new OptionList(R.drawable.pdf4,"d.pdf");
        paperList.add(list2);
        list2 = new OptionList(R.drawable.pdf5,"e.pdf");
        paperList.add(list2);
        list2 = new OptionList(R.drawable.pdf6,"f.pdf");
        paperList.add(list2);
    }
}
