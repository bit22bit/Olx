package com.bits.olx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bits.olx.api_interfaces.JsonPlaceHolderApi;
import com.bits.olx.models.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Record extends AppCompatActivity {
//    List<Posts> ps;
//    private ListView listView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_record);
//        listView =findViewById(R.id.list);
//        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
//                        .baseUrl("http://10.0.2.2/")
//                        .addConverterFactory(GsonConverterFactory.create());
//
//        Retrofit retrofit = retrofitBuilder.build();
//        JsonPlaceHolderApi ap=retrofit.create(JsonPlaceHolderApi.class);
//        Call<List<Posts>> ad=ap.getitems();
//
//        ad.enqueue(new Callback<List<Posts>>() {
//            @Override
//            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
//                ps=response.body();
//                listadapter li=new listadapter();
//                listView.setAdapter(li);
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<List<Posts>> call, Throwable t) {
//                Toast.makeText(Record.this, "connection fail", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    public class listadapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return ps.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            View v=getLayoutInflater().from(Record.this).inflate(R.layout.childjobs,viewGroup,false);
//            TextView tv1=v.findViewById(R.id.heading);
//            TextView tv2=v.findViewById(R.id.heading);
//            TextView tv3=v.findViewById(R.id.heading);
//
//            tv1.setText(ps.get(i).getHeading());
//            tv2.setText(ps.get(i).getPrice());
//            tv3.setText(ps.get(i).getPrice());
//
//            return v;
//        }
//    }


    private TextView text_result;
    JsonPlaceHolderApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        text_result = findViewById(R.id.text_result);

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
                    text_result.setText("Code : "+response.code());
                    return;
                }

                List<Posts> posts = response.body();
                for (Posts posts1:posts){
                    String content = "";
                    content += "Heading: " + posts1.getHeading();
                    content += "Price: " + posts1.getPrice();
                    content += "Details: " + posts1.getDetail();

                    text_result.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                Toast.makeText(Record.this, "onFailure", Toast.LENGTH_SHORT).show();
                text_result.setText(t.getMessage());
            }
        });
    }

}