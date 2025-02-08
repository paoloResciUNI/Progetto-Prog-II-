package borsanova.PoliticaPrezzo;

import borsanova.Borsa.Azione;

/**
 * Questa classe implementa l'interfaccia {@link PoliticaPrezzo} e definisce la politica del prezzo che gestisce la variazione del valore di un'azione in caso di acquisto della stessa.
 * Questa classe rappresenta la politica del prezzo che prevede una variazione costante del valore di un'azione in caso di acquito o vendita.
 */
public class VariazioneCostante implements PoliticaPrezzo {
    /**{@code variazione} è il valore della variazione costante */
    private int variazione;

    /*-
     * AF:
     *     - variazione: è la quantità di valore, aggiunto o tolto, all'azione in caso di acquisto o vendita.
     * IR:
     *     - variazione >= 0
     */

    /**
     * Definizione della variazione costante.
     * @param variazione variazione da applicare al valore dell'azione
     * @throws IllegalArgumentException se {@code variazione} è minore di 0.
     */
    public VariazioneCostante(int variazione) {
        if (variazione < 0) throw new IllegalArgumentException("La variazione deve essere maggiore o uguale a 0.");
        this.variazione = variazione;
    }

    @Override
    public int vendita(Azione azione, int numeroAzioni) {
        if (azione.valore() - variazione > 0) return (azione.valore() - variazione);
            else return 1; 
    }

    @Override
    public int acquisto(Azione azione, int numeroAzioni) {
        return (azione.valore() + variazione);
    }
    
}
