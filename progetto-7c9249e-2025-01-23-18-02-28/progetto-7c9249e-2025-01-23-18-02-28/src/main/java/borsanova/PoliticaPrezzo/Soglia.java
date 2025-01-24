package borsanova.PoliticaPrezzo;

// import java.util.*;
import borsanova.Borsa.*;

public class Soglia implements PoliticaPrezzo {
    private int soglia;

    

    /**
     * Definizione della soglia.
     * @param variazione variazione da applicare al valore dell'azione
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
