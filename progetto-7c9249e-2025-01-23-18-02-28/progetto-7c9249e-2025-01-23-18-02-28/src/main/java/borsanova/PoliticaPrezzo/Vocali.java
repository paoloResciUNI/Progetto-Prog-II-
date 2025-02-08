package borsanova.PoliticaPrezzo;

import borsanova.Borsa.Azione;
import java.util.*;

/**
 * Questa classe implementa l'interfaccia {@link PoliticaPrezzo} e definisce la politica del prezzo che gestisce la variazione del valore di un'azione
 * in caso di acquisto o vendita della stessa.
 * Questa classe rappresenta la politica del prezzo che prevede un raddopiamento valore dell'azione ad ogni acquisto e il suo dimezzamento ad ogni vendita in caso il nome dell'azienda dell'azione 
 * o il nome della nomeBorsa dove risiede l'azione inizini per il carattere {@code lettera} o per vocale.
 */
public class Vocali implements PoliticaPrezzo {
    /**{@code lettera} è il carattere che determina la politica di prezzo della nomeBorsa */
    private char lettera;
    /**{@code vocali} è l'insieme delle vocali minuscole */
    private static final Set<Character> vocali = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
    /**{@code Vocali} è l'insieme delle vocali maiuscole */
    private static final Set<Character> Vocali = new HashSet<>(Arrays.asList('A', 'E', 'I', 'O', 'U'));

    /*-
     * AF: 
     *     - L'azione viene coinvolta nella politica di prezzo se la prima lettera del nome dell'azienda o della nomeBorsa iniziano per il carattere lettera o per vocale.
     * IR:
     *     - lettera != null.
     *     - A <= lettera <= Z && a <= lettera <= z.
     */

    /**
     * Definizione della politica di prezzo.
     * @param lettera carattere che determina quali azioni saranno coinvolte nella politica di prezzo della nomeBorsa
     * @throws IllegalArgumentException se {@code lettera} non è un carattere dell'alfabeto inglese.
     */
    public Vocali(char lettera) {
        if (!Character.isLetter(lettera)) throw new IllegalArgumentException("La lettera deve essere un carattere dell'alfabeto inglese.");
        this.lettera = lettera;
    }

    @Override
    public int vendita(Azione azione, int numeroAzioni) {
        int valoreAttuale = azione.valore();
        if (azione.azienda().nome().charAt(0) == lettera || vocali.contains(azione.azienda().nome().charAt(0)) || azione.nomeBorsa().charAt(0) == lettera || vocali.contains(azione.nomeBorsa().charAt(0)))  {
            return Math.max(1, valoreAttuale / 2);
        }
        if (azione.nomeBorsa().charAt(0) == Character.toUpperCase(lettera) || Vocali.contains(azione.nomeBorsa().charAt(0))|| azione.azienda().nome().charAt(0) == Character.toUpperCase(lettera) || Vocali.contains(azione.azienda().nome().charAt(0))) {
            return Math.max(1, valoreAttuale / 2);
        }
        return azione.valore();
        }

    @Override
    public int acquisto(Azione azione, int numeroAzioni) {
        int valoreAttuale = azione.valore();
        if (azione.azienda().nome().charAt(0) == lettera || vocali.contains(azione.azienda().nome().charAt(0)) || azione.nomeBorsa().charAt(0) == lettera || vocali.contains(azione.nomeBorsa().charAt(0)))  {
            return (valoreAttuale * 2);
        }
        if (azione.nomeBorsa().charAt(0) == Character.toUpperCase(lettera) || Vocali.contains(azione.nomeBorsa().charAt(0))|| azione.azienda().nome().charAt(0) == Character.toUpperCase(lettera) || Vocali.contains(azione.azienda().nome().charAt(0))) {
            return (valoreAttuale * 2);
        }
        return azione.valore();
    }
    
}
