package borsanova.politicaPrezzo;

import borsanova.Borsa.Azione;

/**
 * Applica, seguendo certi criteri, la politica del prezzo che gestisce la variazione del valore di un'azione in caso di vendita o di acquisto della stessa.
 */
public interface PoliticaPrezzo {
    /**
     * Sancisce il cambio di valore dell'azione alla vendita.
     * @param azione l'azione che si vuole vendere.
     * @param numeroAzioni il numero di azioni che si vogliono vendere.
     * @return il nuovo valore dell'azione. 
     */
    public int vendita(Azione azione, int numeroAzioni);
    
    /**
     * Sancisce il cambio di valore dell'azione all'acquisto.
     * @param azione l'azione che si vuole acquistare.
     * @param numeroAzioni il numero di azioni che si vogliono acquistare.
     * @return il nuovo valore dell'azione. 
     */
     public int acquisto(Azione azione, int numeroAzioni);   
}
