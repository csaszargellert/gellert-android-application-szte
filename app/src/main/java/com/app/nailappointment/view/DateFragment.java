package com.app.nailappointment.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.app.nailappointment.R;
import com.app.nailappointment.db.DateEntity;
import com.app.nailappointment.utils.BundleArguments;

public class DateFragment extends Fragment {

    private CalendarView calendarView;

    private DateEntity selectedDate;

    private NavController navigation;

    public DateFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarView = view.findViewById(R.id.calendarView);
        navigation = Navigation.findNavController(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        selectedDate = null;
        calendarView.setOnDateChangeListener(this::onDateSelected);
    }

    private void onDateSelected(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        selectedDate = new DateEntity(
                year,
                month + 1,
                dayOfMonth
        );

        Bundle args = new Bundle();
        args.putSerializable(BundleArguments.SELECTED_DATE.toString(), selectedDate);

        navigation.navigate(R.id.timeslotFragment, args);
    }
}