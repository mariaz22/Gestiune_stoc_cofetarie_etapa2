package model;

import java.time.LocalDate;
import java.util.List;

public class Tort extends Produs {
    private TipBlat tipBlat;
    private TipGlazura tipGlazura;
    private Eveniment eveniment;
    private int portii;

    public Tort(int id, String nume, double pret, int cantitate, List<Ingredient> ingrediente,
                LocalDate dataExpirare, double gramaj, int calorii, Categorie categorie, boolean esteVegan,
                TipBlat tipBlat, TipGlazura tipGlazura, Eveniment eveniment, int portii) {
        super(id, nume, pret, cantitate, ingrediente, dataExpirare, gramaj, calorii, categorie, esteVegan);
        this.tipBlat = tipBlat;
        this.tipGlazura = tipGlazura;
        this.eveniment = eveniment;
        this.portii = portii;
    }

    public enum TipBlat {
        CIOCOLATA,
        VANILIE,
        LAMAIE,
        CAFEA
    }

    public enum TipGlazura {
        CIOCOLATA,
        VANILIE,
        CARAMEL,
        FRISCA,
        FRUCTE
    }

    public enum Eveniment {
        NUNTA,
        ANIVERSARE,
        BOTEZ,
    }


    public TipBlat getTipBlat() {
        return tipBlat;
    }

    public TipGlazura getTipGlazura() {
        return tipGlazura;
    }

    public Eveniment getEveniment() {
        return eveniment;
    }

    public int getPortii() {
        return portii;
    }

    @Override
    public String getTip() {
        return "Tort";
    }


    @Override
    public String toString() {
        return super.toString() +
                " | Tip Blat: " + tipBlat +
                " | Tip Glazură: " + tipGlazura +
                " | Eveniment: " + eveniment +
                " | Porții: " + portii;
    }
}
