package com.ifsp.detectorqueda.business;

public interface ICronometroListener {
    /**
     *  Função que é chamada quando o cronômetro atinge o valor mínimo.(0)
     *
     * @author      Denis Magno
     */
    void onTimeOut();
}
