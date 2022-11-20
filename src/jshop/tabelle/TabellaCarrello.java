package jshop.tabelle;

import java.awt.event.*;
import javax.swing.JButton;
import jshop.prodotti.*;
import jshop.finestre.FinestraEliminazione;

/**
 * Modello usato dalla tabella del carrello per visualizzare i prodotti acquistati e gestirli
 * @author luca
 */
public class TabellaCarrello extends Tabella implements ActionListener{
    private final Carrello car;
    private final JButton jbRimuovi;
    private int row;
    
    public TabellaCarrello(){
        super(new String[] {"Nome", "Marca", "Codice", "Categoria", "Immagine", "Quantità", "Saldo", ""});
        car = Carrello.getCarrello();
        jbRimuovi = new JButton("Rimuovi");
        jbRimuovi.addActionListener(this);
    }
    
    @Override
    public int getRowCount() {
        return car.getSize();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Acquisto a = car.getElemento(rowIndex);
        if(car.getElemento(rowIndex).getPromozione() == Prodotto.Promozioni.sconto){
            a = car.getElemento(rowIndex);
        }else if(car.getElemento(rowIndex).getPromozione() == Prodotto.Promozioni.trexdue){
            a = car.getElemento(rowIndex);
        }
        switch(columnIndex){
            case 0:
                return a.getNome();
            case 1:
                return a.getMarca();
            case 2:
                return a.getCodice();
            case 3:
                return a.getCategoria();
            case 4:                
                // Le immagini vengono inserite usando un renderer personalizzato nella classe FinestraUtente
                return null;          
            case 5:
                return a.getQuantita();
            
            case 6:
                return "€"+String.format("%.2f",a.getSaldo());
                
            case 7:
                return jbRimuovi;
                
            default:
                return null;
        }
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbRimuovi){
            /**
             * Passo alla finestra di conferma dell'eliminazione la riga effettiva in cui è stata richiesta l'eliminazione,
             * oltre al modello della tabella per notificare il cambiamento dei dati
             */
            FinestraEliminazione<Acquisto> fra = new FinestraEliminazione(row, this, Carrello.getCarrello());
        }
    }
}
