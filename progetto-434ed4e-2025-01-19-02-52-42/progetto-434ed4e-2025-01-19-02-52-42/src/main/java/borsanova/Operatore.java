package borsanova;

import java.util.*;

import borsanova.Borsa.Azione;

/**
 * L'operatore può comprare e vendere azioni delle aziende quotate in bora.
 */
public class Operatore implements Comparable<Operatore> {

    /**{@code Nomi_Usati_Operatore} tiene traccia di tutti i nomi usati per definire gli operatori.*/ 
     private static final SortedSet<String> Nomi_Usati_Operatore = new TreeSet<>();
    /**{@code nome} è il nome dell'operatore. */
     private final String nome; 
    /**{@code budget} è il budget che l'operatore ha a disposizione per comprare le azioni.*/
    private int budget;
    /**{@code azioniPossedute} una collezioni che contiene tutte le azioni possedute da questo operatore*/
    private HashMap<Azione, Integer> azioniPossedute;
    
    /**
     * Fabbricatore dell'operatore
     * @param name il nome da dare all'operatore.
     * @return un nuovo operatore di nome {@code name}
     * @throws IllegalArgumentException se {@code name} è vuto o se il nome è già stato usato da un'altro operatore. 
     */
    public static Operatore of(final String name) {
      if (Objects.requireNonNull(name, "Name must not be null.").isBlank())
        throw new IllegalArgumentException("Name must not be empty.");
      if (Nomi_Usati_Operatore.contains(name)) throw new IllegalArgumentException("Name already used.");
      Nomi_Usati_Operatore.add(name);
      return new Operatore(name);
    }

    /**
     * Viene dato un nome all'operatore, che non sia ancora stato usato, e gli si da un budget di partenza pari a 0. 
     * @param nomeOperatore nome del nuovo operatore.
     */
    public Operatore(String nomeOperatore) {
        nome = nomeOperatore;
        budget = 0; 
        HashMap<Azione, Integer> azioniPossedute = new HashMap<>();
    }

    /**
     * Un'operatore decide di acquistare una o più azioni. 
     * @param nomeBorsa è la borsa dal quale l'operatore vuole comprare le azioni dell'azienda che gli interessa.
     * @param nomeAzione è il nome dell'azienda di cui l'operatore vuole acquistare le azioni. 
     * @param liquiditàNecessaria il costo richiesto dall'investimento. 
     * @throws IllegalArgumentException se viene inserito un numero troppo grande di azioni da acquistare rispetto 
     * a quelle acquistabili.
     */
    public void investi(Borsa nomeBorsa, Azienda nomeAzione, int liquiditàNecessaria) throws IllegalArgumentException {  
      Azione azione = nomeBorsa.cercaAzioneBorsa(nomeAzione);
      if (liquiditàNecessaria > budget) throw new IllegalArgumentException("Non hai abbastanza soldi per comprare queste azioni.");
      if (liquiditàNecessaria > azione.valoreAzione()) throw new IllegalArgumentException("Non hai abbastanza soldi per comprare queste azioni.");
      if (liquiditàNecessaria > azione.quantitaAzioni()) throw new IllegalArgumentException("Non ci sono abbastanza azioni disponibili.");
      Integer azioniComprate = liquiditàNecessaria / azione.valoreAzione();
      int resto = liquiditàNecessaria % azione.valoreAzione();
      budget += resto;
      budget -= liquiditàNecessaria;
      azioniPossedute.put(azione, azioniComprate);
      nomeBorsa.aggiungiOperatore(this);
    }


    /**
     * Deposito di fondi dentro il budget.
     * @param daDepositare la quantità da depositare.
     */
    public void deposita(int daDepositare) {
      budget += daDepositare;
    }

    /**
     * Prelievo di fondi dal budget.
     * @param daPrelevare quantità di denaro da prelevare.
     */
    public void preleva(int daPrelevare) {
      budget -= daPrelevare;
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof Operatore other)) return false;
      return nome.equals(other.nome);
    }
  
    @Override
    public int hashCode() {
      return nome.hashCode();
    }
  
    @Override
    public int compareTo(Operatore other) {
      return nome.compareTo(other.nome);
    }

}
