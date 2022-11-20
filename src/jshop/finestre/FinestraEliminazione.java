package jshop.finestre;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import jshop.prodotti.*;

/**
 * Finestra usata per eliminare sia prodotti del catalogo da parte dell'amministratore che prodotti acquistati nel carrello
 * @author luca
 * @param <E> Tipo parametrizzato usato per determinare il tipo della lista da gestire, in modo da poter operare su liste diverse in modo coerente
 */
public class FinestraEliminazione<E> extends JFrame implements ActionListener{
    private final int row;
    private final ListaProdotti<E> c;
    private final AbstractTableModel tm;
    private final JLabel jlMex, jlProdotto;
    private final JButton jbConferma;
    private final JPanel p, p2, p3;
    
    /**
     * 
     * @param row La riga del prodotto interessato, passata per poter stampare le informazioni del prodotto e rimuoverlo in caso di conferma
     * @param tabella Il modello della tabella usato, che può essere una istanza della classe TabellaCliente o TabellaCarrello
     * @param c Passo la "lista" che sto usando, in modo da poter eliminare prodotti sia dal catalogo che dal carrello grazie alla regola di conformità
     */
    public FinestraEliminazione(int row, AbstractTableModel tabella, ListaProdotti<E> c){
        super("Eliminazione");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);
        
        this.row = row;
        this.tm = tabella;
        this.c = c;
        
        p = new JPanel();
        p.setLayout(new BorderLayout());
        
        p2 = new JPanel();
        p2.setLayout(new GridLayout(2,1));
        
        jlMex = new JLabel("Eliminare il prodotto selezionato?");
        jlMex.setFont(new Font("", Font.BOLD, 16));
        p2.add(jlMex);
        
        jlProdotto = new JLabel();
        
        /**
         * La classe ListaProdotti può accettare qualsiasi tipo di dato, così come la classe FinestraEliminazione. Tramite 
         * instanceof determino se sto effettivamente usando una istanza di Acquisto o di Prodotto ed invoco i rispettivi 
         * metodi toString() per impostare il testo di jlProdotto
         */
        if(c.getElemento(row) instanceof Acquisto){
            jlProdotto.setText(((Acquisto)c.getElemento(row)).toString());
        }else if(c.getElemento(row) instanceof Prodotto){
            jlProdotto.setText(((Prodotto)c.getElemento(row)).toString());
        }
        jlProdotto.setFont(new Font("Arial", Font.PLAIN, 13));
        p2.add(jlProdotto);
        
        p.add(p2, BorderLayout.NORTH);
        
        p3 = new JPanel();
        
        jbConferma = new JButton("Conferma");
        p3.add(jbConferma);
        jbConferma.addActionListener(this);
        
        p.add(jbConferma, BorderLayout.SOUTH);
        
        add(p);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbConferma){
            c.rimuoviElemento(row);
            tm.fireTableDataChanged();
        }
        this.setVisible(false);
    }
    
}
