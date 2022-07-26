package com.example.vroomandroid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class homeActivity extends AppCompatActivity {

    public  class AlarmReceiver extends BroadcastReceiver{
        private AlarmManager alarmMgr;
        private PendingIntent alarmIntent;
        @Override
        public void onReceive(Context context, Intent intent) {
            //showNotification(context);


            alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(context, MainActivity.class);
            alarmIntent = PendingIntent.getBroadcast(context, 0, i, 0);

            // Set the alarm to start at 8:30 a.m.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 10);
            calendar.set(Calendar.MINUTE, 03);

            // setRepeating() lets you specify a precise custom interval--in this case,
            // 20 minutes.
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    1000 * 60 * 20, alarmIntent);

        }

        public void showNotification(Context context) {
           Intent i = new Intent(homeActivity.this, MainActivity.class);
           PendingIntent pendingIntent = PendingIntent.getBroadcast(homeActivity.this, 0, i, 0);
            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            long updateInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
            am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + updateInterval, updateInterval, pendingIntent);
        }}


    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Enviar(){
        //String NOTIFICACION_ID="message";
        String texto = "VRoom te saludo";
        //Toast.makeText(this,texto, Toast.LENGTH_LONG).show();
        //NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //NotificationCompat.Builder builder = new NotificationCompat.Builder(this,NOTIFICACION_ID);

        //builder.setAutoCancel(true)
        //.setWhen(System.currentTimeMillis())
        //.setSmallIcon(R.drawable.ic_launcher_background)
        //.setTicker("Notificacion Nueva")
        //.setContentTitle("Nuevo contenido")
        //.setContentText("Agregamos nuevo video")
        //.setContentInfo("Nuevo");
        //Random random = new Random();
        //int idNotify = random.nextInt(8000);
        //assert notificationManager != null;
        //notificationManager.notify(idNotify,builder.build());
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("1", "BIEN", importance);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, homeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("OUTFIT TO DAY")
                .setContentText(texto)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());

    }



    public void  salir(View view){
        mAuth.signOut();
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void tuRopa(View view){
        Intent i = new Intent(this,tuRopaActivity.class);
        startActivity(i);
        Enviar();

    }
    public void tuOutfit(View view){
        Intent i = new Intent(this,tuOutfitActivity.class);
        startActivity(i);
    }
    public void agregarRopa(View view){
        Intent i = new Intent(this,agregarRopaActivity2.class);
        startActivity(i);
    }
}