package com.tatlicilar.sosyalmedyauygulamasi;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by neval on 11/06/17.
 */

public class Kisi implements Serializable{
    private String isim;
    private String parola;
    private String email;
    private String cinsiyet;
    private String tarih;
    private ArrayList<Kisi> arkadaslarim;
    private int resim;
    private String url;


    public Kisi(String isim, String parola, String email, String cinsiyet, String tarih, int resim,String url) {
        this.isim = isim;
        this.parola = parola;
        this.email = email;
        this.cinsiyet = cinsiyet;
        this.tarih = tarih;
        this.resim = resim;
        this.url = url;
        arkadaslarim = new ArrayList<>();

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public int getResim() {
        return resim;
    }

    public void setResim(int resim) {
        this.resim = resim;
    }

    public ArrayList<Kisi> getArkadaslarim() {
        return arkadaslarim;
    }

    public void setArkadaslarim(ArrayList<Kisi> arkadaslarim) {
        this.arkadaslarim = arkadaslarim;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public void arkadasEkle(Kisi k)
    {
        arkadaslarim.add(k);
    }

    public void arkadasSil(int i) { arkadaslarim.remove(i);}

}


















