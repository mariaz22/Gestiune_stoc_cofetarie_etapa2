package service;

import model.Produs;
import model.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;



public class ProdusService {
    private List<Produs> produse = new ArrayList<>();

    public void adaugaProdus(Produs produs) {
        produse.add(produs);
    }



    public boolean stergeProdus(int id) {
        return produse.removeIf(p -> p.getId() == id);
    }


    public Optional<Produs> cautaProdusDupaId(int id) {
        return produse.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    public List<Produs> getProduseExpirate() {
        return produse.stream()
                .filter(Produs::isExpirat)
                .collect(Collectors.toList());
    }

    public List<Produs> getProduseLowStock(int prag) {
        return produse.stream()
                .filter(p -> p.getCantitate() < prag)
                .collect(Collectors.toList());
    }

    public List<Produs> recomandaProduse(boolean preferaVegan) {
        return produse.stream()
                .filter(p -> p.isVegan() == preferaVegan)
                .sorted(Comparator.comparingDouble(Produs::getPret).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public boolean actualizeazaStoc(int id, int cantitate, boolean adauga) {
        Optional<Produs> produsOpt = cautaProdusDupaId(id);
        if (produsOpt.isPresent()) {
            Produs produs = produsOpt.get();
            if (adauga) {
                produs.setCantitate(produs.getCantitate() + cantitate);
                return true;
            } else {
                if (produs.getCantitate() >= cantitate) {
                    produs.setCantitate(produs.getCantitate() - cantitate);
                    return true;
                }
            }
        }
        return false;
    }

    public List<Produs> cautaDupaIngredient(String numeIngredient) {
        return produse.stream()
                .filter(p -> p.contineIngredient(numeIngredient))
                .collect(Collectors.toList());
    }

    public List<Produs> getToateProdusele() {
        return produse;
    }

    public boolean ingredienteleSuntInStoc(Produs produs, List<Ingredient> stoc) {
        return produs.getIngrediente().stream().allMatch(
                ingredientNecesar -> stoc.stream().anyMatch(
                        stocIngredient -> stocIngredient.getNume().equalsIgnoreCase(ingredientNecesar.getNume()) &&
                                stocIngredient.getCantitate() >= ingredientNecesar.getCantitate()
                )
        );
    }

    public SortedSet<Produs> sorteazaDupaCalorii(boolean crescator) {
        Comparator<Produs> comparator = crescator ?
                Comparator.comparingInt(Produs::getCalorii) :
                Comparator.comparingInt(Produs::getCalorii).reversed();

        SortedSet<Produs> setSortat = new TreeSet<>(comparator);
        setSortat.addAll(getToateProdusele());
        return setSortat;
    }


    public List<Produs> getProduseVegane() {
        return produse.stream()
                .filter(Produs::isVegan)
                .collect(Collectors.toList());
    }

}
