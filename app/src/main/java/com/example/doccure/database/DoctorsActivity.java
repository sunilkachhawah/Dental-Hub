package com.example.doccure.database;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.doccure.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DoctorsActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference doctorRef = db.collection("Doctors");

    private DoctorsNameAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctors_layout);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query = doctorRef.orderBy("priority", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Note> options =  new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query,Note.class)
                .build();


        adapter=new DoctorsNameAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.doctor_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
    adapter.stopListening();
    }
}