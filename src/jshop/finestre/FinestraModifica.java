package jshop.finestre;

import jshop.prodotti.Catalogo;
import jshop.prodotti.Prodotto;
import jshop.tabelle.TabellaAdmin;
import java.awt.event.ActionListener;

/*
 * Classe che implementa la funzione di modifica in modo molto semplice. Si invoca la super classe FinestraProdotto, riempiendo
 * i campi della scheda del prodotto con quelli del prodotto interessato dalla modifica, attraverso il metodo fillFields, a cui
 * vengono passate le proprietà del prodotto e la riga, identificante il prodotto che dovrà essere aggiornato. Tramite il metodo
 * fillFields viene cambiato anche il testo del bottone jbInserimento, che diventerà "Modifica", in questo modo nel metodo
 * ActionPerformed(...) si potrà identificare il bottone in base al metodo getSource() in unione al metodo getActionCommand()
 * per comportarsi in modi differenti
 * @author luca
 */
public class FinestraModifica extends FinestraProdotto implements ActionListener{
    private final Catalogo c;
    
    public FinestraModifica(int rowEv, TabellaAdmin tm) {
        /**
         * Creo e mostro la finestra dell'inserimento del prodotto
         */
        super(tm);
        
        c = Catalogo.getCatalogo();
        Prodotto p = c.getElemento(rowEv);
        
        /**
         * Tramite il metodo fillFields inizializzo i campi del modulo del prodotto con quelli del prodotto interessato, passando
         * anche la riga in cui si è verificato l'evento per aggiornarlo successivamente
         */
        super.fillFields(p.getNome(), p.getMarca(), p.getCodice(), p.getPrezzo(), p.getCategoria(), p.getImmagine(), rowEv);
    }
}
