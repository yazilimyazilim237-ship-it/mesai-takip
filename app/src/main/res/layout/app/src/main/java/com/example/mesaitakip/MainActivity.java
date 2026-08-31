package com.example.kariyer;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kariyer.data.ShiftRecord;
import com.example.kariyer.viewmodel.ShiftViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ShiftViewModel shiftViewModel;
    private EditText etDate, etShiftType, etWorkingHours, etOvertimeHours, etNotes;
    private Button btnSave, btnExportPdf;
    private TextView tvTotalWorking, tvTotalOvertime;
    private RecyclerView recyclerViewShifts;
    private ShiftAdapter shiftAdapter;
    private List<ShiftRecord> currentShiftList;

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
        btnExportPdf = findViewById(R.id.btnExportPdf);
        tvTotalWorking = findViewById(R.id.tvTotalWorking);
        tvTotalOvertime = findViewById(R.id.tvTotalOvertime);
        recyclerViewShifts = findViewById(R.id.recyclerViewShifts);

        recyclerViewShifts.setLayoutManager(new LinearLayoutManager(this));
        shiftAdapter = new ShiftAdapter();
        recyclerViewShifts.setAdapter(shiftAdapter);

        shiftViewModel = new ViewModelProvider(this).get(ShiftViewModel.class);

        shiftViewModel.getAllShifts().observe(this, shiftRecords -> {
            currentShiftList = shiftRecords;
            shiftAdapter.setShifts(shiftRecords);
            calculateTotals(shiftRecords);
        });

        btnSave.setOnClickListener(v -> saveShift());
        btnExportPdf.setOnClickListener(v -> generatePdfReport());
    }

    private void calculateTotals(List<ShiftRecord> records) {
        double totalWork = 0;
        double totalOver = 0;
        for (ShiftRecord record : records) {
            totalWork += record.workingHours;
            totalOver += record.overtimeHours;
        }
        tvTotalWorking.setText("Toplam Çalışma: " + totalWork + "s");
        tvTotalOvertime.setText("Toplam Fazla Mesai: " + totalOver + "s");
    }

    private void saveShift() {
        String date = etDate.getText().toString().trim();
        String shiftType = etShiftType.getText().toString().trim();
        String workingHoursStr = etWorkingHours.getText().toString().trim();
        String overtimeHoursStr = etOvertimeHours.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();

        if (date.isEmpty() || shiftType.isEmpty() || workingHoursStr.isEmpty()) {
            Toast.makeText(this, "Tarih, vardiya ve çalışma saati zorunludur!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double workingHours = Double.parseDouble(workingHoursStr);
            double overtimeHours = overtimeHoursStr.isEmpty() ? 0.0 : Double.parseDouble(overtimeHoursStr);

            ShiftRecord newShift = new ShiftRecord(date, shiftType, work…
