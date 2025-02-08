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

    /*-
     * AF:
     *     - incremento: è l'incrememnto del valore dell'azione in caso di acquisto. 
     * IR:
     *     - incremento >= 0
     */

    /**
     * Definizione dell'incremento costante.
     * @param incremento incremento da applicare al valore dell'azione
     * @throws IllegalArgumentException se {@code incremento} è minore o uguale a 0.
     */
    public IncrementoCostante(int incremento) {
        if (incremento <= 0) throw new IllegalArgumentException("L'incremento deve essere maggiore o uguale a 0.");
        this.incremento = Math.abs(incremento);
    }

    @Override
    public int vendita(Azione azione, int numeroAzioni) {
        return azione.valore();
    }

    @Override
    public int acquisto(Azione azione, int numeroAzioni) {
        int valoreAttuale = azione.valore();
        return (valoreAttuale + incremento);
    }
    
}
