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
import java.util.Arrays;



//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ProdusService produsService = new ProdusService();
        ComandaService comandaService = new ComandaService();
        ClientService clientService = new ClientService();
        IngredientService ingredientService = new IngredientService();

        /*
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

        Produs tortCiocolata = new Tort(1, "Tort de Ciocolată", 120.0, 10, ingredienteTortCiocolata, LocalDate.of(2025, 6, 15), 1000, 300, Categorie.TORT, false, Tort.TipBlat.CIOCOLATA, Tort.TipGlazura.CIOCOLATA, Tort.Eveniment.ANIVERSARE, 8);
        Produs tortVanilie = new Tort(2, "Tort de Vanilie", 100.0, 5, ingredienteTortVanilie, LocalDate.of(2025, 6, 20), 950, 280, Categorie.TORT, false, Tort.TipBlat.VANILIE, Tort.TipGlazura.FRISCA, Tort.Eveniment.NUNTA, 8);
        Produs fursec = new Fursec(3, "Fursec cu Vanilie", 5.0, 50, ingredienteFursec, LocalDate.of(2025, 5, 20), 50, 200, Categorie.FURSEC, false, Fursec.Aroma.VANILIE, Fursec.TipUmplutura.CREMA,  false);

        //produsService.createProdus(tortCiocolata);
        //produsService.createProdus(tortVanilie);
        //produsService.createProdus(fursec);

        produsService.adaugaProdus(tortCiocolata);
        produsService.adaugaProdus(tortVanilie);
        produsService.adaugaProdus(fursec);

        Client client1 = new Client("Maria Popescu");
        Client client2 = new Client("Alex Ionescu");
        clientService.adaugaClient(client1);
        clientService.adaugaClient(client2);

        Comanda comanda1 = new Comanda(client1.getIdClient());
        Map<Produs, Integer> listaProduseComanda1 = new HashMap<>();
        listaProduseComanda1.put(tortCiocolata, 2);
        listaProduseComanda1.put(fursec, 5);
        comanda1.setListaProduse(listaProduseComanda1);
        client1.adaugaComanda(comanda1);

        Comanda comanda2 = new Comanda(client2.getIdClient());
        Map<Produs, Integer> listaProduseComanda2 = new HashMap<>();
        listaProduseComanda2.put(tortVanilie, 1);
        comanda2.setListaProduse(listaProduseComanda2);
        client2.adaugaComanda(comanda2);

        comandaService.adaugaComanda(comanda1);
        comandaService.adaugaComanda(comanda2);

        //List<Produs> produse = produsService.getAllProduse();
        //List<Client> clienti = clientService.getAllClienti();
        //List<Comanda> comenzi = comandaService.getAllComenzi();
        //List<Ingredient> ingrediente = ingredientService.getAllIngrediente();*/





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
            System.out.println("1. Afiseaza produse");
            System.out.println("2. Afiseaza ingrediente");
            System.out.println("3. Verifica dacă sunt disponibile toate ingredientele pentru prepararea unui produs");
            System.out.println("4. Cauta produse după ingredient");
            System.out.println("5. Vizualizeaza produsele sortate după calorii");
            System.out.println("6. Verifica daca exista produse/ingrediente expirate");
            System.out.println("7. Verifica stocuri critice și vizualizeaza alertele de stoc");
            System.out.println("8. Adauga cereri aprovizionare");
            System.out.println("9. Adauga produs");
            System.out.println("10. Adauga ingredient");
            System.out.println("11. Sterge produs");
            System.out.println("12. Update produs");
            System.out.println("13. Sterge ingredient");
            System.out.println("14. Update ingredient");
            System.out.println("15. Afiseaza produse dupa tip");
            System.out.println("16. Inapoi");

            System.out.print("Alege o opțiune: ");
            int optiuneStoc = scanner.nextInt();
            scanner.nextLine();

            switch (optiuneStoc) {
                case 1:
                    System.out.println("~~~ Produse disponibile ===");
                    produsService.afiseazaProduseBD();
                    break;

                case 2:
                    System.out.println("~~~ Ingrediente disponibile ~~~");
                    ingredientService.afiseazaIngredienteBD();
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
                    adaugaProdus(scanner, produsService);
                    break;

                case 10:
                    //IngredientService ingredientServicen = new IngredientService(new ArrayList<>());
                    IngredientService ingredientServicen = new IngredientService();
                    adaugaIngredient(ingredientService, scanner);
                    break;

                case 11:
                    System.out.print("Introdu ID-ul produsului de sters: ");
                    int idDeSters = scanner.nextInt();
                    scanner.nextLine();

                    boolean sters = produsService.stergeProdus(idDeSters);
                    if (!sters) {
                        System.out.println("Nu s-a putut sterge produsul.");
                    }
                    break;

                case 12:
                    for (Produs p : produsService.getToateProdusele()) {
                        System.out.println(p.getId() + " - " + p.getNume());
                    }

                    System.out.print("Introdu ID-ul produsului de modificat: ");
                    int idModif = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Ce atribut dorești să modifici? (nume, pret, cantitate, data_expirare, gramaj, calorii, categorie, este_vegan): ");
                    String atribut = scanner.nextLine();

                    System.out.print("Introdu noua valoare: ");
                    String valoareNoua = scanner.nextLine();

                    produsService.updateProdus(idModif, atribut, valoareNoua);
                    break;



                case 13:
                    System.out.print("Introdu ID-ul ingredientului de șters: ");
                    int idDeStersingr = scanner.nextInt();
                    scanner.nextLine();

                    boolean stersingr = ingredientService.stergeIngredient(idDeStersingr);
                    if (!stersingr) {
                        System.out.println("Nu s-a putut șterge ingredientul.");
                    } else {
                        System.out.println("Ingredientul a fost șters cu succes.");
                    }
                    break;

                case 14:
                    System.out.print("Atribut de modificat (nume, cantitate, unitate_masura, data_expirare, pret_per_unitate): ");
                    String atribut_ing = scanner.nextLine();

                    System.out.print("Valoare nouă: ");
                    String valoareNoua_ing = scanner.nextLine();

                    System.out.print("ID ingredient: ");
                    int idIng = scanner.nextInt();
                    scanner.nextLine();

                    boolean actualizat = ingredientService.updateIngredient(idIng, atribut_ing, valoareNoua_ing);
                    if (!actualizat) {
                        System.out.println("Nu s-a putut actualiza ingredientul.");
                    }
                    break;

                case 15:

                    System.out.println("Introduceți tipul de produs dorit (TORT / FURSEC / BOMBOANA / PATISERIE):");
                    String tipProdus = scanner.nextLine();

                    produsService.afiseazaProduseDupaTip(tipProdus);

                case 16:
                    ruleazaStoc = false;
                    break;

                default:
                    System.out.println("Optiune invalida! Te rog sa alegi din nou.");
                    break;
            }
        }
    }
    public static void adaugaIngredient(IngredientService ingredientService, Scanner scanner) {
        System.out.println("=== Adaugă un nou ingredient ===");

        System.out.print("Nume ingredient: ");
        String nume = scanner.nextLine();

        System.out.print("Cantitate: ");
        double cantitate = Double.parseDouble(scanner.nextLine());

        System.out.print("Unitate măsură " + Arrays.toString(UnitateMasura.values()) + ": ");
        UnitateMasura unitate = UnitateMasura.valueOf(scanner.nextLine().toUpperCase());


        System.out.print("Data expirare (YYYY-MM-DD): ");
        LocalDate dataExp = LocalDate.parse(scanner.nextLine());

        System.out.print("Preț per unitate: ");
        double pretPerUnitate = Double.parseDouble(scanner.nextLine());

        System.out.print("Este ingredient SPECIAL? (da/nu): ");
        String raspuns = scanner.nextLine().trim().toLowerCase();

        Ingredient ingredient;

        if (raspuns.equals("da")) {
            System.out.print("Timp preparare (minute): ");
            int timpPreparare = Integer.parseInt(scanner.nextLine());

            System.out.print("Este produs intern? (da/nu): ");
            boolean produsIntern = scanner.nextLine().trim().equalsIgnoreCase("da");

            List<Ingredient> componente = new ArrayList<>();

            IngredientSpecial special = new IngredientSpecial(nume, cantitate, unitate, dataExp, pretPerUnitate, componente);
            special.setTimpPreparare(timpPreparare);
            special.setEsteProdusIntern(produsIntern);

            ingredient = special;
        } else {
            ingredient = new IngredientDeBaza(nume, cantitate, unitate, dataExp, pretPerUnitate);
        }

        ingredientService.createIngredient(ingredient);
    }

    public static void adaugaProdus(Scanner scanner, ProdusService produsService) {
        System.out.println("Introdu tipul produsului (TORT / FURSEC / BOMBOANA / PATISERIE):");
        String tipProdus = scanner.nextLine().trim().toUpperCase();

        System.out.println("Nume:");
        String nume = scanner.nextLine();

        System.out.println("Pret:");
        double pret = Double.parseDouble(scanner.nextLine());

        System.out.println("Cantitate:");
        int cantitate = Integer.parseInt(scanner.nextLine());

        System.out.println("Data expirare (YYYY-MM-DD):");
        LocalDate dataExpirare = LocalDate.parse(scanner.nextLine());

        System.out.println("Gramaj (g):");
        double gramaj = Double.parseDouble(scanner.nextLine());

        System.out.println("Calorii:");
        int calorii = Integer.parseInt(scanner.nextLine());

        System.out.println("Categorie " + Arrays.toString(Categorie.values()) + ":");
        Categorie categorie = Categorie.valueOf(scanner.nextLine().trim().toUpperCase());

        System.out.println("Este vegan? (true/false):");
        boolean esteVegan = Boolean.parseBoolean(scanner.nextLine());

        List<Ingredient> ingrediente = new ArrayList<>();

        Produs produs = null;

        switch (tipProdus) {
            case "TORT" -> {
                System.out.println("Tip blat " + Arrays.toString(Tort.TipBlat.values()) + ":");
                Tort.TipBlat tipBlat = Tort.TipBlat.valueOf(scanner.nextLine().trim().toUpperCase());

                System.out.println("Tip glazura " + Arrays.toString(Tort.TipGlazura.values()) + ":");
                Tort.TipGlazura tipGlazura = Tort.TipGlazura.valueOf(scanner.nextLine().trim().toUpperCase());

                System.out.println("Eveniment " + Arrays.toString(Tort.Eveniment.values()) + ":");
                Tort.Eveniment eveniment = Tort.Eveniment.valueOf(scanner.nextLine().trim().toUpperCase());

                System.out.println("Nr portii:");
                int portii = Integer.parseInt(scanner.nextLine());

                produs = new Tort(0, nume, pret, cantitate, ingrediente, dataExpirare, gramaj, calorii, categorie, esteVegan,
                        tipBlat, tipGlazura, eveniment, portii);
            }
            case "FURSEC" -> {
                System.out.println("Aroma " + Arrays.toString(Fursec.Aroma.values()) + ":");
                Fursec.Aroma aroma = Fursec.Aroma.valueOf(scanner.nextLine().trim().toUpperCase());

                System.out.println("Tip umplutura " + Arrays.toString(Fursec.TipUmplutura.values()) + ":");
                Fursec.TipUmplutura tipUmplutura = Fursec.TipUmplutura.valueOf(scanner.nextLine().trim().toUpperCase());

                System.out.println("Este glazurat? (true/false):");
                boolean glazurat = Boolean.parseBoolean(scanner.nextLine());

                produs = new Fursec(0, nume, pret, cantitate, ingrediente, dataExpirare, gramaj, calorii, categorie, esteVegan,
                        aroma, tipUmplutura, glazurat);
            }
            case "BOMBOANA" -> {
                System.out.println("Tip ciocolata (NEAGRA / ALBA / LAPTE):");
                Bomboana.TipCiocolata tipCiocolata = Bomboana.TipCiocolata.valueOf(scanner.nextLine().toUpperCase());

                System.out.println("Umplutura (ex: caramel, gem, etc.):");
                String umplutura = scanner.nextLine();

                System.out.println("Contine alcool? (true/false):");
                boolean contineAlcool = Boolean.parseBoolean(scanner.nextLine());

                produs = new Bomboana(0, nume, pret, cantitate, ingrediente, dataExpirare, gramaj, calorii, categorie, esteVegan,
                        tipCiocolata, umplutura, contineAlcool);
            }
            case "PATISERIE" -> {
                System.out.println("Tip patiserie " + Arrays.toString(Patiserie.TipPatiserie.values()) + ":");
                Patiserie.TipPatiserie tipPatiserie = Patiserie.TipPatiserie.valueOf(scanner.nextLine().trim().toUpperCase());

                System.out.println("Timp coacere (minute):");
                int timpCoacere = Integer.parseInt(scanner.nextLine());

                System.out.println("Este congelata? (true/false):");
                boolean esteCongelata = Boolean.parseBoolean(scanner.nextLine());

                produs = new Patiserie(0, nume, pret, cantitate, ingrediente, dataExpirare, gramaj, calorii, categorie, esteVegan,
                        tipPatiserie, timpCoacere, esteCongelata);
            }
            default -> {
                System.out.println("Tip produs necunoscut.");
                return;
            }
        }

        produsService.createProdus(produs);
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
            System.out.println("5. Adauga client");
            System.out.println("6. Sterge client");
            System.out.println("7. Update informatii client");
            System.out.println("8. Iesire");

            System.out.print("Alege o opțiune: ");
            int optiune = scanner.nextInt();
            scanner.nextLine();

            switch (optiune) {
                case 1:
                    clientService.afiseazaDetaliiClienti();
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
                    System.out.println("~~~ Adaugare client nou ~~~");
                    System.out.print("Introduceti numele clientului: ");
                    String numeClient = scanner.nextLine();

                    Client clientNou = new Client(numeClient);
                    clientService.createClient(clientNou);

                    break;

                case 6:
                    System.out.print("Introduceți ID-ul clientului de șters: ");
                    int idDeSters = scanner.nextInt();
                    clientService.stergeClientBD(idDeSters);
                    break;

                case 7:
                    System.out.print("ID client: ");
                    int clientid = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Atribut de modificat (nume / bonus_fidelitate): ");
                    String atribut = scanner.nextLine();
                    System.out.print("Valoare nouă: ");
                    String valoare = scanner.nextLine();

                    clientService.updateClient(clientid, atribut, valoare);


                case 8:
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
            System.out.println("4. Adauga comanda");
            System.out.println("5. Calculează totalul unei comenzi");
            System.out.println("6. Sterge comanda");
            System.out.println("7. Update elemente comanda");
            System.out.println("8. Inapoi");

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
                    comandaService.afiseazaComenziBD();
                    break;

                case 4:
                    ClientService clientService = new ClientService();
                    ProdusService produsService = new ProdusService();
                    System.out.println("Adaugare comanda noua:");
                    comandaService.createComanda(scanner, clientService, produsService);
                    break;


                case 5:
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

                case 6:
                    System.out.print("Introdu ID-ul comenzii de șters: ");
                    int idComanda1 = scanner.nextInt();
                    comandaService.stergeComandaDinBD(idComanda1);

                    break;

                case 7:

                    System.out.println("Atribute care pot fi modificate: status, data_plasare (format: yyyy-MM-dd), id_client");

                    System.out.print("Introdu ID-ul comenzii de actualizat: ");
                    int idComanda_ = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Introdu numele atributului de modificat: ");
                    String atribut = scanner.nextLine();

                    System.out.print("Introdu noua valoare: ");
                    String valoareNoua = scanner.nextLine();

                    boolean succes = comandaService.updateComanda(idComanda_, atribut, valoareNoua);

                    if (succes) {
                        System.out.println("Actualizarea a fost realizata cu succes.");
                    } else {
                        System.out.println("Actualizarea a esuat.");
                    }

                    break;


                case 8:
                    ruleazaComenzi = false;
                    break;

                default:
                    System.out.println("Opțiune invalidă.");
            }
        }
    }


}