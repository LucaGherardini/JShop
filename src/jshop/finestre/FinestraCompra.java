package jshop.finestre;

import java.awt.BorderLayout;
import javax.swing.*;
import jshop.prodotti.*;
import java.awt.event.*;

/**
 * Finestra che mostra le informazioni del prodotto che si intende acquistare a possiede un JTextField per dichiarare il
 * numero di prodotti che si intendono acquistare
 * @author luca
 */
public class FinestraCompra extends JFrame implements ActionListener{
    private final JLabel jlNomeProdotto, jlQuantita;
    private final JTextField jtfQuantita;
    private final JPanel p, p2;
    private final JButton jbCompra;
    private final int row;
    private final Catalogo c;
    private final FinestraCarrello fc;
    
    /**
     * @param row Riga del prodotto che si desidera acquistare
     * @param fc Istanza della FinestraCarrello, passata per poter aggiornare il Carrello quando si compra un nuovo prodotto
     */
    public FinestraCompra(int row, FinestraCarrello fc) {
        super("Acquisto prodotto");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);
        
        this.row = row;
        this.fc = fc;
        c = Catalogo.getCatalogo();
        
        p = new JPanel();
        p.setLayout(new BorderLayout());
        
        jlNomeProdotto = new JLabel(c.getElemento(row).toString());
        p.add(jlNomeProdotto, BorderLayout.NORTH);
        
        p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        
        jlQuantita = new JLabel("Inserisci il numero di prodotti che vuoi acquistare: ");
        p2.add(jlQuantita, BorderLayout.WEST);
        
        jtfQuantita = new JTextField(3);
        jtfQuantita.addActionListener(this);
        
        p2.add(jtfQuantita, BorderLayout.CENTER);
        
        p.add(p2, BorderLayout.CENTER);
        
        jbCompra = new JButton("Procedi all'acquisto");
        
        p.add(jbCompra, BorderLayout.SOUTH);
        
        jbCompra.addActionListener(this);
        
        add(p);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbCompra){
            
            Carrello car = Carrello.getCarrello();
            Prodotto prod = c.getElemento(row);
            
            int quantita;
            try{
                quantita = Integer.parseInt(jtfQuantita.getText());
            }catch(NumberFormatException err){
                FinestraErrore fe = new FinestraErrore("Formato errato. Inserisci la quantità di prodotti che si intende acquistare");
                return ;
            }catch(Exception err){
                FinestraErrore fe = new FinestraErrore("Errore");
                return ;
            }
            if(quantita<=0){
                FinestraErrore fe = new FinestraErrore("La quantità non può essere un valore nullo o negativo");
                return ;
            }
            
            boolean prodottoEsistente = false;
            int k;
            
            for(k=0;k<car.getSize();k++){
                if(prod.getCodice().equals(car.getElemento(k).getCodice())){
                    prodottoEsistente = true;
                    break;
                }
            }
            
            if(prodottoEsistente){
                car.getElemento(k).setQuantita(car.getElemento(k).getQuantita()+quantita);
            }else{
                Acquisto a;
                switch (prod.getPromozione()) {
                    case sconto:
                        a = new AcquistoScontato(prod, quantita);
                        car.aggiungiElemento(a);
                        break;
                    case trexdue:
                        a = new AcquistoTrePerDue(prod, quantita);
                        car.aggiungiElemento(a);
                        break;
                    default:
                        a = new Acquisto(prod, quantita);
                        car.aggiungiElemento(a);
                        break;
                }
            }
            
            fc.aggiornaTabella();
            setVisible(false);
        }
    }
}