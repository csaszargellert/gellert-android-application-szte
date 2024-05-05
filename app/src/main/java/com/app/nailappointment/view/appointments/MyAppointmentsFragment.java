//package com.app.nailappointment.view.appointments;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.app.nailappointment.R;
//import com.app.nailappointment.db.DateEntity;
//import com.app.nailappointment.utils.viewmodel.AppointmentViewModel;
//import com.app.nailappointment.utils.viewmodel.UserViewModel;
//import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
//import com.firebase.ui.firestore.FirestoreRecyclerOptions;
//
//public class MyAppointmentsFragment extends Fragment {
//
//    private UserViewModel userViewModel;
//
//    private AppointmentViewModel appointmentViewModel;
//
//    private FirestoreRecyclerAdapter adapter;
//
//    private RecyclerView appointmentRecyclerView;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        this.userViewModel = new ViewModelProvider(requireActivity())
//                .get(UserViewModel.class);
//        this.appointmentViewModel = new ViewModelProvider(requireActivity())
//                .get(AppointmentViewModel.class);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_my_appointments, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        this.appointmentRecyclerView = view.findViewById(R.id.dateRecyclerView);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
//                LinearLayoutManager.VERTICAL,
//                false);
//        appointmentRecyclerView.setLayoutManager(layoutManager);
//
//        FirestoreRecyclerOptions<DateEntity> options = appointmentViewModel
//                .findUserAppointments(userViewModel.getUserId().getValue());
//
//        adapter = new MyAppointmentsAdapter(options, appointmentViewModel);
//
//        adapter.notifyDataSetChanged();
//        appointmentRecyclerView.setAdapter(adapter);
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
//}