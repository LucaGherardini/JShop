package jshop;

import jshop.prodotti.Catalogo;
import jshop.finestre.FinestraAccesso;

/** Programma per la gestione di un negozio online. I prodotti vengono gestiti da un amministratore e venduti ad un cliente.
 * All'avvio dell'applicazione si sceglie quale accesso effettuare, come amministratore o come cliente, con strumenti e visioni
 * diverse. 
 * Un amministratore inserisce, modifica o elimina prodotti, abilita o disabilita promozioni, salva/carica la lista dei prodotti.
 * Un cliente può visualizzare la lista dei prodotti disponibili e acquistarli, andando ad aggiungerli ad un proprio carrello.
 * Successivamente il cliente può confermare i prodotti inseriti nel carrello e procedere all'acquisto, dove inserirà il proprio
 * indirizzo e selezionerà la modalità di pagamento
 * 
 * @author luca
 */
public class JShop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Catalogo c = Catalogo.getCatalogo();
        
        /**
         * Eseguo il caricamento automatico dell'ultimo salvataggio creato/acceduto, nel caso in cui l'amministratore volesse
         * iniziare da capo una lista dovrebbe eliminare il salvataggio automatico o sovrascrivere/eliminare il file lastSave
         * contenuto nella cartella del programma
         */
        c.caricamentoAutomatico();
        FinestraAccesso fa = new FinestraAccesso();
    }
    
}
