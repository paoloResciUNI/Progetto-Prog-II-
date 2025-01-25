package borsanova.PoliticaPrezzo;

import borsanova.Borsa.Azione;
import java.util.*;

/**
 * Questa classe implementa l'interfaccia {@link PoliticaPrezzo} e definisce la politica del prezzo che gestisce la variazione del valore di un'azione
 * in caso di acquisto o vendita della stessa.
 * Questa classe rappresenta la politica del prezzo che prevede un raddopiamento valore dell'azione ad ogni acquisto e il suo dimezzamento ad ogni vendita in caso il nome dell'azienda dell'azione 
 * o il nome della borsa dove risiede l'azione inizini per il carattere {@code lettera} o per vocale.
 */
public class Vocali implements PoliticaPrezzo {
    /**{@code lettera} è il carattere che determina la politica di prezzo della borsa */
    private char lettera;
    /**{@code vocali} è l'insieme delle vocali minuscole */
    private static final Set<Character> vocali = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
    /**{@code Vocali} è l'insieme delle vocali maiuscole */
    private static final Set<Character> Vocali = new HashSet<>(Arrays.asList('A', 'E', 'I', 'O', 'U'));

    /**
     * Definizione della politica di prezzo.
     * @param lettera carattere che determina quali azioni saranno coinvolte nella politica di prezzo della borsa
     */
    public Vocali(char lettera) {
        this.lettera = lettera;
    }

    @Override
    public void vendita(Azione azione, int numeroAzioni) {
        int valoreAttuale = azione.valore();
        if (azione.azienda().nome().charAt(0) == lettera || vocali.contains(azione.azienda().nome().charAt(0)) || azione.borsa().charAt(0) == lettera || vocali.contains(azione.borsa().charAt(0)))  {
            azione.newValue(Math.max(1, valoreAttuale / 2));
            return;
        }
        if (azione.borsa().charAt(0) == Character.toUpperCase(lettera) || Vocali.contains(azione.borsa().charAt(0))|| azione.azienda().nome().charAt(0) == Character.toUpperCase(lettera) || Vocali.contains(azione.azienda().nome().charAt(0))) {
            azione.newValue(Math.max(1, valoreAttuale / 2));
            return;
        }
        }

    @Override
    public void acquisto(Azione azione, int numeroAzioni) {
        int valoreAttuale = azione.valore();
        if (azione.azienda().nome().charAt(0) == lettera || vocali.contains(azione.azienda().nome().charAt(0)) || azione.borsa().charAt(0) == lettera || vocali.contains(azione.borsa().charAt(0)))  {
            azione.newValue(valoreAttuale * 2);
        }
        if (azione.borsa().charAt(0) == Character.toUpperCase(lettera) || Vocali.contains(azione.borsa().charAt(0))|| azione.azienda().nome().charAt(0) == Character.toUpperCase(lettera) || Vocali.contains(azione.azienda().nome().charAt(0))) {
            azione.newValue(valoreAttuale * 2);
        }
    }
    
}
