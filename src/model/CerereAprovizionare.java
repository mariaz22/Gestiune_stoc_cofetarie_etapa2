package model;

public class CerereAprovizionare {
    private static int counter = 0;
    private int idCerere;
    private Ingredient ingredient;
    private double cantitateCeruta;
    private Distribuitor distribuitor;
    private StatusCerere status;

    public enum StatusCerere {
        NECONFIRMATA,
        LIVRATA
    }

    public CerereAprovizionare(Ingredient ingredient, double cantitateCeruta, Distribuitor distribuitor) {
        this.idCerere = ++counter;
        this.ingredient = ingredient;
        this.cantitateCeruta = cantitateCeruta;
        this.distribuitor = distribuitor;
        this.status = StatusCerere.NECONFIRMATA;
    }

    public int getIdCerere() {
        return idCerere;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public double getCantitateCeruta() {
        return cantitateCeruta;
    }

    public Distribuitor getDistribuitor() {
        return distribuitor;
    }

    public StatusCerere getStatus() {
        return status;
    }

    public void confirmaLivrare() {
        this.status = StatusCerere.LIVRATA;
        System.out.println("Cererea " + idCerere + " a fost confirmată ca livrată.");
    }

    @Override
    public String toString() {
        return "Cerere Aprovizionare #" + idCerere +
                ": " + ingredient.getNume() +
                " (" + cantitateCeruta + " " + ingredient.getUnitateMasura() +
                "), Distribuitor: " + distribuitor.getNume() +
                ", Status: " + status;
    }
}
