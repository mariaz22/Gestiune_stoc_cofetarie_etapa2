package model;

import java.time.LocalDate;
import java.util.List;

public class Fursec extends Produs {
    private Aroma aroma;
    private TipUmplutura tipUmplutura;
    private boolean glazurat;

    public Fursec(int id, String nume, double pret, int cantitate, List<Ingredient> ingrediente,
                  LocalDate dataExpirare, double gramaj, int calorii, Categorie categorie, boolean esteVegan,
                  Aroma aroma, TipUmplutura tipUmplutura, boolean glazurat) {
        super(id, nume, pret, cantitate, ingrediente, dataExpirare, gramaj, calorii, categorie, esteVegan);
        this.aroma = aroma;
        this.tipUmplutura = tipUmplutura;
        this.glazurat = glazurat;
    }

    public enum Aroma {
        CIOCOLATA,
        VANILIE,
        CAFEA,
        LAMAIE,
        COCOS,
        MENTA,
        FRUCTE_DE_PADURE,
        CARAMEL
    }

    public enum TipUmplutura {
        GEM,
        CREMA,
        FRUCTE,
        CIOLCOLATA,
        NUCI,
        VANILIE
    }

    public Aroma getAroma() {
        return aroma;
    }

    public TipUmplutura getTipUmplutura() {
        return tipUmplutura;
    }


    public boolean isGlazurat() {
        return glazurat;
    }

    @Override
    public String getTip() {
        return "Fursec";
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Aromă: " + aroma +
                " | Tip Umplutură: " + tipUmplutura +
                " | Glazurat: " + (glazurat ? "Da" : "Nu");
    }
}
