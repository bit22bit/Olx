package com.bits.olx;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bits.olx.api_interfaces.JsonPlaceHolderApi;
import com.bits.olx.models.Posts;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.net.URI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddPost extends AppCompatActivity {
    private Button post,read,choosebtn;
    private TextView heading,price,detail;
    private  static final int PICK_IMAGE_REQUEST = 1;

    private URI mImageUri;
    FirebaseFirestore db= FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        heading=findViewById(R.id.heading);
        price=findViewById(R.id.price);
        detail=findViewById(R.id.detail);
//        choosebtn=findViewById(R.id.choose_image);
        post=findViewById(R.id.button2);
        read=findViewById(R.id.button3);

//        choosebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openFile();
//            }
//        });

        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addjob();
            }
        });
        read.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddPost.this,MainActivity.class));
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_add);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_add: return true;
                    case R.id.nav_posts:startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_profile:startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }
//    private void  openFile(){
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode ==  RESULT_OK && data != null && data.getData() != null){
//            mImageUri= data.getData();
//        }
//    }

    private boolean validate(String title, String prices, String details){
        if(title.isEmpty()){
            heading.setError("Title Required");
            heading.requestFocus();
        }
        if(prices.isEmpty()){
            price.setError("Price Required");
            price.requestFocus();
        }
        if(details.isEmpty()){
            detail.setError("Details is Required");
            detail.requestFocus();
        }
        return false;
    }
    private void addjob( ){
        String title=heading.getText().toString().trim();
        String prices=price.getText().toString().trim() ;
        String details=detail.getText().toString().trim();
        if( !validate(title,prices,details)){

            CollectionReference dbjob=db.collection("Productdetails");
            Posts job = new Posts(title,prices,details);
            dbjob.add(job) .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(AddPost.this, "Item Added", Toast.LENGTH_LONG).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddPost.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }

//    void addPost()
//    {
//        String heading = heading.getText().toString().trim();
//        String price = price.getText().toString().trim();
//        String detail = detail.getText().toString().trim();
//
//        Posts pt = new Posts(heading,price,detail);
//
//        Gson gson = new Gson();
//        String json = gson.toJson(pt);
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:3000/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
//
//        Call<Posts> call = jsonPlaceHolderApi.getitems(pt);
//
//
//
//        call.enqueue(new Callback<Posts>() {
//            @Override
//            public void onResponse(Call<Posts> call, Response<Posts> response) {
//
//                Toast toast = Toast.makeText(AddPost.this,"Post Added", Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
//                toast.show();
//                startActivity(new Intent(AddPost.this,MainActivity.class));
//
//            }
//
//            @Override
//            public void onFailure(Call<Posts> call, Throwable t) {
//
//                Toast toast = Toast.makeText(AddPost.this,t.getMessage(),Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
//                toast.show();
//            }
//        });
//
//    }



}