package com.jedu.re_kos.Menu;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jedu.re_kos.Kos_Saya.KosSaya2Activity;
import com.jedu.re_kos.R;

public class KosSayaFragment extends Fragment {

    private Button buttonSelesai, lanjutkan;

    public KosSayaFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kos_saya, container, false);

        // Inisialisasi tombol
        lanjutkan = view.findViewById(R.id.button_selesai);

        // Set onClickListener untuk tombol lanjutkan
        lanjutkan.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), KosSaya2Activity.class);
            startActivity(intent);
        });


        return view;
    }
}
