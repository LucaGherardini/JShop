package jshop.prodotti;

/**
 * Sottoclasse di acquisto dove viene sovrascritto il metodo getSaldo(), calcolando la spesa per il prezzo e la quantità del
 * del prodotto acquistato con la formula dello sconto
 * @author luca
 */
public class AcquistoScontato extends Acquisto{
    
    public AcquistoScontato(Prodotto p, int quantita) {
        super(p, quantita);
    }
    
    @Override
    public float getSaldo(){
        return (getPrezzo() - getPrezzo() * getSconto()/100) * getQuantita();
    }
}
