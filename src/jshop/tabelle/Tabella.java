package jshop.tabelle;
import jshop.prodotti.Catalogo;
import jshop.prodotti.Prodotto;
import javax.swing.table.AbstractTableModel;

/**
 * Tabella astratta da cui derivano i modelli TabellaAdmin, TabellaCarrello e TabellaCliente, che estende la classe
 * AbstractTableModel definendo alcune proprietà fondamentali della tabella
 * @author luca
 */
public abstract class Tabella extends AbstractTableModel{
    private final Catalogo c;
    private String[] colNames = new String[] {"Nome", "Marca", "Codice", "Categoria", "Prezzo", "Immagine", "Promozione"};
      
    
    public Tabella(){
        super();
        c = Catalogo.getCatalogo();
    }
    
    public Tabella(String[] colNames){
        this();
        this.colNames = colNames;
    }

    @Override
    public String getColumnName(int col) {
        return colNames[col];
    }
    
    @Override
    public int getRowCount() {
        return c.getSize();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Prodotto p = c.getElemento(rowIndex);
        switch(columnIndex){
            case 0:
                return p.getNome();
            case 1:
                return p.getMarca();
            case 2:
                return p.getCodice();
            case 3:
                return p.getCategoria();
            case 4:
                return "€"+String.format("%.2f",p.getPrezzo());
            case 5:                
                // Le immagini vengono inserite usando un renderer personalizzato nella classe FinestraUtente
                return null;
            case 6:
                switch (p.getPromozione()) {
                    case sconto:
                        return p.getSconto()+"% di sconto (€"+String.format("%.2f",(p.getPrezzo()-p.getPrezzo()*p.getSconto()/100))+")";
                    case trexdue:
                        return "3x2";
                    default:
                        return "-";
        }
            default:
                return null;
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int col){
        return false;
    }
}
