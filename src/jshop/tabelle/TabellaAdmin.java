package jshop.tabelle;

import java.awt.event.*;
import jshop.prodotti.*;
import javax.swing.JButton;
import jshop.finestre.*;

/**
 * Modello usato per mostrare all'amministratore una tabella da cui gestire i prodotti
 * @author luca
 */
public class TabellaAdmin extends Tabella implements ActionListener{
    private final Catalogo c;
    private final JButton jbPromozione, jbModifica, jbRimuovi;
    private int rowEv;
    
    public TabellaAdmin(){
        super(new String[] {"Nome", "Marca", "Codice", "Categoria", "Prezzo", "Immagine", "Promozione corrente", "", "", ""});
        c = Catalogo.getCatalogo();
        jbPromozione = new JButton("Promozione");
        jbModifica = new JButton("Modifica");
        jbRimuovi = new JButton("Rimuovi");
        
        jbPromozione.addActionListener(this);
        jbModifica.addActionListener(this);
        jbRimuovi.addActionListener(this);
        
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 7:
                return jbPromozione;
            case 8:
                return jbModifica;
            case 9:
                return jbRimuovi;
            default:
            return super.getValueAt(rowIndex, columnIndex);
        }
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Evento!");
        if(e.getSource() ==  jbPromozione){
            System.out.println("Ciao");
            FinestraPromozione fp = new FinestraPromozione(rowEv, this);
        }else if(e.getSource() == jbModifica){
            FinestraModifica fm = new FinestraModifica(rowEv, this);
        }else if(e.getSource() == jbRimuovi){
            /**
             * Passo alla finestra di conferma dell'eliminazione la riga effettiva in cui è stata richiesta l'eliminazione,
             * oltre al modello della tabella per notificare il cambiamento dei dati
             */
            FinestraEliminazione<Prodotto> fe = new FinestraEliminazione(rowEv, this, Catalogo.getCatalogo());
        }
    }
    
    /**
     * Funzione tramite la quale comunicare al modello in quale riga si è verificato l'evento
     * @param row La riga del prodotto che viene modificato/rimosso
     */
    public void setRow(int row){
        this.rowEv = row;
    }
}
