package com.app.nailappointment;

import android.Manifest;
import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.app.nailappointment.firebase.viewmodel.FirebaseAuthenticationViewmodel;
import com.app.nailappointment.utils.NavBarActivities;
import com.app.nailappointment.utils.NavigationHelper;
import com.app.nailappointment.view.DateActivity;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView welcomeTextView;

    Animation animation;

    Button bounceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        bounceButton = findViewById(R.id.bounceButton);

        animation = AnimationUtils.loadAnimation(this, R.anim.bounce);

        FirebaseAuthenticationViewmodel fireAuthViewmodel = new ViewModelProvider(this)
                .get(FirebaseAuthenticationViewmodel.class);

        (new NavigationHelper(this, fireAuthViewmodel, NavBarActivities.MAIN))
                .setupNavigationListener();
        makeNotification();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bounceButton.setOnClickListener(view -> welcomeTextView.startAnimation(animation));
    }

    public void makeNotification() {
        String channelId = "CHANNEL_ID";

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), channelId);

        builder.setSmallIcon(R.drawable.notifications)
                .setContentTitle("Nail Appointment Application")
                .setContentText("Welcome to my nail appointment application")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(getApplicationContext(), DateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0,
                intent,
                PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelId);

        if (notificationChannel == null) {
            int importance = NotificationManager.IMPORTANCE_HIGH;

            notificationChannel = new NotificationChannel(channelId, "Some string", importance);
            notificationChannel.enableVibration(true);

            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(0, builder.build());
    }
}