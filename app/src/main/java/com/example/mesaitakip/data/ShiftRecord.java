package com.example.mesaitakip.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shift_records")
public class ShiftRecord {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String date;       // Tarih (Örn: 2026-08-31)
    public String shiftType;  // Vardiya türü (Gündüz, Gece, vb.)
    public double workingHours; // Çalışma saati
    public double overtimeHours; // Fazla mesai saati
    public String notes;      // Ek açıklamalar
}
