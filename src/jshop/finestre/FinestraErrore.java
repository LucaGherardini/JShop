package jshop.finestre;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.*;

/**
 * Finestra che serve a mostrare un semplice messaggio di errore all'utente. Il testo viene passato all'atto della creazione
 * della finestra e viene mostrato mediante una JLabel
 * 
 * @author luca
 */
public class FinestraErrore extends JFrame{
    private final JLabel jlErrore;
    private final JPanel p;
    
    public FinestraErrore(String mex){
        super("Errore");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);
        
        jlErrore = new JLabel(mex);
        jlErrore.setFont(new Font("", Font.CENTER_BASELINE, 17));
        
        p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(jlErrore, BorderLayout.CENTER);
        
        add(p);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * Uso un costruttore senza parametri per mostrare un semplice messaggio di "Errore"
     */
    public FinestraErrore(){
        this("Errore");
    }
}
