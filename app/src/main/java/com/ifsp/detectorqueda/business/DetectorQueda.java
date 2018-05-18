package com.ifsp.detectorqueda.business;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import com.ifsp.detectorqueda.activities.AlertaQuedaActivity;
import com.ifsp.detectorqueda.activities.DetectorActivity;
import com.ifsp.detectorqueda.beans.Acelerometro;
import com.ifsp.detectorqueda.helpers.LogHelper;

import java.util.ArrayList;
import java.util.List;

public class DetectorQueda implements SensorEventListener{
    private Context contexto;
    private SensorManager sensorManager;
    private Sensor acelerometro;
    private List<Acelerometro> janela;
    private Integer maxJanela;
    private Integer elementoInicial;
    private static Integer contagem = 0;

    /**
     *  Inicializa os objetos necessários para deteção da queda.
     *
     * @param contexto  Contexto do sistema que chamou a função.
     * @author          Denis Magno.
     */
    public DetectorQueda(Context contexto){
        this.contexto = contexto;
        this.janela = new ArrayList<Acelerometro>();
        this.janela.clear();
        this.maxJanela = 300;
        this.elementoInicial = this.maxJanela - 1;
    }

    /**
     *  Solicita o serviço do sensor do celular, seleciona o sensor a ser utilizado(Acelerômetro) e
     * inicia uma instância de SensorEventListener para o sensor solicitado.
     *
     * @return  Verdadeiro se iniciar normalmente o sensor. Falso se ocorrer algum erro na iniciação
     * do sensor.
     * @author  Denis Magno.
     */
    public boolean iniciar(){
        this.sensorManager = (SensorManager) this.contexto.getSystemService(Context.SENSOR_SERVICE);

        this.acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(this.sensorManager.registerListener((SensorEventListener) this, acelerometro, SensorManager.SENSOR_DELAY_GAME)){
            return true;
        }else{
            return false;
        }
    }

    /**
     *  Pausa os sensores, uma vez iniciados.
     *
     * @return  Verdadeiro se pausar normalmente os sensores. Falso se ocorrer algum erro na parada
     * dos sensores.
     * @author  Denis Magno.
     */
    public boolean pausar(){
        if(this.sensorManager != null && this.acelerometro != null) {
            this.sensorManager.unregisterListener(this, this.acelerometro);

            return true;
        }else{
            return false;
        }
    }

    /**
     *  Escuta o sensor proposto para detecção(Acelerômetro).
     * É chamada automaticamente caso o sensor receba um novo Evento(Seus valores sejam alterados).
     *
     * @param event Evento do sensor alterado.
     * @author      Denis Magno.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        //Monta janela com dados estruturados no objeto 'Acelerometro' vindos do sensor definido.
        this.montaJanela(new Acelerometro(event.values[0], event.values[1], event.values[2], event.timestamp));

        DetectorQueda.contagem++;
        Log.e(DetectorQueda.contagem.toString(),".");

        // Roda algoritmo de detecção de queda para janela montada.
        Integer resultado = this.detectaQuedaLivre();
        if(resultado == 1){
            new LogHelper().cadastrarQueda(contexto);
            Toast.makeText(this.contexto, "Queda detectada", Toast.LENGTH_SHORT).show();
            Log.e("QUEDA:","Queda detectada!");

            Intent in = new Intent(this.contexto, AlertaQuedaActivity.class);
            this.contexto.startActivity(in);
        }else if(resultado == -1){
            Log.e("QUEDA:","Tamanho da janela ainda não é o ideal para detecção!");
        }else if(resultado == -2){
            new LogHelper().cadastrarErro(contexto, "Detector de Queda", "Ultrapassou tamanho máximo da janela!");
            Log.e("QUEDA:","Ultrapassou tamanho máximo da janela!");
        }
    }

    /**
     *  Verifica se houve um movimento de queda livre na janela obtida
     *
     * @return  1 se for detectado uma queda. 0 se não for detectado uma queda. -1 se tamanho da janela
     * ainda não é ideal para verificação. -2 se ultrapassou o tamanho máximo da janela.
     * @author  Denis Magno.
     */
    private int detectaQuedaLivre(){
        //Verifica se janela já possui tamanho ideal para começar detecção.
        if(!this.verificaTamanhoJanela()) {
            return -1;
        }

        //new LogHelper().cadastrarJanelaQueda(contexto, this.janela);

        //Verifica se elemento escolhido como inicial inserido na janela atingiu aceleração baixa(Queda livre).
        if(!(this.janela.get(this.elementoInicial).getMagnitudeAceleracao() < 0.3)){
            //Log.e("ENTROU AQUI",this.janela.get(this.maxJanela - 1).getMagnitudeAceleracao().toString());
            return 0;
        }

        //Define que foi encontrado uma queda livre na janela e onde foi encontrada essa queda.
        int quedaLivre = this.elementoInicial - 1;

        // Percorre janela inteira, até que encontre o elemento com menor valor de queda livre.
        while(quedaLivre >= 0){
            //Verifica se próximo elemento da janela é válido.
            if(!this.verificaProxElemJanela(quedaLivre)) {
                return -2;
            }

            //Verifica se próximo elemento da janela é menor do que elemento anterior(Se atingiu menor nível de queda livre)
            if(this.janela.get(quedaLivre).getMagnitudeAceleracao() < this.janela.get(quedaLivre-1).getMagnitudeAceleracao()){
                //Define próximo elemento da janela como sendo o pico da queda livre
                quedaLivre--;
            }else{
                //Sai do laço de repetição pois encontrou o menor nível de queda livre.
                break;
            }
        }

        //  Define maior elemento da janela como sendo o pico da queda livre para começar a procurar
        // o a ultrapassagem da aceleração da magnitude para definir a possível queda a partir da queda livre.
        int ultrapassagemMagnitude = quedaLivre;

        //  Percorre janela inteira, até que encontre o elemento com maior valor de aceleração da magnitude,
        // a partir da queda livre já identificada no laço anterior.
        while(ultrapassagemMagnitude >= 0){
            //Verifica se próximo elemento da janela é válido.
            if(!this.verificaProxElemJanela(ultrapassagemMagnitude)) {
                return -2;
            }

            //Verifica se próximo elemento da janela é maior do que elemento anterior(Se atingiu seu maior nível de aceleração de magnitude)
            if(this.janela.get(ultrapassagemMagnitude).getMagnitudeAceleracao() > this.janela.get(ultrapassagemMagnitude-1).getMagnitudeAceleracao()){
                ultrapassagemMagnitude--;
            }else{
                // Sai do laço de repetição, pois encontrou a aceleração mais alta.
                break;
            }
        }

        //  Verifica se ultrapassagem da aceleração da magnitude é alta o bastante para definirmos
        // como uma queda.
        if(this.janela.get(ultrapassagemMagnitude).getMagnitudeAceleracao() > 2){
            new LogHelper().cadastrarJanelaQueda(this.contexto, this.janela);

            //  Descarta aquela janela se encontrar uma queda para evitar falsos positivos que podem
            // acontecer com dados posteriores a queda livre encontrada.
            this.janela.clear();
            return 1;
        }else{
            return 0;
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
        if (this.janela.size() == this.maxJanela) {
            this.janela.remove(this.maxJanela - 1);
        }

        //Adiciona dados capturados do acelerometro no primeiro elemento da lista.
        this.janela.add(0, dadosAcelerometro);
    }

    /**
     *  Verifica se janela atingiu o tamanho ideal
     *
     * @return  Retorna verdadeiro se janela atingiu tamanho ideal, e falso se não atingiu tamanho ideal.
     * @author  Denis Magno
     */
    private boolean verificaTamanhoJanela(){
        if(this.janela.size() == this.maxJanela){
            return true;
        }else{
            return false;
        }
    }

    /**
     *  Verifica se próximo elemento da janela é valido
     *
     * @param i Elemento atual da janela
     * @return  Retorna verdadeiro se elemento é válido, e falso se elemento não é válido.
     * @author  Denis Magno
     */
    private boolean verificaProxElemJanela(int i){
        if(i-1 >= 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     *  Não implementado.
     *
     * @param sensor
     * @param accuracy
     * @author          Denis Magno
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}
