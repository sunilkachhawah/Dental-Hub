package com.example.doccure.database;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.doccure.Home_NavigationActivity;
import com.example.doccure.LearnmoreActivity;
import com.example.doccure.R;
import com.example.doccure.database.MedicineAdapter;
import com.example.doccure.database.NoteMedicine;
import com.example.doccure.slotbook.appointmentBooking;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MedicineActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference medicineRef = db.collection("Medicine");

    private MedicineAdapter adapter;
    private Button book1meds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_layout_user);
        setUpRecyclerView();
        book1meds = (Button) findViewById(R.id.book1meds);
        book1meds.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent browserintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.netmeds.com"));
                startActivity(browserintent);
                finish();

            }
        });

        }

    private void setUpRecyclerView() {
        Query query = medicineRef.orderBy("name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<NoteMedicine> options = new FirestoreRecyclerOptions.Builder<NoteMedicine>()
                .setQuery(query, NoteMedicine.class)
                .build();


        adapter = new MedicineAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.medicine_recycler_view);
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