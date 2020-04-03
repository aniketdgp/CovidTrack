package com.aniket.covidtrack;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


public class AppUpdateService extends Service {
    public AppUpdateService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        onTaskRemoved(intent);
        //Start Services To run in Background
        new AppUpdateService.getVersion().execute();
        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {


        super.onTaskRemoved(rootIntent);

    }


    // Title AsyncTask Refresh Data On Load In BackGround India
    private class getVersion extends AsyncTask<Void, Void, Void> {



        String url = "http://testaniket.dx.am/covidtrack.html";
        String version="1" ;
        String downloadlink = "";
        String updatefeatures = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                // Connect to the web site
                Document doc = Jsoup.connect(url).get();
                String title = doc.title();
                version = title;


                String dl = doc.select("[class=dl]").text();

                String arr[] = dl.split("##");

                downloadlink = arr[0];
                updatefeatures = arr[1];

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

          final AppVersionData avd = new AppVersionData(AppUpdateService.this);
          avd.setAppVersion(version);
          avd.setUpdateLink(downloadlink);
          avd.setUpdatefeatures(updatefeatures);

        }
    }



}