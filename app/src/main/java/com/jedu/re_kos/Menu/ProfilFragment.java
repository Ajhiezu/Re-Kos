package com.jedu.re_kos.Menu;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Calendar;
import com.jedu.re_kos.R;
import com.jedu.re_kos.Register_Login.LoginActivity;
import com.jedu.re_kos.Register_Login.RegisterasiActivity;

public class ProfilFragment extends Fragment {

    private EditText birthdateEditText;
    private Button lanjut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        lanjut = view.findViewById(R.id.lanjut);
        lanjut.setOnClickListener(view1 -> {
            Intent intent = new Intent(requireActivity(), profil2.class);
            startActivity(intent);
        });

        // Setup Spinner for gender selection
        Spinner genderSpinner = view.findViewById(R.id.spinner_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.gender_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        // Setup DatePickerDialog for birthdate selection
        birthdateEditText = view.findViewById(R.id.editText_birthdate);
        birthdateEditText.setOnClickListener(v -> showDatePickerDialog());

        return view;
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    birthdateEditText.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }
}
