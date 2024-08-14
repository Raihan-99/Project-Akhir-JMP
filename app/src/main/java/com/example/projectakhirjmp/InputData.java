package com.example.projectakhirjmp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputData extends AppCompatActivity {

    Button btnInput;
    EditText nama1, umur, motto;
    DatabaseHelper dbmaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_input_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnInput = findViewById(R.id.btnInput);
        nama1 = findViewById(R.id.nama1);
        umur = findViewById(R.id.umur);
        motto = findViewById(R.id.motto);
        dbmaster = new DatabaseHelper(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");

        if (id != null) {
            Cursor data = dbmaster.getAllData();
            if (data.moveToFirst()) {
                do {
                    if (data.getString(0).equals(id)) {
                        nama1.setText(data.getString(1));
                        umur.setText(data.getString(2));
                        motto.setText(data.getString(3));
                        btnInput.setText("Update Data");
                        break;
                    }
                } while (data.moveToNext());
            }

            btnInput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isUpdated = dbmaster.updateData(id, nama1.getText().toString(), Integer.parseInt(umur.getText().toString()),motto.getText().toString());
                    if (isUpdated)
                        Toast.makeText(InputData.this, "Data Updated", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(InputData.this, "Data Not Updated", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            btnInput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isInserted = dbmaster.insertData(nama1.getText().toString(), Integer.parseInt(umur.getText().toString()),motto.getText().toString());
                    if (isInserted)
                        Toast.makeText(InputData.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(InputData.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}