package jshop.prodotti;

import java.io.Serializable;

/**
 * Classe rappresentante un prodotto, contenente gli attributi e le operazioni permesse. I controlli sui valori
 * (es. prezzi negativi o nulli) vengono svolti dalle opportune Finestre
 * @author luca
 */
public class Prodotto implements Serializable{
    private String nome, marca, codice, categoria, immagine;
    
    /**
     * Uso un tipo enumerato per rappresentare la tipologia di promozioni possibili
     */
    public enum Promozioni{
        nessuna,
        trexdue,
        sconto;
    }
    
    private Promozioni promo;
    private float prezzo;
    private int sconto;
    
    /**
     * 
     * @param nome Il nome del prodotto
     * @param marca La marca del prodotto
     * @param codice Il codice, univoco in tutto il catalogo, del prodotto
     * @param categoria La categoria del prodotto
     * @param immagine Il percorso dell'immagine del prodotto
     * @param prezzo Il valore del prodotto
     */
    public Prodotto(String nome, String marca, String codice, String categoria, String immagine, float prezzo){
        this.nome = nome;
        this.marca = marca;
        this.codice = codice;
        this.categoria = categoria;
        this.immagine = immagine;
        this.prezzo = prezzo;
        this.promo = Promozioni.nessuna;
        this.sconto = 0;
    }
    
    // Dichiarazione metodi SET
    
    public final void setNome(String nome){
        this.nome = nome;
    }
    
    public final void setMarca(String marca){
        this.marca = marca;
    }
    
    public final void setCodice(String codice){
        this.codice = codice;
    }
    
    public final void setCategoria(String categoria){
        this.categoria = categoria;
    }
    
    public final void setImmagine(String immagine){
        this.immagine = immagine;
    }
    
    public final void setPrezzo(float prezzo){
        this.prezzo = prezzo;
    }
    
    public final void setPromozione(Promozioni p){
        promo = p;
    }
    
    public final void setSconto(int sconto){
        this.sconto = sconto;
    }
    
    // Dichiarazione metodi GET
    
    public final String getNome(){
        return nome;
    }
    
    public final String getMarca(){
        return marca;
    }
    
    public final String getCodice(){
        return codice;
    }
    
    public final String getCategoria(){
        return categoria;
    }
    
    public final String getImmagine(){
        return immagine;
    }
    
    public final float getPrezzo(){
        return prezzo;
    }
    
    public final Promozioni getPromozione(){
        return promo;
    }
    
    public final int getSconto(){
        return sconto;
    }
    
    /**
     * @return Una stringa con le proprietà più importanti di un prodotto
     */
    @Override
    public final String toString(){
        return nome+" "+marca+" "+codice+" "+String.format("%.2f",prezzo)+"€";
    }
}
