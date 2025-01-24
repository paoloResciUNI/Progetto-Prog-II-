package borsanova.PoliticaPrezzo;

import borsanova.Borsa.Azione;

public class IncrementoCostante implements PoliticaPrezzo {
    private int incremento;

    /**
     * Definizione dell'incremento costante.
     * @param incremento incremento da applicare al valore dell'azione
     */
    public IncrementoCostante(int incremento) {
        this.incremento = Math.abs(incremento);
    }

    @Override
    public void vendita(Azione azione, int numeroAzioni) {
        return;
    }

    @Override
    public void acquisto(Azione azione, int numeroAzioni) {
        int valoreAttuale = azione.valore();
        azione.newValue(valoreAttuale + incremento);
    }
    
}
