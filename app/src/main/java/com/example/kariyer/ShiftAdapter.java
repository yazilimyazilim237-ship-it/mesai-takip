package com.example.kariyer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kariyer.data.ShiftRecord;

import java.util.ArrayList;
import java.util.List;

public class ShiftAdapter extends RecyclerView.Adapter<ShiftAdapter.ShiftViewHolder> {

    private List<ShiftRecord> shifts = new ArrayList<>();

    @NonNull
    @Override
    public ShiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shift, parent, false);
        return new ShiftViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShiftViewHolder holder, int position) {
        ShiftRecord currentShift = shifts.get(position);
        holder.tvDate.setText("Tarih: " + currentShift.date);
        holder.tvShiftType.setText("Vardiya: " + currentShift.shiftType);
        holder.tvHours.setText("Çalışma: " + currentShift.workingHours + "s | Fazla Mesai: " + currentShift.overtimeHours + "s");
        
        if (currentShift.notes != null && !currentShift.notes.isEmpty()) {
            holder.tvNotes.setText("Not: " + currentShift.notes);
            holder.tvNotes.setVisibility(View.VISIBLE);
        } else {
            holder.tvNotes.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return shifts.size();
    }

    public void setShifts(List<ShiftRecord> shifts) {
        this.shifts = shifts;
        notifyDataSetChanged();
    }

    static class ShiftViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDate, tvShiftType, tvHours, tvNotes;

        public ShiftViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvShiftType = itemView.findViewById(R.id.tvShiftType);
            tvHours = itemView.findViewById(R.id.tvHours);
            tvNotes = itemView.findViewById(R.id.tvNotes);
        }
    }
}
