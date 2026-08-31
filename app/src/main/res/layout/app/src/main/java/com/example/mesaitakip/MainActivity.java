package com.example.mesaitakip;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mesaitakip.data.ShiftRecord;
import com.example.mesaitakip.data.ShiftViewModel;

public class MainActivity extends AppCompatActivity {

    private ShiftViewModel shiftViewModel;
    private EditText etDate, etShiftType, etWorkingHours, etOvertimeHours, etNotes;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDate = findViewById(R.id.etDate);
        etShiftType = findViewById(R.id.etShiftType);
        etWorkingHours = findViewById(R.id.etWorkingHours);
        etOvertimeHours = findViewById(R.id.etOvertimeHours);
        etNotes = findViewById(R.id.etNotes);
        btnSave = findViewById(R.id.btnSave);

        shiftViewModel = new ViewModelProvider(this).get(ShiftViewModel.class);

        btnSave.setOnClickListener(v -> saveShift());
    }

    private void saveShift() {
        String date = etDate.getText().toString().trim();
        String shiftType = etShiftType.getText().toString().trim();
        String workingStr = etWorkingHours.getText().toString().trim();
        String overtimeStr = etOvertimeHours.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();

        if (date.isEmpty() || workingStr.isEmpty()) {
            Toast.makeText(this, "Lütfen tarih ve çalışma saatini girin!", Toast.LENGTH_SHORT).show();
            return;
        }

        ShiftRecord record = new ShiftRecord();
        record.date = date;
        record.shiftType = shiftType;
        record.workingHours = Double.parseDouble(workingStr);
        record.overtimeHours = overtimeStr.isEmpty() ? 0.0 : Double.parseDouble(overtimeStr);
        record.notes = notes;

        shiftViewModel.insert(record);

        Toast.makeText(this, "Vardiya başarıyla kaydedildi!", Toast.LENGTH_SHORT).show();
        
        // Alanları temizle
        etDate.setText("");
        etShiftType.setText("");
        etWorkingHours.setText("");
        etOvertimeHours.setText("");
        etNotes.setText("");
    }
}
