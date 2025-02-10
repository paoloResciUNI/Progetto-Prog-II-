package borsanova;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import borsanova.politicaPrezzo.PoliticaPrezzo;


//Per lo svolgimento del progetto mi sono confrontato con i miei colleghi: Fernando Gavezzotti, Matteo Mascherpa, Alessandro Fascini e Gabriele Fioco. 
 

/**
 * La borsa tiene traccia di tutte le aziende quotate in essa e delle relative azioni. 
 * La borsa tiene traccia di tutti gli operatori che operano su di essa.
 * 
 * Ogni borsa: 
 *  - è identificata da un nome.
 *  - ha un'insieme contenente le azioni di tutte le aizende quotate in essa.
 *  - ha un'insieme di tutti gli operatori che hanno operato con essa. 
 *  - ha una politica prezzo che indica la variazione del prezzo secondo certi criteri in un determinato momento. 
 * 
 * Ogni borsa può:
 *  - restituire il nome che la identifica. 
 *  - restituire l'azione relativa ad una determinata azienda, se quest'ultima si è quota nella borsa in questione.
 *  - restituire un iteratore per le azioni presenti in essa.
 *  - restituire la politica prezzo vigente in un determinato momento.
 *  - cambiare la politica prezzo in ogni momento. 
 *  - quotare un'azienda, nella borsa in questione.
 *  - permettere ad un'operatore di comprare, se possibile, una carta quantità di azioni.
 *  - permettere ad un'operatore di vendere, se possibile, una certa quantità di azioni.  
 */
public class Borsa implements Comparable<Borsa> {
    /**{@code ISTANZE} è una collezione contenente tutti i nomi usati per nominare le borse. */
    private static final SortedSet<String> ISTANZE = new TreeSet<>();
    /**{@code nome} è il nome che identifica questa borsa. */
    private final String nome;
    /**{@code azioniQuotate} contiene tutte le azioni quotate in questa borsa.*/
    private final SortedSet<Azione> azioniQuotate;
    /**{@code operatoriBorsa} tiene traccia di tutti gli operatori che operano con questa borsa. */
    private final SortedSet<Operatore> operatoriBorsa;
    /**{@code politicaPrezzo} sancisce la variazione del valore delle azioni in base a determinati criteri. */
    private PoliticaPrezzo politicaPrezzo;
      
    /*-
     * AF:
     *    - nome: è il nome che identifica la borsa. 
     *    - azioniQuotate: l'insieme di tutte le azioni quotate in questa borsa.
     *    - operatoriBorsa: tiene traccia di tutti gli operatori che operano con questa borsa.
     *    - politicaPrezzo: è la politica che gestisce la variazione del valore delle azioni.
     * RI:
     *    - nome != null && !nome.isBlank().
     *    - azioniQuotate != null && a != null per ogni a in azioniQuotate.
     *    - operatoriBorsa != null && o != null per ogni o in operatoriBorsa.   
     */

    /**
     * Fabbricazione della Borsa
     * 
     * @param nome il nome da dare alla nuova borsa creata.
     * @throws IllegalArgumentException se {@code name} è {@code null} oppure se il nome è già in uso.  
     * @return la nuova borsa creata. 
     */
    public static Borsa of(final String nome) throws IllegalArgumentException {
        if (Objects.requireNonNull(nome, "Name must not be null.").isBlank())
            throw new IllegalArgumentException("Name must not be empty.");
        if (ISTANZE.contains(nome))
            throw new IllegalArgumentException("Name already used.");
        ISTANZE.add(nome);
        return new Borsa(nome);
    }

    /**
     * Costruisce una nuova istanza di Borsa. 
     * @param nome il nome della Borsa. 
     */
    private Borsa(String nome) {
        this.nome = nome;
        azioniQuotate = new TreeSet<>();
        operatoriBorsa = new TreeSet<>();
    }

    /**
     * Restituisce il nome di questa borsa. 
     * @return il nome di questa borsa.
     */
    public String nome() {
        return nome;
    }

    /**
     * Cerca l'azione relativa un'azienda quotata in questa borsa.
     * @param azienda l'azienda di cui si vuole prendere l'azione. 
     * @return l'azione relativa all'azienda cercata che si è quotata in questa borsa.
     * @throws NoSuchElementException se l'azienda cerca non è quotata in questa borsa. 
     * @throws NullPointerException se il nome dell'azienda è {@code null}.
     */
    public Azione cercaAzioneBorsa(Azienda azienda) throws NoSuchElementException {
        Objects.requireNonNull(azienda);
        for (Azione a: azioniQuotate) {
            if (azienda.nome().equals(a.azienda().nome())) return a;  
        }
        throw new NoSuchElementException("Bisogna prendere le azione di un'azienda quotata in questa borsa.");
    }

    /**
     * Restituisce un iteratore per le azioni quotate in questa borsa. 
     * @return un iteratore per le azioni quotate in questa borsa. 
     */
    public Iterator<Azione> azioniQuotate() {
        return Collections.unmodifiableCollection(azioniQuotate).iterator();
    }

    /**
     * Cambia la politica prezzo di questa borsa borsa.
     * @param politicaPrezzo la nuova politica prezzo di questa borsa borsa.
     */
    public void politicaPrezzo(PoliticaPrezzo politicaPrezzo) throws NullPointerException {
        this.politicaPrezzo = politicaPrezzo;
    }

    /**
     * Restituisce la politica di prezzo di questa borsa.
     * @return la politica di prezzo di questa borsa.
     */
    public PoliticaPrezzo politicaPrezzo() {
        return politicaPrezzo;
    }


    /**
     * Quota un'azione in questa borsa. 
     * @param azienda è l'azienda che si sta quotando in questa borsa.
     * @param valoreAzione valore per azione dell'azienda.
     * @param quantitaAzione quantità di azioni che l'azienda mette a disposizione.
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
     * Permette l'acquisto di un determinato numero di azioni da parte di un'operatore. 
     * @param operatore è l'operatore che vuole comprare un'azione da questa borsa.
     * @param azienda è l'azienda del quale l'operatore vuole comprare le azioni.
     * @param investimento è il capitale che l'operatore vuole investire. 
     * @throws IllegalArgumentException se l'investimento dell'operatore è: maggiore del suo budget, minore del valore di una singola azione oppure se non ci sono abbastanza azioni da comprare nella borsa. 
     * @throws NullPointerException se l'operatore o la borsa sono {@code null}.
     */
    public void acquisto(Operatore operatore, Azienda azienda, int investimento) throws NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(operatore, "L'operatore non può essere null.");
        Objects.requireNonNull(azienda, "L'azione non può essere null.");
        Azione azione = cercaAzioneBorsa(azienda); 
        if (investimento > operatore.budget()) throw new IllegalArgumentException("L'operatore non ha i soldi per effettuare l'investimento.");
        if (investimento < azione.valore()) throw new IllegalArgumentException("L'operatore non ha abbastanza soldi per comprare queste azioni.");
        if (investimento/azione.valore() > azione.quantitaDisponibile()) throw new IllegalArgumentException("Non ci sono abbastanza azioni disponibili.");
        operatore.preleva((investimento/azione.valore())*azione.valore());
        if (azione.proprietari.containsKey(operatore)) {
            int azioniInPossesso = azione.proprietari.get(operatore); 
            azione.proprietari.put(operatore, (investimento/azione.valore())+azioniInPossesso);
        } else azione.proprietari.put(operatore, investimento/azione.valore());
        if (politicaPrezzo != null) {
            int nuovoValore = politicaPrezzo.acquisto(azione, investimento/azione.valore());
            azione.valore(nuovoValore);
        }
        operatoriBorsa.add(operatore);
        operatore.aggiornaAzioni(this);
    }

    /**
     * Permette la vendita di un certo numero di azioni da parte dell'operatore a questa borsa. 
     * @param operatore è l'operatore che vuole vendere un certo quantitativo di azioni.
     * @param azione è l'azione che l'operatore vuole vendere.
     * @param quantita è la quantità di azioni che l'operatore vuole vendere. 
     * @throws NullPointerException se l'azione o l'operatore è {@code null}.
     * @throws IllegalArgumentException se l'operatore non possiede le azioni che vuole vendere o non ne possiede in sufficiente quantità.
     */
    public void vendita(Operatore operatore, Azione azione, int quantita) throws NullPointerException, IllegalArgumentException {
      Objects.requireNonNull(azione, "L'azione non può essere null.");
      Objects.requireNonNull(operatore, "L'operatore non può essere null");
      int azioniAttualmentePossedute = operatore.numeroAzioni(azione);
      if (operatore.possiedeAzione(azione) && azioniAttualmentePossedute < quantita) throw new IllegalArgumentException("L'operatore non ha abbastanza azioni da vendere.");
      int azioniRimanenti = azioniAttualmentePossedute - quantita;
      azione.proprietari.put(operatore, azioniRimanenti);
      operatore.deposita(quantita*azione.valore());
      if (politicaPrezzo != null) {
        var nuovoValore = politicaPrezzo.vendita(azione, quantita);
        azione.valore(nuovoValore);     
      }
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

    /**
     * L'azione può essere venduta o acquistata da un'operatore all'interno della borsa. 
     * L'azione può cambiare valore in base alla politica di prezzo della borsa.
     * 
     * Ogni azione:
     *  - è identificata dall'azienda che l'ha emessa.
     *  - ha un valore per singola azione.
     *  - sa la quantità di azioni, della stessa azienda, presenti nella borsa. 
     *  - sa chi la posside e sa la quantità di azioni che ogni proprietario ha. 
     * 
     * L'azione può:
     *  - restituire l'azienda che l'ha emessa.
     *  - restituire il nome della borsa nel quale si trova. 
     *  - restituire il suo valore in un determinato momento.
     *  - restituire la quantità di azioni presenti nella borsa.
     *  - restituire la quantità di azioni disponibili per essere acquistate.
     *  - restituire il numero di azioni possedute da un'operatore, se ne possiede.
     * 
     * Inoltre il valore dell'azione può essere cambiato in base alla politica prezzo, se presente.
     */
    public class Azione implements Comparable<Azione> {
        /**{@code azienda} l'azienda a cui è associata questa azione quando si è quotata in borsa. */
        private final Azienda azienda;
        /**{@code valore} è il valore per ogni singola azione. */
        private int valore;
        /**{@code quantita} è la quantità di azioni esistenti. */
        private final int quantita;
        /**{@code proprietari} contiene i proprietari di questa azione associati al nemero di azioni possedute da ogni proprietario. */
        private TreeMap<Operatore, Integer> proprietari;

        /*-
         * AF:
         *    - azienda: è l'azienda al quale è associata l'azione.
         *    - quantita: rappresenta il numero di azioni presenti in questa borsa.  
         *    - valore: rappresenta il valore della singola Azione. 
         *    - proprietari: è l'insieme degli operatori che possiedono questa azione e ogni proprietario è associato al numero di azioni che possiede.
         *    
         * RI:  
         *    - azienda != null.
         *    - valore > 0.
         *    - quantità > 0.
         *    - nomeBorsa != null && !nomeBorsa.isBlank().
         *    - proprietari.keySet() != null && k != null per ogni k in proprietari.keySet().
         *    - proprietari.values() != null && v != null per ogni v in proprietari.values().   
         */

        /**
         * Costruisce un'azione associata ad una determinata azienda.
         * @param nome l'azienda che emette l'azone.
         * @param value il valore per singola azione.
         * @param numeroAzioni il numero di azioni che l'azienda vuole emettere.
         * @throws IllegalArgumentException se il numero di azioni o il valore per singola azione sono minori o uguali a 0.
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
         * Restituisce l'azienda a cui è associata l'azione. 
         * @return l'azienda.
         */
        public Azienda  azienda() {
            return azienda;
        }

        /**
         * Restituisce il nome della borsa nel quale si trova questa azione.
         * @return il nome della borsa.
         */
        public String nomeBorsa() {
            return nome;
        }

        /**
         * Restituisce il valore per singola azione. 
         * @return il valore dell'azione. 
         */
        public int valore() {
            return valore;
        }

        /**
         * Restituisce la quantità totale di azioni esistenti in questa borsa.
         * @return la quantità di azioni nella borsa. 
         */
        public int quantita() {
            return quantita;
        }
        
        /**
         * Restituisce la quantità di azioni disponibili per essere acquistate. 
         * @return la quantità delle aziende disponibili per l'acquisto. 
         */
        public int quantitaDisponibile() {
            int azioniVendute = 0;
            for (Operatore o: operatoriBorsa) {
                if (o.possiedeAzione(this)) azioniVendute += o.numeroAzioni(this);
            } 
            return quantita-azioniVendute;
        }

        /**
         * Restituisce il numero di azioni possedute da uno specifico operatore. Se l'operatore non ne possiede restituisce 0. 
         * @param operatore è l'operatore del quale si vuole sapere il numero di azioni possedute.
         * @return il numero di azioni possedute dall'operatore.
         * @throws NoSuchElementException se l'operatore non possiede questa azionione. 
         */
        public int azioniDetenute(Operatore operatore) {
            int nAzioni = proprietari.getOrDefault(operatore, 0);
            if (nAzioni > 0) return nAzioni;
            throw new NoSuchElementException("L'operatore non possiede questa azione.");
        }

        /**
         * Modifica il valore dell'azione. 
         * @param nuovoValore il nuovo valore dell'azione.
         * @throws IllegalArgumentException se il nuovo valore è minore o uguale a 0.
         */
        private void valore(int nuovoValore) {
            if (nuovoValore <= 0) throw new IllegalArgumentException("Il nuovo valore non può essere minore o uguale a 0");
            valore = nuovoValore;
        }


        @Override
        public int compareTo(Azione o) {
            if(this.nomeBorsa().equals(o.nomeBorsa())) return this.azienda().compareTo(o.azienda());
            return this.nomeBorsa().compareTo(o.nomeBorsa());
        }
        }

    }
