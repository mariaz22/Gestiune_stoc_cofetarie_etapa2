package model;

import java.time.LocalDate;
import java.util.List;

public class Patiserie extends Produs {
    private TipPatiserie tip;
    private int timpCoacere;
    private boolean esteCongelata;

    public Patiserie(int id, String nume, double pret, int cantitate, List<Ingredient> ingrediente,
                     LocalDate dataExpirare, double gramaj, int calorii, Categorie categorie, boolean esteVegan,
                     TipPatiserie tip, int timpCoacere, boolean esteCongelata) {
        super(id, nume, pret, cantitate, ingrediente, dataExpirare, gramaj, calorii, categorie, esteVegan);
        this.tip = tip;
        this.timpCoacere = timpCoacere;
        this.esteCongelata = esteCongelata;
    }

    public enum TipPatiserie {
        CROISSANT,
        GOGOASA,
        ECLER,
        TARTA,
        STRUDEL
    }

    public TipPatiserie getTipPatiserie() {
        return tip;
    }

    public int getTimpCoacere() {
        return timpCoacere;
    }

    public boolean isEsteCongelata() {
        return esteCongelata;
    }

    @Override
    public String getTip() {
        return "Patiserie";
    }


    @Override
    public String toString() {
        return super.toString() +
                " | Tip: " + tip +
                " | Timp Coacere: " + timpCoacere + " minute" +
                " | Este congelată: " + (esteCongelata ? "Da" : "Nu");
    }
}
