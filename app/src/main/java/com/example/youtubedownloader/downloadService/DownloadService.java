package com.example.youtubedownloader.downloadService;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.youtubedownloader.Constant;
import com.example.youtubedownloader.R;
import com.example.youtubedownloader.VideoRequest;
import com.example.youtubedownloader.api.ApiClient;
import com.example.youtubedownloader.api.ServiceGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = intent.getStringExtra(Constant.URL);
        doInBackground(url);
        return START_NOT_STICKY;
    }

    private void doInBackground(String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApiClient client = ServiceGenerator.createService(ApiClient.class);
                VideoRequest request = new VideoRequest(url);
                client.getVideoFromUrl(request)
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                if(response.isSuccessful()){
                                    Log.d("HHHHH","OK");
                                    saveFile(response.body());
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.d("HHHHH",t.getMessage());
                            }
                        });
            }
        }).start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public String saveFile(ResponseBody body) {
        String file = Environment.getExternalStorageDirectory().getPath()+ File.separator+getString(R.string.app_name);
        File dir = new File(file);

        if(!dir.exists()){
            dir.mkdir();
        }

        String fileName = "efg.mp4";
        File myFile = new File(file, fileName);


        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            byte[] fileReader = new byte[4096];

            long fileSize = body.contentLength();
            long fileSizeDownloaded = 0;

            inputStream = body.byteStream();
            outputStream = new FileOutputStream(myFile);

            while (true) {
                int read = inputStream.read(fileReader);

                if (read == -1) {
                    break;
                }

                outputStream.write(fileReader, 0, read);

                fileSizeDownloaded += read;
            }

            outputStream.flush();
            return myFile.getPath();

        } catch (IOException e) {
            Log.d("ERROR",e.getMessage());
            return null;
        } finally {
            Log.d("HHHH","Done");
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
