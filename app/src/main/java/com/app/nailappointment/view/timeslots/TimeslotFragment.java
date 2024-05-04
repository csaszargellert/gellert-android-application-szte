package com.app.nailappointment.view.timeslots;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.nailappointment.R;
import com.app.nailappointment.db.DateEntity;
import com.app.nailappointment.db.Timeslot;
import com.app.nailappointment.utils.BundleArguments;
import com.app.nailappointment.utils.DateTimeHelper;
import com.app.nailappointment.viewmodel.AppointmentViewModel;
import com.app.nailappointment.viewmodel.AuthenticationViewModel;
import com.app.nailappointment.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class TimeslotFragment extends Fragment {

    private Button bookNowButton;

    private DateEntity selectedDate;

    private Button goBackButton;

    private TextView selectedDateTextView;

    private NavController navController;

    private AuthenticationViewModel authViewModel;

    private UserViewModel userViewModel;

    private AppointmentViewModel appointmentViewModel;

    private final ArrayList<Timeslot> arrayList = new ArrayList<>(
            Arrays.asList(
                    new Timeslot(8, 9, 0),
                    new Timeslot(9, 10, 1),
                    new Timeslot(10, 11, 2),
                    new Timeslot(11, 12, 3),
                    new Timeslot(12, 13, 4),
                    new Timeslot(13, 14, 5),
                    new Timeslot(14, 15, 6),
                    new Timeslot(15, 16, 7)
            )
    );

    private ArrayAdapter<Timeslot> adapter;

    public TimeslotFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.authViewModel = new ViewModelProvider(requireActivity())
                .get(AuthenticationViewModel.class);
        this.userViewModel = new ViewModelProvider(requireActivity())
                .get(UserViewModel.class);
        this.appointmentViewModel = new ViewModelProvider(requireActivity())
                .get(AppointmentViewModel.class);

        Bundle bundle = getArguments();
        selectedDate = (DateEntity) bundle.getSerializable(BundleArguments.SELECTED_DATE.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeslot, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        goBackButton = view.findViewById(R.id.goBackButton);
        bookNowButton = view.findViewById(R.id.bookNowButton);
        selectedDateTextView = view.findViewById(R.id.selectedDateTextView);

        ListView appointmentsListView = view.findViewById(R.id.appointmentsListView);
        adapter = new TimeslotAdapter(requireContext(), arrayList, selectedDate);
        adapter.notifyDataSetChanged();
        appointmentsListView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        excludeItems();
        goBackButton.setOnClickListener(this::onGoBack);
        bookNowButton.setOnClickListener(this::onBooking);
    }

    private void excludeItems() {
    }

    private void init() {
        String formattedDate = DateTimeHelper.formatDate(
                selectedDate.getYear(),
                selectedDate.getMonth(),
                selectedDate.getDayOfMonth()
        );
        selectedDateTextView.setText(formattedDate);
    }

    private void onGoBack(View view) {
        navController.popBackStack();
    }

    private void onBooking(View view) {
        if (!authViewModel.getIsUserLoggedIn().getValue()) {
            navController.navigate(R.id.loginFragment);
            return;
        }

        if (Objects.isNull(selectedDate.getTimeslotList()) || selectedDate.getTimeslotList().isEmpty()) {
            Toast.makeText(getContext(), "Please select a timeslot", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        selectedDate.setUserId(userViewModel.getUserId().getValue());
        appointmentViewModel.saveAppointment(selectedDate);
    }
}