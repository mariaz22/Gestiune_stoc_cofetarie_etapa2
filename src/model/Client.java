package model;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private static int counter = 0;
    private int idClient;
    private String nume;
    private int bonusFidelitate;
    private List<Comanda> istoricComenzi;

    public Client(String nume) {
        this.idClient = ++counter;
        this.nume = nume;
        this.bonusFidelitate = 0;
        this.istoricComenzi = new ArrayList<>();
    }

    public int getIdClient() {
        return idClient;
    }

    public String getNume() {
        return nume;
    }

    public int getBonusFidelitate() {
        return bonusFidelitate;
    }

    public void adaugaPuncteFidelitate(int puncte) {
        this.bonusFidelitate += puncte;
    }

    public List<Comanda> getIstoricComenzi() {
        return istoricComenzi;
    }

    public void adaugaComanda(Comanda comanda) {
        istoricComenzi.add(comanda);
        System.out.println("Comanda a fost adăugată în istoric: " + comanda);
    }

    public void afiseazaIstoricComenzi() {
        System.out.println("Istoricul comenzilor pentru clientul " + nume + ":");
        for (Comanda comanda : istoricComenzi) {
            System.out.println("- " + comanda);
        }
    }


    public double calculeazaTotalComenzi() {
        double total = 0;
        for (Comanda comanda : istoricComenzi) {
            total += comanda.calculeazaTotal();
        }
        return total;
    }

    public void adaugaBonusFidelitate(int puncte) {
        if (puncte > 0) {
            bonusFidelitate += puncte;
            System.out.println("Puncte de fidelitate adăugate: " + puncte + ". Total puncte: " + bonusFidelitate);
        } else {
            System.out.println("Numărul de puncte trebuie să fie pozitiv!");
        }
    }

    public boolean verificaComanda(int idComanda) {
        for (Comanda comanda : istoricComenzi) {
            if (comanda.getIdComanda() == idComanda) {
                return true; // Comanda există
            }
        }
        return false; // Comanda nu există
    }

    @Override
    public String toString() {
        return "Client: " + nume + ", Puncte de fidelitate: " + bonusFidelitate;
    }
}
