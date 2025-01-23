/*

Copyright 2024 Massimo Santini

This file is part of "Programmazione 2 @ UniMI" teaching material.

This is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This material is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this file.  If not, see <https://www.gnu.org/licenses/>.

*/
package clients;

import java.util.*;
import borsanova.*;

/**
 * Client di test per alcune funzionalità relative alle <strong>azioni</strong>.
 */
public class AzioneClient {

  /** . */
  private AzioneClient() {
  }

  /*-
   * Scriva un {@code main} che, ricevuto un nome di borsa come parametro sulla
   * linea di comando, legge dal flusso in ingresso una sequenza di linee della
   * forma
   *
   *     nome_azienda numero prezzo_unitario
   *
   * e dopo aver quotato l'azienda nella borsa specificata come parametro
   * produce le azioni quotate emettendo per ciascuna di esse, nel flusso
   * d'uscita, il nome dell'azienda (in ordine alfabetico) seguito da prezzo e
   * numero (separati da virgole). Assuma che il nome dell'azienda non contenga
   * spazi.
   */
  public static void main(String args[]) {
    if (args.length < 1) {
      System.err.println("Errore: specificare il nome della borsa come parametro.");
      System.exit(1);
    }
    String nomeBorsa = args[0];
    borsanova.Borsa borsa = borsanova.Borsa.of(nomeBorsa);
    try (Scanner scanner = new Scanner(System.in)) {
      while (scanner.hasNextLine()) {
        String stringaIngresso = scanner.nextLine();
        String[] dati = stringaIngresso.split(" ");
        String nomeAzienda = dati[0];
        int numero = 0;
        int prezzoUnitario = 0;
        numero = Integer.parseInt(dati[1]);
        prezzoUnitario = Integer.parseInt(dati[2]);
        Azienda azienda = Azienda.of(nomeAzienda);
        azienda.quotazioneInBorsa(borsa, numero, prezzoUnitario);
      }
    }
    Iterator<Borsa.Azione> azioni = borsa.aziendeQuotate();
    while (azioni.hasNext()) {
      Borsa.Azione azione = azioni.next();
      System.out.println(azione.azienda().nome() + ", " + azione.valore() + ", " + azione.quantita());  
    }
  }
}
