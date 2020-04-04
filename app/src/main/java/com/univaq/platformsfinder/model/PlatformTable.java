package com.univaq.platformsfinder.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PlatformTable
{
    @PrimaryKey
    public int id;

    @ColumnInfo (name = "denominazione")
    public String denominazione;

    @ColumnInfo (name = "stato")
    public String stato;

    @ColumnInfo (name = "anno")
    public int anno;

    @ColumnInfo (name = "tipo")
    public String tipo;

    @ColumnInfo (name = "minerale")
    public String minerale;

    @ColumnInfo (name = "operatore")
    public String operatore;

    @ColumnInfo (name = "numero_pozzi_allacciati")
    public int numeroPozziAllacciati;

    @ColumnInfo (name = "pozzi_produttivi_non_eroganti")
    public int pozziProduttiviNonEroganti;

    @ColumnInfo (name = "pozzi_in_produzione")
    public int pozziInProduzione;

    @ColumnInfo (name = "pozzi_in_monitoraggio")
    public int pozziInMonitoraggio;

    @ColumnInfo (name = "titolo_minerario")
    public String titoloMinerario;

    @ColumnInfo (name = "collegata_alla_centrale")
    public String collegataAllaCentrale;

    @ColumnInfo (name = "zona")
    public String zona;

    @ColumnInfo (name = "foglio")
    public int foglio;

    @ColumnInfo (name = "sezione")
    public String sezione;

    @ColumnInfo (name = "capitaneria_di_porto")
    public String capitaneriaDiPorto;

    @ColumnInfo (name = "distanza_costa")
    public int distanzaCosta;

    @ColumnInfo (name = "altezza_slm")
    public int altezzaSlm;

    @ColumnInfo (name = "profondita_fondale")
    public int profonditaFondale;

    @ColumnInfo (name = "dimensioni")
    public String dimensioni;

    @ColumnInfo (name = "longitudine")
    public double longitudine;

    @ColumnInfo (name = "latitudine")
    public double latitudine;
}
