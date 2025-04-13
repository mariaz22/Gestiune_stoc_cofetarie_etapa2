package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Comanda {
    private static int counter = 0;
    private int idComanda;
    private Client client;
    private Map<Produs, Integer> listaProduse;
    private LocalDate dataPlasare;
    private StatusComanda status;

    public enum StatusComanda {
        IN_ASTEPTARE,
        FINALIZATA,
        ANULATA
    }

    public Comanda(Client client) {
        this.idComanda = ++counter;
        this.client = client;
        this.listaProduse = new HashMap<>();
        this.dataPlasare = LocalDate.now();
        this.status = StatusComanda.IN_ASTEPTARE;
    }

    public int getIdComanda() {
        return idComanda;
    }

    public Client getClient() {
        return client;
    }

    public void modificaStatus(StatusComanda noulStatus) {
        this.status = noulStatus;
        System.out.println("Statusul comenzii #" + idComanda + " a fost actualizat la: " + noulStatus);
    }

    public Map<Produs, Integer> getListaProduse() {
        return listaProduse;
    }

    public LocalDate getDataPlasare() {
        return dataPlasare;
    }

    public StatusComanda getStatus() {
        return status;
    }

    public void setListaProduse(Map<Produs, Integer> listaProduse) {
        this.listaProduse = listaProduse;
    }

    public void adaugaProdus(Produs produs, int cantitate) {
        listaProduse.put(produs, cantitate);
    }


    public double calculeazaTotal() {
        double total = 0;
        for (Map.Entry<Produs, Integer> entry : listaProduse.entrySet()) {
            total += entry.getKey().getPret() * entry.getValue();
        }
        return total;
    }

    public void exportFactura() {
        System.out.println("Factura pentru comanda #" + idComanda);
        System.out.println("Client: " + client.getNume());
        System.out.println("Data plasare: " + dataPlasare);
        System.out.println("Produse:");
        for (Map.Entry<Produs, Integer> entry : listaProduse.entrySet()) {
            System.out.println("- " + entry.getKey().getNume() + " x " + entry.getValue());
        }
        System.out.printf("Total: %.2f RON%n", calculeazaTotal());
        System.out.println("Status: " + status);
    }

    public void finalizeazaComanda() {
        this.status = StatusComanda.FINALIZATA;
        System.out.println("Comanda #" + idComanda + " a fost finalizată.");
    }


    @Override
    public String toString() {
        return "Comanda #" + idComanda + " - " + client.getNume() + " | Status: " + status;
    }
}
