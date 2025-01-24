package borsanova.PoliticaPrezzo;

import borsanova.Borsa.Azione;

public class DecrementoCostante implements PoliticaPrezzo {
    private int decremento;

    /**
     * Definizione del decremento costatne.
     * @param decremento decremento da applicare al valore dell'azione
     */
    public DecrementoCostante(int decremento) {
        this.decremento = Math.abs(decremento);
    }

    @Override
    public void vendita(Azione azione, int numeroAzioni) {
        int valoreAttuale = azione.valore();
        if (valoreAttuale - decremento > 0) azione.newValue(valoreAttuale - decremento);
        else azione.newValue(1); 
    }

    @Override
    public void acquisto(Azione azione, int numeroAzioni) {
        return;
    }
    
}
