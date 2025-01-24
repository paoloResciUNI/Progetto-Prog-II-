package borsanova.PoliticaPrezzo;

import borsanova.Borsa.Azione;
import java.util.*;

public class Vocali implements PoliticaPrezzo {
    private char lettera;
    private static final Set<Character> vocali = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
    private static final Set<Character> Vocali = new HashSet<>(Arrays.asList('A', 'E', 'I', 'O', 'U'));

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
