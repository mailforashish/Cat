package com.catexam.app.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.catexam.app.R;
import com.catexam.app.adapter.BannerAdapter;
import com.catexam.app.adapter.OptionAdapter;
import com.catexam.app.databinding.ActivityMainBinding;
import com.catexam.app.response.BannerResult;
import com.catexam.app.response.OptionList;
import com.catexam.app.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Context context = this;
    ViewPager viewPager;
    private List<BannerResult> bannerList = new ArrayList<>();
    Timer timer;
    OptionAdapter optionAdapter;
    List<OptionList> optionLists = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        binding.setClickListener(new EventHandler(this));
        binding.rvOption.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        optionAdapter = new OptionAdapter(MainActivity.this, optionLists);
        binding.rvOption.setAdapter(optionAdapter);
        optionAdapter.notifyDataSetChanged();
        setData();

        //initialize database here first
        DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);
        mDatabaseHelper.initialize();
        viewPager = (ViewPager) findViewById(R.id.myViewPager);
        BannerAdapter bannerAdapter = new BannerAdapter(this, bannerList);
        viewPager.setAdapter(bannerAdapter);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                viewPager.post(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % bannerList.size());
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);


        // DatabaseHelper DbHelper = new DatabaseHelper(this);
        //first time set id according to db
        // mainCategory = mDatabaseHelper.getParent(1);

        if (ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            requestPermission();
        }


    }

    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void openDrawer() {
            binding.drawerLayout.openDrawer(Gravity.LEFT);

        }

        public void shareApp() {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBodyText = "https://play.google.com/store/apps/details?id=" + getPackageName();
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject/Title");
            intent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(intent, "Choose sharing method"));
            binding.drawerLayout.closeDrawer(Gravity.LEFT);

        }

        public void rateUs() {
            final String appPackageName = getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
            binding.drawerLayout.closeDrawer(Gravity.LEFT);
        }

        public void moreApp() {
            Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Appkul"));
            startActivity(intent3);
            binding.drawerLayout.closeDrawer(Gravity.LEFT);

        }

        public void previousPaper() {
            startActivity(new Intent(context, PaperActivity.class));
        }
    }

    public void setData() {
        optionLists.clear();
        OptionList list2 = new OptionList(R.drawable.exam1);
        optionLists.add(list2);
        list2 = new OptionList(R.drawable.exam2);
        optionLists.add(list2);
        list2 = new OptionList(R.drawable.exam3);
        optionLists.add(list2);
        list2 = new OptionList(R.drawable.exam4);
        optionLists.add(list2);


        BannerResult bannerResult = new BannerResult(R.drawable.banner1);
        bannerList.add(bannerResult);
        bannerResult = new BannerResult(R.drawable.banner2);
        bannerList.add(bannerResult);
        bannerResult = new BannerResult(R.drawable.banner3);
        bannerList.add(bannerResult);
        bannerResult = new BannerResult(R.drawable.banner4);
        bannerList.add(bannerResult);
        bannerResult = new BannerResult(R.drawable.banner5);
        bannerList.add(bannerResult);
        bannerResult = new BannerResult(R.drawable.banner6);
        bannerList.add(bannerResult);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission() {
        requestPermissions(new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 100);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            exitMessage();
        }
    }

    private void exitMessage() {
        final AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.exist_custom_layout, null);
        builder.setView(viewInflated);
        final AlertDialog alert = builder.create();
        TextView moreApp = (TextView) viewInflated.findViewById(R.id.moreBTN);
        // TextView askLatter = (TextView) viewInflated.findViewById(R.id.nagetiveBTN);
        final TextView rateAs = (TextView) viewInflated.findViewById(R.id.positiveBTN);
        final RatingBar ratingBar = (RatingBar) viewInflated.findViewById(R.id.ratebar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
            }
        });


        moreApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });
        rateAs.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                alert.dismiss();
                finishAffinity();
                moveTaskToBack(true);

            }
        });

        alert.show();
    }
}
