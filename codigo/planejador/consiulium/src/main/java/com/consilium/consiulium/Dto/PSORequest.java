package com.consilium.consiulium.Dto;

public class PSORequest {

    private int n;
    private int m;
    private double[] pedidos;
    private double[][] tempoPedidos;

    // Getters e Setters

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public double[] getPedidos() {
        return pedidos;
    }

    public void setPedidos(double[] pedidos) {
        this.pedidos = pedidos;
    }

    public double[][] getTempoPedidos() {
        return tempoPedidos;
    }

    public void setTempoPedidos(double[][] tempoPedidos) {
        this.tempoPedidos = tempoPedidos;
    }
}
