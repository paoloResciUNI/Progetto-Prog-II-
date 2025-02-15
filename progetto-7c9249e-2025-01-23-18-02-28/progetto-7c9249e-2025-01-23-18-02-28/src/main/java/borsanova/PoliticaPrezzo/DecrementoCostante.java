package borsanova.politicaprezzo;

import borsanova.Borsa.Azione;

/**
 * Questa classe implementa l'interfaccia {@link PoliticaPrezzo}
 * Definisce la politica del prezzo che gestisce la variazione del valore di un'azione in caso di vendita della stessa.
 * Questa politica prezzo prevede un decremento costante del valore di un'azione in caso di vendita.
 */
public class DecrementoCostante implements PoliticaPrezzo {
    /**{@code decremento} il valore del decremento costante */
    private int decremento;

    /*-
     * AF:
     *    - decremento: è il decremento del valore dell'azione in caso di vendita. 
     * IR:
     *    - decremento >= 0.
     */


    /**
     * Definizione del decremento costatne.
     * @param decremento decremento da applicare al valore dell'azione
     */
    public DecrementoCostante(int decremento) {
        this.decremento = Math.abs(decremento);
    }

    @Override
    public int vendita(Azione azione, int numeroAzioni) {
        int valoreAttuale = azione.valore();
        if (valoreAttuale - decremento > 0) return (valoreAttuale - decremento);
        else return 1; 
    }

    @Override
    public int acquisto(Azione azione, int numeroAzioni) {
        return azione.valore();
    }
    
}
