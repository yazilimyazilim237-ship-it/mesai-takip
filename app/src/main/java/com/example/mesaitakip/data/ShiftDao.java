package com.example.mesaitakip.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ShiftDao {

    @Insert
    void insert(ShiftRecord shiftRecord);

    @Query("SELECT * FROM shift_records ORDER BY id DESC")
    List<ShiftRecord> getAllShifts();
}
