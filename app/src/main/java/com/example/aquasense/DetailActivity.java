package com.example.aquasense;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.aquasense.databinding.ActivityDetailBinding;
import com.example.aquasense.model.CustomCity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private FirebaseFirestore db;
    private CustomCity customCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(v -> onBackPressed());
        db = FirebaseFirestore.getInstance();

        String cityId = getIntent().getStringExtra("city_id");

        db.collection("cities").document(cityId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Convert Firestore data to CustomCity object
                            customCity = document.toObject(CustomCity.class);
                            String pHString = document.getString("pH");
                            String dissolvedOxygenString = document.getString("dissolvedOxygen");
                            String temperatureString = document.getString("temperature");
                            String turbidityString = document.getString("turbidity");
                            String chloridesString = document.getString("chlorides");

                            if (pHString != null && dissolvedOxygenString != null && temperatureString != null
                                    && turbidityString != null && chloridesString != null) {
                                try {
                                    double pH = Double.parseDouble(pHString);
                                    double dissolvedOxygen = Double.parseDouble(dissolvedOxygenString);
                                    double temperature = Double.parseDouble(temperatureString);
                                    double turbidity = Double.parseDouble(turbidityString);
                                    double chlorides = Double.parseDouble(chloridesString);

                                    int waterQualityScore = WaterQualityCalculator.calculateWaterQuality(
                                            pH, dissolvedOxygen, temperature, turbidity, chlorides);

                                    binding.waterQualityOutOf100.setText("Water Quality: " + waterQualityScore);

                                    String waterQualityStatus = getWaterQualityStatus(waterQualityScore);

                                    // Set water quality status
                                    binding.waterQualityStatus.setText("Water Quality Status: " + waterQualityStatus);

                                } catch (NumberFormatException e) {
                                    // Handle parsing errors
                                    e.printStackTrace(); // Log the error for debugging
                                    Toast.makeText(getApplicationContext(), "Error parsing numeric values", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Handle case where one or more values are null
                                // For example, show a toast message
                                Toast.makeText(getApplicationContext(), "Values not Available in Database", Toast.LENGTH_SHORT).show();
                            }

                            showCityDetails();

                        } else {
                            // Show toast message if the document does not exist
                            showToast("Document does not exist");
                        }
                    } else {
                        // Show toast message for errors
                        showToast("Error fetching document: " + task.getException().getMessage());
                    }
                });

    }
    private void showCityDetails() {
        binding.cityName.setText(customCity.getName());
        Glide.with(this)
                .load(customCity.getImageResource())
                .apply(new RequestOptions().centerCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.cityImage);
        binding.pHValue.setText("pH: " + customCity.getpH());
        binding.dissolvedOxygen.setText("Dissolved Oxygen: " + customCity.getDissolvedOxygen());
        binding.temperature.setText("Temperature: " + customCity.getTemperature());
        binding.turbidity.setText("Turbidity: " + customCity.getTurbidity());
        binding.chlorides.setText("Chlorides: " + customCity.getChlorides());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private String getWaterQualityStatus(int score) {
        if (score >= 80) {
            return "Excellent";
        } else if (score >= 60) {
            return "Good";
        } else if (score >= 40) {
            return "Fair";
        } else if (score >= 20) {
            return "Poor";
        } else {
            return "Very Poor";
        }
    }

}