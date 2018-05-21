package com.ifsp.detectorqueda.business;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class CronometroAsync extends AsyncTask<Integer, Integer, Boolean>{
    private ICronometroListener callback;
    private TextView text;

    public CronometroAsync(ICronometroListener cba, TextView text)
    {
        this.callback = cba;
        this.text = text;
    }

    /**
     *  Função que roda em background.
     *  Conta o tempo, em segundos, até que chegue no número indicado ou seja cancelado.
     *
     * @param params    Número indicado para o fim da contagem.
     * @return          Retorna true se cronometro chegar a 0.
     * @author          Denis Magno.
     */
    @Override
    protected Boolean doInBackground(Integer... params) {
        int n = 0;
        Integer i = params[0];
        while(i > n){
            // Publica um progresso no método assíncrono. (Chama 'onProgressUpdate' com o parâmetro
            // passado).
            this.publishProgress(i);
            if (isCancelled()) {
                break;
            }

            try{
                Thread.sleep(1000);
                Log.e("Opa",i.toString());
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            i--;
        }

        return true;
    }

    /**
     *  Atualiza dados do progresso da funcionalidade. (Trabalha na Thread da UI).
     *
     * @param progress  Valor do progresso da função 'doInBackground'
     * @author          Denis Magno.
     */
    protected void onProgressUpdate(Integer... progress){
        this.text.setText(String.valueOf(progress[0]));
    }

    /**
     *  Função que é chamada após 'doBackGround' ser finalizada.
     *
     * @param result    Valor do retorno da função 'doInBackground'
     * @author          Denis Magno.
     */
    @Override
    protected void onPostExecute(Boolean result){
        if(result == true){
            callback.onTimeOut();
            Log.e("Parou cronômetro",".");
        }
    }
}
