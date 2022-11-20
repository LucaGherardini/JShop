package jshop.prodotti;

/**
 * Sottoclasse della classe Acquisto, dove viene sovrascritto il metodo getSaldo(), calcolando la spesa per il prezzo e la quantità del
 * del prodotto acquistato con la formula del 3x2
 * @author luca
 */
public class AcquistoTrePerDue extends Acquisto {
    
    public AcquistoTrePerDue(Prodotto p, int quantita) {
        super(p, quantita);
    }
    
    @Override
    public float getSaldo(){
        return (getQuantita() / 3) * getPrezzo() * 2 + getQuantita() % 3 * getPrezzo();
    }
}
