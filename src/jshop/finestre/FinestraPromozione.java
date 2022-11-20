package jshop.finestre;

import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import jshop.prodotti.*;
import jshop.tabelle.TabellaAdmin;

/**
 * Classe che implementa una finestra tramite la quale modificare la promozione attiva su un dato prodotto, con anche la
 * possibilità di disattivarla
 * @author luca
 */
public class FinestraPromozione extends JFrame implements ActionListener, ChangeListener{
    private final JRadioButton jrbNessuna, jrb3x2, jrbSconto;
    private final ButtonGroup bgOpzioni;
    private final JTextField jtfSconto;
    private final JPanel p, p2;
    private final JButton jbOk;
    private final int row;
    private final TabellaAdmin tm;
    private final Catalogo c;
    
    /**
     * Passo una variabile row, rappresentante la riga della tabella nella quale è stato cliccato il pulsante "Promozione", e
     * il modello usato dalla JTable, una istanza di TabellaAdmin, che servirà per aggiornare successivamente la tabella
     * @param row Riga della tabella a cui ci si stà riferendo
     * @param tm Modello della tabella, che andrà aggiornato alla conferma della promozione selezionata
     */
    public FinestraPromozione(int row, TabellaAdmin tm){
        super("Selezione della promozione");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);
        
        this.row = row;
        this.tm = tm;
        c = Catalogo.getCatalogo();
        
        setTitle("Gestione promozione su "+c.getElemento(row).toString());
        
        p = new JPanel();
        p.setLayout(new GridLayout(4,1));
        
        jrbNessuna = new JRadioButton("Nessuna promozione");
        jrb3x2 = new JRadioButton("3x2");
        jrbSconto = new JRadioButton("Sconto percentuale");
        p.add(jrbNessuna);
        
        /**
         * Scelgo che l'abolizione delle promozioni sul prodotto sia la scelta predefinita
         */
        jrbNessuna.setSelected(true);
        p.add(jrb3x2);
        
        /**
         * Ho scelto di creare un secondo pannello per affiancare nella stessa riga il JRadioButton jrbSconto e il 
         * JTextField jtfSconto, andandolo poi ad inserire al pannello principale p
         */
        p2 = new JPanel();
        
        p2.add(jrbSconto);
        
        bgOpzioni = new ButtonGroup();
        bgOpzioni.add(jrbNessuna);
        bgOpzioni.add(jrb3x2);
        bgOpzioni.add(jrbSconto);
        
        /**
         * Uso dei ChangeListener per gestire il cambiamento della selezione: se all'atto del cambiamento di selezione 
         * verrà selezionata o meno l'opzione Sconto, il JTextField jtfSconto verrà abilitato o meno
         */
        jrbNessuna.addChangeListener(this);
        jrb3x2.addChangeListener(this);
        jrbSconto.addChangeListener(this);
        
        jtfSconto = new JTextField(2);
        /**
         * Di default è disabilitato siccome è selezionata l'opzione Nessuna promozione
         */
        jtfSconto.setEnabled(false);
        
        p2.add(jtfSconto);
        p.add(p2);
        
        jbOk = new JButton("Ok");
        jbOk.addActionListener(this);
        p.add(jbOk);
        
        add(p);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbOk){
            
            /**
             * A seconda della opzione selezionata varia il valore della variabile di tipo Promozioni del prodotto selezionato
             */
            if(jrbNessuna.isSelected()){
                c.getElemento(row).setPromozione(Prodotto.Promozioni.nessuna);
            }else if(jrb3x2.isSelected()){
                c.getElemento(row).setPromozione(Prodotto.Promozioni.trexdue);
            }else{
                int sconto;
                /**
                 * Controllo che lo sconto sia un valore intero e che questo sia compreso fra 0 e 100 (esclusi),
                 * in caso di errore mostro una finestra di errore con un messaggio in merito
                 */
                try{
                    sconto = Integer.parseInt(jtfSconto.getText());
                }catch(NumberFormatException er){
                    FinestraErrore fe = new FinestraErrore("Lo sconto inserito è in un formato errato");
                    return ;
                }
                if(sconto<=0 || sconto >=100){
                    FinestraErrore fe = new FinestraErrore("Lo sconto inserito non è compreso fra 0 e 100 (esclusi)");
                    return ;
                }
                /**
                 * Superati i controlli, setto la promozione al valore Sconto e imposto la gravità dello sconto nell'apposito
                 * campo del prodotto
                 */
                c.getElemento(row).setPromozione(Prodotto.Promozioni.sconto);
                c.getElemento(row).setSconto(sconto);
            }  
            
            /**
             * Notifico il cambiamento al modello della tabella, che aggiornerà la riga row interessata
             */
            tm.fireTableRowsUpdated(row, row);
            this.setVisible(false);
        }
        
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(jrbSconto.isSelected()){
            jtfSconto.setEnabled(true);
        }else{
            jtfSconto.setEnabled(false);
        }
    }
}
