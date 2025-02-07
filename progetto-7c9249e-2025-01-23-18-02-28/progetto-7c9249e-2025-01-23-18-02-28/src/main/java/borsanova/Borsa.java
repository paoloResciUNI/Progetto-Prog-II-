package borsanova;

import java.util.*;

import borsanova.PoliticaPrezzo.PoliticaPrezzo;


//Per lo svolgimento del progetto mi sono confrontato con i miei colleghi: Fernando Gavezzotti, Matteo Mascherpa e Alessandro Fascini. 
 

/**
 * La borsa tiene traccia di tutte le aziende quotate e delle loro azioni. Tiene anche traccia di tutti gli operatori che 
 * operano con questa borsa. Usa anche una politica di prezzo per operare direttamente sul valore delle singole azioni. 
 */
public class Borsa implements Comparable<Borsa> {
    /**{@code ISTANZE} è una collezione contenente tutti i nomi usati per nominare le borse. */
    private static final SortedSet<String> ISTANZE = new TreeSet<>();
    /**{@code nome} è il nome di questa borsa. */
    private final String nome;
    /**{@code azioniQuotate} è una collezione contente tutte le azioni in questa borsa.*/
    private final SortedSet<Azione> azioniQuotate;
    /**{@code operatoriBorsa} Collezione che tiene traccia di tutti gli operatori. */
    private final SortedSet<Operatore> operatoriBorsa;
    /**{@code politicaPrezzo} è la politica di prezzo che gestisce la variazione del valore delle azioni. */
    private PoliticaPrezzo politicaPrezzo;
      
    /*-
     * AF:
     *    - nome: è il nome che identifica la borsa. 
     *    - aziendeQuotate: l'insieme di tutte le aziende quotate in questa borsa.
     *    - operatoriBorsa: tiene traccia di tutti gli operatori che operano con questa borsa.
     *    - politicaPrezzo: è la politica che gestisce la variazione del valore delle azioni.
     * RI:
     *    - nome != null && !nome.isBlank().
     *    - aziendeQuotate != null && a != null per ogni a in aziendeQuotate.
     *    - operatoriBorsa != null && o != null per ogni o in operatoriBorsa.
     *    
     */

    /**
     * Questo è il metodo di fabbricazione per il tipo Borsa
     * 
     * @param name il nome da dare alla nuova borsa creata.
     * @throws IllegalArgumentException se {@code name} è {@code null} oppure se il nome è già in uso.  
     * @return la nuova borsa creata. 
     */
    public static Borsa of(final String name) throws IllegalArgumentException {
        if (Objects.requireNonNull(name, "Name must not be null.").isBlank())
            throw new IllegalArgumentException("Name must not be empty.");
        if (ISTANZE.contains(name))
            throw new IllegalArgumentException("Name already used.");
        ISTANZE.add(name);
        return new Borsa(name);
    }

    /**
     * Costruttore per la classe Borsa. 
     * @param nome il nome della Borsa. 
     */
    private Borsa(String nome) {
        this.nome = nome;
        azioniQuotate = new TreeSet<>();
        operatoriBorsa = new TreeSet<>();
    }

    /**
     * Restituisce un iteratore per le azioni quotate in questa borsa. 
     * @return un iteratore per le azioni quotate in questa borsa. 
     */
    public Iterator<Azione> azioniQuotate() {
        return Collections.unmodifiableCollection(azioniQuotate).iterator();
    }

    /**
     * Cambia la politica prezzo della borsa.
     * @param politicaPrezzo la nuova politica prezzo della borsa.
     * @throws NullPointerException se la politica prezzo è {@code null}. 
     */
    public void politicaPrezzo(PoliticaPrezzo politicaPrezzo) throws NullPointerException {
        Objects.requireNonNull(politicaPrezzo);
        this.politicaPrezzo = politicaPrezzo;
    }

    /**
     * Mostra la politica di prezzo della borsa.
     * @return la politica di prezzo della borsa.
     */
    public PoliticaPrezzo politicaPrezzo() {
        return politicaPrezzo;
    }

    /**
     * Applica la politica di vendita all'azione.
     * @param azione l'azione a cui applicare la politica di vendita.
     * @param numeroAzioni il numero di azioni da vendere.
     * @throws NullPointerException se l'azione è {@code null}.
     */
    private void appilicaPoliticaVendita(Azione azione, int numeroAzioni) throws NullPointerException {
        Objects.requireNonNull(azione);
        var nuovoValore = politicaPrezzo.vendita(azione, numeroAzioni);
        azione.nuovoValore(nuovoValore);        
    }

    /**
     * Applica la politica di acquisto all'azione.
     * @param azione l'azione a cui applicare la politica di acquisto.
     * @param numeroAzioni il numero di azioni da acquistare.
     * @throws NullPointerException se l'azione è {@code null}.
     */
    private void appilicaPoliticaAcquisto(Azione azione, int numeroAzioni) throws NullPointerException {
        Objects.requireNonNull(azione);
        int nuovoValore = politicaPrezzo.acquisto(azione, numeroAzioni);
        azione.nuovoValore(nuovoValore);
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
     * @param azienda è il nome dell'azinda che si sta quotando in borsa.
     * @param valoreAzione valore per azione dell'azienda.
     * @param quantitàAzione quantità di azioni che l'azienda mette a disposizione.
     * @throws NullPointerException se il nome dell'azienda è {@code null}.
     * @throws IllegalArgumentException se la quantità delle azioni o il loro valore è minore o uguale a 0, oppure se l'azienda è già quotata in questa borsa.  
     */
    void QuotaAzienda(Azienda azienda, int valoreAzione, int quantitaAzione) throws NullPointerException {
        Objects.requireNonNull(azienda);
        if (quantitaAzione <= 0 || valoreAzione <= 0) throw new IllegalArgumentException("Il numero delle azioni e il loro valore deve essere maggiore di zero.");
        Azione nuovaAzione = new Azione(azienda, valoreAzione, quantitaAzione);
        if (azioniQuotate.contains(nuovaAzione)) throw new IllegalArgumentException("Questa azienda è già quotata in questa borsa!");
        Iterator<Borsa> borseAzienda = azienda.borseQuotate();
        while (borseAzienda.hasNext()) {
            if (borseAzienda.next().equals(this)) {
                azioniQuotate.add(nuovaAzione);
                return;
            }
        }
        throw new IllegalArgumentException("Non puoi quotare l'azienda in questa borsa.");
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
        for (Azione a: azioniQuotate) {
            if (nomeAzione.nome().equals(a.azienda().nome())) return a;  
        }
        throw new NoSuchElementException("Bisogna prendere le azione di un'azienda quotata in questa borsa.");
    }

    /**
     * Un'operatore richiede l'azione che vuole comprare a questa borsa e, se l'azione può essere rilasciata, quest'ultima applica la politica prezzo della alla fine dell'acquisto. 
     * @param operatore è l'operatore che vuole coprare un'azione da questa 
     * @param azienda è l'azienda del quale l'operatore vule comprare le azioni.
     * @param investimento è l'investimento che l'operatore vuole effettuare. 
     * @throws IllegalArgumentException se l'investimento dell'operatore è: maggiore del suo budget, minore del valore di una singola azione oppure se non ci sono abbastanza azioni da comprare nella borsa. 
     * @throws NullPointerException se l'operatore o la borsa sono {@code null}.
     */
    public void acquisto(Operatore operatore, Azienda azienda, int investimento) throws NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(operatore, "L'operatore non può essere null.");
        Objects.requireNonNull(azienda, "L'azione non può essere null.");
        Azione azione = cercaAzioneBorsa(azienda); 
        if (investimento > operatore.budget()) throw new IllegalArgumentException("L'operatore non ha i soldi per effettuare l'investimento.");
        if (investimento < azione.valore()) throw new IllegalArgumentException("L'operatore non ha abbastanza soldi per comprare queste azioni.");
        if (investimento/azione.valore() > azione.quantita()) throw new IllegalArgumentException("Non ci sono abbastanza azioni disponibili.");
        operatore.preleva((investimento/azione.valore())*azione.valore());
        azione.proprietari.put(operatore, investimento/azione.valore());
        if (politicaPrezzo != null) appilicaPoliticaAcquisto(azione, investimento);
        operatoriBorsa.add(operatore);
        operatore.aggiornaAzioni(this);
    }

    /**
     * Questa borsa controlla la vendita che l'operatore vuole fare e, se è possibile vendere, applica la politica prezzo dopo la vendita.  
     * @param operatore è l'operatore che vuole vendere un certo quantitativo di azioni.
     * @param azione è l'azione che l'operatore vule vendere.
     * @param quantita è la quantità di azioni che l'operatore vuole vendere. 
     * @throws NullPointerException se l'azione o l'operatore è {@code null}.
     * @throws IllegalArgumentException 
     */
    void vendita(Operatore operatore, Azione azione, int quantita) throws NullPointerException, IllegalArgumentException {
      Objects.requireNonNull(azione, "L'azione non può essere null.");
      Objects.requireNonNull(operatore, "L'operatore non può essere null");
      int azioniAttualmentePossedute = operatore.mostraQuantitaAzione(azione.azienda());
      if (operatore.possiedeAzione(azione) && azioniAttualmentePossedute < quantita) throw new IllegalArgumentException("L'operatore non ha abbastanza azioni da vendere.");
      int azioniRimanenti = azioniAttualmentePossedute - quantita;
      azione.proprietari.put(operatore, azioniRimanenti);
      operatore.deposita(quantita*azione.valore());
      appilicaPoliticaVendita(azione, quantita);
      operatore.aggiornaAzioni(this);
    }


    @Override 
    public boolean equals(Object obj) {
        if (obj instanceof Borsa other) {
            return nome.equals(other.nome());
        }
        return false;
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
        return nome+ ": " + azioniQuotate.toString() + "\n";
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
        /**{@code proprietari} è una mappa contenente i proprietari di questa azione associati al nemero di azioni possedute. */
        private TreeMap<Operatore, Integer> proprietari;

        /*-
         * AF:
         *    - nome: è il nome dell'azienda al quale è associata l'azione.
         *    - quantita: rappresenta il numero di azioni presenti in questa borsa.  
         *    - valore: rappresenta il valore della singola Azione. 
         *    - nomeBorsa: è il nome della borsa dove si trova l'azione. 
         *    - proprietari: è l'insieme degli operatori che possiedono questa azione e del numero di azioni possedute.
         *    
         * RI: 
         *    - azienda != null.
         *    - valore > 0.
         *    - quantità > 0.
         *    - nomeBorsa != null && !nomeBorsa.isBlank().
         *    - prorpietari != null && proprietiario != null per ogni proprietario in proprietari. 
         */

        /**
         * Crea un'insieme un'azione di per una determinata azienda.
         * @param nome il nome dell'azienda che emette l'azone.
         * @param value il valore per singola azione.
         * @param proprietari mappa contente i proprietari dell'azione con la relativa quantità. 
         * @param numeroAzioni il numero di azioni disponibili per l'acquisto.
         * @throws IllegalArgumentException se il numero di azioni o il valore per singola azione sono \le 0.
         * @throws NullPointerException se il nome dell'azienda è {@code null}. 
         */
        private Azione(Azienda nome, int value, int numeroAzioni) throws IllegalArgumentException, NullPointerException {
            Objects.nonNull(nome);
            if (value <= 0 || numeroAzioni <= 0) throw new IllegalArgumentException("Il valore dell'azione e la quantità di azioni deve essere > 0.");
            azienda = nome;
            valore = value;
            quantita = numeroAzioni;
            proprietari = new TreeMap<>();
        }

        /**
         * Prendo il nome dell'azienda che ha emesso l'azione. 
         * @return il nome dall'azienda.
         */
        public Azienda  azienda() {
            return azienda;
        }

        /**
         * Restituisce il nome della borsa.
         * @return il nome di questa borsa.
         */
        public String nomeBorsa() {
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
         * Ritorna le azioni possedute da uno specifico operatore. Se l'operatore non possiede stock di quest'azione restituisce 0. 
         * @param operatore è l'operatore del quale si vuole sapere il numero di azioni possedute.
         * @return il numero di azioni possedute dall'operatore.
         */
        public int azioniDetenute(Operatore operatore) {
            return proprietari.getOrDefault(operatore, 0);
        }

        /**
         * Modifica il valore dell'azione. 
         * @param nuovoValore il nuovo valore dell'azione.
         */
        private void nuovoValore(int nuovoValore) {
            valore = nuovoValore;
        }

        /**
         * Restituisce la quantità di azioni disponibili. 
         * @return la quantità delle aziende disponibili per l'acquisto. 
         */
        public int quantita() {
            int azioniVendute = 0;
            for (Operatore o: operatoriBorsa) {
                if (o.possiedeAzione(this)) azioniVendute += o.mostraQuantitaAzione(azienda);
            } 
            return quantita-azioniVendute;
        }

        @Override
        public int compareTo(Azione o) {
            if(this.nomeBorsa().equals(o.nomeBorsa())) return this.azienda().compareTo(o.azienda());
            return this.nomeBorsa().compareTo(o.nomeBorsa());
        }
        }

    }
