package com.example.lightingapp.example.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lightingapp.R;
import com.example.lightingapp.example.model.blog.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private CategoryAdapter.OnItemClickListener mListener;
    private ArrayList<Category> categories = new ArrayList<>();

    private int selectedPosition = -1; // Default to no selection


    public CategoryAdapter(ArrayList<Category> categories, CategoryAdapter.OnItemClickListener mListener) {
        this.categories = categories;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_card, parent, false);
        CategoryAdapter.CategoryViewHolder categoryViewHolder = new CategoryAdapter.CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    public void setCategories(ArrayList<Category> newCategories) {
        if (categories != null && !categories.isEmpty()) {
            categories.clear();
        }

        categories = newCategories;
        notifyDataSetChanged();
    }

    public List<Category> getCategories() {
        return categories;
    }

    protected class CategoryViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public CardView cardRoot;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> mListener.onCategoryItemClicked(getAdapterPosition()));
            name = itemView.findViewById(R.id.text_category_name);
            cardRoot = itemView.findViewById(R.id.card_root);
        }

        public void bind(int position) {
            name.setText(categories.get(position).getName());
            if (selectedPosition == position) {
                cardRoot.setCardBackgroundColor(itemView.getContext().getResources().getColor(R.color.basic_yellow));
                name.setTextColor(itemView.getContext().getResources().getColor(R.color.white));
            } else {
                cardRoot.setCardBackgroundColor(itemView.getContext().getResources().getColor(R.color.white));
                name.setTextColor(itemView.getContext().getResources().getColor(R.color.black));
            }
        }
    }

    public interface OnItemClickListener {
        void onCategoryItemClicked(int position);
    }

}
