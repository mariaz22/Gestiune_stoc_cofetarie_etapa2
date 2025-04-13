import model.*;
import service.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Optional;
import java.util.Set;



//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Serviciile
        ProdusService produsService = new ProdusService();
        ComandaService comandaService = new ComandaService();
        ClientService clientService = new ClientService();

        IngredientDeBaza lapte = new IngredientDeBaza("Lapte", 10.0, UnitateMasura.LITRI, LocalDate.of(2025, 5, 1), 5.0);
        IngredientDeBaza frisca = new IngredientDeBaza("Frișcă", 5.0, UnitateMasura.LITRI, LocalDate.of(2025, 4, 20), 6.0);
        IngredientDeBaza oua = new IngredientDeBaza("Ouă", 25, UnitateMasura.BUCATI, LocalDate.of(2025, 4, 15), 0.9);
        IngredientDeBaza zaharVanilat = new IngredientDeBaza("Zahăr vanilat", 2.0, UnitateMasura.KILOGRAME, LocalDate.of(2026, 1, 1), 10.0);
        IngredientDeBaza cacao = new IngredientDeBaza("Cacao", 3.0, UnitateMasura.KILOGRAME, LocalDate.of(2025, 12, 15), 30.0);
        IngredientDeBaza faina = new IngredientDeBaza("Făină", 10.0, UnitateMasura.KILOGRAME, LocalDate.of(2025, 10, 10), 5.0);
        IngredientDeBaza prafDeCopt = new IngredientDeBaza("Praf de copt", 0.1, UnitateMasura.KILOGRAME, LocalDate.of(2026, 3, 3), 5.0);

        List<Ingredient> ingredienteCrema = List.of(lapte, frisca, zaharVanilat);
        IngredientSpecial crema = new IngredientSpecial("Cremă", 1.0, UnitateMasura.KILOGRAME, LocalDate.of(2025, 4, 25), 25.0, ingredienteCrema);

        List<Ingredient> ingredienteGlazura = List.of(zaharVanilat, frisca);
        IngredientSpecial glazura = new IngredientSpecial("Glazură", 2.8, UnitateMasura.KILOGRAME, LocalDate.of(2025, 4, 27), 22.0, ingredienteGlazura);

        List<Ingredient> ingredienteInitiale = new ArrayList<>();
        ingredienteInitiale.add(lapte);
        ingredienteInitiale.add(frisca);
        ingredienteInitiale.add(oua);
        ingredienteInitiale.add(zaharVanilat);
        ingredienteInitiale.add(cacao);
        ingredienteInitiale.add(faina);
        ingredienteInitiale.add(prafDeCopt);
        ingredienteInitiale.add(glazura);
        ingredienteInitiale.add(faina);

        IngredientService ingredientService = new IngredientService(ingredienteInitiale);

        List<Ingredient> ingredienteTortCiocolata = List.of(ingredienteInitiale.get(0), ingredienteInitiale.get(1), ingredienteInitiale.get(3));
        List<Ingredient> ingredienteTortVanilie = List.of(ingredienteInitiale.get(1), ingredienteInitiale.get(3), ingredienteInitiale.get(4));
        List<Ingredient> ingredienteFursec = List.of(ingredienteInitiale.get(1), ingredienteInitiale.get(3), ingredienteInitiale.get(5));

        Produs tortCiocolata = new Tort(1, "Tort de Ciocolată", 120.0, 10, ingredienteTortCiocolata, LocalDate.of(2025, 6, 15), 1000, 300, Categorie.TORT, false, "Blat de ciocolată", "Glazură de ciocolată", "Aniversare", 8);
        Produs tortVanilie = new Tort(2, "Tort de Vanilie", 100.0, 5, ingredienteTortVanilie, LocalDate.of(2025, 6, 20), 950, 280, Categorie.TORT, false, "Blat de vanilie", "Glazură de frișcă", "Nuntă", 8);
        Produs fursec = new Fursec(3, "Fursec cu Vanilie", 5.0, 50, ingredienteFursec, LocalDate.of(2025, 5, 20), 50, 200, Categorie.FURSEC, false, "Vanilie", "Crema", true, false);

        produsService.adaugaProdus(tortCiocolata);
        produsService.adaugaProdus(tortVanilie);
        produsService.adaugaProdus(fursec);

        Client client1 = new Client("Maria Popescu");
        Client client2 = new Client("Alex Ionescu");
        clientService.adaugaClient(client1);
        clientService.adaugaClient(client2);

        Comanda comanda1 = new Comanda(client1);
        Map<Produs, Integer> listaProduseComanda1 = new HashMap<>();
        listaProduseComanda1.put(tortCiocolata, 2);
        listaProduseComanda1.put(fursec, 5);
        comanda1.setListaProduse(listaProduseComanda1);
        client1.adaugaComanda(comanda1);

        Comanda comanda2 = new Comanda(client2);
        Map<Produs, Integer> listaProduseComanda2 = new HashMap<>();
        listaProduseComanda2.put(tortVanilie, 1);
        comanda2.setListaProduse(listaProduseComanda2);
        client2.adaugaComanda(comanda2);

        comandaService.adaugaComanda(comanda1);
        comandaService.adaugaComanda(comanda2);




        boolean ruleaza = true;

        while (ruleaza) {
            System.out.println("\n~~~ MENIU PRINCIPAL ~~~");
            System.out.println("1. Meniu Client");
            System.out.println("2. Meniu Organizator");
            System.out.println("3. Ieșire");

            System.out.print("Alege o opțiune: ");
            int optiune = scanner.nextInt();
            scanner.nextLine();

            switch (optiune) {
                case 1:
                    meniuClient(scanner, clientService, produsService);
                    break;

                case 2:
                    meniuOrganizator(scanner, produsService, ingredientService, clientService, comandaService);
                    break;

                case 3:
                    ruleaza = false;
                    System.out.println("Ieșire din aplicatie.");
                    break;

                default:
                    System.out.println("Optiune invalida! Te rog sa alegi din nou.");
                    break;
            }
        }
        scanner.close();
    }
    private static void meniuClient(Scanner scanner, ClientService clientService, ProdusService produsService) {
        boolean ruleazaClient = true;

        while (ruleazaClient) {
            System.out.println("\n~~~ MENIU CLIENT ~~~");
            System.out.println("1. Vreau sa vizualizez produsele din cofetarie");
            System.out.println("2. Vreau sa imi vizualizez comenzile");
            System.out.println("3. Vreau sa imi verific punctele de fidelitate");
            System.out.println("4. Vreau să primesc recomandări de produse");
            System.out.println("5. Iesire");

            System.out.print("Alege o opțiune: ");
            int optiuneClient = scanner.nextInt();
            scanner.nextLine();

            switch (optiuneClient) {
                case 1:
                    System.out.println("~~~ Produse disponibile ~~~");
                    produsService.getToateProdusele().forEach(System.out::println);
                    break;

                case 2:
                    System.out.print("Va rog sa va introduceti ID-ul: ");
                    int idClient = scanner.nextInt();
                    Client client = clientService.cautaClientDupaId(idClient).orElse(null);

                    if (client != null) {
                        client.afiseazaIstoricComenzi();
                    } else {
                        System.out.println("Clientul cu ID-ul " + idClient + " nu a fost gasit.");
                    }
                    break;

                case 3:
                    System.out.print("Va rog sa va introduceti ID-u: ");
                    int clientId = scanner.nextInt();
                    Client clientFidelitate = clientService.cautaClientDupaId(clientId).orElse(null);

                    if (clientFidelitate != null) {
                        System.out.println("Clientul " + clientFidelitate.getNume() + " are " + clientFidelitate.getBonusFidelitate() + " puncte de fidelitate.");
                    } else {
                        System.out.println("Clientul cu ID-ul " + clientId + " nu a fost gasit.");
                    }
                    break;
                case 4:
                    System.out.print("Preferi produse vegane? (true/false): ");
                    boolean preferaVegan = scanner.nextBoolean();
                    List<Produs> recomandari = produsService.recomandaProduse(preferaVegan);
                    System.out.println("Produse recomandate:");
                    recomandari.forEach(System.out::println);
                    break;


                case 5:
                    ruleazaClient = false;
                    break;

                default:
                    System.out.println("Optiune invalida! Te rog să alegi din nou.");
                    break;
            }
        }
    }

    private static void meniuOrganizator(Scanner scanner, ProdusService produsService, IngredientService ingredientService, ClientService clientService, ComandaService comandaService) {
        boolean ruleazaOrganizator = true;

        while (ruleazaOrganizator) {
            System.out.println("\n~~~ MENIU ORGANIZATOR ~~~");
            System.out.println("1. Stoc Produse");
            System.out.println("2. Editare Clienți");
            System.out.println("3. Editare Comenzi");
            System.out.println("4. Ieșire");

            System.out.print("Alege o opțiune: ");
            int optiuneOrganizator = scanner.nextInt();
            scanner.nextLine();

            switch (optiuneOrganizator) {
                case 1:
                    stocProduse(scanner, produsService, ingredientService);
                    break;

                case 2:
                    editareClienti(scanner, clientService, comandaService);
                    break;

                case 3:
                    editareComenzi(scanner, comandaService);
                    break;

                case 4:
                    ruleazaOrganizator = false;
                    System.out.println("Iesire din meniul organizatorului.");
                    break;

                default:
                    System.out.println("Optiune invalida! Te rog să alegi din nou.");
                    break;
            }
        }
    }

    private static void stocProduse(Scanner scanner, ProdusService produsService, IngredientService ingredientService) {
        boolean ruleazaStoc = true;

        while (ruleazaStoc) {
            System.out.println("\n~~~ STOC PRODUSE ~~~");
            System.out.println("1. Afisează produse");
            System.out.println("2. Afisează ingrediente");
            System.out.println("3. Verifica dacă sunt disponibile toate ingredientele pentru prepararea unui produs");
            System.out.println("4. Cauta produse după ingredient");
            System.out.println("5. Vizualizeaza produsele sortate după calorii");
            System.out.println("6. Verifica daca exista produse/ingrediente expirate");
            System.out.println("7. Verifica stocuri critice și vizualizeaza alertele de stoc");
            System.out.println("8. Adauga cereri aprovizionare");
            System.out.println("9. Adauga produs");
            System.out.println("10. Adauga ingredient");
            System.out.println("11. Sterge produs");
            System.out.println("12. Sterge ingredient");
            System.out.println("13. Inapoi");

            System.out.print("Alege o opțiune: ");
            int optiuneStoc = scanner.nextInt();
            scanner.nextLine();

            switch (optiuneStoc) {
                case 1:
                    System.out.println("=== Produse disponibile ===");
                    produsService.getToateProdusele().forEach(System.out::println);
                    break;

                case 2:
                    System.out.println("=== Ingrediente disponibile ===");
                    ingredientService.afiseazaIngrediente();
                    break;

                case 3:
                    verificaDacaSePoatePrepara(scanner, produsService, ingredientService);
                    break;

                case 4:
                    System.out.print("Introdu numele ingredientului: ");
                    String numeIng = scanner.nextLine();

                    List<Produs> produseGasite = produsService.cautaDupaIngredient(numeIng);
                    if (produseGasite.isEmpty()) {
                        System.out.println("Nu s-au gasit produse care sa contina ingredientul: " + numeIng);
                    } else {
                        System.out.println("Produse care conțin ingredientul " + numeIng + ":");
                        produseGasite.forEach(System.out::println);
                    }
                    break;

                case 5:
                    System.out.print("Vrei să sortezi crescător (true/false)? ");
                    boolean crescator = scanner.nextBoolean();
                    scanner.nextLine();

                    Set<Produs> produseSortate = produsService.sorteazaDupaCalorii(crescator);
                    System.out.println("Produse sortate după calorii:");
                    produseSortate.forEach(System.out::println);
                    break;


                case 6:
                    afiseazaExpirate(scanner, ingredientService, produsService);
                    break;

                case 7:
                    verificaStocCritic(scanner, ingredientService, produsService);
                    break;

                case 8:
                    System.out.print("Introdu pragul pentru stoc critic: ");
                    double prag = scanner.nextDouble();
                    scanner.nextLine();

                    Distribuitor distribuitor1 = new Distribuitor("Distribuitor SRL", "123456789", "contact@distribuitor.ro", "Str. Salcamilor");


                    List<CerereAprovizionare> cereri = ingredientService.genereazaCereriAprovizionare(prag, distribuitor1);
                    if (cereri.isEmpty()) {
                        System.out.println("Nu exista ingrediente sub pragul de " + prag);
                    } else {
                        System.out.println("\n~~~ Cereri generate ~~~");
                        for (CerereAprovizionare c : cereri) {
                            System.out.println(c);
                            System.out.println();
                        }
                    }
                    break;


                case 9:
                    // Adăugare produs
                    //adaugaProdus(scanner, produsService);
                    break;

                case 10:
                    // Adăugare ingredient
                    //adaugaIngredient(scanner, ingredientService);
                    break;

                case 11:
                    // Șterge produs
                    //stergeProdus(scanner, produsService);
                    break;

                case 12:
                    // Șterge ingredient
                    //stergeIngredient(scanner, ingredientService);
                    break;

                case 13:
                    ruleazaStoc = false;
                    break;

                default:
                    System.out.println("Opțiune invalidă! Te rog să alegi din nou.");
                    break;
            }
        }
    }
    private static void verificaDacaSePoatePrepara(Scanner scanner, ProdusService produsService, IngredientService ingredientService) {
        System.out.println("Introdu ID-ul produsului pentru verificare: ");
        int idProdus = scanner.nextInt();
        scanner.nextLine();

        Optional<Produs> produsOpt = produsService.getToateProdusele()
                .stream()
                .filter(p -> p.getId() == idProdus)
                .findFirst();

        if (produsOpt.isPresent()) {
            Produs produs = produsOpt.get();
            boolean poateFiPreparat = produsService.ingredienteleSuntInStoc(produs, ingredientService.getToateIngredientele());

            if (poateFiPreparat) {
                System.out.println("Produsul \"" + produs.getNume() + "\" poate fi preparat. Ingredientele sunt suficiente.");
            } else {
                System.out.println("Produsul \"" + produs.getNume() + "\" NU poate fi preparat. Lipsesc unele ingrediente sau cantitatea nu este suficienta.");
            }
        } else {
            System.out.println("Nu s-a gasit niciun produs cu ID-ul introdus.");
        }
    }

    private static void afiseazaExpirate(Scanner scanner, IngredientService ingredientService, ProdusService produsService) {
        System.out.println("\n~~~ Verificare Produse/Ingrediente Expirate ~~~");
        System.out.println("1. Afiseaza ingrediente expirate");
        System.out.println("2. Afisează produse expirate");

        System.out.print("Alege o opțiune: ");
        int alegere = scanner.nextInt();
        scanner.nextLine();

        switch (alegere) {
            case 1:
                List<Ingredient> ingredienteExpirate = ingredientService.getIngredienteExpirate();
                if (ingredienteExpirate.isEmpty()) {
                    System.out.println("Nu exista ingrediente expirate.");
                } else {
                    System.out.println("=== Ingrediente Expirate ===");
                    ingredienteExpirate.forEach(System.out::println);
                }
                break;

            case 2:
                List<Produs> produseExpirate = produsService.getProduseExpirate();
                if (produseExpirate.isEmpty()) {
                    System.out.println("Nu exista produse expirate.");
                } else {
                    System.out.println("=== Produse Expirate ===");
                    produseExpirate.forEach(System.out::println);
                }
                break;

            default:
                System.out.println("Opțiune invalidă!");
                break;
        }
    }

    private static void verificaStocCritic(Scanner scanner, IngredientService ingredientService, ProdusService produsService) {
        System.out.print("Introduceți pragul de stoc critic: ");
        double prag = scanner.nextDouble();
        scanner.nextLine();

        List<Ingredient> ingredienteCritice = ingredientService.getIngredienteCritice(prag);
        List<Produs> produseLowStock = produsService.getProduseLowStock((int) prag);

        if (ingredienteCritice.isEmpty() && produseLowStock.isEmpty()) {
            System.out.println("Nu exista alerte de stoc. Toate valorile sunt peste prag.");
            return;
        }

        System.out.println("\n=== Alerta Stoc ===");

        for (Ingredient ing : ingredienteCritice) {
            AlertaStoc alerta = new AlertaStoc(ing, "Ingredientul '" + ing.getNume() + "' are un stoc scăzut: " + ing.getCantitate());
            alerta.trimiteNotificare();
            System.out.println();

        }

        for (Produs produs : produseLowStock) {
            AlertaStoc alerta = new AlertaStoc(produs, "Produsul '" + produs.getNume() + "' are un stoc scazut: " + produs.getCantitate());
            alerta.trimiteNotificare();
            System.out.println();

        }
    }

    private static void editareClienti(Scanner scanner, ClientService clientService, ComandaService comandaService) {
        boolean ruleaza = true;

        while (ruleaza) {
            System.out.println("\n~~~ EDITARE CLIENTI ~~~");
            System.out.println("1. Afișeaza toti clientii");
            System.out.println("2. Adauga puncte de fidelitate");
            System.out.println("3. Afișează clientii fideli");
            System.out.println("4. Afisează comenzile unui client");
            System.out.println("5. Iesire");

            System.out.print("Alege o opțiune: ");
            int optiune = scanner.nextInt();
            scanner.nextLine();

            switch (optiune) {
                case 1:
                    clientService.afiseazaClienti();
                    break;

                case 2:
                    System.out.print("Introduceti ID-ul clientului: ");
                    int id = scanner.nextInt();

                    System.out.print("Introduceti numarul de puncte de fidelitate de adaugat: ");
                    int puncte = scanner.nextInt();
                    scanner.nextLine();

                    clientService.adaugaPuncteFidelitate(id, puncte);
                    break;

                case 3:
                    System.out.print("Introduceți pragul de puncte de fidelitate: ");
                    int prag = scanner.nextInt();
                    scanner.nextLine();
                    clientService.afiseazaClientiCuPuncteFidelitatePesteUnPrag(prag);
                    break;

                case 4:
                    System.out.print("Introduceti ID-ul clientului: ");
                    int idCautat = scanner.nextInt();
                    scanner.nextLine();

                    Optional<Client> clientOpt = clientService.cautaClientDupaId(idCautat);
                    if (clientOpt.isPresent()) {
                        List<Comanda> comenziClient = comandaService.getComenziClient(clientOpt.get());
                        if (comenziClient.isEmpty()) {
                            System.out.println("Clientul nu are comenzi înregistrate.");
                        } else {
                            System.out.println("Comenzile clientului " + clientOpt.get().getNume() + ":");
                            comenziClient.forEach(System.out::println);
                        }
                    } else {
                        System.out.println("Clientul cu ID-ul introdus nu a fost gasit.");
                    }
                    break;

                case 5:
                    ruleaza = false;
                    break;

                default:
                    System.out.println("Optiune invalida! Incearca din nou.");
            }
        }
    }

    private static void editareComenzi(Scanner scanner, ComandaService comandaService) {
        boolean ruleazaComenzi = true;

        while (ruleazaComenzi) {
            System.out.println("\n~~~ Editare Comenzi ~~~");
            System.out.println("1. Afisare comenzi după status");
            System.out.println("2. Modificare status comandă");
            System.out.println("3. Afisare toate comenzile");
            System.out.println("4. Calculează totalul unei comenzi");
            System.out.println("5. Inapoi");

            System.out.print("Alege o optiune: ");
            int optiune = scanner.nextInt();
            scanner.nextLine();

            switch (optiune) {
                case 1:
                    System.out.println("Alege statusul:");
                    System.out.println("1. IN_ASTEPTARE");
                    System.out.println("2. FINALIZATA");
                    System.out.println("3. ANULATA");

                    int statusOptiune = scanner.nextInt();
                    scanner.nextLine();

                    Comanda.StatusComanda status = switch (statusOptiune) {
                        case 1 -> Comanda.StatusComanda.IN_ASTEPTARE;
                        case 2 -> Comanda.StatusComanda.FINALIZATA;
                        case 3 -> Comanda.StatusComanda.ANULATA;
                        default -> null;
                    };

                    if (status != null) {
                        List<Comanda> comenzi = comandaService.filtreazaComenziDupaStatus(status);
                        if (comenzi.isEmpty()) {
                            System.out.println("Nu există comenzi cu statusul ales.");
                        } else {
                            comenzi.forEach(System.out::println);
                        }
                    } else {
                        System.out.println("Optiune invalida.");
                    }
                    break;

                case 2:
                    System.out.print("Introduceți ID-ul comenzii: ");
                    int idComanda = scanner.nextInt();
                    scanner.nextLine(); // Consumăm newline

                    System.out.println("Alegeți noul status:");
                    System.out.println("1. IN_ASTEPTARE");
                    System.out.println("2. FINALIZATA");
                    System.out.println("3. ANULATA");

                    int statusOpt = scanner.nextInt();
                    Comanda.StatusComanda noulStatus = switch (statusOpt) {
                        case 1 -> Comanda.StatusComanda.IN_ASTEPTARE;
                        case 2 -> Comanda.StatusComanda.FINALIZATA;
                        case 3 -> Comanda.StatusComanda.ANULATA;
                        default -> null;
                    };

                    if (noulStatus != null) {
                        comandaService.modificaStatusComanda(idComanda, noulStatus);
                    } else {
                        System.out.println("Status invalid.");
                    }
                    break;

                case 3:
                    comandaService.afiseazaToateComenzile();
                    break;

                case 4:
                    System.out.print("ID comanda pentru calcul total: ");
                    int idTotal = scanner.nextInt();
                    scanner.nextLine();

                    Optional<Comanda> comandaOpt = comandaService.cautaComandaDupaId(idTotal);
                    if (comandaOpt.isPresent()) {
                        double total = comandaOpt.get().calculeazaTotal();
                        System.out.printf("Totalul comenzii #%d este: %.2f RON%n", idTotal, total);
                    } else {
                        System.out.println("Comanda nu a fost găsită.");
                    }
                    break;


                case 5:
                    ruleazaComenzi = false;
                    break;

                default:
                    System.out.println("Opțiune invalidă.");
            }
        }
    }


}