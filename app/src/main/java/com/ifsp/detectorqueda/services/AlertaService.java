package com.ifsp.detectorqueda.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.Nullable;

import com.ifsp.detectorqueda.R;

public class AlertaService extends Service {
    private Vibrator vibrator;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        this.mediaPlayer = MediaPlayer.create(this, R.raw.alert);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        iniciarVibracao();
        iniciarAlertaSonoro();
        return Service.START_STICKY;
    }

    /**
     *  Inicia vibração do Smartphone.
     *
     * @author      Denis Magno
     */
    public void iniciarVibracao(){
        long[] timings = {0, 1000, 100};

        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(timings, 0));
        }else{
            //deprecated in API 26
            vibrator.vibrate(timings, 0);
        }
    }

    /**
     *  Inicia alerta sonoro
     *
     * @author      Denis Magno
     */
    public void iniciarAlertaSonoro(){
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onDestroy() {
        this.vibrator.cancel();
        this.mediaPlayer.stop();
    }
}
