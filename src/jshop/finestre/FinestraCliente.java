package jshop.finestre;

import jshop.prodotti.Catalogo;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.table.*;
import jshop.tabelle.TabellaCliente;
import javax.swing.event.DocumentListener;

/**
 * Finestra che mostra al cliente il catalogo, permettendo l'acquisto dei prodotti e la visualizzazione del carrello
 * @author luca
 */
public class FinestraCliente extends FinestraUtente implements ActionListener, ChangeListener, DocumentListener{
    private final JButton jbAcquista, jbCarrello;
    private final JLabel jlRicerca;
    private JFrame dialog;
    private final JTextField jtfRicerca;
    private final JRadioButton jrbNome, jrbTutti;
    private final JComboBox jcbCategoria;
    private final FinestraCarrello fc;
    
    public FinestraCliente() {
        super("JShop");
        
        /**
         * Un cliente non ha la capacità di caricare liste autonomamente, per cui il catalogo che gli viene mostrato viene
         * caricato automaticamente all'avvio del programma. Se l'ultimo salvataggio non è reperibile l'admin deve crearne uno.
         */
        if(Catalogo.getCatalogo().getSize()==0){
            super.setVisible(false);
            setVisible(false);
            FinestraErrore fe = new FinestraErrore("Impossibile caricare il catalogo, contattare l'amministratore");
            fe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        
        /**
         * Creo una istanza della classe FinestraCarrello, in modo da poterla aggiornare quando acquisto un prodotto e poterla
         * mostrare quando clicco sul bottone "Carrello"
         */
        fc = new FinestraCarrello();
        
        jtTabella.setModel(new TabellaCliente());
        impostaTabella();
        
        // Questo bottone non va aggiunto alla finestra, verrà aggiunto alla tabella dal renderer
        jbAcquista = new JButton("Acquista");
        
        JPanel b = new JPanel();
        b.setLayout(new BorderLayout());
        
        jbCarrello = new JButton("Carrello");
        b.add(jbCarrello, BorderLayout.WEST);
        
        JPanel b2 = new JPanel();
        
        jcbCategoria = new JComboBox();
        caricaCategorie();
        b2.add(jcbCategoria);
        
        jlRicerca = new JLabel("Ricerca:");
        b2.add(jlRicerca);
        
        jtfRicerca = new JTextField(25);
        b2.add(jtfRicerca);
        
        jrbNome = new JRadioButton("Per nome");
        jrbNome.setSelected(true);
        b2.add(jrbNome);
        
        jrbTutti = new JRadioButton("Per tutti i campi");        
        b2.add(jrbTutti);        
        
        ButtonGroup bg = new ButtonGroup();
        bg.add(jrbNome);
        bg.add(jrbTutti);
        b.add(b2);
        
        super.add(b, BorderLayout.NORTH);        
        
        jbCarrello.addActionListener(this);
        jtfRicerca.addActionListener(this);
        jcbCategoria.addActionListener(this);
        
        jrbNome.addChangeListener(this);
        jrbTutti.addChangeListener(this);
        
        jtfRicerca.getDocument().addDocumentListener(this);
        
        pack();
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbCarrello){
            
            /**
             * Mediante il metodo aggiornaTabella(), aggiorno il contenuto del carrello e mostro la finestra 
             */
            fc.aggiornaTabella();
            fc.setVisible(true);
        }else if(e.getSource() == jcbCategoria){
            ricerca();
        }
    }
    
    /**
     * Metodo che imposta sul Modello della tabella i parametri per filtrare i prodotti da acquistare, se la barra della ricerca
     * è vuota non vengono considerati i parametri di ricerca
     */
    public void ricerca(){
        if(jtfRicerca.getText().equals("")){            
            ((TabellaCliente)jtTabella.getModel()).setField("");
        }else if(jrbNome.isSelected()){
            ((TabellaCliente)jtTabella.getModel()).setField("Nome");
        }else if(jrbTutti.isSelected()){
            ((TabellaCliente)jtTabella.getModel()).setField("Tutti");
        }
        
        ((TabellaCliente)jtTabella.getModel()).setCategoria((String)jcbCategoria.getSelectedItem());
        
        
        if(!jtfRicerca.getText().equals("")){            
            ((TabellaCliente)jtTabella.getModel()).setSearch(jtfRicerca.getText());
        }
        
        ((TabellaCliente)jtTabella.getModel()).fireTableDataChanged();
        
        filtra();        
    }
    
    /**
     * Metodo tramite il quale filtrare le righe mostrate dalla tabella
     */
    public void filtra(){                
      TableRowSorter<TableModel> sorter = new TableRowSorter<>(jtTabella.getModel());
      RowFilter hidefilter = getRowFilter();
      sorter.setRowFilter(hidefilter);
      jtTabella.setRowSorter(sorter);  
    }  

    /**
     * Metodo che imposta un filtro per la tabella
     * @return Il RowFilter usato per filtrare le righe mostrate dalla tabella
     */
    private RowFilter getRowFilter(){
        RowFilter<DefaultTableModel, Integer> filter = new RowFilter<DefaultTableModel, Integer>() {

        @Override
        public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                     
                //Le righe che non devono essere mostrate vengono marchiata dal modello della tabella con null
                return entry.getValue(0) != null;
            }
        };
        return filter;
    }
    
    /**
     * Metodo per caricare un JComboBox con le categorie esistenti
     */
    public final void caricaCategorie(){
        Catalogo c = Catalogo.getCatalogo();
        boolean categoriaPresente;
        jcbCategoria.addItem("Scegli per categoria");
        for(int k=0; k<c.getSize(); k++){
            categoriaPresente = false;
            for(int j=0; j<jcbCategoria.getItemCount(); j++){
                if(c.getElemento(k).getCategoria().equals(jcbCategoria.getItemAt(j))){
                    categoriaPresente = true;
                }
            }
            if(!categoriaPresente){
               jcbCategoria.addItem(c.getElemento(k).getCategoria());
            }
        }
    }
    
    @Override
    protected final void impostaTabella(){
        super.impostaTabella();
        
        jtTabella.getColumnModel().getColumn(5).setCellRenderer((TableCellRenderer)getRenderer());
        jtTabella.getColumnModel().getColumn(7).setCellRenderer((TableCellRenderer)getRenderer()); 
        
        jtTabella.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                /**
                * Cattura la riga e la colonna nella quale si è verificato l'evento
                */
                int rowEv = jtTabella.rowAtPoint(e.getPoint());
                int colEv = jtTabella.columnAtPoint(e.getPoint());
                
                Catalogo c = Catalogo.getCatalogo();
                
                for(int k=0;k<c.getSize();k++){        
                    if(jtTabella.getValueAt(rowEv, 2) == c.getElemento(k).getCodice()){
                        /**
                        * Ottengo l'oggetto su cui si è verificato il click, è sicuramente un bottone ma per sicurezza controllo
                        * che sia una istanza della classe JButton
                        */
                        Object value = jtTabella.getValueAt(rowEv, colEv);
                    
                        if(value instanceof JButton){
                        
                            /**
                            * Passo al modello usato dalla tabella la riga in cui si è verificato l'evento, 
                            * in modo da permetterne la distinzione
                            */
                            ((TabellaCliente)jtTabella.getModel()).setRow(k);
                        
                            /**
                            * Passo al modello l'istanza della classe FinestraCarrello
                            */
                            ((TabellaCliente)jtTabella.getModel()).setFinestra(fc);
                                
                            /**
                            * Simulo il click sul bottone
                            */
                            ((JButton)jtTabella.getValueAt(rowEv, colEv)).doClick();
                               
                            return ;
                        }
                    }   
                }
            }
        });
    }  
    
    @Override
    protected Component getRenderer() {
        return new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
                                
                switch (column) {
                    
                    case 5:
                        JLabel jlImg = new JLabel();
                        Catalogo c = Catalogo.getCatalogo();
                        int k;
                        for(k=row;k<c.getSize();k++){
                            if(jtTabella.getValueAt(row, 2) == c.getElemento(k).getCodice()){
                                break;
                            }
                        }
                        ImageIcon ic = new ImageIcon(c.getElemento(k).getImmagine());
                        jlImg.setIcon(ic);   
                        return jlImg;
                    
                    case 7:
                        return jbAcquista;
                }
                return null;
            }
        };
    }    

    /**
     * Quando cambio il criterio di ricerca la rifaccio
     * @param e evento scatenato di tipo ChangeEvent
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        ricerca();
    }
    
    @Override
    public void changedUpdate(DocumentEvent e){
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        ricerca();
    }

    @Override
    public void removeUpdate(DocumentEvent e){
        ricerca();
    }
}