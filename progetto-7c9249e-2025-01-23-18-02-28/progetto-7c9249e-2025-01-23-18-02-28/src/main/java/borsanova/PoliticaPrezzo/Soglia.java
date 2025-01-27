package borsanova.PoliticaPrezzo;

// import java.util.*;
import borsanova.Borsa.*;

/**
 * Questa classe implementa l'interfaccia {@link PoliticaPrezzo} e definisce la politica del prezzo che gestisce la variazione del valore di un'azione
 * in caso di acquisto della stessa.
 * Questa classe rappresenta la politica del prezzo che prevede un raddoppiamento del valore di un'ìazione in caso ne sia acquisata una quantità maggiore alla soglia e ne dimezza
 * il valore in caso ne sia venduta una quantità maggiore alla soglia.
 */
public class Soglia implements PoliticaPrezzo {
    /**{@code soglia} è il valore della soglia che se superata, in caso di acquisto o vendita, applica il cambiamento del valore delle azioni*/
    private int soglia;

    /**
     * AF:
     *     soglia dal quale si applica il cambio del valore.
     * IR:
     *     soglia >= 0
     */

    /**
     * Definizione della soglia.
     * @param soglia soglia da applicare al valore dell'azione
     */
    public Soglia(int soglia) {
        this.soglia = Math.abs(soglia);
    }

    /**
     * Sancisce il cambio di valore dell'azione alla vendita dell'azione.
     * @param azione l'azione che si vuole vendere.
     * @param numeroAzioni il numero di azioni che si vogliono vendere.
     */
    public void vendita(Azione azione, int numeroAzioni) {
        int valoreAttuale = azione.valore();
        if (numeroAzioni > soglia && valoreAttuale/2 >= 1) azione.newValue(valoreAttuale/2);
        else if (valoreAttuale/2 < 1) azione.newValue(1);
    }
    
    /**
     * Sancisce il cambio di valore dell'azione all'acquisto dell'azione.
     * @param azione l'azione che si vuole acquistare.
     * @param numeroAzioni il numero di azioni che si vogliono acquistare.
     */
    public void acquisto(Azione azione, int numeroAzioni) {
        int valoreAttuale = azione.valore();
        if (numeroAzioni > soglia) azione.newValue(valoreAttuale*2);
    }
}
