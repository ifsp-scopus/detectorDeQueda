package com.ifsp.detectorqueda.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ifsp.detectorqueda.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     *  Inicia 'DetectorActivity'
     *
     * @param view  Objeto que representa o botão que foi clicado.
     * @author      Denis Magno.
     */
    public void bIniciarDeteccaoQueda(View view){
        Intent in = new Intent(this, DetectorActivity.class);
        this.startActivity(in);
    }
}
