package borsanova;

import java.util.*;

import borsanova.Borsa.Azione;

/**
 * L'operatore può comprare e vendere azioni delle aziende quotate in bora.
 */
public class Operatore implements Comparable<Operatore> {

    /**{@code Nomi_Usati_Operatore} tiene traccia di tutti i nomi usati per definire gli operatori.*/ 
     private static final SortedSet<String> nomiUsatiOperatore = new TreeSet<>();
    /**{@code nome} è il nome dell'operatore. */
     private final String nome; 
    /**{@code budget} è il budget che l'operatore ha a disposizione per comprare le azioni.*/
    private int budget;
    /**{@code azioniPossedute} una collezioni che contiene tutte le azioni possedute da questo operatore*/
    private TreeMap<Azione, Integer> azioniPossedute;
    

    /**
     * AF: 
     *    Ogni peratore è identificato da un nome. 
     *    Ogni operatore ha un budget.
     *    Ogni operatore ha una collezione di azioni possedute. Ogni azione posseduta è associata ad un intero che rappresenta la quantità di azioni possedute. 
     * RI:
     *    nome != null && nome != ""
     *    budget >= 0
     *    azioniPossedute.values() >= 0. Per ogni azione all'interno di azioniPossedute.
     *    azioniPossedute.keySet() != null. Per ogni azione all'interno di azioniPossedute.
     */


    /**
     * Fabbricatore dell'operatore
     * @param name il nome da dare all'operatore.
     * @return un nuovo operatore di nome {@code name}
     * @throws IllegalArgumentException se {@code name} è vuto o se il nome è già stato usato da un'altro operatore. 
     */
    public static Operatore of(final String name) {
      if (Objects.requireNonNull(name, "Name must not be null.").isBlank())
        throw new IllegalArgumentException("Name must not be empty.");
      if (nomiUsatiOperatore.contains(name)) throw new IllegalArgumentException("Name already used.");
      nomiUsatiOperatore.add(name);
      return new Operatore(name);
    }

    /**
     * Viene dato un nome all'operatore, che non sia ancora stato usato, e gli si da un budget di partenza pari a 0. 
     * @param nomeOperatore nome del nuovo operatore.
     */
    private Operatore(String nomeOperatore) {
        nome = nomeOperatore;
        budget = 0; 
        azioniPossedute = new TreeMap<Azione, Integer>();
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
     * @param nomeAzienda l'azienda di cui si vuole sapere la quantità di azioni possedute.
     * @return il numero di azioni della specifica azienda possedute dall'operatore.
     * @throws NoSuchElementException se l'operatore non possiede azioni di questa azienda.
     * @throws NullPointerException se l'azienda è null.
     */
    public Integer mostraQuantitaAzione(Azienda nomeAzienda) throws NoSuchElementException, NullPointerException {
      Objects.requireNonNull(nomeAzienda, "L'azienda non può essere null.");
      for (Azione a : azioniPossedute.keySet()) {
        if(a.azienda().nome().equals(nomeAzienda.nome())) return azioniPossedute.get(a);
      }
      throw new NoSuchElementException("L'operatore non ha stock di questa azienda");
    }
    
    /**
     * Se l'operatore trova l'azione nelle azioni possedute.
     * @param azione l'azione da conoscere.
     * @return {@code true} se l'azione è posseduta, {@code false} altrimenti.
     */
    public boolean possiedeAzione(Azione azione) {
      Objects.requireNonNull(azione, "L'azione non può essere null.");
      return azioniPossedute.containsKey(azione) && azioniPossedute.get(azione) > 0;
    }

    /**
     * Un'operatore decide di acquistare una o più azioni. L'investimento deve essere maggiore di 0.
     * L'investimento dell'operatore non può superare il suo budget e il budget deve essere maggiore del valore della singola azione.   
     * @param nomeBorsa è la borsa dal quale l'operatore vuole comprare le azioni dell'azienda che gli interessa.
     * @param nomeAzione è il nome dell'azienda di cui l'operatore vuole acquistare le azioni. 
     * @param investimento l'investimento. 
     * @throws IllegalArgumentException se viene inserito un numero troppo grande di azioni da acquistare rispetto 
     * a quelle acquistabili.
     * @throws NullPointerException se la borsa o l'azienda sono null.
     */
    public void investi(Borsa nomeBorsa, Azienda nomeAzione, int investimento) throws IllegalArgumentException { 
      Objects.requireNonNull(nomeBorsa, "La borsa non può essere null.");
      Objects.requireNonNull(nomeAzione, "L'azienda non può essere null.");  
      Azione azione = nomeBorsa.cercaAzioneBorsa(nomeAzione);
      if (investimento > budget) throw new IllegalArgumentException("Non hai abbastanza soldi per comprare queste azioni.");
      if (investimento < azione.valore()) throw new IllegalArgumentException("Non hai abbastanza soldi per comprare queste azioni.");
      if (investimento/azione.valore() > azione.quantita()) throw new IllegalArgumentException("Non ci sono abbastanza azioni disponibili.");
      Integer azioniComprate = investimento / azione.valore();
      preleva(azioniComprate * azione.valore());
      if (!(possiedeAzione(azione))) {
        azioniPossedute.put(azione, azioniComprate);
        nomeBorsa.aggiungiOperatore(this);
      } else {
        Integer nuovaQuantita = mostraQuantitaAzione(nomeAzione);
        nuovaQuantita += azioniComprate;
        azioniPossedute.put(azione, nuovaQuantita);
      }
      if (nomeBorsa.mostraPoliticaPrezzo() != null) nomeBorsa.appilicaPoliticaAcquisto(azione, investimento); 
    }

    /**
     * Un'operatore decide di vendere una o più azioni nella borsa.
     * @param azione l'azione che l'operatore vuole vendere.
     * @param borsa la borsa in cui l'operatore vende le azioni.
     * @param quantità la quantità di azioni che l'operatore vuole vendere.
     * @throws IllegalArgumentException se l'operatore non possiede abbastanza azioni da vendere.
     * @throws NullPointerException se l'azione è null.
     */
    public void vendi(Borsa borsa, Azione azione, int quantità) {
      Objects.requireNonNull(azione, "L'azione non può essere null.");
      int azioniAttualmentePossedute = mostraQuantitaAzione(azione.azienda());
      if (possiedeAzione(azione) && azioniAttualmentePossedute < quantità) throw new IllegalArgumentException("Non hai abbastanza azioni da vendere.");
      azioniPossedute.put(azione, azioniAttualmentePossedute - quantità);
      budget += azione.valore() * quantità;
      if (borsa.mostraPoliticaPrezzo() != null) borsa.appilicaPoliticaVendita(azione, quantità);
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
