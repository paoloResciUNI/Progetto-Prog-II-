package borsanova;

import java.util.*;

/**
 * L'azinda può emettere le azioni quotandosi in borsa. 
 */
public class Azienda implements Comparable<Azienda> {
    /**{@code Nomi_Usati_Aziende} tiene traccia dei nomi usati per definire le aziende. */
    private static final SortedSet<String> Nomi_Usati_Aziende = new TreeSet<>();
    /**{@code nome} è il nome dell'azienda. */
    private final String nome;
    /**{@code borseQuotare} è una lista che contiene tutte le borse nel quale l'azienda si è quotata. */
    private final SortedSet<Borsa> borseQuotate;

    /**
     * AF:
     *      {@code nome}: è il nome che identifica l'azienda.
     *      {@code borseQuotate}: è l'insieme contenente tutte le borse nel quale l'azienda è quotata. 
     * RI:
     *      nome != null && nome != "" || !nome.isBlank().
     */

    /**
     * Questo è il metodo di fabbricazione per la classe {@code Azienda}
     * 
     * @param name è il nome dell'azienda da aggiungere all'elenco dei nomi usati. 
     * @return un nuovo oggetto di tipo {@code Azienda}. 
     * @throws IllegalArgumentException se {@code name}  è null o see è già presente in {@code Nomi_Usati_Aziende}.
     */
    public static Azienda of(final String name) {
        if (Objects.requireNonNull(name, "Name must not be null.").isBlank())
            throw new IllegalArgumentException("Name must not be empty.");
        if (Nomi_Usati_Aziende.contains(name))
            throw new IllegalArgumentException("Name already used.");
        Nomi_Usati_Aziende.add(name);
        return new Azienda(name);
    }

    /**
     * Questa classe assegna un nome all'azienda.
     * 
     * @param nome è il nome che avrà l'azienda.
     */
    private Azienda(String nome) throws IllegalArgumentException {
        this.nome = nome;
        borseQuotate = new TreeSet<>();
    }

    /**
     * Questo metodo quota questa azienda in una borsa.
     * L'azienda viene quotata se il numero delle azioni e il loro valore è maggiore di zero. 
     * Un'azienda si può quotare al più una volta nella stessa borsa  
     * 
     * @param borsa indica la borsa nel quale l'azienda si vuole quotare.
     * @param numeroAzioni il numero di azioni che l'azienda vuole vendere.
     * @param valorePerAzione il valore per singola azione.
     * @throws IllegalArgumentException se {@code numeroAzioni} e {@code valorePerAzione} è minore o uguale a 0.  
     * @throws NullPointerException se {@code borsa} è {@code null}. 
     */
    public void quotazioneInBorsa(Borsa borsa, int numeroAzioni, int valorePerAzione) throws IllegalArgumentException {
        Objects.requireNonNull(borsa, "La borsa non può essere null."); 
        if (numeroAzioni <= 0 || valorePerAzione <= 0) throw new IllegalArgumentException("Il numero delle azioni e il loro valore deve essere maggiore di zero.");
        if (!(borseQuotate.contains(borsa))) {
            borseQuotate.add(borsa);
            borsa.QuotaAzienda(this, valorePerAzione, numeroAzioni);
        } else return;
    }
    
    /**
     * Restituisce un iteratore per le borse quotate in questa azienda.
     * @return un iteratore per le borse quotate in questa azienda.
     */
    public Iterator<Borsa> borseQuotate() {
        return Collections.unmodifiableCollection(borseQuotate).iterator();
    }

    /**
     * restituisce il nome dell'azienda.
     * @return il nome dell'azienda.
     */
    public String nome() {
        return nome;
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Azienda))
            return false;
        return nome.equals(obj.toString());
    }

    @Override
    public int compareTo(Azienda altAzienda) {
        return nome.compareTo(altAzienda.nome);
    }

}
