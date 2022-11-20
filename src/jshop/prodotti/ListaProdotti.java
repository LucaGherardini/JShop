package jshop.prodotti;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che implementa una lista sfruttando i Generics, utilizzando un ArrayList parametrizzato 
 * per effetturare delle operazioni su di esso. Le classi Catalogo e Carrello derivano direttamente da questa classe, specificando
 * il tipo di dato che vogliono usare per l'ArrayList elenco.
 * @author luca
 * @param <E> Il tipo su cui la lista dovrà operare
 */
public class ListaProdotti<E>{
    /**
     * Protected per permettere alla classe Catalogo di salvare/caricare la Lista tramite serializzazione/deserializzazione.
     * La protezione dei dati è effettutata tramite l'incorporamento delle classi usanti la ListaProdotti in un package apposito
     */
    protected List<E> elenco;
    
    public ListaProdotti(){
        elenco = new ArrayList<>();
    }
    
    /** 
     * @param p L'elemento da aggiungere alla lista
     */
    public void aggiungiElemento(E p){
        elenco.add(p);
    }
    
    /**
     * @param pos La posizione dell'elemento che si vuole rimuovere dalla lista
     */
    public void rimuoviElemento(int pos){
        elenco.remove(pos);
    }
    
    /**
     * Metodo che restituisce il numero di elementi(Prodotti o Acquisti) presenti
     * @return La dimensione della Lista, cioè il numero dei elementi presenti
     */
    public int getSize(){
        return elenco.size();
    }
    
    /**
     * Funzione che restituisce un elemento data la sua posizione all'interno dell'elenco.
     * @param pos La posizione all'interno della lista dell'Elemento che si vuole ottenere
     * @return L'elemento E della Lista
     */
    public E getElemento(int pos){
        return elenco.get(pos);
    }    
}
