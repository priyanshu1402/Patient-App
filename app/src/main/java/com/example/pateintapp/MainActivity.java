package com.example.pateintapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RetrofitInterface retrofitInterface;
    private Retrofit retrofit;
    private String BASE_URL="http://192.168.107.140:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit =new Retrofit.Builder()
                .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                                .build();
        retrofitInterface=retrofit.create(RetrofitInterface.class);
        findViewById(R.id.loginb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogindailog();
            }
        });
    }

    private void handleLogindailog() {
        View v= getLayoutInflater().inflate(R.layout.activity_main, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this );
        builder.setView(v).show();
        Button loginb = v.findViewById(R.id.loginb);
        EditText name= v.findViewById(R.id.username);
        EditText password=v.findViewById(R.id.password);

        loginb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                HashMap<String,String> map =new HashMap<>();
                map.put("name", name.getText().toString());
                map.put("password", password.getText().toString());
                Call<loginr>call=retrofitInterface.executeLogin(map);
                call.enqueue(new Callback<loginr>() {
                    @Override
                    public void onResponse(Call<loginr> call, Response<loginr> response) {
                        if (response.code()==200){
                            Toast.makeText(MainActivity.this, "signedin",Toast.LENGTH_LONG).show();
                        } else if (response.code() == 400) {
                            Toast.makeText(MainActivity.this, "400",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<loginr> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }

        });


    }
}