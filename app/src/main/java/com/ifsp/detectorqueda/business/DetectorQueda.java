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

public class DetectorQueda implements SensorEventListener{
    private Context contexto;
    private SensorManager sensorManager;
    public Acelerometro acelerometro;

    /**
     *  Inicializa os objetos necessários para deteção da queda, solicita o service do sensor do
     * celular, seleciona o sensor a ser utilizado(Acelerômetro) e retorna feedback para usuário
     * informando da iniciação da detecção de queda.
     *
     * @param contexto  Contexto do sistema que chamou a função.
     * @author          Denis Magno.
     */
    public DetectorQueda(Context contexto){
        this.acelerometro = new Acelerometro();
        this.contexto = contexto;

        this.sensorManager = (SensorManager) this.contexto.getSystemService(Context.SENSOR_SERVICE);

        Sensor acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.sensorManager.registerListener((SensorEventListener) this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);

        Toast.makeText(contexto, "Detector de queda iniciado!", Toast.LENGTH_SHORT).show();
    }

    /**
     *  Verifica se houve uma possível queda do usuário com base nas alterações dos valores do acelerômetro.
     *
     * @return  Verdadeiro se for detectado uma queda. Falso se não for detectado uma queda.
     * @author  Denis Magno.
     */
    private boolean detectarQueda(){
        Log.e("MAGNITUDE", this.magnitude().toString());
        if(this.magnitude() > 13){
            return true;
        }else{
            return false;
        }
    }

    /**
     *  Calcula a magnitude da queda utilizando dados do acelerômetro já recebidos pela função 'onSensorChanged'.
     *
     * @return  Magnitude da queda.
     * @author  Denis Magno.
     */
    private Double magnitude(){
        Double magnitude = Math.sqrt(   (this.acelerometro.getxAxis() * this.acelerometro.getxAxis()) +
                (this.acelerometro.getyAxis() * this.acelerometro.getyAxis()) +
                (this.acelerometro.getzAxis() * this.acelerometro.getzAxis()));

        return magnitude;
    }

    /**
     *  Escuta o sensor proposto para detecção(Acelerômetro).
     *  É chamada automaticamente caso o sensor recebe um novo Evento(Seus valores sejam alterados.
     *
     * @param event Evento do sensor alterado.
     * @author      Denis Magno.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        this.acelerometro.setxAxis(event.values[0]);
        this.acelerometro.setyAxis(event.values[1]);
        this.acelerometro.setzAxis(event.values[2]);
        this.acelerometro.setTempoOcorrido(event.timestamp);

        if(this.detectarQueda()){
            Log.w("Detector de queda","Queda detectada! "+event.timestamp);
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
