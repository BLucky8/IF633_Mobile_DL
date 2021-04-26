package umn.ac.id.week11_31943;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View mainLayout = findViewById(R.id.MainLayout);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView progressText = findViewById(R.id.progressText);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONPlaceholderService service = retrofit.create(JSONPlaceholderService.class);
        Call<ArrayList<Data>> dataCall = service.getAllData();

        dataCall.enqueue(new Callback<ArrayList<Data>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Data>> call, @NonNull Response<ArrayList<Data>> response) {
                progressBar.setVisibility(View.GONE);
                progressText.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {
                    ArrayList<Data> data = response.body();
                    recyclerView.setAdapter(new DataAdapter(data));
                    Snackbar.make(mainLayout, getString(R.string.berhasil), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(mainLayout, getString(R.string.gagal), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Data>> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                progressText.setVisibility(View.GONE);

                Snackbar.make(mainLayout, getString(R.string.error), Snackbar.LENGTH_LONG).show();
            }
        });
    }
}