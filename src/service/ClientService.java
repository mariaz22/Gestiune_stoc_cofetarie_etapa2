package service;

import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientService {
    private List<Client> clienti;

    public ClientService() {
        this.clienti = new ArrayList<>();
    }

    public void adaugaClient(Client client) {
        clienti.add(client);
        System.out.println("Clientul " + client.getNume() + " a fost adăugat.");
    }

    public boolean stergeClient(int idClient) {
        return clienti.removeIf(client -> client.getIdClient() == idClient);
    }

    public Optional<Client> cautaClientDupaId(int idClient) {
        return clienti.stream()
                .filter(client -> client.getIdClient() == idClient)
                .findFirst();
    }

    public void adaugaPuncteFidelitate(int idClient, int puncte) {
        Optional<Client> clientOpt = cautaClientDupaId(idClient);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            client.adaugaPuncteFidelitate(puncte);
            System.out.println("Au fost adăugate " + puncte + " puncte de fidelitate clientului " + client.getNume() + ".");
        } else {
            System.out.println("Clientul cu ID " + idClient + " nu a fost găsit.");
        }
    }


    public void afiseazaClienti() {
        if (clienti.isEmpty()) {
            System.out.println("Nu există clienți înregistrati.");
        } else {
            System.out.println("=== Lista Clienților ===");
            for (Client client : clienti) {
                System.out.println(client);
            }
        }
    }

    public void afiseazaClientiCuPuncteFidelitatePesteUnPrag(int prag) {
        List<Client> clientiFiltrati = clienti.stream()
                .filter(c -> c.getBonusFidelitate() > prag)
                .toList();

        if (clientiFiltrati.isEmpty()) {
            System.out.println("Nu există clienți cu mai mult de " + prag + " puncte de fidelitate.");
        } else {
            System.out.println("Clienți cu mai mult de " + prag + " puncte de fidelitate:");
            clientiFiltrati.forEach(System.out::println);
        }
    }


    public void afiseazaIstoricComenzi(int idClient) {
        Optional<Client> clientOpt = cautaClientDupaId(idClient);
        if (clientOpt.isPresent()) {
            clientOpt.get().afiseazaIstoricComenzi();
        } else {
            System.out.println("Clientul cu ID " + idClient + " nu a fost găsit.");
        }
    }


    public boolean verificaClient(int idClient) {
        return clienti.stream().anyMatch(client -> client.getIdClient() == idClient);
    }
}
