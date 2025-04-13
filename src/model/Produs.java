package model;

import java.time.LocalDate;
import java.util.List;

public abstract class Produs {
    protected int id;
    protected String nume;
    protected double pret;
    protected int cantitate;
    protected List<Ingredient> ingrediente;
    protected LocalDate dataExpirare;
    protected double gramaj; // per bucată
    protected int calorii;   // per bucată
    protected Categorie categorie;
    protected boolean esteVegan;

    public Produs(int id, String nume, double pret, int cantitate, List<Ingredient> ingrediente,
                  LocalDate dataExpirare, double gramaj, int calorii, Categorie categorie, boolean esteVegan) {
        this.id = id;
        this.nume = nume;
        this.pret = pret;
        this.cantitate = cantitate;
        this.ingrediente = ingrediente;
        this.dataExpirare = dataExpirare;
        this.gramaj = gramaj;
        this.calorii = calorii;
        this.categorie = categorie;
        this.esteVegan = esteVegan;
    }

    public boolean isExpirat() {
        return LocalDate.now().isAfter(dataExpirare);
    }

    public abstract String getTip();

    @Override
    public String toString() {
        return "Tip produs: " + getTip() + nume +
                " | Pret: " + pret + " RON" +
                " | Cantitate: " + cantitate +
                " | Expira: " + dataExpirare +
                " | Gramaj: " + gramaj + "g" +
                " | Calorii: " + calorii + " kcal"+
                " | Vegan: " + (esteVegan ? "Da" : "Nu");

    }

    // Getters
    public int getId() { return id; }
    public String getNume() { return nume; }
    public double getPret() { return pret; }
    public int getCantitate() { return cantitate; }
    public List<Ingredient> getIngrediente() { return ingrediente; }
    public LocalDate getDataExpirare() { return dataExpirare; }
    public double getGramaj() { return gramaj; }
    public int getCalorii() { return calorii; }
    public Categorie getCategorie() { return categorie; }
    public boolean isVegan() { return esteVegan; }

    // Setters
    public void setCantitate(int cantitate) { this.cantitate = cantitate; }
    public void setPret(double pret) { this.pret = pret; }
    public void setCalorii(int calorii) { this.calorii = calorii; }
    public void setVegan(boolean esteVegan) { this.esteVegan = esteVegan; }

    public boolean contineIngredient(String numeIngredient) {
        return ingrediente.stream()
                .anyMatch(ing -> ing.getNume().equalsIgnoreCase(numeIngredient));
    }

    public boolean esteLowStock(int prag) {
        return cantitate < prag;
    }

}
