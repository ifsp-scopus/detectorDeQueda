package com.ifsp.detectorqueda.beans;

public class Acelerometro {
    private Float xAxis;
    private Float yAxis;
    private Float zAxis;
    private Long tempoOcorrido;

    public Acelerometro(){
        this.xAxis = Float.valueOf(0);
        this.yAxis = Float.valueOf(0);
        this.zAxis = Float.valueOf(0);
        this.tempoOcorrido = Long.valueOf(0);
    }

    public Float getxAxis() {
        return xAxis;
    }

    public void setxAxis(Float xAxis) {
        this.xAxis = xAxis;
    }

    public Float getyAxis() {
        return yAxis;
    }

    public void setyAxis(Float yAxis) {
        this.yAxis = yAxis;
    }

    public Float getzAxis() {
        return zAxis;
    }

    public void setzAxis(Float zAxis) {
        this.zAxis = zAxis;
    }

    public Long getTempoOcorrido() {
        return tempoOcorrido;
    }

    public void setTempoOcorrido(Long tempoOcorrido) {
        this.tempoOcorrido = tempoOcorrido;
    }
}
