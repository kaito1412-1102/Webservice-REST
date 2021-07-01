package com.example.webservicerest;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FoodAdapter.IFoodClick {
    RecyclerView rvFoods;
    FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initFoods();
    }

    private void initFoods() {
        ServiceGenerator.getRequestAPI().getFoods().enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response.isSuccessful()) {
                    adapter.addFoods(response.body());
                } else {
                    Log.d("TAG", "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Log.d("TAG", "onFailure: " + call.toString() + " - " + t.toString());
            }
        });
    }

    private void initViews() {
        rvFoods = findViewById(R.id.rv_food);
        adapter = new FoodAdapter(this, this);
        rvFoods.setLayoutManager(new LinearLayoutManager(this));
        rvFoods.setAdapter(adapter);
    }

    @Override
    public void onFoodClick(Food food) {

    }
}