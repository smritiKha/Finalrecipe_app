package com.myrecipeap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.myrecipeap.Adapter.AdapterPopular;
import com.myrecipeap.Model.ResModel;
import com.myrecipeap.RoomDB.AppDatabase;
import com.myrecipeap.RoomDB.User;
import com.myrecipeap.RoomDB.UserDao;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ImageView salad, main, drinks, dessert, menu;
    RecyclerView rcview_home;
    List<User> dataPopular = new ArrayList<>();
    LottieAnimationView lottie;
    EditText editText;
    List<ResModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find views
        salad = findViewById(R.id.salad);
        main = findViewById(R.id.MainDish);
        drinks = findViewById(R.id.Drinks);
        dessert = findViewById(R.id.Desserts);
        rcview_home = findViewById(R.id.rcview_popular);
        lottie = findViewById(R.id.lottie);
        editText = findViewById(R.id.editText);
        menu = findViewById(R.id.imageView);

        // Set layout to recyclerView
        rcview_home.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Set Popular recipes
        setPopularList();

        // Category buttons - start new activity with intent method "start"
        salad.setOnClickListener(v -> start("Salad", "Salad"));
        main.setOnClickListener(v -> start("Dish", "Main dish"));
        drinks.setOnClickListener(v -> start("Drinks", "Drinks"));
        dessert.setOnClickListener(v -> start("Desserts", "Dessert"));

        // Open search activity
        editText.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        // Menu button
        menu.setOnClickListener(v -> showBottomSheet());
    }

    public void setPopularList() {
        // Get database
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "db_name").allowMainThreadQueries()
                .createFromAsset("database/recipe.db")
                .build();
        UserDao userDao = db.userDao();

        // Get all recipes from database
        List<User> recipes = userDao.getAll();

        // Filter Popular category from all recipes
        for (User recipe : recipes) {
            if (recipe.getCategory().contains("Popular")) {
                dataPopular.add(recipe);
            }
        }

        // Set popular list to adapter
        AdapterPopular adapter = new AdapterPopular(dataPopular, getApplicationContext());
        rcview_home.setAdapter(adapter);

        // Hide progress animation
        lottie.setVisibility(View.GONE);
    }

    // Start MainActivity (Recipe list) with intent message
    public void start(String p, String title) {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        intent.putExtra("Category", p);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    // Create a bottom dialog for privacy policy and about
    private void showBottomSheet() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet);

        LinearLayout privacyPolicy = dialog.findViewById(R.id.privacy_policy);
        LinearLayout aboutDev = dialog.findViewById(R.id.about_dev);

        // Redirect to Privacy Policy Activity
        privacyPolicy.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, PrivacyPolicy.class);
            startActivity(intent);
            dialog.dismiss(); // Dismiss the dialog after starting the activity
        });

        // Redirect to About Developer Activity
        aboutDev.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class); // Assuming LoginActivity is your About Developer page
            startActivity(intent);
            dialog.dismiss(); // Dismiss the dialog after starting the activity
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}