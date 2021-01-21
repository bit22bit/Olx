package com.bits.olx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bits.olx.api_interfaces.JsonPlaceHolderApi;
import com.bits.olx.models.Posts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView tread;
    JsonPlaceHolderApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tread = findViewById(R.id.read);

        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi Api = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Posts>> call = Api.getitems();

        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                if (!response.isSuccessful()){
                    tread.setText("Code : "+response.code());
                    return;
                }

                List<Posts> posts = response.body();
                for (Posts posts1:posts){
                    String content = "";
                    content += "\n\n Heading: " + posts1.getHeading();
                    content += "\nPrice: " + posts1.getPrice();
                    content += "\nDetails: " + posts1.getDetail();

                    tread.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                tread.setText(t.getMessage());
            }
        });


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_posts);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_add:startActivity(new Intent(getApplicationContext(), AddPost.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_posts: return true;
                    case R.id.nav_profile:startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}