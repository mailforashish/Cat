package com.catexam.app.activity;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import com.catexam.app.adapter.CategoryAdapter;
import com.catexam.app.databinding.ActivityCategoryBinding;
import com.catexam.app.response.DatabaseModel;
import com.catexam.app.R;
import com.catexam.app.util.DatabaseHelper;
import com.catexam.app.util.HideStatus;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    List<DatabaseModel> arrayList;
    int mSelectedId;
    DatabaseHelper mDatabaseHelper;
    ActivityCategoryBinding binding;
    CategoryAdapter categoryAdapter;
    HideStatus hideStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        hideStatus = new HideStatus(getWindow(), true);
        mDatabaseHelper = new DatabaseHelper(this);
        mSelectedId = getIntent().getIntExtra("SelectedId", 0);
        //Log.e("TAG", "onCreate: " + mSelectedId);
        arrayList = mDatabaseHelper.getSub_category(mSelectedId);
        //Log.d(TAG, "onCreate: "+mSelectedId);
        //first time set id according to db
        //arrayList = mDatabaseHelper.getSub_category_id(1);
        //arrayList = mDatabaseHelper.getSub_category(mSelectedId);
        //Log.d(TAG,"onCreate"+mSelectedId);
        categoryAdapter = new CategoryAdapter(this, arrayList);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(CategoryActivity.this, 1));
        binding.recyclerView.setAdapter(categoryAdapter);

    }
}