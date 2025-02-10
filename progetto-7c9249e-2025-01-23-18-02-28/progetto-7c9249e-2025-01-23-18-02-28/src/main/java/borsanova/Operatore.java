package borsanova;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import borsanova.Borsa.Azione;

/**
 * L'operatore può comprare e vendere azioni delle aziende quotate in una determinata borsa, in base al budget che possiede o alla quantità di azioni che vuole vendere.
 * 
 * Ogni operatore: 
 *  - è identificato da un nome. 
 *  - ha un budget che gli permette di comprare azioni nelle varie borse.
 *  - sa quali azioni possiede e quante ne ha.
 * 
 * L'operatore può restituire:
 *  - il nome che lo identifica.
 *  - il budget che ha in un determinato momento.
 *  - se possiede o non possiede una determinata azione.
 *  - se possiede una determinata azione, il quantitativo posseduto. 
 *  - l'elenco delle azioni che possiede in un determinato momento. 
 * 
 * Inoltre può effettuare operazioni di deposito e prelievo sul proprio budget rispettivamente per, aggiungere denaro o toglierlo.  
 */
public class Operatore implements Comparable<Operatore> {

    /**{@code ISTANZE} tiene traccia di tutti i nomi usati per definire gli operatori.*/ 
     private static final SortedSet<String> ISTANZE = new TreeSet<>();
    /**{@code nome} è il nome che identifica l'operatore. */
     private final String nome; 
    /**{@code budget} è il budget che l'operatore ha a disposizione per comprare le azioni.*/
    private int budget;
    /**{@code azioniPossedute} una mappa che contiene tutte le azioni possedute da questo operatore, con associato per ogni azione la quantità posseduta.*/
    private final Map<Azione, Integer> azioniPossedute;
    

    /*-
     * AF: 
     *    - nome: è il nome che identifica l'operatore. 
     *    - budget: è il budget che ogni operatore può usare per fare acquisti.
     *    - azioniPossedute: contiene tutte le azioni posseduta da questo operatore. Ogni azione è associata alla quantità posseduta dall'operatore in un determinato momento.
     * RI:
     *    - nome != null && !nome.isBlank().
     *    - budget >= 0.
     *    - k != null per ogni k all'interno di azioniPossedute.keySet().
     *    - v > 0 && v != null per ogni v in azioniPossedute.values().
     */


    /**
     * Fabbricatore dell'operatore
     * @param name il nome da dare all'operatore.
     * @return un nuovo operatore di nome {@code name}
     * @throws IllegalArgumentException se {@code name} è vuoto o se il nome è già stato usato da un'altro operatore. 
     */
    public static Operatore of(final String name) {
      if (Objects.requireNonNull(name, "Il nome non può essere null.").isBlank())
        throw new IllegalArgumentException("Il nome non può essere vuoto.");
      if (ISTANZE.contains(name)) throw new IllegalArgumentException("Nome già usato.");
      ISTANZE.add(name);
      return new Operatore(name);
    }

    /**
     * Viene istanziato un nuovo operatore. 
     * @param nomeOperatore nome del nuovo operatore.
     */
    private Operatore(String nomeOperatore) {
        nome = nomeOperatore;
        budget = 0; 
        azioniPossedute = new TreeMap<>();
    }

    /**
     * Restituisce il nome dell'operatore.
     * @return il nome dell'operatore.
     */
    public String nome() {
        return nome;
    }

    /**
     * Restituisce il budget attuale dell'operatore.
     * @return il budget di questo operatore.
     */
    public int budget() {
      return budget;
    }

    /**
     * Restituisce la quantità di una determinata azione possedute da quoesto operatore.
     * @param azione l'azione di cui si vuole sapere la quantità posseduta.
     * @return il numero di azioni della specifica azienda possedute da questo operatore.
     * @throws NoSuchElementException se questo operatore non possiede azioni di questa azienda.
     * @throws NullPointerException se l'azienda è null.
     */
    public int numeroAzioni(Azione azione) throws NoSuchElementException, NullPointerException {
      Objects.requireNonNull(azione, "L'azione non può essere null.");
      int nAzioni = azioniPossedute.getOrDefault(azione, 0);
      if (nAzioni > 0) return nAzioni;
      throw new NoSuchElementException("Questo operatore non possiede l'azione.");
    }
    
    /**
     * Restituisce, attraverso un valore booleano, se questo operatore trova {@code azione} nelle azioni che possiede.
     * @param azione l'azione da conoscere.
     * @return {@code true} se l'azione è posseduta, {@code false} altrimenti.
     * @throws NullPointerException se l'azione è null.
     */
    public boolean possiedeAzione(Azione azione) throws NullPointerException {
      Objects.requireNonNull(azione, "L'azione non può essere null.");
      return azioniPossedute.containsKey(azione);
    }

    /**
     * Aggiorna l'elenco delle azioni possedute da questo operatore, in una determinata borsa, confrontandosi con la borsa.
     * @param borsa la borsa con il quale confrontare le azioni possedute.
     * @throws NullPointerException se la borsa è {@code null}.
     */
    public void aggiornaAzioni(Borsa borsa) throws NullPointerException {
      Objects.requireNonNull(borsa, "La borsa non può essere null.");
      Iterator<Azione> azioniBorsa = borsa.azioniQuotate();
      while (azioniBorsa.hasNext()) {
        Azione a = azioniBorsa.next();
       try  {
        int numeroAzioni = a.azioniDetenute(this);
        if (a.nomeBorsa().equals(borsa.nome())) azioniPossedute.put(a, numeroAzioni);
        } catch (NoSuchElementException e) {
          azioniPossedute.remove(a);
        }
      }
    }

    /**
     * Ritorna il valore totale di tutte le azioni possedute da questo operatore.
     * @return il valore di tutte le azioni possedute.
     */
    public int valoreAzioni() {
      int valoreTotale = 0; 
      for (Map.Entry<Azione, Integer> elemento: azioniPossedute.entrySet()) {
        valoreTotale += elemento.getKey().valore() * elemento.getValue();
       }
       return valoreTotale;
    }

    /**
     * Restituisce un iteratore delle azioni possedute da questo operatore.
     * @return l'iteratore alle azioni possedute da questo operatore. 
     */
    public Iterator<Azione> elencoAzioni() {
      return Collections.unmodifiableCollection(azioniPossedute.keySet()).iterator();
    }

    /**
     * Esegue un deposito di fondi dentro al budget.
     * @param daDepositare la quantità da depositare.
     * @throws IllegalArgumentException se {@code daDepositare} è minore o uguale a 0.
     */
    public void deposita(int daDepositare) throws IllegalArgumentException {
      if (daDepositare <= 0) throw new IllegalArgumentException("Il deposito non può avere valore nullo o negativo.");
      budget += daDepositare;
    }

    /**
     * Permette un prelievo di fondi dal budget.
     * @param daPrelevare quantità di denaro da prelevare.
     * @throws IllegalArgumentException se l'operatore non ha abbastanza soldi per prelevare la somma richiesta o se la somma da prelevare è negativa o ugauale a 0.
     */
    public void preleva(int daPrelevare)throws IllegalArgumentException {
      if (daPrelevare > budget || daPrelevare <= 0) throw new IllegalArgumentException("Non hai abbastanza soldi per prelevare questa somma.");
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
