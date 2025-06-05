CREATE TABLE produs (
                        id SERIAL PRIMARY KEY,
                        nume VARCHAR(100) NOT NULL,
                        pret DOUBLE PRECISION NOT NULL,
                        cantitate INTEGER NOT NULL,
                        data_expirare DATE NOT NULL,
                        gramaj DOUBLE PRECISION NOT NULL,
                        calorii INTEGER NOT NULL,
                        categorie VARCHAR(20) NOT NULL CHECK (categorie IN (
                                                                            'TORT',
                                                                            'PRAJITURA',
                                                                            'FURSEC',
                                                                            'TARTA',
                                                                            'CHEC',
                                                                            'DULCIURI'
                            )),
                        este_vegan BOOLEAN NOT NULL
);
CREATE TABLE ingredient (
                            id SERIAL PRIMARY KEY,
                            nume VARCHAR(100) NOT NULL,
                            cantitate DOUBLE PRECISION NOT NULL,
                            unitate_masura VARCHAR(20) NOT NULL CHECK (
                                unitate_masura IN ('GRAME', 'KILOGRAME', 'LITRI', 'MILILITRI', 'BUCATI')
                                ),
                            data_expirare DATE NOT NULL,
                            pret_per_unitate DOUBLE PRECISION NOT NULL
);

CREATE TABLE produs_ingredient (
                                   produs_id INT REFERENCES produs(id) ON DELETE CASCADE,
                                   ingredient_id INT REFERENCES ingredient(id) ON DELETE CASCADE,
                                   PRIMARY KEY (produs_id, ingredient_id)
);

CREATE TABLE bomboana (
                          id INT PRIMARY KEY REFERENCES produs(id) ON DELETE CASCADE,
                          tip_ciocolata VARCHAR(10) CHECK (tip_ciocolata IN ('NEAGRA', 'LAPTE', 'ALBA')),
                          umplutura VARCHAR(255),
                          contine_alcool BOOLEAN NOT NULL
);

CREATE TABLE fursec (
                        id INT PRIMARY KEY REFERENCES produs(id) ON DELETE CASCADE,
                        aroma VARCHAR(50) CHECK (aroma IN (
                                                           'CIOCOLATA', 'VANILIE', 'CAFEA', 'LAMAIE', 'COCOS', 'MENTA', 'FRUCTE_DE_PADURE', 'CARAMEL'
                            )),
                        tip_umplutura VARCHAR(50) CHECK (tip_umplutura IN (
                                                                           'GEM', 'CREMA', 'FRUCTE', 'CIOLCOLATA', 'NUCI', 'VANILIE'
                            )),
                        glazurat BOOLEAN
);
CREATE TABLE patiserie (
                           id INT PRIMARY KEY REFERENCES produs(id) ON DELETE CASCADE,
                           tip VARCHAR(50) CHECK (tip IN ('CROISSANT', 'GOGOASA', 'ECLER', 'TARTA', 'STRUDEL')),
                           timp_coacere INT CHECK (timp_coacere >= 0),
                           este_congelata BOOLEAN
);
CREATE TABLE tort (
                      id INT PRIMARY KEY REFERENCES produs(id) ON DELETE CASCADE,
                      tip_blat VARCHAR(50) CHECK (tip_blat IN ('CIOCOLATA', 'VANILIE', 'LAMAIE', 'CAFEA')),
                      tip_glazura VARCHAR(50) CHECK (tip_glazura IN ('CIOCOLATA', 'VANILIE', 'CARAMEL', 'FRISCA', 'FRUCTE')),
                      eveniment VARCHAR(50) CHECK (eveniment IN ('NUNTA', 'ANIVERSARE', 'BOTEZ')),
                      portii INT CHECK (portii > 0)
);
CREATE TABLE distribuitor (
                              id SERIAL PRIMARY KEY,
                              nume VARCHAR(100) NOT NULL,
                              telefon VARCHAR(20),
                              email VARCHAR(100),
                              adresa TEXT
);

CREATE TABLE cerere_aprovizionare (
                                      id_cerere SERIAL PRIMARY KEY,
                                      ingredient_id INT NOT NULL,
                                      cantitate_ceruta DOUBLE PRECISION CHECK (cantitate_ceruta > 0),
                                      distribuitor_id INT NOT NULL,
                                      status VARCHAR(20) CHECK (status IN ('NECONFIRMATA', 'LIVRATA')) DEFAULT 'NECONFIRMATA',

                                      FOREIGN KEY (ingredient_id) REFERENCES ingredient(id) ON DELETE CASCADE,
                                      FOREIGN KEY (distribuitor_id) REFERENCES distribuitor(id) ON DELETE CASCADE
);

CREATE TABLE client (
                        id_client SERIAL PRIMARY KEY,
                        nume VARCHAR(100) NOT NULL,
                        bonus_fidelitate INT DEFAULT 0
);


CREATE TABLE comanda (
                         id_comanda SERIAL PRIMARY KEY,
                         id_client INT,
                         data_plasare DATE NOT NULL,
                         status VARCHAR(20) NOT NULL CHECK (status IN ('IN_ASTEPTARE', 'FINALIZATA', 'ANULATA')),
                         FOREIGN KEY (id_client) REFERENCES client(id_client) ON DELETE CASCADE
);
CREATE TABLE IngredientDeBaza (
                                  id INT PRIMARY KEY,
                                  FOREIGN KEY (id) REFERENCES Ingredient(id) ON DELETE CASCADE
);
CREATE TABLE IngredientSpecial (
                                   id INT PRIMARY KEY,
                                   timp_preparare INT,
                                   este_produs_intern BOOLEAN,
                                   FOREIGN KEY (id) REFERENCES Ingredient(id) ON DELETE CASCADE
);
CREATE TABLE IngredientSpecial_Ingrediente (
                                               id_special INT,
                                               id_componenta INT,
                                               FOREIGN KEY (id_special) REFERENCES IngredientSpecial(id) ON DELETE CASCADE,
                                               FOREIGN KEY (id_componenta) REFERENCES Ingredient(id),
                                               PRIMARY KEY (id_special, id_componenta)
);

CREATE TABLE comanda_produs (
                                id_comanda INT,
                                id_produs INT,
                                cantitate INT NOT NULL,
                                PRIMARY KEY (id_comanda, id_produs),
                                FOREIGN KEY (id_comanda) REFERENCES comanda(id_comanda) ON DELETE CASCADE,
                                FOREIGN KEY (id_produs) REFERENCES produs(id) ON DELETE CASCADE
);
