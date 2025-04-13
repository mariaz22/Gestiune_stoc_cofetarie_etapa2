package model;

import java.time.LocalDate;
import java.util.List;

public class Bomboana extends Produs {
    private TipCiocolata tipCiocolata;
    private String umplutura;
    private boolean contineAlcool;

    public Bomboana(int id, String nume, double pret, int cantitate, List<Ingredient> ingrediente,
                    LocalDate dataExpirare, double gramaj, int calorii, Categorie categorie, boolean esteVegan,
                    TipCiocolata tipCiocolata, String umplutura, boolean contineAlcool) {
        super(id, nume, pret, cantitate, ingrediente, dataExpirare, gramaj, calorii, categorie, esteVegan);
        this.tipCiocolata = tipCiocolata;
        this.umplutura = umplutura;
        this.contineAlcool = contineAlcool;
    }

    public enum TipCiocolata {
        NEAGRA,
        LAPTE,
        ALBA
    }



    public TipCiocolata getTipCiocolata() {
        return tipCiocolata;
    }

    public String getUmplutura() {
        return umplutura;
    }

    public boolean isContineAlcool() {
        return contineAlcool;
    }

    @Override
    public String getTip() {
        return "Bomboană";
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Tip Ciocolată: " + tipCiocolata +
                " | Umplutură: " + umplutura +
                " | Conține alcool: " + (contineAlcool ? "Da" : "Nu");
    }
}
