package borsanova;

import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * La borsa tiene traccia di tutte le aziende quotate e delle loro azioni. Tiene anche traccia di tutti gli operatori che 
 * operano con questa borsa. Usa anche una politica di prezzo per operare direttamente sul valore delle singole azioni. 
 */
public class Borsa implements Comparable<Borsa> {
    /**{@code Nomi_Usati_Borse} è una collezione contenente tutti i nomi usati per nominare le borse. */
    private static final SortedSet<String> Nomi_Usati_Borse = new TreeSet<>();
    /**{@code nome} è il nome di questa borsa. */
    private final String nome;
    /**{@code aziendeQuotate} è una collezione contente tutte le aziende quotate in questa borsa.*/
    private SortedSet<Azione> aziendeQuotate;
    /**{@code operatoriBorsa} Collezione che tiene traccia di tutti gli operatori. */
    private SortedSet<Operatore> operatoriBorsa;
      
    /**
     * Questo è il metodo di fabbricazione per il tipo Borsa
     * 
     * @param name il nome da dare alla nuova borsa creata.
     * @throws IllegalArgumentException se {@code name} è {@code null} oppure se il nome è già in uso.  
     * @return la nuova borsa creata. 
     */
    public static Borsa of(final String name) {
        if (Objects.requireNonNull(name, "Name must not be null.").isBlank())
            throw new IllegalArgumentException("Name must not be empty.");
        if (Nomi_Usati_Borse.contains(name))
            throw new IllegalArgumentException("Name already used.");
        Nomi_Usati_Borse.add(name);
        return new Borsa(name);
    }

    /**
     * Costruttore per la classe Borsa. 
     * @param nome il nome della Borsa. 
     */
    private Borsa(String nome) {
        this.nome = nome;
        SortedSet<Azienda> aziendeQuotate = new TreeSet<>();
        SortedSet<Operatore> operatoriBorsa = new TreeSet<>();
    }

    /**
     * Restituisce il nome di questa borsa. 
     * @return il nome di questa borsa.
     */
    public String nome() {
        return nome;
    }

    /**
     * Aggiunge l'azione all'insieme delle aziende quotate in questa borsa 
     * @param nomeAzienda è il nome dell'azinda che si sta quotando in borsa.
     * @param valoreAzione valore per azione dell'azienda.
     * @param quantitàAzione quantità di azioni che l'azienda mette a disposizione.
     */
    public void aggiungiAzione(String nomeAzienda, int valoreAzione, int quantitàAzione) {
        Azione nuovaAzienda = new Azione(nomeAzienda, valoreAzione, quantitàAzione);
        aziendeQuotate.add(nuovaAzienda);
    }

    /**
     * Cerca l'azione di un'azienda quotata in borsa.
     * @param nomeAzione il nome dell'azienda di cui si voglia prendere l'azione. 
     * @return l'azione dell'azienda cerca che si è quotata in questa borsa.
     * @throws IllegalArgumentException se l'azienda cerca non è quotata in questa borsa. 
     */
    public Azione cercaAzioneBorsa(String nomeAzione) throws IllegalArgumentException {
        for (Azione a: aziendeQuotate) {
            if (nomeAzione.equals(a.aziendaAzione())) return a;  
        }
        throw new IllegalArgumentException("Bisogna prendere le azione di un'azienda quotata in questa borsa.");
    }

    @Override 
    public boolean equals(Object obj) {
        if (!(obj instanceof Borsa)) {
            return false;
        }
        return nome.equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }
    @Override
    public int compareTo(Borsa altraBorsa) {
        return nome.compareTo(altraBorsa.nome);
    }

    /**
     * Azione associata alla singola azienda. 
     */
    public class Azione {
        /**{@code azienda} l'azienda a cui è associata questa azione quando l'azienda si è quotata in questa borsa. */
        private String azienda;
        /**{@code valore} è il valore della singola azione. */
        private int valore;
        /**{@code quantità} è la quantità di azioni totalmente disponibili. */
        private int quantità;

        /**
         * AF:
         *      
         * RI: 
         *      
         */

        /**
         * Crea un'insieme un'azione di per una determinata azienda.
         * @param nome il nome dell'azienda che emette l'azone.
         * @param value il valore per singola azione.
         * @param numeroAzioni il numero di azioni disponibili per l'acquisto.
         * @throws IllegalArgumentException se il numero di azioni o il valore per singola azione sono \le 0. 
         */
        private Azione(String nome, int value, int numeroAzioni) throws IllegalArgumentException {
            if (value <= 0 || numeroAzioni <= 0) throw new IllegalArgumentException("Il valore dell'azione e la quantità di azioni deve essere > 0.");
            azienda = nome;
            valore = value;
            quantità = numeroAzioni;
        }

        /**
         * Prendo il nome dell'azinda che ha emesso l'azione. 
         * @return il nome dall'azienda.
         */
        public String aziendaAzione() {
            return azienda;
        }

        /**
         * Restituisce il valore per azione delle azioni. 
         * @return il valore dell'azione. 
         */
        public int valoreAzione() {
            return valore;
        }

        /**
         * Restituisce la quantità di azioni disponibili. 
         * @return la quantità delle aziende disponibili per l'acquisto. 
         */
        public int quantitàAzioni() {
            return quantità;
        }

    }
}
