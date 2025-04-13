package service;

import model.Client;
import model.Comanda;
import model.Produs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComandaService {

    private List<Comanda> comenzi;

    public ComandaService() {
        this.comenzi = new ArrayList<>();
    }



    public Comanda plaseazaComanda(Client client, List<Produs> produse, List<Integer> cantitati) {
        if (produse.size() != cantitati.size()) {
            throw new IllegalArgumentException("Lista de produse și cantități trebuie să aibă aceeași dimensiune.");
        }

        Comanda comanda = new Comanda(client);

        for (int i = 0; i < produse.size(); i++) {
            comanda.adaugaProdus(produse.get(i), cantitati.get(i));
        }

        comenzi.add(comanda);
        client.adaugaComanda(comanda);

        System.out.println("Comanda a fost plasată cu succes: " + comanda);
        return comanda;
    }

    public void adaugaComanda(Comanda comanda) {
        comenzi.add(comanda);
    }


    public List<Comanda> filtreazaComenziDupaStatus(Comanda.StatusComanda status) {
        return comenzi.stream()
                .filter(c -> c.getStatus() == status)
                .toList();
    }

    public void modificaStatusComanda(int idComanda, Comanda.StatusComanda noulStatus) {
        Optional<Comanda> comandaOpt = cautaComandaDupaId(idComanda); // Presupunând că ai această metodă
        if (comandaOpt.isPresent()) {
            comandaOpt.get().modificaStatus(noulStatus);
        } else {
            System.out.println("Comanda cu ID-ul " + idComanda + " nu a fost găsită.");
        }
    }


    public boolean finalizeazaComanda(int idComanda) {
        Optional<Comanda> comandaOpt = comenzi.stream()
                .filter(c -> c.getIdComanda() == idComanda)
                .findFirst();

        if (comandaOpt.isPresent()) {
            comandaOpt.get().finalizeazaComanda();
            return true;
        }

        System.out.println("Comanda cu ID-ul " + idComanda + " nu a fost găsită.");
        return false;
    }

    public void afiseazaToateComenzile() {
        if (comenzi.isEmpty()) {
            System.out.println("Nu există comenzi înregistrate.");
            return;
        }
        comenzi.forEach(System.out::println);
    }

    public List<Comanda> getComenziClient(Client client) {
        return comenzi.stream()
                .filter(c -> c.getClient().equals(client))
                .toList();
    }

    public Optional<Comanda> cautaComandaDupaId(int idComanda) {
        return comenzi.stream()
                .filter(c -> c.getIdComanda() == idComanda)
                .findFirst();
    }

    public boolean stergeComanda(int idComanda) {
        return comenzi.removeIf(c -> c.getIdComanda() == idComanda);
    }
}
