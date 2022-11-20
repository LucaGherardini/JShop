package jshop.finestre;
import jshop.prodotti.Catalogo;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.File;
import javax.swing.table.*;
import jshop.tabelle.TabellaAdmin;

/**
 * Classe che eredita dalla classe astratta FinestraUtente, classe che crea un JFrame con le proprietà fondamentali, 
 * contenente una tabella per la visualizzazione/gestione dei prodotti presenti e dei bottoni per 
 * le funzionalità di salvataggio/caricamento del catalogo e di aggiunta di un prodotto. Nella stessa classe è implementato
 * l'action listener per gestire gli eventi
 * @author luca
 */
public class FinestraAmministratore extends FinestraUtente implements ActionListener{
    private final JPanel b;
    private final LayoutManager mgrB;
    private final JButton jbAggiungiProdotto, jbCaricaLista, jbSalvaLista, jbPromozione, jbModifica, jbRimuovi;
    private final JFileChooser jfcFileLista;
    private final FileNameExtensionFilter filter;
    
    public FinestraAmministratore() {
        super("Centro di controllo");
        
        mgrB = new BorderLayout();
        b = new JPanel();
        b.setLayout(mgrB);
        
        /**
         * Imposto il nuovo modello per la tabella, che cambia solo quello che serve rispetto al modello base
         */
        jtTabella.setModel(new TabellaAdmin());
        impostaTabella();
        
        jbAggiungiProdotto = new JButton("Aggiungi Prodotto");
        b.add(BorderLayout.CENTER, jbAggiungiProdotto);
        
        jbCaricaLista = new JButton("Carica lista");
        b.add(BorderLayout.EAST, jbCaricaLista);
        
        jbSalvaLista = new JButton("Salva lista");
        b.add(BorderLayout.WEST, jbSalvaLista);
        
        add(BorderLayout.NORTH, b);
        
        filter = new FileNameExtensionFilter("Salvataggio .ser","ser");
        jfcFileLista = new JFileChooser();
        jfcFileLista.setFileFilter(filter);
        
        /**
         * Questi bottoni fungono da "etichette" per la tabella, e verranno usati dal renderer personalizzato
         * di questa classe per impostare nelle rispettive colonne le icone dei bottoni. La vera azione è catturata sui bottoni 
         * omonimi della classe TabellaAdmin
         */
        jbPromozione = new JButton("Promozione");
        jbModifica = new JButton("Modifica");
        jbRimuovi = new JButton("Rimuovi");
        
        jbAggiungiProdotto.addActionListener(this);
        jbCaricaLista.addActionListener(this);
        jbSalvaLista.addActionListener(this);
        jfcFileLista.addActionListener(this);
        
        pack();
        setVisible(true);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbCaricaLista){
            /**
             * Creo un frame contenente una interfaccia per la scelta del salvataggio da caricare
             */
            JFrame dialog = new JFrame("Scegli il salvataggio");
           
            /**
             * Mostro il Dialog di "Apertura" di un file e assegno alla variabile intera rVal
             * l'opzione desiderata dal JFileChooser jfcFileLista, in questo caso corrispondente alla costante 
             * APPROVE_OPTION
             */
            int rVal = jfcFileLista.showOpenDialog(FinestraAmministratore.this);
                if(rVal == JFileChooser.APPROVE_OPTION) {
                    /**
                     * Riempio il JTextField filename con il nome del file selezionato, che deve possedere l'estensione .ser
                     */
                    JTextField filename = new JTextField(jfcFileLista.getSelectedFile().getName());
                   
                    /**
                     * Riempio il JTextField dir con:
                     * percorso in cui ci si trova + separatore della cartella corrente + nome del file
                     */
                    JTextField dir = new JTextField(jfcFileLista.getCurrentDirectory().toString()+File.separator+filename.getText());
                
                    System.out.println(dir.getText());
                    Catalogo c = Catalogo.getCatalogo();
                
                    /**
                    * Carico il catalogo dal file selezionato
                    */
                    c.carica(dir.getText());
                
                    /**
                    * Aggiorno la tabella notificandogli il cambiamento
                    */
                    ((TabellaAdmin)jtTabella.getModel()).fireTableDataChanged();
                }
        }else if(e.getSource() == jbAggiungiProdotto){
            
            /**
             * Per aggiungere un prodotto mostro una finestra apposita a cui passo il modello della tabella, in modo da
             * poter aggiornare la tabella a inserimento finito
             */
            FinestraProdotto fp = new FinestraProdotto((TabellaAdmin)jtTabella.getModel());
        }else if(e.getSource() == jbSalvaLista){
            /**
             * Analogamente per il caricamento, questa volta mostro il dialog di "Selezione" di un file
             */
            JFrame dialog = new JFrame("Scegli dove salvare il file");
            int rVal = jfcFileLista.showSaveDialog(FinestraAmministratore.this);
            if(rVal == JFileChooser.APPROVE_OPTION) {
                JTextField filename = new JTextField(jfcFileLista.getSelectedFile().getName());
                JTextField dir = new JTextField(jfcFileLista.getCurrentDirectory().toString()+File.separator+filename.getText());

                System.out.println(dir.getText());
                
                /**
                * Se il file non finisce con .ser forzo l'estensione
                */
                if(!dir.getText().endsWith(".ser")){
                    dir.setText(dir.getText()+".ser");
                }
                Catalogo c = Catalogo.getCatalogo();
                
                /**
                 * Salvo il catalogo nel percorso indicato
                 */
                c.salva(dir.getText());
            }
        }
    }
    
    /**
     * Metodo che imposta alcune proprietà della tabella peculiari alla tabella della classe FinestraAmministratore, 
     * richiamando il metodo analogo della classe padre FinestraUtente.
     * Si setta la larghezza preferita di alcune colonne e si richiama su di esse un renderer personalizzato.
     * La colonna 5 si rifà al renderer della classe padre, mentre la 7/8/9 a quello della classe, che conterrà i JButton
     */
    @Override
    protected final void impostaTabella(){
        super.impostaTabella();
        
        jtTabella.getColumnModel().getColumn(8).setPreferredWidth(135);
        jtTabella.getColumnModel().getColumn(9).setPreferredWidth(135);
        
        jtTabella.getColumnModel().getColumn(8).setMaxWidth(150);
        jtTabella.getColumnModel().getColumn(9).setMaxWidth(150);
        
        jtTabella.getColumnModel().getColumn(5).setCellRenderer((TableCellRenderer)super.getRenderer());
        jtTabella.getColumnModel().getColumn(7).setCellRenderer((TableCellRenderer)getRenderer());
        jtTabella.getColumnModel().getColumn(8).setCellRenderer((TableCellRenderer)getRenderer());
        jtTabella.getColumnModel().getColumn(9).setCellRenderer((TableCellRenderer)getRenderer());  
        
        /**
         * Creo un MouseListener e lo assegno alla tabella. All'avvenimento catturo le coordinate e le trasformo nelle
         * corrispettive righe e colonne, per poi simulare la pressione del bottone sul JButton corrispondente della classe
         * TabellaAdmin, ho fatto in questo modo perché gli altri eventi catturabili (pressed, released, entered and exited non mi interessano)
         */
        jtTabella.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                /**
                 * Cattura la riga e la colonna nella quale si è verificato l'evento
                 */
                int row = jtTabella.rowAtPoint(evt.getPoint());
                int col = jtTabella.columnAtPoint(evt.getPoint());
            
                /**
                * Solo i bottoni sono "cliccabili", per cui solo le colonne dalla 7° in poi possono scatenare l'evento, per 
                * sicurezza faccio dei controlli aggiuntivi sulla riga e colonna ottenuti
                */
                if(row>=0 && row<jtTabella.getRowCount() && col>=7 && col<jtTabella.getColumnCount()){
                    /**
                     * Ottengo l'oggetto su cui si è verificato il click
                     */
                    Object value = jtTabella.getValueAt(row, col);
                    
                    /**
                     * Per sicurezza controllo che sia una istanza della classe JButton
                     */
                    if (value instanceof JButton) {
                        /**
                         * Passo al modello usato dalla tabella la riga in cui si è verificato l'evento
                         */
                        ((TabellaAdmin)jtTabella.getModel()).setRow(row);
                        
                        /**
                         * Simulo il click sul bottone. La tabella non riconosce la riga su cui si è verificato l'evento,
                         * per questo ho passato la riga al metodo setRow
                         */
                        ((JButton)value).doClick();
                    }
                }
            }
        });
    }  
    
    /**
     * Renderer personalizzato per la visualizzazione dei bottoni nella tabella
     * @return Il componente da mostrare nella riga e colonna specificate
     */
    @Override
    protected Component getRenderer() {
        return new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
                                
                switch (column) {
                    case 7:
                        return jbPromozione; 
                    case 8:
                        return jbModifica;
                    case 9:
                        return jbRimuovi;
                }
                return null;
            }
        };
    }
}
