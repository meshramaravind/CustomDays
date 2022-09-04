package com.arvind.customdays.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.arvind.customdays.R;
import com.arvind.customdays.databinding.ItemsCategoryBinding;
import com.arvind.customdays.model.DaysModel;

import java.util.ArrayList;


public class CustomDaysDynamicTabsAdapter extends RecyclerView.Adapter<CustomDaysDynamicTabsAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<DaysModel> daysModelArrayList;
    private OnItemSelectedListener onItemSelectedListener;

    public CustomDaysDynamicTabsAdapter(Context context, ArrayList<DaysModel> listDaysTabs, OnItemSelectedListener onItemSelectedListener) {
        this.context = context;
        this.daysModelArrayList = listDaysTabs;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public CustomDaysDynamicTabsAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsCategoryBinding itemBlogsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.items_category, parent, false);
        return new ItemViewHolder(itemBlogsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        DaysModel frequencyDaysModel = daysModelArrayList.get(position);
        holder.itemBinding.tvTitle.setText(frequencyDaysModel.getTextTitle());
        holder.itemBinding.cardFrequencyDays.setCardBackgroundColor(frequencyDaysModel.getIsSelected() ? context.getResources().getColor(R.color.tab_selected_color2) : context.getResources().getColor(R.color.tab_unselected_color2));
        holder.itemBinding.tvTitle.setTextColor(frequencyDaysModel.getIsSelected() ? context.getResources().getColor(R.color.white) : context.getResources().getColor(R.color.colorPrimaryDark));

        holder.itemBinding.cardFrequencyDays.setOnClickListener(view ->
                onItemSelectedListener.onDaysSelected(position, frequencyDaysModel));
    }

    @Override
    public int getItemCount() {
        return daysModelArrayList != null ? daysModelArrayList.size() : 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(ArrayList<DaysModel> newList) {
        daysModelArrayList = newList;
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemsCategoryBinding itemBinding;

        public ItemViewHolder(@NonNull ItemsCategoryBinding itemBlogsBinding) {
            super(itemBlogsBinding.getRoot());
            this.itemBinding = itemBlogsBinding;
        }
    }

    public interface OnItemSelectedListener {
        void onDaysSelected(int pos, DaysModel daysModel);
    }
}

