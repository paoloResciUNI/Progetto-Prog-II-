package borsanova.politicaprezzo;

import borsanova.Borsa.Azione;

/**
 * Questa classe implementa l'interfaccia {@code PoliticaPrezzo}.
 * Definisce la politica del prezzo che gestisce la variazione del valore di un'azione in caso di acquisto o di vendita della stessa.
 * Questa politica prezzo prevede:
 *  - In caso di vendita, di una quantità di azioni maggiore rispetto alla soglia definita, un dimezzamento del valore dell'azione. 
 *  - In caso di acquisto, di una quantità di azioni maggiore rispetto alla soglia definita, un raddoppiamento del valore dell'azione.
 */
public class Soglia implements PoliticaPrezzo {
    /**{@code soglia} il valore della soglia che se superata, in caso di acquisto o vendita, applica il cambiamento del valore dell'azione*/
    private final int soglia;

    /*-
     * AF:
     *     - soglia: è la quantità di azioni che se superato va ad applicare il cambio del valore dell'azione.
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
