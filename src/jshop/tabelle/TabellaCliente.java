package jshop.tabelle;

import java.awt.event.*;
import javax.swing.JButton;
import jshop.prodotti.*;
import jshop.finestre.*;

/**
 * Modello usato per mostrare al cliente i prodotti acquistabili e permetterne l'acquisto
 * @author luca
 */
public class TabellaCliente extends Tabella implements ActionListener{
    private final Catalogo c;
    private final JButton jbAcquista;
    private int row;
    private FinestraCarrello fc;
    private String field, search, categoria;
    
    public TabellaCliente(){
        super(new String[] {"Nome", "Marca", "Codice", "Categoria", "Prezzo", "Immagine", "Promozione", ""});
        c = Catalogo.getCatalogo();
        jbAcquista = new JButton();
        jbAcquista.addActionListener(this);
        field = "";
        search = "";
        categoria = "Scegli per categoria";
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Prodotto p = c.getElemento(rowIndex);
        
        switch (field) {
            case "Nome":
                if(!p.getNome().contains(search)){
                        return null;
                }
                break;
                
            case "Tutti":
                if(!(p.getNome().contains(search) || p.getMarca().contains(search) || p.getCodice().contains(search))){
                    return null;
                }   
        }
        
        if(!categoria.equals("Scegli per categoria")){
            if(!p.getCategoria().equals(categoria)){
                return null;
            }
        }
        
        switch(columnIndex){
            case 7:
                return jbAcquista;
                        
            default:
                return super.getValueAt(rowIndex, columnIndex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbAcquista){
            /**
             * Passo alla istanza della classe FinestraCompra l'istanza della FinestraCarrello, in modo che possa aggiornarne
             * i dati alla conferma dell'acquisto
             */
            FinestraCompra fcompra = new FinestraCompra(row, fc);
        }
    }

    /**
     * Passo al modello la riga su cui è stato cliccato il bottone "Acquista"
     * @param row  Riga su cui è stato cliccato il bottone acquista
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Passo la finestra del carrello usata
     * @param fc Finestra del carrello, aggiornata al termine dell'acquisto
     */
    public void setFinestra(FinestraCarrello fc) {
        this.fc = fc;
    }

    /**
     * Imposto i parametri di ricerca ("Nome", "Tutti o "")
     * @param field La stringa rappresentante il parametro tramite il quale è decretato il criterio di ricerca
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * Passo il contenuto della barra di ricerca
     * @param search Stringa rappresentante la ricerca nella barra di ricerca
     */
    public void setSearch(String search) {
        this.search = search;
    }    

    /**
     * Passo la categoria selezionata, di default "Scegli per categoria"
     * @param categoria Stringa della categoria selezionata
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}