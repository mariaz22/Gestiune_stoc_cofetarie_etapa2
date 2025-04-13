package model;

import java.util.ArrayList;
import java.util.List;

public class Distribuitor {
    private String nume;
    private String telefon;
    private String email;
    private String adresa;
    private List<Ingredient> produseFurnizate;

    public Distribuitor(String nume, String telefon, String email, String adresa) {
        this.nume = nume;
        this.telefon = telefon;
        this.email = email;
        this.adresa = adresa;
        this.produseFurnizate = new ArrayList<>();
    }

    public String getNume() {
        return nume;
    }

    public void adaugaProdusFurnizat(Ingredient ingredient) {
        produseFurnizate.add(ingredient);
    }

    public List<Ingredient> getProduseFurnizate() {
        return produseFurnizate;
    }

    @Override
    public String toString() {
        return "Distributor: " + nume + ", Telefon: " + telefon +
                ", Email: " + email + ", Adresa: " + adresa +
                ", Produse furnizate: " + produseFurnizate.size();
    }
}
