package com.ifsp.detectorqueda.helpers;

import android.content.Context;
import android.os.FileUriExposedException;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogHelper {
    /**
     *  Cadastra erros do sistema em arquivo de LOG do sistema para posterior visualização.
     *
     * @param contexto  Contexto do sistema que chamou a função.
     * @param titulo    Título do erro do sistema.
     * @param corpo     Corpo do erro do sistema(Descrição).
     * @author          Denis Magno.
     */
    public void cadastrarErro(Context contexto, String titulo, String corpo){
        try {
            FileOutputStream arquivo = contexto.openFileOutput("logErrors.txt", Context.MODE_APPEND);

            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS");
            String linha = f.format(new Date()) +" - "+ titulo +": "+ corpo +"\n";

            arquivo.write(linha.getBytes());
            arquivo.close();
        }catch(FileNotFoundException ex){
            Log.e("ERROR FILE","Erro ao ler/criar o arquivo:");
            Log.e("EXCEPTION: ", ex.getMessage());
        }catch(FileUriExposedException ex){
            Log.e("ERROR FILE","Erro ao ler/criar o arquivo:");
            Log.e("EXCEPTION: ", ex.getMessage());
        }catch(IOException ex){
            Log.e("ERROR FILE","Erro ao escrever no arquivo:");
            Log.e("EXCEPTION", ex.getMessage());
        }
    }

    /**
     *  Cadastra possível queda em arquivo de LOG do sistema para posterior visualização.
     *
     * @param contexto  Contexto do sistema que chamou a função.
     * @author          Denis Magno.
     */
    public void cadastrarQueda(Context contexto){
        try {
            FileOutputStream arquivo = contexto.openFileOutput("logQuedas.txt", Context.MODE_APPEND);

            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS");
            String linha = f.format(new Date()) + " - Possível queda encontrada!\n";

            arquivo.write(linha.getBytes());
            arquivo.close();
        }catch(FileNotFoundException ex){
            Log.e("ERROR FILE","Erro ao ler/criar o arquivo:");
            Log.e("EXCEPTION: ", ex.getMessage());
        }catch(FileUriExposedException ex){
            Log.e("ERROR FILE","Erro ao ler/criar o arquivo:");
            Log.e("EXCEPTION: ", ex.getMessage());
        }catch(IOException ex){
            Log.e("ERROR FILE","Erro ao escrever no arquivo:");
            Log.e("EXCEPTION", ex.getMessage());
        }
    }
}
