package borsanova;

import java.util.*;
import borsanova.Borsa.Azione;

/**
 * L'operatore può comprare e vendere azioni delle aziende quotate in bora, in base al budget che possiede.
 * Il budget non può essere negativo. 
 * L'operatore tiene traccia delle azioni che possiede. 
 */
public class Operatore implements Comparable<Operatore> {

    /**{@code ISTANZE} tiene traccia di tutti i nomi usati per definire gli operatori.*/ 
     private static final SortedSet<String> ISTANZE = new TreeSet<>();
    /**{@code nome} è il nome dell'operatore. */
     private final String nome; 
    /**{@code budget} è il budget che l'operatore ha a disposizione per comprare le azioni.*/
    private int budget;
    /**{@code azioniPossedute} una collezione che contiene tutte le azioni possedute da questo operatore, con associato per ogni azione la sua quantità.*/
    private final Map<Azione, Integer> azioniPossedute;
    

    /*-
     * AF: 
     *    - nome: è il nome che identifica l'operatore. 
     *    - budget: è il budget che ogni operatore può usare per fare acquisti, inizialmente uguale a 0.
     *    - azioniPossedute: contiene tutte le azioni posseduta da questo operatore. Ogni azione è associata alla quantità posseduta dall'operatore in un determinato momento.
     * RI:
     *    - nome != null && !nome.isBlank().
     *    - budget >= 0.
     *    - k != null per ogni azione all'interno di azioniPossedute.keySet().
     *    - v > 0 per ogni v in azioniPossedute.values().
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
     * Viene dato un nome all'operatore e gli si da un budget di partenza pari a 0. 
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
     * Restituisce la quantità di azioni possedute dall'operatore.
     * @param azione l'azione di cui si vuole sapere la quantità di stock posseduti.
     * @return il numero di azioni della specifica azienda possedute dall'operatore.
     * @throws NoSuchElementException se l'operatore non possiede azioni di questa azienda.
     * @throws NullPointerException se l'azienda è null.
     */
    public int numeroAzioni(Azione azione) throws NoSuchElementException, NullPointerException {
      Objects.requireNonNull(azione, "L'azione non può essere null.");
      return azioniPossedute.getOrDefault(azione, 0);
    }
    
    /**
     * Se l'operatore trova {@code azione} nelle azioni possedute viene restituito un valore booleano {@code true} altrimenti {@code false}.
     * @param azione l'azione da conoscere.
     * @return {@code true} se l'azione è posseduta, {@code false} altrimenti.
     */
    public boolean possiedeAzione(Azione azione) {
      Objects.requireNonNull(azione, "L'azione non può essere null.");
      return azioniPossedute.containsKey(azione);
    }

    /**
     * Aggiorna l'elenco delle azioni possedute da questo operatore, in una determinata borsa, confrontandosi con la borsa.
     * @param borsa la borsa con il quale confrontare le azioni possedute. 
     */
    public void aggiornaAzioni(Borsa borsa) {
      Iterator<Azione> azioniBorsa = borsa.azioniQuotate();
      while (azioniBorsa.hasNext()) {
        Azione a = azioniBorsa.next();
        int numeroAzioni = a.azioniDetenute(this);
        if (numeroAzioni > 0 && a.nomeBorsa().equals(borsa.nome())) {
          azioniPossedute.put(a, numeroAzioni);
        } else if (numeroAzioni == 0 && azioniPossedute.containsKey(a)) azioniPossedute.remove(a);
      }
    }

    /**
     * Ritorna il valore totale di tutte le azioni possedute dall'operatore.
     * @return un intero raprresentante il valore di tutte le azioni possedute, se l'operatore non ne possiede restituisce 0.
     */
    public int valoreAzioni() {
      int valoreTotale = 0; 
      for (Map.Entry<Azione, Integer> elemento: azioniPossedute.entrySet()) {
        valoreTotale += elemento.getKey().valore() * elemento.getValue();
       }
       return valoreTotale;
    }

    /**
     * Restituisce un iteratore alle sole azioni possedute dall'operatore.
     * @return l'iteratore alle azioni possedute da questo operatore. 
     */
    public Iterator<Azione> elencoAzioni() {
      return Collections.unmodifiableCollection(azioniPossedute.keySet()).iterator();
    }

    /**
     * Deposito di fondi dentro il budget.
     * @param daDepositare la quantità da depositare.
     * @throws IllegalArgumentException se {@code daDepositare} è minore o uguale a 0.
     */
    public void deposita(int daDepositare) throws IllegalArgumentException {
      if (daDepositare <= 0) throw new IllegalArgumentException("Il deposito non può avere valore nullo o negativo.");
      budget += daDepositare;
    }

    /**
     * Prelievo di fondi dal budget.
     * @param daPrelevare quantità di denaro da prelevare.
     * @throws IllegalArgumentException se l'operatore non ha abbastanza soldi per prelevare questa somma.
     */
    public void preleva(int daPrelevare)throws IllegalArgumentException {
      if (daPrelevare > budget) throw new IllegalArgumentException("Non hai abbastanza soldi per prelevare questa somma.");
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
