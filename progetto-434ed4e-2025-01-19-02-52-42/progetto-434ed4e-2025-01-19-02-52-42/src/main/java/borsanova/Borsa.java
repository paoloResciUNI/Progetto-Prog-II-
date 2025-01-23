package borsanova;

import java.util.*;

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
    private final SortedSet<Azione> aziendeQuotate;
    /**{@code operatoriBorsa} Collezione che tiene traccia di tutti gli operatori. */
    private final SortedSet<Operatore> operatoriBorsa;
      
    /**
     * AF:
     *    La borsa ha un nome.
     *    La borsa tiene traccia di tutte le aziende quotate.
     *    La borsa tiene traccia di tutti gli operatori che operano con questa borsa.
     * RI:
     *    Il nome della borsa non può essere vuoto o null.
     *    Il nome della borsa non può essere già stato usato.
     */

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
        aziendeQuotate = new TreeSet<Azione>();
        operatoriBorsa = new TreeSet<Operatore>();
    }

    /**
     * Restituisce un iteratore per le azioni quotate in questa borsa. 
     * @return un iteratore per le azioni quotate in questa borsa. 
     */
    public Iterator<Azione> aziendeQuotate() {
        return Collections.unmodifiableCollection(aziendeQuotate).iterator();
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
     * @throws NullPointerException se il nome dell'azienda è {@code null}.
     */
    public void aggiungiAzione(Azienda nomeAzienda, int valoreAzione, int quantitàAzione) {
        Objects.requireNonNull(nomeAzienda);
        Azione nuovaAzienda = new Azione(nomeAzienda, valoreAzione, quantitàAzione);
        aziendeQuotate.add(nuovaAzienda);
    }

    /**
     * Cerca l'azione di un'azienda quotata in borsa.
     * @param nomeAzione il nome dell'azienda di cui si voglia prendere l'azione. 
     * @return l'azione dell'azienda cerca che si è quotata in questa borsa.
     * @throws NoSuchElementException se l'azienda cerca non è quotata in questa borsa. 
     * @throws NullPointerException se il nome dell'azienda è {@code null}.
     */
    public Azione cercaAzioneBorsa(Azienda nomeAzione) throws NoSuchElementException {
        Objects.requireNonNull(nomeAzione);
        for (Azione a: aziendeQuotate) {
            if (nomeAzione.nome().equals(a.azienda().nome())) return a;  
        }
        throw new NoSuchElementException("Bisogna prendere le azione di un'azienda quotata in questa borsa.");
    }

    /**
     * Ricerca del numero di azioni disponibili nella borsa. 
     * @param nomeAzione è il nome dell'azione da cercare.
     * @return il numeor di azioni disponibili ancora in questa borsa.
     * @throws NullPointerException se il nome dell'azienda è {@code null}.
     */
    public int azioniDisponibili(Azienda nomeAzione) {
        Objects.requireNonNull(nomeAzione);
        int disponibili = 0; 
        int comprate = 0;
        for (Azione a: aziendeQuotate) {
            if (nomeAzione.nome().equals(a.azienda().nome())) disponibili = a.quantita();
        }
        for (Operatore o: operatoriBorsa) {
            comprate += o.mostraAzioniPossedute(nomeAzione);
        }
        return disponibili - comprate;
    }
    
    /**
     * Aggiunge un operatore alla borsa.
     * @param nuovoOperatore il nuovo operatore da aggiungere alla borsa.
     * @throws NullPointerException se il nome dell'operatore è {@code null}.  
     */
    public void aggiungiOperatore(Operatore nuovoOperatore) {
        Objects.requireNonNull(nuovoOperatore);
        operatoriBorsa.add(nuovoOperatore);
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

    @Override
    public String toString() {
        return nome+ ": " + aziendeQuotate.toString() + "\n";
    }

    /**
     * Azione associata alla singola azienda. 
     */
    public class Azione implements Comparable<Azione> {
        /**{@code azienda} l'azienda a cui è associata questa azione quando l'azienda si è quotata in questa borsa. */
        private final Azienda azienda;
        /**{@code valore} è il valore della singola azione. */
        private int valore;
        /**{@code quantità} è la quantità di azioni totalmente disponibili. */
        private int quantita;

        /**
         * AF:
         *    L'azione è associata ad una singola azienda.
         *    L'azione ha un valore per singola azione.
         *    L'azione ha una quantità di azioni disponibili. 
         * RI: 
         *    L'azienda a cui è associata l'azione non può essere vuota o null.
         *    Il valore per singola azione deve essere > 0.
         *    L'azienda deve avere un numero di azioni disponibili > 0.
         */

        /**
         * Crea un'insieme un'azione di per una determinata azienda.
         * @param nome il nome dell'azienda che emette l'azone.
         * @param value il valore per singola azione.
         * @param numeroAzioni il numero di azioni disponibili per l'acquisto.
         * @throws IllegalArgumentException se il numero di azioni o il valore per singola azione sono \le 0.
         * @throws NullPointerException se il nome dell'azienda è {@code null}. 
         */
        private Azione(Azienda nome, int value, int numeroAzioni) throws IllegalArgumentException {
            Objects.nonNull(nome);
            if (value <= 0 || numeroAzioni <= 0) throw new IllegalArgumentException("Il valore dell'azione e la quantità di azioni deve essere > 0.");
            azienda = nome;
            valore = value;
            quantita = numeroAzioni;
        }

        /**
         * Prendo il nome dell'azinda che ha emesso l'azione. 
         * @return il nome dall'azienda.
         */
        public Azienda  azienda() {
            return azienda;
        }

        public String borsa() {
            return nome;
        }

        /**
         * Restituisce il valore per azione delle azioni. 
         * @return il valore dell'azione. 
         */
        public int valore() {
            return valore;
        }

        /**
         * Restituisce la quantità di azioni disponibili. 
         * @return la quantità delle aziende disponibili per l'acquisto. 
         */
        public int quantita() {
            int azioniVendute = 0;
            for (Operatore o: operatoriBorsa) {
                if (o.possiedeAzione(this)) azioniVendute += o.mostraAzioniPossedute(azienda);
            } 
            return quantita-azioniVendute;
        }

        @Override
        public int compareTo(Azione o) {
            if(this.borsa().equals(o.borsa())) return this.azienda().compareTo(o.azienda());
            return this.borsa().compareTo(o.borsa());
        }
        }

    }
