package com.example.webservicerest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FoodAdapter.IFoodClick, View.OnClickListener {
    RecyclerView rvFoods;
    FoodAdapter adapter;
    //insert
    EditText edtName;
    EditText edtPrice;
    ImageView imgFood;
    Button btnChooseImage;
    Bitmap imageBitmap;
    Button btnInsert;
    public static final int CHOOSE_IMAGE = 123;
    public static final int PERMISSION_GALLERY = 12;


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
        //insert
        edtName = findViewById(R.id.edt_name);
        edtPrice = findViewById(R.id.edt_price);
        btnChooseImage = findViewById(R.id.btn_choose_image);
        btnInsert = findViewById(R.id.btn_insert);
        imgFood = findViewById(R.id.img_food);
        btnInsert.setOnClickListener(this::onClick);
        btnChooseImage.setOnClickListener(this::onClick);
    }

    @Override
    public void onFoodClick(Food food) {
        Log.d("TAG", "onFoodClick: "+food.getId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_choose_image:
                checkPhotoPermission();
                break;
            case R.id.btn_insert:
                insertFoodToServer();
                break;
        }
    }

    private void insertFoodToServer() {
        String nameFood = edtName.getText().toString();
        int price = Integer.parseInt(edtPrice.getText().toString());
        String imageName = "image" + System.currentTimeMillis();
        //upload image to server
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        String encodeImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);

        ServiceGenerator.getRequestAPI().insertFood(nameFood, price, encodeImage, imageName).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    StringBuilder imagePath = new StringBuilder();
                    imagePath.append("image/").append(imageName).append(".jpg");
                    int idFood = Integer.parseInt(response.body());
                    Food food = new Food(idFood, nameFood, price, imagePath.toString());
                    adapter.addFood(food);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), CHOOSE_IMAGE);
    }

    private void checkPhotoPermission() {
        Log.d("TAG", "checkPhotoPermission: ");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Please accept for required permission", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_GALLERY);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_GALLERY);
            }
        } else {
            openGallery();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CHOOSE_IMAGE) {
            Uri selectedImageUri = data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                imgFood.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}