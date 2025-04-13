// model/AlertaStoc.java
package model;

import java.time.LocalDateTime;

public class AlertaStoc {
    private static int counter = 0;
    private int idAlerta;
    private Object entitate;
    private String mesaj;
    private LocalDateTime dataGenerare;

    public AlertaStoc(Object entitate, String mesaj) {
        this.idAlerta = ++counter;
        this.entitate = entitate;
        this.mesaj = mesaj;
        this.dataGenerare = LocalDateTime.now();
    }

    public int getIdAlerta() {
        return idAlerta;
    }

    public Object getEntitate() {
        return entitate;
    }

    public String getMesaj() {
        return mesaj;
    }

    public LocalDateTime getDataGenerare() {
        return dataGenerare;
    }

    public void trimiteNotificare() {
        System.out.println("ALERTĂ STOC: " + mesaj);
        System.out.println("Entitate: " + entitate);
        System.out.println("Generată la: " + dataGenerare);
    }
}
