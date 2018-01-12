package com.example.wahib.asynctask;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

public class MyService extends Service {
    public static boolean isRunning = false;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int limit = intent.getIntExtra("limit",0);
        Async task = new Async();
        task.execute(limit,startId);
        Toast.makeText(this, "MyService Started.", Toast.LENGTH_SHORT).show();
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        Toast.makeText(this, "MyService Completed or Stopped.", Toast.LENGTH_SHORT).show();
    }

    class Async extends AsyncTask<Integer,Void,Void>{

        @Override
        protected Void doInBackground(Integer... voids) {


            for (int i = 0; i <= voids[0]; i++) {

                int percentage = (i * 100) / voids[0];

                try {
                    Thread.sleep(1000);
                    Event event = new Event(percentage);
                    EventBus.getDefault().post(event);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!isRunning){
                    break;
                }

            }

            stopSelfResult(voids[1]);
            return null;
        }
    }


}