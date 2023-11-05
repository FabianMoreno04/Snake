package model;

public class NivelConfig {
    private int velocidad;
    private int tamanoInicial;
    private int tiempoComida;
    private int tiempoObstaculo;

    public NivelConfig(int velocidad, int tamanoInicial, int tiempoComida, int tiempoObstaculo) {
        this.velocidad = velocidad;
        this.tamanoInicial = tamanoInicial;
        this.tiempoComida = tiempoComida;
        this.tiempoObstaculo = tiempoObstaculo;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getTamanoInicial() {
        return tamanoInicial;
    }

    public void setTamanoInicial(int tamanoInicial) {
        this.tamanoInicial = tamanoInicial;
    }

    public int getTiempoComida() {
        return tiempoComida;
    }

    public void setTiempoComida(int tiempoComida) {
        this.tiempoComida = tiempoComida;
    }

    public int getTiempoObstaculo() {
        return tiempoObstaculo;
    }

    public void setTiempoObstaculo(int tiempoObstaculo) {
        this.tiempoObstaculo = tiempoObstaculo;
    }
}

