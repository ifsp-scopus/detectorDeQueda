package com.ifsp.detectorqueda.activities;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.ifsp.detectorqueda.R;
import com.ifsp.detectorqueda.business.CronometroAsync;
import com.ifsp.detectorqueda.business.ICronometroListener;

public class AlertaQuedaActivity extends AppCompatActivity implements ICronometroListener{
    private CronometroAsync cronometro;
    private Vibrator vibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_alerta_queda);

        TextView tvCronometro = (TextView)this.findViewById(R.id.tvCronometro);

        this.vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        this.cronometro = new CronometroAsync(this, tvCronometro);

        //Executa cronometro definindo segundos a serem contados.
        this.cronometro.execute(60);
        this.iniciarVibracao();
    }

    /**
     *  Informa que possível queda foi um falso positivo ou está tudo bem.
     *
     * @param view  Objeto que representa o botão que foi clicado.
     * @author      Denis Magno
     */
    public void onClickSim(View view){
        Toast.makeText(this, "Ok. Desculpe-nos pelo incomodo.", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    /**
     *  Confirma que houve uma queda.
     *
     * @param view  Objeto que representa o botão que foi clicado.
     * @author      Denis Magno
     */
    public void onClickNao(View view){
        Toast.makeText(this, "Será enviado um alerta sobre sua condição.", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    /**
     *  É chamado quando o usuário não responde o alerta de queda depois de um período de tempo
     * estipulado no método '.execute()', em 'onCreate'.
     *
     * @author      Denis Magno
     */
    @Override
    public void onTimeOut() {
        Toast.makeText(this, "Tempo esgotado!", Toast.LENGTH_SHORT).show();
        this.finish();
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
            vibrate.vibrate(VibrationEffect.createWaveform(timings, 0));
        }else{
            //deprecated in API 26
            vibrate.vibrate(timings, 0);
        }
    }

    /**
     *  Para cronômetro.
     *
     * @author Denis Magno
     */
    @Override
    protected void onDestroy(){
        super.onDestroy();
        this.cronometro.cancel(true);
        this.vibrate.cancel();
        Toast.makeText(this, "Cronômetro parado!", Toast.LENGTH_SHORT).show();
    }
}
