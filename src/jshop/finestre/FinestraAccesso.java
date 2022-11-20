package jshop.finestre;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Prima finestra presentata all'utente, tramite la quale selezionare la modalità di accesso come amministratore o
 * come cliente
 * @author luca
 */
public class FinestraAccesso extends JFrame implements ActionListener{
    
    private final JPanel p;
    private final LayoutManager mgr;
    private final JLabel jlMessaggio;
    private final JButton jbAdmin, jbCliente;
    
    public FinestraAccesso(){
        super("Accesso");
        setAlwaysOnTop(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        p = new JPanel();
        mgr = new BorderLayout();
        p.setLayout(mgr);
        
        jlMessaggio = new JLabel("Benvenuto, seleziona la tipologia di accesso");
        jlMessaggio.setFont(new Font("", Font.PLAIN, 17));
        p.add(BorderLayout.NORTH, jlMessaggio);
        
        jbAdmin = new JButton("Amministratore");
        p.add(BorderLayout.WEST, jbAdmin);
        
        jbCliente = new JButton("Cliente");
        p.add(BorderLayout.EAST, jbCliente);
        
        jbAdmin.addActionListener(this);
        jbCliente.addActionListener(this);
        
        add(p);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * A seconda del bottone premuto creo una istanza della classe FinestraAmministratore o FinestraCliente
     * @param e L'ActionEvent generato
     */
    @Override
    public void actionPerformed(ActionEvent e){
        setVisible(false);
        FinestraUtente fu;
        if(e.getSource() == jbAdmin){
            fu = new FinestraAmministratore();
        }else{
            fu = new FinestraCliente();
        }
    }
}
