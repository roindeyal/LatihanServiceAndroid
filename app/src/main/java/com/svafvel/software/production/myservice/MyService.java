package com.svafvel.software.production.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.lang.ref.WeakReference;

// Langkah 2 : Implement Interface dan Implement Method preASync dan postAsync
public class MyService extends Service implements DummyAsyncCallback{

    public static final String TAG = "MyService";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(TAG, "Service dijalankan....");

        // Langkah 4 : inisialisasi dan jalankan ASyncTask
        DummyAsync dummyAsync = new DummyAsync(this);
        dummyAsync.execute();

        return START_STICKY;
    }

    //Langkah 5 : Tamabahkan Aksi dicallback
    @Override
    public void preASync() {

        Log.d(TAG, "preAsync: Mulai...");

    }

    @Override
    public void postAsync() {

        Log.d(TAG, "postAsync: Selesai...");
        stopSelf();

    }

    //Langkah 3 : buat AsyncTask dan WeakReference
    private static class DummyAsync extends AsyncTask<Void, Void, Void>{

        private final WeakReference<DummyAsyncCallback> callback;

        DummyAsync(DummyAsyncCallback callback) {
            this.callback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            Log.d(TAG, "onPreExecute");
            callback.get().preASync();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackgroup: ");
            try {
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            Log.d(TAG, "onPostExecute");
            callback.get().postAsync();
        }
    }
}

//Langkah 1 : buat Interface
interface DummyAsyncCallback{
    void preASync();
    void postAsync();
}