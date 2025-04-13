package model;

import java.time.LocalDate;

public class IngredientDeBaza extends Ingredient {

    public IngredientDeBaza(String nume, double cantitate, UnitateMasura unitateMasura,
                            LocalDate dataExpirare, double pretPerUnitate) {
        super(nume, cantitate, unitateMasura, dataExpirare, pretPerUnitate);
    }

    @Override
    public String getTip() {
        return "de bază";
    }
}
