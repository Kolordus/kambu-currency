package com.kolak.kambucurrency.model.nbpapi;

public class Rate {
    private double mid;

    public Rate() {
    }

    public Rate(double mid) {
        this.mid = mid;
    }


    public double getMid() {
        return mid;
    }

    public void setMid(double mid) {
        this.mid = mid;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "mid=" + mid +
                '}';
    }
}
