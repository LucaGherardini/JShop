package jshop.prodotti;

/**
 * Classe che rappresenta un acquisto effettuato. Gli attributi sono un riferimento al Prodotto acquistato e la quantità 
 * acquistata. I metodi permettono di ottenere/settare la quantità, mentre dei metodi GET permettono di ottenere
 * le proprietà del prodotto a cui ci si riferisce.
 * @author luca
 */
public class Acquisto{
    private int quantita;
    private final Prodotto p;
    
    public Acquisto(Prodotto p, int quantita) {
        this.p = p;
        this.quantita = quantita;
    }
    
    public void setQuantita(int quantita){
        this.quantita = quantita;
    }
    
    public int getQuantita(){
        return quantita;
    }
    
    public Prodotto.Promozioni getPromozione(){
        return p.getPromozione();
    }
    
    public int getSconto(){
        return p.getSconto();
    }
    
    public float getPrezzo(){
        return p.getPrezzo();
    }
    
    public final String getNome(){
        return p.getNome();
    }
    
    public final String getMarca(){
        return p.getMarca();
    }
    
    public final String getCodice(){
        return p.getCodice();
    }
    
    public final String getCategoria(){
        return p.getCategoria();
    }
    
    public final String getImmagine(){
        return p.getImmagine();
    }
    
    /**
     * Metodo che restituisce una stringa contenente le proprietà salienti di un acquisto
     * @return La stringa contenente le proprietà di un acquisto
     */
    @Override
    public final String toString(){
        return p.toString()+" x "+getQuantita();
    }

    /**
     * Metodo che calcola il saldo di un acquisto, cioè il prezzo del prodotto per il numero di prodotti acquistati
     * @return Il saldo dell'acquisto
     */
    public float getSaldo() {
        return getPrezzo() * getQuantita();  
    }
    
}