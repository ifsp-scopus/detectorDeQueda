package com.ifsp.detectorqueda.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ifsp.detectorqueda.R;

public class FichaMedicaActivity extends AppCompatActivity {
    private TextView txtNome;
    private TextView txtDataNascimento;
    private TextView txtSangue;
    private TextView txtPeso;
    private TextView txtAltura;
    private TextView txtEndereco;
    private TextView txtAlergias;
    private TextView txtMedicamentos;
    private TextView txtParentesco;
    private TextView txtNomeContato;
    private TextView txtTelefone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_ficha_medica);

        iniciarComponentesGraficos();
        preencherDados();
    }

    private void iniciarComponentesGraficos(){
        this.txtNome = (TextView)this.findViewById(R.id.txtFichaMedicaNome);
        this.txtDataNascimento = (TextView)this.findViewById(R.id.txtFichaMedicaDataNascimento);
        this.txtSangue = (TextView)this.findViewById(R.id.txtFichaMedicaSangue);
        this.txtPeso = (TextView)this.findViewById(R.id.txtFichaMedicaPeso);
        this.txtAltura = (TextView)this.findViewById(R.id.txtFichaMedicaAltura);
        this.txtEndereco = (TextView)this.findViewById(R.id.txtFichaMedicaEndereco);
        this.txtAlergias = (TextView)this.findViewById(R.id.txtFichaMedicaAlergias);
        this.txtMedicamentos = (TextView)this.findViewById(R.id.txtFichaMedicaMedicamentos);
        this.txtParentesco = (TextView)this.findViewById(R.id.txtFichaMedicaParentesco);
        this.txtNomeContato = (TextView)this.findViewById(R.id.txtFichaMedicaNomeContato);
        this.txtTelefone = (TextView)this.findViewById(R.id.txtFichaMedicaTelefone);
    }

    private void preencherDados(){
        this.txtNome.setText("Fulano da Silva");
        this.txtDataNascimento.setText("01 de janeiro de 2000 (18)");
        this.txtSangue.setText("O+");
        this.txtPeso.setText("60kg");
        this.txtAltura.setText("170cm");
        this.txtEndereco.setText("Av. Um, Bairro, 1740, 07124-000, São Paulo - SP");
        this.txtAlergias.setText("Sinusite, Rinite, Poeira");
        this.txtMedicamentos.setText("Metiformina, Insulina");
        this.txtParentesco.setText("Filho");
        this.txtNomeContato.setText("José da Silva");
        this.txtTelefone.setText("(11) 9 9999-9999");
    }
}
