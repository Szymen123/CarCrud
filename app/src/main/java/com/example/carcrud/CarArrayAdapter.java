package com.example.carcrud;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CarArrayAdapter extends ArrayAdapter<Car> {

    private final Activity context;
    private final List<Car> cars;
    private final CarDatabase carDatabase;

    public CarArrayAdapter(@NonNull Activity context, List<Car> cars, CarDatabase carDatabase) {
        super(context, R.layout.activity_car_list, cars);
        this.context = context;
        this.cars = cars;
        this.carDatabase = carDatabase;
    }

    public View getView(int pos, View view, ViewGroup parent) {
        View listViewItem = getListView();

        Car car = cars.get(pos);

        TextView carName = listViewItem.findViewById(R.id.carName);
        carName.setText(car.getName());

        listViewItem.findViewById(R.id.buttonDelete)
                .setOnClickListener(v -> carDatabase.remove(car));

        listViewItem.findViewById(R.id.buttonUpdate)
                .setOnClickListener(v -> startCarUpdateActivity(car));

        return listViewItem;
    }

    private void startCarUpdateActivity(Car car) {
        String carId = car.getId();
        Intent intent = new Intent(context, CarUpdateActivity.class);
        intent.putExtra("key", carId);
        context.startActivity(intent);
    }

    private View getListView() {
        LayoutInflater inflater = context.getLayoutInflater();
        return inflater.inflate(R.layout.activity_car_list, null, true);
    }
}



