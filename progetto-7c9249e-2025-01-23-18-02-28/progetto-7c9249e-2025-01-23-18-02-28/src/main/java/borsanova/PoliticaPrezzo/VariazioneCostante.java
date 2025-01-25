package borsanova.PoliticaPrezzo;

import borsanova.Borsa.Azione;

/**
 * Questa classe implementa l'interfaccia {@link PoliticaPrezzo} e definisce la politica del prezzo che gestisce la variazione del valore di un'azione
 * in caso di acquisto della stessa.
 * Questa classe rappresenta la politica del prezzo che prevede una variazione costante del valore di un'azione in caso di acquito o vendita.
 */
public class VariazioneCostante implements PoliticaPrezzo {
    /**{@code variazione} è il valore della variazione costante */
    private int variazione;

    /**
     * AF = variazione costante del valore delle azioni.
     * IR = variazione >= 0
     */

    /**
     * Definizione della variazione costante.
     * @param variazione variazione da applicare al valore dell'azione
     */
    public VariazioneCostante(int variazione) {
        this.variazione = variazione;
    }

    @Override
    public void vendita(Azione azione, int numeroAzioni) {
        if (azione.valore() - variazione > 0) azione.newValue(azione.valore() - variazione);
            else azione.newValue(1); 
    }

    @Override
    public void acquisto(Azione azione, int numeroAzioni) {
        azione.newValue(azione.valore() + variazione);
    }
    
}
