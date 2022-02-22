package com.example.carcrud;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CarUpdateActivity extends AppCompatActivity {

    public static final String CAR_INTENT_KEY = "key";
    public static final String CAR_UPDATED_SUCCESSFULLY_TEXT = "Car Updated Successfully!";
    private EditText editCarName;
    private CarDatabase carDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_update);

        setDisplayHome();

        carDatabase = new CarDatabase();

        editCarName = findViewById(R.id.editUpdateCar);

        findViewById(R.id.buttonUpdateConfirm)
                .setOnClickListener(v -> {
                    updateCarInDb();
                    showToast();
                    editCarName.setText(null);
                    startMainActivity();
                });
    }

    private void showToast() {
        Toast.makeText(
                CarUpdateActivity.this,
                CAR_UPDATED_SUCCESSFULLY_TEXT,
                Toast.LENGTH_SHORT)
                .show();
    }

    private void updateCarInDb() {
        String name = editCarName.getText().toString();
        String carId = getIntent().getStringExtra(CAR_INTENT_KEY);
        carDatabase.update(carId, name);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    private void setDisplayHome() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

}
