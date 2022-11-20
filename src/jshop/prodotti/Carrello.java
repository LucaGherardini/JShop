package jshop.prodotti;

import java.util.ArrayList;
import jshop.finestre.FinestraSvuotaCarrello;
import jshop.tabelle.TabellaCarrello;

/** 
 * Classe rappresentante un carrello, che gestisce una lista di prodotti di tipo Acquisto
 * @author luca
 */
public class Carrello extends ListaProdotti<Acquisto>{
    private static Carrello c = null;
    
    private Carrello(){
        super();
    }
    
    public static Carrello getCarrello(){
        if(c == null){
            c = new Carrello();
        }
        return c;
    }

    /**
     * Metodo usato per richiamare una finestra di conferma allo svuotamento
     * @param tm Modello della tabella del carrello, per poterla aggiornare 
     * @param mex Messaggio da far visualizzare all'utente
     */
    public void svuotaCarrello(TabellaCarrello tm, String mex) {
        FinestraSvuotaCarrello fsc = new FinestraSvuotaCarrello(c, tm, mex);
    }

    /**
     * Quando la finestra FinestraSvuotaCarrello riceve la conferma a svuotare il carrello, viene invocato il metodo svuotaLista
     * @param tm Il modello della tabella del Carrello, che va aggiornato una volta svuotata la lista
     */
    public void svuotaLista(TabellaCarrello tm) {
        elenco = new ArrayList<>();
        tm.fireTableDataChanged();
    }
}