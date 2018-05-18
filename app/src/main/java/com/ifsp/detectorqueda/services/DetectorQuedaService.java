package com.ifsp.detectorqueda.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.ifsp.detectorqueda.business.DetectorQueda;

public class DetectorQuedaService extends Service {
    private DetectorQueda detector;

    @Override
    public void onCreate(){
        this.detector = new DetectorQueda(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(this.detector.iniciar()){
            Toast.makeText(this, "Serviço \"Detecção de Queda\" iniciado!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Erro ao iniciar serviço!", Toast.LENGTH_SHORT).show();
        }

        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if(this.detector.pausar()){
            Toast.makeText(this, "Serviço \"Detecção de Queda\" parado!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Erro ao parar serviço!", Toast.LENGTH_SHORT).show();
        }
    }

}

