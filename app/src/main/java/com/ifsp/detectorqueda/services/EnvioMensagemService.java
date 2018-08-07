package com.ifsp.detectorqueda.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;

public class EnvioMensagemService extends Service {
    private String numero;
    private String texto;

    private SmsManager smsManager;

    @Override
    public void onCreate() {
        this.smsManager = SmsManager.getDefault();
        this.numero = "5511988885595";
        this.texto = "Mensagem teste";
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        enviarMensagem();
        return Service.START_STICKY;
    }

    /**
     * Envia mensagem
     *
     * @author Denis Magno
     */
    private void enviarMensagem(){
        try {
            smsManager.sendTextMessage(this.numero, null, this.texto, null, null);
        }catch(Exception e){
            Log.e("Erro ao enviar mensagem", e.getMessage());
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }
}
