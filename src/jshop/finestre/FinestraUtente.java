package jshop.finestre;

import jshop.prodotti.Catalogo;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;

/**
 * Finestra con le proprietà comuni alle finestre dell'amministratore, del cliente e del carrello
 * @author luca
 */
public abstract class FinestraUtente extends JFrame{
    private final JPanel p;
    private final LayoutManager mgr;
    protected JTable jtTabella;
    private final JScrollPane jspTab;
    
   
    public FinestraUtente(String nomeFinestra){
        super(nomeFinestra);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1000, 0));
        
        mgr = new BorderLayout();
        p = new JPanel();
        p.setLayout(mgr);
        
        jtTabella = new JTable();
        
        /**
         * Vieto all'utente di ridimensionare o riordinare manualmente le colonne
         */
        jtTabella.getTableHeader().setReorderingAllowed(false);
        jspTab = new JScrollPane(jtTabella);
        p.add(BorderLayout.CENTER, jspTab);
        
        add(p);  
        pack();
    }
    
    protected void impostaTabella(){
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER); // Renderer personalizzato per allineare il contenuto al centro
               
        for(int k=0;k<jtTabella.getColumnCount();k++){
            jtTabella.getColumnModel().getColumn(k).setCellRenderer(centerRenderer); // Centro il testo nelle varie celle
        }
        jtTabella.setRowSelectionAllowed(false);
        
        jtTabella.getColumnModel().getColumn(5).setPreferredWidth(100);
        jtTabella.getColumnModel().getColumn(6).setPreferredWidth(200);
        jtTabella.getColumnModel().getColumn(7).setPreferredWidth(135);
        
        jtTabella.getColumnModel().getColumn(5).setMaxWidth(100); 
        jtTabella.getColumnModel().getColumn(6).setMaxWidth(200);
        jtTabella.getColumnModel().getColumn(7).setMaxWidth(150);
        
        jtTabella.setRowHeight(100);
    }
    
     /**
     * Devo creare un renderer personalizzato per le celle che conterranno l'immagine del prodotto. L'immagine la rappresento
     * tramite una JLabel
     * @return Il componente da mostrare nella riga e nella colonna indicate
     */
    protected Component getRenderer() {
        return new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
                
                if(column == 5){
                        JLabel jlImg = new JLabel();
                        Catalogo c = Catalogo.getCatalogo();
                        ImageIcon ic = new ImageIcon(c.getElemento(row).getImmagine()); // Il renderer personalizzato è usato solo per le immagini
                        jlImg.setIcon(ic);   
                        return jlImg;
                }
            
                return null;
            }
        };
    }
}