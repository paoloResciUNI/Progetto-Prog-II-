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
 * L'operatore tiene traccia delle azioni che possiede. 
 * 
 * Ogni operatore: 
 *  - è identificato da un nome. 
 *  - dspone di un budget che gli permette di comprare azioni nelle varie borse.
 *  - sa quali azioni possiede e in quale quantità.
 * 
 * L'operatore può restituire:
 *  - il nome che lo identifica.
 *  - il budget che possiede in un determinato momento.
 *  - se possiede o meno una determinata azione.
 *  - la quantità di una determinata azione, se ne è in possesso. 
 *  - l'elenco delle azioni detenute in un determinato momento. 
 * 
 * Inoltre può effettuare operazioni di deposito e prelievo sul proprio budget rispettivamente per aggiungere o sottrarre denaro.  
 * Il criterio di confronto e ordinamento degli operatori è il nome.
 */
public class Operatore implements Comparable<Operatore> {

    /**{@code ISTANZE} tiene traccia di tutti i nomi usati per definire gli operatori.*/ 
     private static final SortedSet<String> ISTANZE = new TreeSet<>();
    /**{@code nome} il nome che identifica l'operatore. */
     private final String nome; 
    /**{@code budget} il budget che l'operatore ha a disposizione per comprare le azioni.*/
    private int budget;
    /**{@code azioniPossedute} una mappa che contiene tutte le azioni possedute da questo operatore, con associato per ogni azione la quantità posseduta.*/
    private final Map<Azione, Integer> azioniPossedute;
    

    /*-
     * AF: 
     *    - nome: è il nome che identifica l'operatore. 
     *    - budget: è il budget che ogni operatore può usare per fare acquisti.
     *    - azioniPossedute: contiene tutte le azioni posseduta da questo operatore. 
     *      Ogni azione è associata alla quantità posseduta dall'operatore in un determinato momento.
     * RI:
     *    - nome != null && !nome.isBlank().
     *    - budget >= 0.
     *    - azioniPossedute != null.
     *    - k != null per ogni k all'interno di azioniPossedute.keySet().
     *    - v > 0 && v != null per ogni v in azioniPossedute.values().
     */


    /**
     * Metodo di fabbricazione per creare un'istanza di Operatore.
     * @param nome il nome da dare all'operatore.
     * @return un nuovo operatore di nome {@code nome}
     * @throws IllegalArgumentException se {@code nome} è vuoto o se il nome è già stato usato. 
     */
    public static Operatore of(final String nome) {
      if (Objects.requireNonNull(nome, "Il nome non può essere null.").isBlank())
        throw new IllegalArgumentException("Il nome non può essere vuoto.");
      if (ISTANZE.contains(nome)) throw new IllegalArgumentException("Nome già usato.");
      ISTANZE.add(nome);
      return new Operatore(nome);
    }

    /**
     * Costruisce una nuova istanza di operatore. 
     * @param nome nome del nuovo operatore.
     */
    private Operatore(String nome) {
        this.nome = nome;
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
     * Restituisce la quantità di una determinata azione possedute da questo operatore.
     * @param azione l'azione di cui si vuole sapere la quantità posseduta.
     * @return il numero di azioni possedute da questo operatore.
     * @throws NoSuchElementException se questo operatore non possiede questa azione.
     * @throws NullPointerException se l'azione è null.
     */
    public int numeroAzioni(Azione azione) throws NoSuchElementException, NullPointerException {
      Objects.requireNonNull(azione, "L'azione non può essere null.");
      int nAzioni = azioniPossedute.get(azione);
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
     * Aggiorna l'elenco delle azioni che questo operatore possiede confrontandole con le azioni presenti nella borsa.
     * Per ogni azione presente nella borsa viene controllata la mappa dei proprietari, se nella mappa compare questo operatore allora viene aggiunta l'azione nella mappa delle azioni possedute con 
     * il relativo quantitativo. 
     * @param borsa la borsa con il quale confrontare le azioni possedute.
     * @throws NullPointerException se la borsa è {@code null}.
     */
     void aggiornaAzioni(Borsa borsa) throws NullPointerException {
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
     * Restituisce il valore totale di tutte le azioni che questo operatore possiede.
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
     * @throws IllegalArgumentException se l'operatore non ha abbastanza denaro per prelevare la somma richiesta o se la somma da prelevare è negativa o ugauale a 0.
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
