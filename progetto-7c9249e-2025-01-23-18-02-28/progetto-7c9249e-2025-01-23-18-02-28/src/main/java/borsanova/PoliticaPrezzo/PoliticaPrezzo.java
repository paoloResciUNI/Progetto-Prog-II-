package borsanova.PoliticaPrezzo;

import borsanova.Borsa.Azione;

/**
 * Questa interfaccia serve per applicare la politica del prezzo che gestisce la variazione del valore di un'azione
 * in caso di vendita o di acquisto della stessa.
 * Tutte le classi che implementano questa interfaccia devono sovrascrivere i metodi vendita e acquisto che gestiranno la politica
 * del prezzo durante le relative operazioni.
 */
public interface PoliticaPrezzo {
    /**
     * Sancisce il cambio di valore dell'azione alla vendita dell'azione.
     * @param azione l'azione che si vuole vendere.
     * @param numeroAzioni il numero di azioni che si vogliono vendere.
     */
    public void vendita(Azione azione, int numeroAzioni);
    
    /**
     * Sancisce il cambio di valore dell'azione all'acquisto dell'azione.
     * @param azione l'azione che si vuole acquistare.
     * @param numeroAzioni il numero di azioni che si vogliono acquistare.
     */
     public void acquisto(Azione azione, int numeroAzioni);   
}
