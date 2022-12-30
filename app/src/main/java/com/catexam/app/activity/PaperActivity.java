package com.catexam.app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.WindowManager;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.catexam.app.BuildConfig;
import com.catexam.app.R;
import com.catexam.app.adapter.PaperAdapter;
import com.catexam.app.databinding.ActivityPaperBinding;
import com.catexam.app.response.OptionList;
import com.catexam.app.util.PaperListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class PaperActivity extends AppCompatActivity implements PaperListener {
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
        paperAdapter = new PaperAdapter(PaperActivity.this, paperList,PaperActivity.this);
        binding.rvPaper.setAdapter(paperAdapter);
        paperAdapter.notifyDataSetChanged();
        setData();
    }


    @Override
    public void getPaperClick(boolean click, String uri) {
        if (click) {
            OpenPdf(uri);
        }
    }

    private void OpenPdf(String fileName) {
        AssetManager assetManager = getAssets();
        InputStream in = null;
        OutputStream out = null;
        File file = new File(getFilesDir(), fileName);
        try {
            in = assetManager.open(fileName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                out = openFileOutput(file.getName(), Context.MODE_PRIVATE);
            } else {
                out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);
            }
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
        Uri pdfFileURI;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pdfFileURI = FileProvider.getUriForFile(PaperActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            pdfFileURI = Uri.parse("file://" + getFilesDir() + "/" + fileName);
        }
        intent.setDataAndType(pdfFileURI, "application/pdf");
        startActivity(intent);

    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
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
