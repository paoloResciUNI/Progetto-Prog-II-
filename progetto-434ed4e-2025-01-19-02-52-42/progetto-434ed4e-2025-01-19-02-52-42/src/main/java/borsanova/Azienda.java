package borsanova;

import java.util.SortedSet;
import java.util.Objects;
import java.util.TreeSet;

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
     *      nomeAzienda: è il nome che identifica l'azienda. 
     *      borseQuotate: è l'insieme contenente tutte le borse nel quale l'azienda è quotata. 
     *      borseEsistenti: è l'insieme che contiene tutte le borse esistenti. 
     * RI:
     *      nomeAzienda != null && nomeAzienda != "".
     *      Azione.valore > 0 && Azione.quantità > 0
     * 
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
     * @throws IllegalArgumentException se il nome è {@code null} o se è uguale alla stringa vuta.
     */
    private Azienda(String nome) throws IllegalArgumentException {
        if (nome == null)
            throw new IllegalArgumentException("L'azienda deve avere un nome.");
        if (nome == "")
            throw new IllegalArgumentException("Il nome dell'azienda non può essere vuoto.");
        this.nome = nome;
        borseQuotate = new TreeSet<>();
    }

    /**
     * Questo metodo quota quota un'azione in borsa. 
     * 
     * @param nomeBorsa di tipo {@code Borsa}, indica la borsa nel quale l'azienda si vuole quotare.
     * @param numeroAzioni di tipo {@code int}, il numero di azioni che l'azienda vuole vendere.
     * @param valorePerAzione di tipo {@code int}, il valore per singola azione.
     * @throws IllegalArgumentException se {@code numeroAzioni} e {@code valorePerAzione} è minore o uguale a 0.   
     */
    public void quotazioneInBorsa(Borsa nomeBorsa, int numeroAzioni, int valorePerAzione) throws IllegalArgumentException {
        if (numeroAzioni <= 0 || valorePerAzione <= 0) throw new IllegalArgumentException("Il numero delle azioni e il loro valore deve essere maggiore di zero.");
        nomeBorsa.aggiungiAzione(nome, valorePerAzione, numeroAzioni);
        borseQuotate.add(nomeBorsa);
    }

    @Override
    public String toString() {
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
