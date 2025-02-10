package borsanova.politicaPrezzo;

// import java.util.*;
import borsanova.Borsa.Azione;

/**
 * Questa classe implementa l'interfaccia {@link PoliticaPrezzo} e definisce la politica del prezzo che gestisce la variazione del valore di un'azione
 * in caso di acquisto della stessa.
 * Questa classe rappresenta la politica del prezzo che prevede un raddoppiamento del valore di un'ìazione in caso ne sia acquisata una quantità maggiore alla soglia e ne dimezza
 * il valore in caso ne sia venduta una quantità maggiore alla soglia.
 */
public class Soglia implements PoliticaPrezzo {
    /**{@code soglia} è il valore della soglia che se superata, in caso di acquisto o vendita, applica il cambiamento del valore delle azioni*/
    private final int soglia;

    /*-
     * AF:
     *     - soglia: è il numero di azioni che se superato va ad applicare il cambio del valore dell'azione.
     * IR:
     *     - soglia >= 0
     */

    /**
     * Definizione della soglia.
     * @param soglia soglia da applicare al valore dell'azione
     */
    public Soglia(int soglia) {
        this.soglia = Math.abs(soglia);
    }

    @Override
    public int vendita(Azione azione, int numeroAzioni) {
        int valoreAttuale = azione.valore();
        if (numeroAzioni > soglia && valoreAttuale/2 >= 1) return (valoreAttuale/2);
        else if (numeroAzioni > soglia && valoreAttuale/2 < 1) return 1;
        else return azione.valore();
    }
    

    @Override
    public int acquisto(Azione azione, int numeroAzioni) {
        int valoreAttuale = azione.valore();
        if (numeroAzioni > soglia) return (valoreAttuale*2);
        else return azione.valore();
    }
}
