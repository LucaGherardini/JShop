package jshop.finestre;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import jshop.prodotti.Carrello;
import jshop.tabelle.TabellaCarrello;

/**
 * Classe che implementa una finestra rappresentante il carrello dell'utente
 * @author luca
 */
public class FinestraCarrello extends FinestraUtente implements ActionListener{
    private final JButton jbRimuovi, jbSvuotaCarrello, jbConferma;
    private final JPanel p;
    
    public FinestraCarrello(){
        super("Carrello");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        
        jtTabella.setModel(new TabellaCarrello());
        impostaTabella();
        
        // Usato dal renderer personalizzato per aggiungere l'icona del bottone alla tabella
        jbRimuovi = new JButton("Rimuovi");
        
        p = new JPanel();
        
        jbSvuotaCarrello = new JButton("Svuota Carrello");
        p.add(jbSvuotaCarrello);
        
        jbConferma = new JButton("Conferma gli acquisti");
        p.add(jbConferma);
        
        super.add(p, BorderLayout.NORTH);
        
        jbSvuotaCarrello.addActionListener(this);
        jbConferma.addActionListener(this);
        
        pack();
    }
    
    /**
     * Funzione che imposta alcune proprietà della tabella, richiamando anche quella della classe padre FinestraUtente
     * Si setta la larghezza preferita di alcune colonne e si richiama su di esse un renderer personalizzato.
     * La colonna 5 si rifà al renderer della classe padre, mentre la 6/7/8 a quello della classe, che conterrà i JButton
     */
    @Override
    protected final void impostaTabella(){
        super.impostaTabella();
        
        jtTabella.getColumnModel().getColumn(4).setCellRenderer((TableCellRenderer)getRenderer());
        jtTabella.getColumnModel().getColumn(7).setCellRenderer((TableCellRenderer)getRenderer());
        
        jtTabella.getColumnModel().getColumn(4).setMinWidth(100);
        jtTabella.getColumnModel().getColumn(4).setMaxWidth(100);
        
        /**
         * Creo un MouseListener e lo assegno alla tabella. All'avvenimento catturo le coordinate e le trasformo nelle
         * corrispettive righe e colonne, per poi simulare la pressione del bottone sul JButton corrispondente della classe
         * TabellaCarrello
         */
        jtTabella.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent evt) {
        
                /**
                * Catturo la riga e la colonna nella quale si è verificato l'evento
                */
                int row = jtTabella.rowAtPoint(evt.getPoint());
                int col = jtTabella.columnAtPoint(evt.getPoint());
            
                /**
                * Solo i bottoni sono "cliccabili", per cui solo la 9° colonna può scatenare l'evento
                */
                if(row>=0 && row<jtTabella.getRowCount() && col==7 && col<jtTabella.getColumnCount()){
                    /**
                    * Ottengo l'oggetto su cui si è verificato il click, è sicuramente un bottone ma per sicurezza controllo
                    * che sia una istanza della classe JButton
                    */
                    Object value = jtTabella.getValueAt(row, col);
                    if (value instanceof JButton) {
                        /**
                        * Passo al modello usato dalla tabella la riga in cui si è verificato l'evento, in modo da permetterne
                        * la distinzione
                        */
                        ((TabellaCarrello)jtTabella.getModel()).setRow(row);
                        
                        /**
                        * Simulo il click sul bottone
                        */
                        ((JButton)jtTabella.getValueAt(row, col)).doClick();
                        
                    }   
                }
            }   
        });
    }
    
    /**
     * Devo sovrascrivere il renderer usato dalla classe FinestraUtente, perché in questo caso devo accedere al carrello, non
     * al catalogo, e le colonne sono diverse
     * @return Il componente da inserire nella tabella, che può essere l'immagine del prodotto o il bottone Rimuovi
     */
    @Override
    protected Component getRenderer() {
        return new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
                                
                if(column == 4){
                    JLabel jlImg = new JLabel();
                    Carrello car = Carrello.getCarrello();
                    ImageIcon ic = new ImageIcon(car.getElemento(row).getImmagine()); // Il renderer personalizzato è usato solo per le immagini
                    jlImg.setIcon(ic);   
                    return jlImg;
                }else if(column == 7){
                    return jbRimuovi;
                }
                return null;
            }
        };
    }

    /**
     * Metodo usato per aggiornare il modello della tabella rappresentante il carrello dalla FinestraCliente.
     */
    public void aggiornaTabella() {
        System.out.println("Aggiorno il carrello!");
        ((TabellaCarrello)jtTabella.getModel()).fireTableDataChanged();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbSvuotaCarrello){
            Carrello car = Carrello.getCarrello();
            car.svuotaCarrello((TabellaCarrello)jtTabella.getModel(), "Svuotare il carrello?");
            ((TabellaCarrello)jtTabella.getModel()).fireTableDataChanged();
        }else if(e.getSource() == jbConferma){
            FinestraCheckOut fco = new FinestraCheckOut((TabellaCarrello)jtTabella.getModel());
        }
    }
}