package model;

import java.time.LocalDate;

public abstract class Ingredient {
    protected int id;
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

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public double getCantitate() {
        return cantitate;
    }

    public LocalDate getDataExpirare() {
        return dataExpirare;
    }

    public double getPretPerUnitate() {
        return pretPerUnitate;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setCantitate(double cantitate) {
        this.cantitate = cantitate;
    }

    public void setUnitateMasura(UnitateMasura unitateMasura) {
        this.unitateMasura = unitateMasura;
    }

    public void setDataExpirare(LocalDate dataExpirare) {
        this.dataExpirare = dataExpirare;
    }

    public void setPretPerUnitate(double pretPerUnitate) {
        this.pretPerUnitate = pretPerUnitate;
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
