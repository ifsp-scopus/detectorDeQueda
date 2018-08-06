package com.ifsp.detectorqueda.activities;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ifsp.detectorqueda.R;
import com.ifsp.detectorqueda.business.CronometroAsync;
import com.ifsp.detectorqueda.business.ICronometroListener;

public class AlertaQuedaActivity extends AppCompatActivity implements ICronometroListener{
    private Button btnSim;
    private Button btnNao;
    private TextView txtCronometro;

    private CronometroAsync cronometro;
    private Vibrator vibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_alerta_queda);

        this.vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        inicializaComponentesGraficos();

        this.cronometro = new CronometroAsync(this, txtCronometro);

        //Executa cronometro definindo segundos a serem contados.
        this.cronometro.execute(60);
        this.iniciarVibracao();
    }

    private void inicializaComponentesGraficos(){
        this.txtCronometro = (TextView)this.findViewById(R.id.txtCronometro);

        /**
         *  Informa que possível queda foi um falso positivo ou está tudo bem.
         */
        this.btnSim = (Button) findViewById(R.id.btnSim);
        this.btnSim.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(AlertaQuedaActivity.this, "Ok. Desculpe-nos pelo incomodo.", Toast.LENGTH_SHORT).show();
                finish();
                return false;
            }
        });

        /**
         *  Confirma que houve uma queda.
         */
        this.btnNao = (Button) findViewById(R.id.btnNao);
        this.btnNao.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(AlertaQuedaActivity.this, "Será enviado um alerta sobre sua condição.", Toast.LENGTH_SHORT).show();
                finish();
                return false;
            }
        });
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
