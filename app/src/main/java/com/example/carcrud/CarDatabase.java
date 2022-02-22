package com.example.carcrud;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CarDatabase {

    private final DatabaseReference databaseReference;

    public CarDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("cars");
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
        databaseReference.child(carId).child("name").setValue(name);
    }
}
