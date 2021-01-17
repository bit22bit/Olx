package com.bits.olx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.bits.olx.api_interfaces.JsonPlaceHolderApi;
import com.bits.olx.models.Posts;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    // The singleton HTTP client.
    public final OkHttpClient client = new OkHttpClient();

    public static final String API_BASE_URL = "http://192.168.1.7:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view_result);


        OkHttpClient eagerClient = client.newBuilder()
                .readTimeout(50000, TimeUnit.MILLISECONDS)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(eagerClient)
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Posts>> call =jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {

                if (!response.isSuccessful()){
                    textView.setText("Code: " + response.code());
                    return;
                }

                List<Posts> posts = response.body();
                for (Posts post:posts){
                    String content = "";
                    content += "Heading: " + post.getHeading() + "\n";
                    content += "Price: " + post.getPrice() + "\n";
                    content += "Detail: " + post.getDetail() + "\n\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}