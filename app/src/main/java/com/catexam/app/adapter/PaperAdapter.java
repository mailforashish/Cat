package com.catexam.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.catexam.app.R;
import com.catexam.app.activity.CategoryActivity;
import com.catexam.app.response.OptionList;

import java.util.List;


public class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.MyViewHolder> {
    List<OptionList> list;
    Context context;

    public PaperAdapter(Context context, List<OptionList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_row, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        try {
            holder.iv_option.setImageResource(list.get(position).getIcon());
            holder.cl_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CategoryActivity.class);
                    intent.putExtra("SelectedId", position + 1);
                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.size();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_option;
        public ConstraintLayout cl_main;

        public MyViewHolder(View view) {
            super(view);
            iv_option = itemView.findViewById(R.id.iv_option);
            cl_main = itemView.findViewById(R.id.cl_main);

            Animation animation = AnimationUtils.loadAnimation(context, R.anim.swing_up_left);
            iv_option.startAnimation(animation);

        }
    }
}
