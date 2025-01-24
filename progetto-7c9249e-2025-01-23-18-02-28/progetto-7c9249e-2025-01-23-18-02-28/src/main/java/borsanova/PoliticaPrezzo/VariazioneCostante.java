package borsanova.PoliticaPrezzo;

import borsanova.Borsa.Azione;

public class VariazioneCostante implements PoliticaPrezzo {
    private int variazione;

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
