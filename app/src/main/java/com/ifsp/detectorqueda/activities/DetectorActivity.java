package com.ifsp.detectorqueda.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ifsp.detectorqueda.R;
import com.ifsp.detectorqueda.business.DetectorQueda;

public class DetectorActivity extends AppCompatActivity {
    private DetectorQueda detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detector);

        this.detector = new DetectorQueda(this);

        if(this.detector.iniciar()){
            Toast.makeText(this, "Detector de queda iniciado!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Erro ao iniciar detector de queda!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        if(this.detector.pausar()){
            Toast.makeText(this, "Detector de queda parado!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Erro ao parar detector de queda!", Toast.LENGTH_SHORT).show();
        }
    }
}