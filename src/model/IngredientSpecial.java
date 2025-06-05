package model;

import java.time.LocalDate;
import java.util.List;

public class IngredientSpecial extends Ingredient {
    private int timpPreparare;
    private boolean esteProdusIntern; // true = facut în cofetarie
    private List<Ingredient> ingredienteComponente;

    public IngredientSpecial(String nume, double cantitate, UnitateMasura unitateMasura,
                             LocalDate dataExpirare, double pretPerUnitate,
                             List<Ingredient> ingredienteComponente) {
        super(nume, cantitate, unitateMasura, dataExpirare, pretPerUnitate);
        this.ingredienteComponente = ingredienteComponente;
    }

    public List<Ingredient> getIngredienteComponente() {
        return ingredienteComponente;
    }

    @Override
    public String getTip() {
        return "special";
    }

    public int getTimpPreparare() {
        return timpPreparare;
    }

    public boolean getEsteProdusIntern() {
        return esteProdusIntern;
    }

    public void setTimpPreparare(int timpPreparare) {
        this.timpPreparare = timpPreparare;
    }

    public void setEsteProdusIntern(boolean esteProdusIntern) {
        this.esteProdusIntern = esteProdusIntern;
    }

    public void afiseazaIngredienteComponente() {
        System.out.println("Ingrediente componente pentru " + nume + ":");
        for (Ingredient i : ingredienteComponente) {
            System.out.println(" - " + i);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " (conține " + ingredienteComponente.size() + " ingrediente)";
    }
}
