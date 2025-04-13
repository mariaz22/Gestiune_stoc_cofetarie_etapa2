package model;

import java.time.LocalDate;

public abstract class Ingredient {
    protected String nume;
    protected double cantitate;
    protected UnitateMasura unitateMasura;
    protected LocalDate dataExpirare;
    protected double pretPerUnitate;

    public Ingredient(String nume, double cantitate, UnitateMasura unitateMasura,
                      LocalDate dataExpirare, double pretPerUnitate) {
        this.nume = nume;
        this.cantitate = cantitate;
        this.unitateMasura = unitateMasura;
        this.dataExpirare = dataExpirare;
        this.pretPerUnitate = pretPerUnitate;
    }

    public String getNume() {
        return nume;
    }

    public double getCantitate() {
        return cantitate;
    }

    public UnitateMasura getUnitateMasura() {
        return unitateMasura;
    }

    public double getValoareTotala() {
        return pretPerUnitate * cantitate;
    }

    public boolean esteExpirat() {
        return LocalDate.now().isAfter(dataExpirare);
    }

    public boolean areSuficient(double valoare) {
        return cantitate >= valoare;
    }

    public void adaugaCantitate(double valoare) {
        if (valoare > 0) {
            this.cantitate += valoare;
        }
    }

    public boolean scadeCantitate(double valoare) {
        if (valoare > 0 && areSuficient(valoare)) {
            this.cantitate -= valoare;
            return true;
        }
        return false;
    }

    public abstract String getTip();

    @Override
    public String toString() {
        return nume + " (" + cantitate + " " + unitateMasura +
                ", expiră la " + dataExpirare + ", " + getTip() + ")";
    }
}
