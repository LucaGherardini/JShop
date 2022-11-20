package jshop.finestre;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import jshop.prodotti.Acquisto;
import jshop.prodotti.Carrello;
import jshop.tabelle.TabellaCarrello;

/**
 * Finestra di conferma dell'ordine, dove si chiede all'utente di inserire i dati necessari a processare l'ordine. Al termine
 * dell'inserimento il carrello viene svuotato
 * @author luca
 */
public class FinestraCheckOut extends JFrame implements ActionListener{
    private final JButton jbOk;
    private final JLabel jlNome, jlCognome, jlIndirizzo, jlStato, jlCitta, jlVia, jlCivico, jlCAP, jlModalitaPagamento, jlSaldo;
    private final JTextField jtfNome, jtfCognome, jtfStato, jtfCitta, jtfVia, jtfCivico, jtfCAP;
    private final ButtonGroup bg;
    private final JRadioButton jrbCartaDiCredito, jrbContrassegno, jrbBuonoSpesa, jrbBonifico;
    private final JPanel p, a, b, indirizzo, modalita;
    private final TabellaCarrello tm;
    
    public FinestraCheckOut(TabellaCarrello tm){
        super("Pagamento");
        setResizable(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); 
        
        this.tm = tm;
        
        p = new JPanel();
        p.setLayout(new BorderLayout());
        
        a = new JPanel();
        
        jlNome = new JLabel("Nome: ");
        a.add(jlNome);
        
        jtfNome = new JTextField(15);
        a.add(jtfNome);
        
        jlCognome = new JLabel("Cognome: ");
        a.add(jlCognome);
        
        jtfCognome = new JTextField(15);
        a.add(jtfCognome);
        
        float spesa = 0;
        Carrello car = Carrello.getCarrello();
        for(int k=0;k<tm.getRowCount();k++){
            Acquisto ac = car.getElemento(k);
            spesa += ac.getSaldo();
        }
        jlSaldo = new JLabel("Saldo totale: "+String.format("%.2f", spesa)+"€");
        a.add(jlSaldo);
        
        p.add(a, BorderLayout.NORTH);
        
        b = new JPanel();
        b.setLayout(new GridLayout(2,1));
        
        jlIndirizzo = new JLabel("Inserisci l'indirizzo di spedizione");
        b.add(jlIndirizzo);
        
        indirizzo = new JPanel();
        indirizzo.setLayout(new GridLayout(5, 2));
        
        jlVia = new JLabel("Via: ");
        indirizzo.add(jlVia);           
        jtfVia = new JTextField(20);
        indirizzo.add(jtfVia);
        
        jlCivico = new JLabel("Numero civico: ");
        indirizzo.add(jlCivico);        
        jtfCivico = new JTextField(4);
        indirizzo.add(jtfCivico);
        
        jlCAP = new JLabel("CAP: ");
        indirizzo.add(jlCAP);
        jtfCAP = new JTextField(5);
        indirizzo.add(jtfCAP);
        
        jlCitta = new JLabel("Città: ");
        indirizzo.add(jlCitta);
        jtfCitta = new JTextField(20);
        indirizzo.add(jtfCitta);
        
        jlStato = new JLabel("Stato: ");
        indirizzo.add(jlStato);
        jtfStato = new JTextField(20);
        indirizzo.add(jtfStato);
        
        b.add(indirizzo);
        
        p.add(b, BorderLayout.EAST);
        
        JPanel c = new JPanel();
        
        jlModalitaPagamento = new JLabel("Seleziona la modalità di pagamento");
        c.add(jlModalitaPagamento);
        
        modalita = new JPanel();
        modalita.setLayout(new GridLayout(4,1));
        
        jrbCartaDiCredito = new JRadioButton("Carta di Credito");        
        jrbCartaDiCredito.setSelected(true);
        modalita.add(jrbCartaDiCredito);
        
        jrbContrassegno = new JRadioButton("Contrassegno");
        modalita.add(jrbContrassegno);
        
        jrbBuonoSpesa = new JRadioButton("Buono Spesa");
        modalita.add(jrbBuonoSpesa);
        
        jrbBonifico = new JRadioButton("Bonifico Bancario");
        modalita.add(jrbBonifico);
        
        c.add(modalita);
        
        p.add(c, BorderLayout.WEST);
        
        bg = new ButtonGroup();
        bg.add(jrbCartaDiCredito);
        bg.add(jrbContrassegno);
        bg.add(jrbBuonoSpesa);
        bg.add(jrbBonifico);
        
        jbOk = new JButton("Conferma");
        
        p.add(jbOk, BorderLayout.SOUTH);
        
        if(Carrello.getCarrello().getSize()==0){
            FinestraErrore fe = new FinestraErrore("Il carrello è vuoto");
            setVisible(false);
            return ;
        }
        
        jbOk.addActionListener(this);
        
        add(p);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);     
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbOk){
            if(controllaCampi() && controllaIndirizzo()){
                Carrello.getCarrello().svuotaLista(tm);
                setVisible(false);            
            }else{
                FinestraErrore fe = new FinestraErrore("Devi riempire tutti i campi");
            }
        }
    }
    
    /**
     * Metodo che controlla che tutti i campi siano stati riempiti
     * @return Vero se tutti i campi sono riempiti, falso altrimenti
     */
    public boolean controllaCampi(){
        return !(jtfStato.getText().isEmpty() || jtfCitta.getText().isEmpty() || jtfVia.getText().isEmpty()  || jtfCAP.getText().isEmpty() || jtfNome.getText().isEmpty()  || jtfCognome.getText().isEmpty() );
    }
    
    /**
     * Controlla che numero civico e CAP siano numero interi e che il CAP abbia 5 cifre
     * @return vero se indirizzo e CAP rispettano i criteri, falso altrimenti
     */
    public boolean controllaIndirizzo(){
        try{
            Integer.parseInt(jtfCAP.getText());
            Integer.parseInt(jtfCivico.getText());
        }catch(NumberFormatException e){
            return false;
        }
        
        if(jtfCAP.getText().length() != 5  || Integer.parseInt(jtfCAP.getText())<=0){
            FinestraErrore fe = new FinestraErrore("Il CAP deve essere un numero a 5 cifre");
            return false;
        }
        
        if(Integer.parseInt(jtfCivico.getText())<=0){
            FinestraErrore fe = new FinestraErrore("Il numero civico non può essere negativo!");
            return false;
        }
        return true;
    }
}