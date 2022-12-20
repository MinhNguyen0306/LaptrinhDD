package com.example.thtuan3_nguyenleminh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.thtuan3_nguyenleminh.Model.Category;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {

    public CategoryAdapter(Context context, int resource, List<Category> categories){
        super(context, resource, categories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_selected, parent, false);
        TextView selected = convertView.findViewById(R.id.tv_selected);
        Category category = this.getItem(position);
        if(category != null){
            selected.setText(category.getId() + " - " + category.getTitle());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        TextView tvCategoy = convertView.findViewById(R.id.tv_itemcate);
        Category category = this.getItem(position);
        if(category != null){
            tvCategoy.setText(category.getId() + " - " + category.getTitle());
        }
        return convertView;
    }
}
