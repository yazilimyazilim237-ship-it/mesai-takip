package com.example.kariyer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kariyer.data.ShiftRecord;
import com.example.kariyer.viewmodel.ShiftViewModel;

public class MainActivity extends AppCompatActivity {

    private ShiftViewModel shiftViewModel;
    private EditText etDate, etShiftType, etWorkingHours, etOvertimeHours, etNotes;
    private Button btnSave;
    private RecyclerView recyclerViewShifts;
    private ShiftAdapter shiftAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI bileşenlerini bağlıyoruz
        etDate = findViewById(R.id.etDate);
        etShiftType = findViewById(R.id.etShiftType);
        etWorkingHours = findViewById(R.id.etWorkingHours);
        etOvertimeHours = findViewById(R.id.etOvertimeHours);
        etNotes = findViewById(R.id.etNotes);
        btnSave = findViewById(R.id.btnSave);
        recyclerViewShifts = findViewById(R.id.recyclerViewShifts);

        // RecyclerView ve Adapter ayarları
        recyclerViewShifts.setLayoutManager(new LinearLayoutManager(this));
        shiftAdapter = new ShiftAdapter();
        recyclerViewShifts.setAdapter(shiftAdapter);

        // ViewModel bağlantısı
        shiftViewModel = new ViewModelProvider(this).get(ShiftViewModel.class);

        // Veritabanındaki değişiklikleri canlı olarak gözlemliyoruz
        shiftViewModel.getAllShifts().observe(this, shiftRecords -> {
            shiftAdapter.setShifts(shiftRecords);
        });

        // Kaydet butonuna basıldığında çalışacak kodlar
        btnSave.setOnClickListener(v -> saveShift());
    }

    private void saveShift() {
        String date = etDate.getText().toString().trim();
        String shiftType = etShiftType.getText().toString().trim();
        String workingHoursStr = etWorkingHours.getText().toString().trim();
        String overtimeHoursStr = etOvertimeHours.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();

        if (date.isEmpty() || shiftType.isEmpty() || workingHoursStr.isEmpty()) {
            Toast.makeText(this, "Lütfen tarih, vardiya ve çalışma saati alanlarını doldurun!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double workingHours = Double.parseDouble(workingHoursStr);
            double overtimeHours = overtimeHoursStr.isEmpty() ? 0.0 : Double.parseDouble(overtimeHoursStr);

            ShiftRecord newShift = new ShiftRecord(date, shiftType, workingHours, overtimeHours, notes);
            shiftViewModel.insert(newShift);

            // Alanları temizle
            etDate.setText("");
            etShiftType.setText("");
            etWorkingHours.setText("");
            etOvertimeHours.setText("");
            etNotes.setText("");

            Toast.makeText(this, "Mesai başarıyla kaydedildi!", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Lütfen saat alanlarına geçerli bir sayı girin!", Toast.LENGTH_SHORT).show();
        }
    }
}
