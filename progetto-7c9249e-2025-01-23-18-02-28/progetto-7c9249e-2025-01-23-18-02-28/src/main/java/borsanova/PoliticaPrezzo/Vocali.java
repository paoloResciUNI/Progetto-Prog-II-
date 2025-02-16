package borsanova.politicaprezzo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import borsanova.Borsa.Azione;

/**
 * Questa classe implementa l'interfaccia {@code PoliticaPrezzo}.
 * Definisce la politica del prezzo che gestisce la variazione del valore di un'azione in caso di acquisto o di vendita della stessa.
 * Questa politica del prezzo prevede:
 *  - In caso di vendita, se l'iniziale del nome dell'azienda o l'iniziale del nome della borsa è il carattere {@code lettera} o una vocale, il valore dell'azione viene dimezzato.
 *  - In caso di acquisto, se l'iniziale del nome dell'azienda o l'iniziale del nome della borsa è il carattere {@code lettera} o una vocale, il valore dell'azione viene raddoppiato.   
 */
public class Vocali implements PoliticaPrezzo {
    /**{@code lettera} il carattere che determina il cambiamento del valore dell'azione. */
    private char lettera;
    /**{@code vocali} l'insieme delle vocali minuscole */
    private static final Set<Character> vocali = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
    /**{@code Vocali} l'insieme delle vocali maiuscole */
    private static final Set<Character> Vocali = new HashSet<>(Arrays.asList('A', 'E', 'I', 'O', 'U'));

    /*-
     * AF: 
     *     - lettera: è il carattere che può coinvolgere l'azione nel dimezzamenro del suo valore in caso di vendita o nel raddopiamento in caso di acquisto.
     * IR:
     *     - lettera != null.
     *     - A <= lettera <= Z || a <= lettera <= z.
     */

    /**
     * Definizione della lettera.
     * @param lettera carattere che può determinare il cambiamento del valore dell'azione.  
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
