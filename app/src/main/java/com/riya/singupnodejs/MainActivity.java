package com.riya.singupnodejs;

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

    private Retrofit retrofit;
    private RetrofitInterference retrofitInterference;
    private String BASE_URL = "http://10.0.2.2:7777";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        retrofitInterference = retrofit.create(RetrofitInterference.class);

        findViewById(R.id.loginbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLoginDialog();
            }
        });
        findViewById(R.id.singupbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSingupDialog();
            }
        });
    }

    private void handleLoginDialog() {
        View view = getLayoutInflater().inflate(R.layout.activity_login,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view).show();

        Button loginbtn = view.findViewById(R.id.loginbutton);
        EditText emailtxt= view.findViewById(R.id.emailbox);
        EditText passtxt = view.findViewById(R.id.passowrdbox);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> map = new HashMap<>();

                map.put("email", emailtxt.getText().toString());
                map.put("password",passtxt.getText().toString());

                Call<login> call = retrofitInterference.executeLogin(map);
                call.enqueue(new Callback<login>() {
                    @Override
                    public void onResponse(Call<login> call, Response<login> response) {

                        if(response.code() == 200){

                            login result = response.body();
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                                    builder1.setTitle(result.getPassword());
                                    builder1.setMessage(result.getEmail());
                                    builder1.show();

                        }else if (response.code() == 404){
                            Toast.makeText(MainActivity.this, "Somrthing went Wrong", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<login> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    private void handleSingupDialog() {

        View view = getLayoutInflater().inflate(R.layout.activity_singup,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view).show();

        Button singupbtn = view.findViewById(R.id.singupbutton);
        EditText nametxt = view.findViewById(R.id.namebox);
        EditText emailTxt = view.findViewById(R.id.emailbox);
        EditText passtxt = view.findViewById(R.id.passowrdbox);

        singupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String,String> map = new HashMap<>();
                map.put("name", nametxt.getText().toString());

                map.put("email", emailTxt.getText().toString());
                map.put("password",passtxt.getText().toString());

                Call<Void> call = retrofitInterference.executeSingup(map);
                 call.enqueue(new Callback<Void>() {
                     @Override
                     public void onResponse(Call<Void> call, Response<Void> response) {
                         if(response.code() == 200){
                             Toast.makeText(MainActivity.this, "SingUp successfully", Toast.LENGTH_SHORT).show();
                         }else if(response.code() == 400){
                             Toast.makeText(MainActivity.this, "already register", Toast.LENGTH_SHORT).show();
                         }

                     }

                     @Override
                     public void onFailure(Call<Void> call, Throwable t) {
                         Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                     }
                 });

            }
        });

    }
}