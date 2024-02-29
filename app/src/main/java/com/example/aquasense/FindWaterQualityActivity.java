package com.example.aquasense;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aquasense.databinding.ActivityFindWaterQualityBinding;

public class FindWaterQualityActivity extends AppCompatActivity {
    private ActivityFindWaterQualityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFindWaterQualityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(v -> onBackPressed());

        // Set onClickListener for the calculateButton
        binding.calculateButton.setOnClickListener(v -> calculateWaterQuality());
    }

    private void calculateWaterQuality() {
        // Retrieve input values from TextInputEditText fields
        String pHString = binding.pHInputEditText.getText().toString();
        String dissolvedOxygenString = binding.dissolvedOxygenInputEditText.getText().toString();
        String temperatureString = binding.temperatureInputEditText.getText().toString();
        String turbidityString = binding.turbidityInputEditText.getText().toString();
        String chloridesString = binding.chloridesInputEditText.getText().toString();

        // Check if any of the input fields are empty
        if (TextUtils.isEmpty(pHString)
                || TextUtils.isEmpty(dissolvedOxygenString)
                || TextUtils.isEmpty(temperatureString)
                || TextUtils.isEmpty(turbidityString)
                || TextUtils.isEmpty(chloridesString)) {
            Toast.makeText(this, "Please enter all values", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Convert input values to double
            double pH = Double.parseDouble(pHString);
            double dissolvedOxygen = Double.parseDouble(dissolvedOxygenString);
            double temperature = Double.parseDouble(temperatureString);
            double turbidity = Double.parseDouble(turbidityString);
            double chlorides = Double.parseDouble(chloridesString);

            // Calculate water quality score
            int waterQualityScore = WaterQualityCalculator.calculateWaterQuality(
                    pH, dissolvedOxygen, temperature, turbidity, chlorides);

            // Display the water quality score and status
            binding.waterQualityTextView.setText("Water Quality: " + waterQualityScore);
            binding.waterQualityStatusTextView.setText(getWaterQualityStatus(waterQualityScore));
        } catch (NumberFormatException e) {
            // Handle parsing errors
            e.printStackTrace(); // Log the error for debugging
            Toast.makeText(this, "Error parsing input values", Toast.LENGTH_SHORT).show();
        }
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