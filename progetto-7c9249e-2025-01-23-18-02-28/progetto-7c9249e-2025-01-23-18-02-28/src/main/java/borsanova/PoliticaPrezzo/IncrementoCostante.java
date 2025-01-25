package borsanova.PoliticaPrezzo;

import borsanova.Borsa.Azione;

/**
 * Questa classe implementa l'interfaccia {@link PoliticaPrezzo} e definisce la politica del prezzo che gestisce la variazione del valore di un'azione
 * in caso di acquisto della stessa.
 * Questa classe rappresenta la politica del prezzo che prevede un incremento costante del valore dell'azione ad ogni acquisto.
 */
public class IncrementoCostante implements PoliticaPrezzo {
    /**{@code incremento} è il valore dell'incremento costante */
    private int incremento;

    /**
     * AF = incremento costante del valore delle azioni. 
     * IR = incremento >= 0
     */

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
