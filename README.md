# Gestiune_Stoc_Cofetarie_etapa2

## Lista de actiuni ale aplicatiei (etapa 1):
    1.recomandare produse pentru client (meniu client)

    2.verificare daca se poate prepara un produs - daca are toate ingredientele in stoc (meniu organizator → stoc produs)

    3.cauta produse care contin un ingredient dat (meniu organizator → stoc produs)

    4.afisaza produsele sortate dupa calorii (meniu organizator → stoc produs)

    5.verifica daca exista produse sau ingrediente expirate (meniu organizator → stoc produs)

    6.verifica daca stocul este critic si genereaza alerte de stoc (meniu organizator → stoc produs)

    7.afisare cereri aprovizionare in functie de prag (meniu organizator → stoc produs)

    8.adaugare puncte de fidelitate pentru un client (meniu organizator → editare client)

    9.afisare clienti fideli (peste un prag de puncte de fidelitate dat  (meniu organizator → editare client)

    10.afisaza comenzile in functie de status (meniu organizator → editare comenzi)

    11.modifica statusul unei comenzi (meniu organizator → editare comenzi)

    12.calculeaza totalul unei comenzi (meniu organizator → editare comenzi)

    13.afisaza comenzile unui anumit client (meniu organizator → editare clienti)

    14.vizualizare istoric comenzi pentru client

    15.vizualizare puncte de fidelitate pentru client


## Realizarea unei baze de date
    -am folosit PostgreSQL
    -am adaugat un fisier cu codul SQL pentru crearea tabelelor in database.sql

## Lista de actiuni ale aplicatiei (etapa 2):
    1.toate operatiile CRUD pentru produse (tabela Produs si cele 4 subtabele ale acesteia) meniu organizator → stoc produs
    2.toate operatiile CRUD pentru tabela Client meniu organizator → editare clienti
    3.toate operatiile CRUD pentru tabela Ingredient (si cele doua subtabele ale acesteia) meniu organizator → stoc produs
    4.toate operatiile CRUD pentru tabela Comanda meniu organizator → editare comenzi

## Servicii singleton generice
  -am creat clasele GenericReadService si GenericWriteService
  -folosite pentru operatiile CRUD pe subtabelele Produs


## Realizarea unui serviciu de audit
  -am creat clasa AuditService
  -se adauga modificarile asupra bazei de date in fisierul audit.csv
