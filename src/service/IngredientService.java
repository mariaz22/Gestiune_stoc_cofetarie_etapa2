package service;

import model.Ingredient;
import model.UnitateMasura;
import model.CerereAprovizionare;
import model.Distribuitor;
import java.util.ArrayList;



import java.util.List;
import java.util.stream.Collectors;

public class IngredientService {

    private List<Ingredient> ingrediente;

    public IngredientService(List<Ingredient> ingrediente) {
        this.ingrediente = ingrediente;
    }

    public void adaugaIngredient(Ingredient ingredient) {
        ingrediente.add(ingredient);
        //System.out.println("Ingredientul " + ingredient.getNume() + " a fost adăugat cu succes.");
    }

    public List<Ingredient> getIngredienteExpirate() {
        return ingrediente.stream()
                .filter(Ingredient::esteExpirat)
                .collect(Collectors.toList());
    }

    public List<Ingredient> getIngredienteCritice(double prag) {
        return ingrediente.stream()
                .filter(ing -> ing.getCantitate() < prag)
                .collect(Collectors.toList());
    }

    public List<CerereAprovizionare> genereazaCereriAprovizionare(double pragCritic, Distribuitor distribuitorImplicit) {
        List<CerereAprovizionare> cereri = new ArrayList<>();

        for (Ingredient ingredient : ingrediente) {
            if (ingredient.getCantitate() < pragCritic) {
                double cantitateNecesar = pragCritic - ingredient.getCantitate();

                CerereAprovizionare cerere = new CerereAprovizionare(
                        ingredient,
                        cantitateNecesar,
                        distribuitorImplicit
                );
                cereri.add(cerere);
                System.out.println("\n[GENERAT] " + cerere);
            }
        }

        if (cereri.isEmpty()) {
            System.out.println("\n✔️ Nu au fost identificate ingrediente sub pragul critic.");
        }

        return cereri;
    }


    public double calculeazaValoareTotalaStoc() {
        return ingrediente.stream()
                .mapToDouble(Ingredient::getValoareTotala)
                .sum();
    }

    public List<Ingredient> getToateIngredientele() {
        return ingrediente;
    }

    public void afiseazaIngrediente() {
        if (ingrediente.isEmpty()) {
            System.out.println("Nu există ingrediente în stoc.");
        } else {
            System.out.println("=== Lista Ingredientelor ===");
            for (Ingredient ing : ingrediente) {
                System.out.println(ing);
            }
        }
    }

    public boolean stergeIngredient(String nume) {
        return ingrediente.removeIf(ing -> ing.getNume().equalsIgnoreCase(nume));
    }

    public void afiseazaIngredienteSortateDupaStoc() {
        if (ingrediente.isEmpty()) {
            System.out.println("Nu exista ingrediente in stoc.");
        } else {
            System.out.println("~~~ Lista Ingredientelor sortate după stoc ~~~");
            List<Ingredient> ingredienteSortate = ingrediente.stream()
                    .sorted((ing1, ing2) -> Double.compare(ing1.getCantitate(), ing2.getCantitate()))
                    .collect(Collectors.toList());

            for (Ingredient ing : ingredienteSortate) {
                System.out.println(ing);
            }
        }
    }

}
