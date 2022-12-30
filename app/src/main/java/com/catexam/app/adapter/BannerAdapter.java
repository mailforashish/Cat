package com.catexam.app.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.catexam.app.R;
import com.catexam.app.response.BannerResult;
import java.util.List;
import java.util.Objects;

public class BannerAdapter extends PagerAdapter {
    private List<BannerResult> imageList;
    private LayoutInflater layoutInflater;
    private Context context;

    public BannerAdapter(Context context, List<BannerResult> imageList) {
        this.imageList = imageList;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.banner_row, container, false);
        ImageView iv_banner = (ImageView) itemView.findViewById(R.id.imageViewMain);
        iv_banner.setImageResource(imageList.get(position).getImageUrl());
        Objects.requireNonNull(container).addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
