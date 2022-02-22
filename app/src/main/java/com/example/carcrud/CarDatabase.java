package com.example.carcrud;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CarDatabase {

    public static final String CARS_REFERENCE_PATH = "cars";
    public static final String CAR_NAME_PATH = "name";
    private final DatabaseReference databaseReference;

    public CarDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference(CARS_REFERENCE_PATH);
    }

    void remove(Car car) {
        databaseReference.child(car.getId()).removeValue();
    }

    boolean create(String name) {
        String id = databaseReference.push().getKey();
        if (id != null) {
            databaseReference.child(id)
                    .setValue(new Car(id, name));
            return true;
        } else {
            return false;
        }
    }

    public void addValueEventListener(ValueEventListener valueEventListener) {
        databaseReference.addValueEventListener(valueEventListener);
    }

    public void update(String carId, String name) {
        databaseReference.child(carId).child(CAR_NAME_PATH).setValue(name);
    }
}
