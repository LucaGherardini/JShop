package jshop.prodotti;
import java.io.*;
import java.util.List;

/**
 * Classe implementa il concetto di singleton per il catalogo dei prodotti esistenti, cioè una singola istanza di una classe a
 * costruttore privato.
 * @author luca
 */
public class Catalogo extends ListaProdotti<Prodotto> implements Serializable{
    private final long SerialVersionUID = 1L;
    private static Catalogo c = null;
    private final String ultimoSalvataggio;
    
    private Catalogo(){
        super();
        ultimoSalvataggio = "lastSave";
    }
    
    /**
     * Metodo statico che inizializza, se necessario, la variabile Catalogo, e la restituisce
     * @return La istanza del catalogo da usare
     */
    public static Catalogo getCatalogo(){
        if(c==null){
            c = new Catalogo();
        }
        return c;
    }
    
     /**
     * Metodo che riceve in ingresso una stringa di testo rappresentante il nome di un file ed eventualmente il suo percorso
     * assoluto/relativo, creando un FileOutputStream e un ObjectOutputStream per la scrittura
     * @param file Il percorso del file su cui serializzare il la lista
     */
    public void salva(String file){
        
        ObjectOutputStream oosSalva;
        
        try{
            oosSalva = new ObjectOutputStream(new FileOutputStream(file));
        }catch(IOException e){
            System.out.println(e);
            System.out.println("Problema nell'apertura del file, salvataggio abortito");
            return ;
        }    
        
        try{
            oosSalva.writeObject(elenco);
        }catch(IOException e){
            System.out.println(e);
            System.out.println("Problemi con il salvataggio, operazione abortita");
            return ;
        }
        
        try{
            oosSalva.flush();
            oosSalva.close();
        }catch(IOException e){
            System.out.println(e);
            System.out.println("Problemi con la terminazione dell'operazione");
            return ;
        }
        
        /**
         * Aggiorno l'ultimo salvataggio, scrivendo sul file "lastSave" il percorso dell'ultimo salvataggio creato
         */
        try{
            oosSalva = new ObjectOutputStream(new FileOutputStream(ultimoSalvataggio));
            oosSalva.writeObject(file);
            oosSalva.flush();
            oosSalva.close();
        }catch(IOException e){
            System.out.println("Impossibile caricare l'ultimo salvataggio creato, caricarlo manualmente");
        }
        System.out.println("Salvataggio completato");
    }
    
    /**
     * Metodo che riceve in ingresso una stringa di testo rappresentante il nome di un file ed eventualmente il suo percorso
     * assoluto/relativo, creando un FileInputStream e un ObjectInputStream per la lettura
     * @param file Il percorso del file da caricare
     */
    public void carica(String file){
        
        ObjectInputStream oisCarica;
        
        try{
            oisCarica = new ObjectInputStream(new FileInputStream(file));
        }catch(IOException e){
            System.out.println(e);
            System.out.println("Problema nell'apertura del file, caricamento abortito");
            return ;
        }
        
        
        try{
            elenco = (List<Prodotto>) (oisCarica.readObject());
        }catch(IOException | ClassNotFoundException e){
            System.out.println(e);
            System.out.println("Problemi con il caricamento, operazione abortita");
            return ;
        }

        try{
            oisCarica.close();
        }catch(IOException e){
            System.out.println(e);
            System.out.println("Problemi con la terminazione dell'operazione");
            return ;
        }
        
        /**
         * Aggiorno l'ultimo salvataggio, scrivendo sul file "lastSave" il percorso dell'ultimo salvataggio acceduto
         */
        ObjectOutputStream oosSalva;
        try{
            oosSalva = new ObjectOutputStream(new FileOutputStream(ultimoSalvataggio));
            oosSalva.writeObject(file);
            oosSalva.flush();
            oosSalva.close();
        }catch(IOException e){
            System.out.println("Impossibile caricare l'ultimo salvataggio acceduto, caricarlo manualmente");
        }
        
        System.out.println("Caricamento completato");
    }
    
    /**
     * Metodo che carica automaticamente l'ultimo catalogo salvato.
     */
    public void caricamentoAutomatico(){
        ObjectInputStream oisCarica;
        String file;
        try{
            oisCarica = new ObjectInputStream(new FileInputStream(ultimoSalvataggio));
            file = (String)oisCarica.readObject();
        }catch(IOException | ClassNotFoundException e){
            System.out.println(e);
            System.out.println("Ultimo salvataggio non raggiungibile, è stato spostato o eliminato");
            return ;
        }
        System.out.println("Caricamento automatico dell'ultimo salvataggio "+file);
        carica(file);
    }
}
