package com.example.aquasense;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aquasense.databinding.ActivityMainBinding;
import com.example.aquasense.model.City;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CityAdapter cityAdapter;
    private List<City> cities;
    private FirebaseFirestore db;
    ProgressBar progressBar;
    private SearchView searchView;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressBar = binding.progressBar;
        progressBar.getVisibility();
        progressBar.setVisibility(View.VISIBLE);

        binding.floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, FindWaterQualityActivity.class);
            startActivity(intent);
        });

        db = FirebaseFirestore.getInstance();
        recyclerView = binding.recyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        cities = new ArrayList<City>();

        cityAdapter = new CityAdapter(this, cities);
        recyclerView.setAdapter(cityAdapter);

        CollectionReference citiesRef = db.collection("cities");

        citiesRef.orderBy("name")
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    progressBar.setVisibility(View.VISIBLE);
                    cities.clear(); // ----
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        progressBar.setVisibility(View.INVISIBLE);
                        City city = documentSnapshot.toObject(City.class);
                        cities.add(city);
                        cityAdapter.getFilter().filter("");
                        cityAdapter.notifyDataSetChanged();
                        Log.d("City", city.getName());
                    }
                }).addOnFailureListener(e -> Log.e("Firestore", "Error getting cities", e));


        searchView = binding.searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                cityAdapter.getFilter().filter(newText);
                return true;
            }
        });

    }

}
