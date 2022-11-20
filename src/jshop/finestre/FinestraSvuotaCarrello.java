package jshop.finestre;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import jshop.prodotti.Carrello;
import jshop.tabelle.TabellaCarrello;

/**
 * Finestra che chiede conferma prima di svuotare il carrello
 * @author luca
 */
public class FinestraSvuotaCarrello extends JFrame implements ActionListener{
    private final JLabel jlMex;
    private final JButton jbConferma;
    private final JPanel p;
    private final Carrello c;
    private final TabellaCarrello tm;
    
    public FinestraSvuotaCarrello(Carrello c, TabellaCarrello tm, String mex) {
        super();
        
        this.c = c;
        this.tm = tm;
        
        p = new JPanel();
        p.setLayout(new BorderLayout());
        
        jlMex = new JLabel(mex);
        jlMex.setFont(new Font("Verdana", Font.PLAIN, 16));
        p.add(jlMex, BorderLayout.NORTH);
        
        jbConferma = new JButton("Conferma");
        p.add(jbConferma, BorderLayout.SOUTH);
        
        jbConferma.addActionListener(this);
        
        add(p);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbConferma){
            c.svuotaLista(tm);
        }
        this.setVisible(false);
    }
    
}
