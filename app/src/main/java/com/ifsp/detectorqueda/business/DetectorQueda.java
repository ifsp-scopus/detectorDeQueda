package com.ifsp.detectorqueda.business;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import com.ifsp.detectorqueda.beans.Acelerometro;
import com.ifsp.detectorqueda.helpers.LogHelper;

import java.util.ArrayList;
import java.util.List;

public class DetectorQueda implements SensorEventListener{
    private Context contexto;
    private SensorManager sensorManager;
    private List<Acelerometro> janela;

    /**
     *  Inicializa os objetos necessários para deteção da queda, solicita o service do sensor do
     * celular, seleciona o sensor a ser utilizado(Acelerômetro) e retorna feedback para usuário
     * informando da iniciação da detecção de queda.
     *
     * @param contexto  Contexto do sistema que chamou a função.
     * @author          Denis Magno.
     */
    public DetectorQueda(Context contexto){
        this.contexto = contexto;
        this.janela = new ArrayList<Acelerometro>();

        this.sensorManager = (SensorManager) this.contexto.getSystemService(Context.SENSOR_SERVICE);

        Sensor acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.sensorManager.registerListener((SensorEventListener) this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);

        Toast.makeText(contexto, "Detector de queda iniciado!", Toast.LENGTH_SHORT).show();
    }

    /**
     *  Verifica se houve uma possível queda do usuário com base nas alterações dos valores do acelerômetro.
     *
     * @param dadosAcelerometro Dados do acelerômetro.
     * @return                  Verdadeiro se for detectado uma queda. Falso se não for detectado uma queda.
     * @author                  Denis Magno.
     */
    private boolean detectarQueda(Acelerometro dadosAcelerometro){
        //Log.e("MAGNITUDE", this.magnitude().toString());
        Log.e("ACELERAÇÃO DA GRAVIDADE", this.getMagnitudeAceleracao(dadosAcelerometro).toString());
        if(this.getMagnitudeAceleracao(dadosAcelerometro) > 2){
            this.organizaJanela(dadosAcelerometro);
            return true;
        }else{
            this.montaJanela(dadosAcelerometro);
            return false;
        }
    }

    /**
     *  Organiza janela caso seja encontrado uma possível queda para manter valor de magnitude no meio da lista.
     *
     * @param dadosAcelerometro Dados do acelerômetro.
     * @author                  Denis Magno.
     */
    private void organizaJanela(Acelerometro dadosAcelerometro){
        for(int i = 0; i < 149; i++){
            //Remove último elemento caso a janela já tenha atingido seu valor total
            if(this.janela.size() == 300){
                this.janela.remove(299);
            }

            //Continua buscando valores do acelerômetro para popular o resto da janela.
            /**
             * TODO
             */
            Acelerometro aux = new Acelerometro();
            aux.setxAxis((float) 0);
            aux.setyAxis((float) 0);
            aux.setzAxis((float) 0);
            this.janela.add(0, aux);
        }
    }

    /**
     *  Monta janela de deteccção de queda.
     *
     * @param dadosAcelerometro Dados do acelerômetro.
     * @author                  Denis Magno.
     */
    private void montaJanela(Acelerometro dadosAcelerometro){
        //Remove último elemento caso a janela já tenha atingido seu valor total
        if(this.janela.size() == 300){
            this.janela.remove(299);
        }

        //Adiciona dados capturados do acelerometro no primeiro elemento da lista.
        this.janela.add(0, dadosAcelerometro);
    }

    /**
     *  Calcula a magnitude da aceleração da possível queda utilizando dados do acelerômetro.
     *
     * @param dadosAcelerometro Dados do acelerômetro.
     * @return                  Magnitude da aceleração da possível queda.
     * @author                  Denis Magno.
     */
    private Double getMagnitudeAceleracao(Acelerometro dadosAcelerometro){
        //Calcula magnitude da queda.
        Double magnitude = Math.sqrt(   Math.pow(dadosAcelerometro.getxAxis(), 2) +
                                        Math.pow(dadosAcelerometro.getyAxis(), 2) +
                                        Math.pow(dadosAcelerometro.getzAxis(), 2));

        //Calcula aceleração da magnitude da queda.
        Double aceleracao = magnitude / 9.8;

        return aceleracao;
    }

    /**
     *  Escuta o sensor proposto para detecção(Acelerômetro).
     * É chamada automaticamente caso o sensor recebe um novo Evento(Seus valores sejam alterados.
     *
     * @param event Evento do sensor alterado.
     * @author      Denis Magno.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        Acelerometro dadosAcelerometro = new Acelerometro();
        dadosAcelerometro.setxAxis(event.values[0]);
        dadosAcelerometro.setyAxis(event.values[1]);
        dadosAcelerometro.setzAxis(event.values[2]);

        if(this.detectarQueda(dadosAcelerometro)){
            Log.w("Detector de queda","Queda detectada! " + event.timestamp);
            new LogHelper().cadastrarQueda(contexto);
            Toast.makeText(this.contexto, "Queda detectada", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        /*
            Não implementado.
         */
    }
}
