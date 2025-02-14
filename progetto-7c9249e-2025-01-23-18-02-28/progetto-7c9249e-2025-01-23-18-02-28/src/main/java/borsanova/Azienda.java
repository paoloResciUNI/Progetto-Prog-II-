package borsanova;

import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 
 * L'azienda può generare delle azioni quotandosi in borsa. 
 * 
 * Ogni azienda:
 *  - ha un nome che la identifica 
 *  - tiene traccia delle borse nel quale è quotata attraverso un'insieme contenente tali borse.
 * 
 * L'azienda può:
 *  - quotarsi in una borsa. 
 *  - restituire il suo nome e le borse nel quale è quotata.   
 * 
 * Il criterio di confronto e ordinamento delle aziende è il nome.
 */
public class Azienda implements Comparable<Azienda> {
    /**{@code ISTANZE} tiene traccia dei nomi usati per definire le aziende. */
    private static final SortedSet<String> ISTANZE = new TreeSet<>();
    /**{@code nome} è il nome dell'azienda. */
    private final String nome;
    /**{@code borseQuotate} contiene tutte le borse nel quale l'azienda si è quotata. */
    private final SortedSet<Borsa> borseQuotate;

    /*-
     * AF:
     *      - nome: è il nome che identifica l'azienda.
     *      - borseQuotate: è l'insieme contenente tutte le borse nel quale l'azienda è quotata. 
     * RI:
     *      - nome != null && !nome.isBlank().
     *      - borseQuotate != null && b != null per ogni b in borseQuotate. 
     */

    /**
     * Metodo di fabbricazione per creare un'istanza di Azienda.
     * @param nome è il nome della nuova azienda. 
     * @return un nuovo oggetto di tipo {@code Azienda}. 
     * @throws IllegalArgumentException se {@code nome} è null o se il nome è già stato usato.
     */
    public static Azienda of(final String nome) {
        if (Objects.requireNonNull(nome, "Name must not be null.").isBlank())
            throw new IllegalArgumentException("Name must not be empty.");
        if (ISTANZE.contains(nome))
            throw new IllegalArgumentException("Name already used.");
        ISTANZE.add(nome);
        return new Azienda(nome);
    }

    /**
     * Costruisce una nuova istanza di azienda. 
     * @param nome è il nome che avrà l'azienda.
     */
    private Azienda(String nome) {
        this.nome = nome;
        borseQuotate = new TreeSet<>();
    }

    /**
     * Quota questa azienda in una borsa.
     * L'azienda viene quotata se il numero delle azioni e il loro valore è maggiore di zero o se non è già stata quotata nella borsa.      * 
     * @param borsa indica la borsa nel quale l'azienda si vuole quotare.
     * @param numeroAzioni il numero di azioni che l'azienda vuole vendere.
     * @param valorePerAzione il valore per singola azione.
     * @throws IllegalArgumentException se {@code numeroAzioni} o {@code valorePerAzione} è minore o uguale a 0, oppure se {@code borsa} è già all'interno di {@code borseQuotate}.  
     * @throws NullPointerException se {@code borsa} è {@code null}. 
     */
    public void quotazioneInBorsa(Borsa borsa, int numeroAzioni, int valorePerAzione) throws IllegalArgumentException, NullPointerException {
        Objects.requireNonNull(borsa, "La borsa non può essere null."); 
        if (numeroAzioni <= 0 || valorePerAzione <= 0) throw new IllegalArgumentException("Il numero delle azioni e il loro valore deve essere maggiore di zero.");
        if (borseQuotate.add(borsa)) {
            try {
                borsa.quotaAzienda(this, valorePerAzione, numeroAzioni);
            } catch (IllegalArgumentException e) {
                borseQuotate.remove(borsa);
            }
        } else throw new IllegalArgumentException("Quest'azienda è già quota nella borsa specficata!");
    }
    
    /**
     * Restituisce un iteratore per le borse nel quale questa azienda è quotata.
     * @return un iteratore per le borse quotate in questa azienda.
     */
    public Iterator<Borsa> borseQuotate() {
        return Collections.unmodifiableCollection(borseQuotate).iterator();
    }

    /**
     * Restituisce il nome di questa azienda.
     * @return il nome di questa azienda.
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
