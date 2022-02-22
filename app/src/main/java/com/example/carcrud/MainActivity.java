package com.example.carcrud;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button buttonSave;
    EditText editCarName;
    CarDatabase carDatabase;
    ListView listViewCars;
    List<Car> cars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initVariables();

        buttonSave.setOnClickListener(getAddCarClickListener());
    }

    @NonNull
    private View.OnClickListener getAddCarClickListener() {
        return view -> {
            String name = editCarName.getText().toString();
            if (name.isEmpty()) {
                showError();
                return;
            }

            if (carDatabase.create(name)) {
                showToast("Car Created Successfully!");
            } else {
                showToast("Car Create Failure!");
            }
        };
    }

    private void showToast(String value) {
        Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
    }

    private void showError() {
        editCarName.setError("This field is required");
        editCarName.requestFocus();
    }

    private void initVariables() {
        cars = new ArrayList<>();
        carDatabase = new CarDatabase();
        buttonSave = findViewById(R.id.buttonSave);
        editCarName = findViewById(R.id.editCarName);
        listViewCars = findViewById(R.id.listViewCars);
    }

    @Override
    protected void onStart() {
        super.onStart();
        carDatabase.addValueEventListener(getValueEventListener());
    }

    @NonNull
    private ValueEventListener getValueEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fillCarData(dataSnapshot);
                listViewCars.setAdapter(
                        new CarArrayAdapter(
                                MainActivity.this,
                                cars,
                                carDatabase));
            }

            private void fillCarData(@NonNull DataSnapshot dataSnapshot) {
                cars.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Car car = postSnapshot.getValue(Car.class);
                    cars.add(car);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }
}