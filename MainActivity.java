package com.example.temperatureconverter;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etTemperature;
    private Spinner spFromUnit, spToUnit;
    private Button btnConvert;
    private TextView tvResult, tvError;

    private final String[] units = {"Celsius", "Fahrenheit", "Kelvin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTemperature = findViewById(R.id.etTemperature);
        spFromUnit = findViewById(R.id.spFromUnit);
        spToUnit = findViewById(R.id.spToUnit);
        btnConvert = findViewById(R.id.btnConvert);
        tvResult = findViewById(R.id.tvResult);
        tvError = findViewById(R.id.tvError);

        setupSpinners();

        btnConvert.setOnClickListener(v -> convert());
    }

    private void setupSpinners() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                units
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spFromUnit.setAdapter(adapter);
        spToUnit.setAdapter(adapter);
    }

    private void convert() {
        tvError.setText("");
        tvResult.setText("Result will appear here");

        String input = etTemperature.getText().toString().trim();

        if (input.isEmpty()) {
            tvError.setText("Please enter a temperature value.");
            return;
        }

        double value;
        try {
            value = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            tvError.setText("Please enter a valid number.");
            return;
        }

        String fromUnit = spFromUnit.getSelectedItem().toString();
        String toUnit = spToUnit.getSelectedItem().toString();

        double result = convertTemperature(value, fromUnit, toUnit);

        tvResult.setText(String.format("%.2f %s", result, toUnit));
    }

    private double convertTemperature(double value, String from, String to) {
        if (from.equals(to)) {
            return value;
        }

        double celsiusValue;

        switch (from) {
            case "Celsius":
                celsiusValue = value;
                break;
            case "Fahrenheit":
                celsiusValue = (value - 32) * 5 / 9;
                break;
            case "Kelvin":
                celsiusValue = value - 273.15;
                break;
            default:
                celsiusValue = value;
        }

        switch (to) {
            case "Celsius":
                return celsiusValue;
            case "Fahrenheit":
                return celsiusValue * 9 / 5 + 32;
            case "Kelvin":
                return celsiusValue + 273.15;
            default:
                return celsiusValue;
        }
    }
}