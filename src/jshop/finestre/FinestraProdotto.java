package jshop.finestre;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import jshop.prodotti.Catalogo;
import jshop.prodotti.Prodotto;
import jshop.tabelle.TabellaAdmin;

/**
 * Finestra usata per inserire un prodotto nel catalogo da parte dell'amministratore. La finestra viene riciclata anche per
 * modificare un prodotto esistente
 * @author luca
 */
public class FinestraProdotto extends JFrame implements ActionListener{
    private final JLabel jlNome, jlMarca, jlCodice, jlPrezzo, jlCategoria, jlImmagine;
    private final JTextField jtfNome, jtfMarca, jtfPrezzo, jtfCodice, jtfImmagine;
    private final JComboBox jcbCategoria;
    private final JButton jbInserimento, jbImmagine;
    private final JPanel p, p2;
    private final TabellaAdmin tm;
    private JFrame dialog;
    private final JFileChooser jfcImmagine;
    private final JTextField filename, dir;
    private final FileNameExtensionFilter filter;
    private int index, row;
    
    public FinestraProdotto(TabellaAdmin tm){
        super("Inserimento nuovo prodotto");
        setAlwaysOnTop(true);
        setResizable(false);
        
        this.tm = tm;
        
        p = new JPanel();
        p.setLayout(new GridLayout(8, 2));
        
        jlNome = new JLabel("* Nome del prodotto: ");
        jtfNome = new JTextField();        
        p.add(jlNome);
        p.add(jtfNome);        
        
        jlMarca = new JLabel("* Marca: ");
        jtfMarca = new JTextField();
        p.add(jlMarca);
        p.add(jtfMarca);
        
        jlCodice = new JLabel("* Codice: ");
        jtfCodice = new JTextField();
        p.add(jlCodice);
        p.add(jtfCodice);
        
        jlPrezzo = new JLabel("* Prezzo: ");
        jtfPrezzo = new JTextField();
        p.add(jlPrezzo);
        p.add(jtfPrezzo);
        
        jlCategoria = new JLabel("* Digita o seleziona la categoria: ");
        jcbCategoria = new JComboBox();
        jcbCategoria.setEditable(true);
        caricaCategorie();
        p.add(jlCategoria);
        p.add(jcbCategoria);
        
        jlImmagine = new JLabel("Seleziona l'immagine: ");
        p.add(jlImmagine);        
        
        filter = new FileNameExtensionFilter("Immagine (.jpg/.png/...)","jpg","png","gif","jpeg");
        jfcImmagine = new JFileChooser();
        jfcImmagine.setFileFilter(filter);
        filename = new JTextField();
        dir = new JTextField();
        
        p2 = new JPanel();
        
        jtfImmagine = new JTextField(40);
        jbImmagine = new JButton("Browse");
        p2.add(jtfImmagine);
        p2.add(jbImmagine);
        p.add(p2);
        
        jbInserimento = new JButton("Inserisci Prodotto");
        p.add(jbInserimento);
        
        jbInserimento.addActionListener(this);
        jbImmagine.addActionListener(this);       
       
        add(p);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);        
    }
    
    /**
     * Metodo richiamato dalla sottoclasse FinestraModifica per riempire i campi. Riciclo la finestra creata dalla classe
     * FinestraProdotto per modificare il prodotto selezionato, riempiendo i campi con i dati del prodotto e modificando la label
     * del bottone jbInserimento per poter distinguere nel metodo actionPerformed(...) il ruolo che ha il bottone in quel momento
     * @param nome Passo la stringa rappresentante il nome del prodotto
     * @param marca Passo la stringa rappresentante la marca del prodotto
     * @param codice Passo la stringa rappresentante il codice del prodotto
     * @param prezzo Passo il prezzo
     * @param categoria Passo la categoria selezionata dall'elenco
     * @param immagine Passo il percorso dell'immagine dichiarato
     * @param row Passo la riga della tabella del prodotto che sto modificando, in modo da poterla aggiornare
     */
    protected void fillFields(String nome, String marca, String codice, float prezzo, String categoria, String immagine, int row){
        jtfNome.setText(nome);
        jtfMarca.setText(marca);
        jtfCodice.setText(codice);
        jtfPrezzo.setText(String.format("%.2f", prezzo));
        jcbCategoria.setSelectedItem(categoria);
        jtfImmagine.setText(immagine);
        this.row = row;
        jbInserimento.setText("Modifica");
    }
    
    
    /**
     * Metodo per caricare la JComboBox con le categorie esistenti
     */
    public final void caricaCategorie(){
        Catalogo c = Catalogo.getCatalogo();
        boolean categoriaPresente;
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
    
    /**
     * Metodo che controlla se i campi obbligatori sono stati riempiti, restituendo un valore booleano vero o falso
     * @return Vero se ci sono dei campi vuoti, falso altrimenti
     */
    public boolean checkFields(){
        return jtfNome.getText().isEmpty() || jtfMarca.getText().isEmpty() || jtfCodice.getText().isEmpty() || jtfPrezzo.getText().isEmpty() || jcbCategoria.getSelectedItem() == null;
    }
    
    /**
     * Metodo che controlla se il codice inserito nell'apposito campo jtfCodice non sia già stato usato per un altro prodotto
     * @return Vero se nessun altro prodotto possiede quel codice, ritorna falso altrimenti
     */
    public boolean checkCodice(){
        Catalogo c = Catalogo.getCatalogo();
        
        /**
         * Memorizzo l'indice nella variabile index per far visualizzare all'amministratore il prodotto che possiede il codice
         */
        for(index=0; index<c.getSize();index++){
            if(jtfCodice.getText().equals(c.getElemento(index).getCodice())){
                return false;
            }   
        }
        return true;
    }
    
    /**
     * Metodo che raccoglie i dati dai campi del modulo e che restituisce un prodotto
     * @return Viene restituito il prodotto creato attraverso i dati contenuti nei campi del modulo
     */
    public Prodotto raccoltaDati(){
        String nome, marca, codice, categoria, immagine;
        float prezzo;
        nome = jtfNome.getText();
        marca = jtfMarca.getText();
        codice = jtfCodice.getText();
        categoria = (String)jcbCategoria.getSelectedItem();
        immagine = jtfImmagine.getText();
        
        try{
            /**
             * Converto in float il contenuto del JTextField jtfPrezzo
             */
            prezzo = Float.parseFloat(jtfPrezzo.getText());
        }catch(NumberFormatException e){
            FinestraErrore fe = new FinestraErrore("Prezzo inserito errato!");
            return null;
        }
        
        if(prezzo<=0){
            FinestraErrore fe = new FinestraErrore("Prezzo inserito errato! Non può essere nullo o negativo!");
            return null;
        }       
        
        return new Prodotto(nome, marca, codice, categoria, immagine, prezzo);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        
        /**
         * Il bottone jbInserimento viene usato sia per inserire un nuovo elemento che per modificarlo, in questo modo nasce
         * la necessità di identificare anche il nome del bottone per distinguere il suo ruolo in quel dato momento
         */
        if(e.getSource() == jbInserimento && e.getActionCommand().equals("Inserisci Prodotto")){
            if(checkFields()){
                FinestraErrore fe = new FinestraErrore("Errore, devi riempire tutti i campi obbligatori *");
            }else if(!checkCodice()){
                Catalogo c = Catalogo.getCatalogo();
                FinestraErrore fe = new FinestraErrore("Il codice inserito appartiene già ad un altro prodotto "+c.getElemento(index).toString());
            }else{
                Catalogo c = Catalogo.getCatalogo();
                Prodotto prod = raccoltaDati();
                if(prod != null){
                    c.aggiungiElemento(raccoltaDati());
                    System.out.println("Aggiorno tabella");
                    tm.fireTableDataChanged(); // Notifico il cambiamento alla tabella
                    System.out.println("Aggiornamento eseguito");
                    this.setVisible(false);
                }
            }
        }else if(e.getSource() == jbInserimento && e.getActionCommand().equals("Modifica")){
            Catalogo c = Catalogo.getCatalogo();
            if(checkFields()){
                FinestraErrore fe = new FinestraErrore("Errore, devi riempire tutti i campi obbligatori *");
                
                /**
                 * Nella modifica del prodotto, reinserendo lo stesso codice il metodo checkCodice() restituisce false,
                 * il che non è errato, se il codice precedente del prodotto è uguale a quello inserito nel JTextField jtfCodice
                 */
            }else if(!checkCodice() && !c.getElemento(row).getCodice().equals(jtfCodice.getText())){
                FinestraErrore fe = new FinestraErrore("Il codice inserito appartiene già ad un altro prodotto "+c.getElemento(index).toString());
            }else{
                System.out.println("Modifico");
                Prodotto prod = raccoltaDati();
                c.getElemento(row).setNome(prod.getNome());
                c.getElemento(row).setMarca(prod.getMarca());
                c.getElemento(row).setCodice(prod.getCodice());
                c.getElemento(row).setPrezzo(prod.getPrezzo());
                c.getElemento(row).setCategoria(prod.getCategoria());
                c.getElemento(row).setImmagine(prod.getImmagine());
                tm.fireTableDataChanged();
                this.setVisible(false);
            }
        }else if(e.getSource() == jbImmagine){
            dialog = new JFrame("Scegli l'immagine");
            
            int rVal = jfcImmagine.showOpenDialog(FinestraProdotto.this);
                if(rVal == JFileChooser.APPROVE_OPTION) {
                filename.setText(jfcImmagine.getSelectedFile().getName());
                dir.setText(jfcImmagine.getCurrentDirectory().toString()+File.separator+filename.getText());
                jtfImmagine.setText(dir.getText());
                System.out.println(dir.getText());
                }
        }
    }
}
