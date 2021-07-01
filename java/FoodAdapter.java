package com.example.webservicerest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private Context mContext;
    List<Food> mFoods;
    IFoodClick mListener;

    public FoodAdapter(Context context, IFoodClick listener) {
        mContext = context;
        mListener = listener;
        mFoods = new ArrayList<>();
    }

    public void addFoods(List<Food> foods) {
        mFoods.addAll(foods);
        notifyDataSetChanged();
    }

    public void addFood(Food food) {
        mFoods.add(food);
        notifyDataSetChanged();
    }

    void deleteFood(Food food) {
        mFoods.remove(food);
        notifyDataSetChanged();
    }

    void updateFood(int pos, Food food) {
        mFoods.set(pos, food);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = mFoods.get(position);
        holder.bindView(food);
    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFood;
        TextView txtName;
        TextView txtPrice;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.img_food);
            txtName = itemView.findViewById(R.id.txt_name);
            txtPrice = itemView.findViewById(R.id.txt_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onFoodClick(mFoods.get(getAbsoluteAdapterPosition()));
                }
            });
        }

        public void bindView(Food food) {
            Log.d("TAG", "bindView: " + food.getImage());
            Glide.with(mContext)
                    .load(ServiceGenerator.BASE_URL + food.getImage())
                    .centerCrop()
                    .into(imgFood);
            txtName.setText(food.getName());
            txtPrice.setText(String.valueOf(food.getPrice()));
        }
    }

    interface IFoodClick {
        void onFoodClick(Food food);
    }
}
