package com.ifsp.detectorqueda.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ifsp.detectorqueda.R;

public class AlertaQuedaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta_queda);
    }

    public void onClickSim(View view){
        Toast.makeText(this, "Ok. Desculpe-nos pelo incomodo.", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    public void onClickNao(View view){
        Toast.makeText(this, "Será enviado um alerta sobre sua condição.", Toast.LENGTH_SHORT).show();
        this.finish();
    }
}
