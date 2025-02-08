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

import java.util.Iterator;
import java.util.Scanner;

import borsanova.Azienda;
import borsanova.Borsa;
import borsanova.Operatore;
import borsanova.PoliticaPrezzo.DecrementoCostante;
import borsanova.PoliticaPrezzo.IncrementoCostante;

/**
 * Client di test per alcune funzionalità relative alle <strong>borse</strong>.
 */
public class PoliticaPrezzoClient {

  /** . */
  private PoliticaPrezzoClient() {
  }

  /*-
   * Scriva un [@code main} che riceve come parametri sulla linea di comando
   *
   *      nome_borsa valore nome_operatore budget_iniziale
   *
   * il secondo parametro è un intero che determina la politica di prezzo della
   * borsa come segue: se è positivo, la politica è ad incremento costante pari
   * a tale valore, viceversa se è negativo, la politica è a decremento costante
   * pari al valore assoluto di tale valore.
   *
   * Il programma quindi legge dal flusso in ingresso una sequenza di due gruppi
   * di linee (separati tra loro dalla linea contenente solo --) ciascuno della
   * forma descritta di seguito:
   *
   *     nome_azienda numero prezzo_unitario
   *     ...
   *     --
   *     b nome_azienda prezzo_totale
   *     ... [oppure]
   *     s nome_azienda numero_azioni
   *
   * in base al contenuto del primo blocco, quota le azioni delle aziende
   * specificate nella borsa (definita dal primo parametro sulla linea di
   * comando) secondo il numero e prezzo unitario specificati, in base al
   * contenuto del secondo blocco — una volta creato un operatore (di nome
   * e budget iniziale specificati dal terzo e quarto parametro sulla
   * linea di comando) — esegue le operazioni a seconda che il carattere che
   * segue il nome dell'operatore sia:
   *
   * - b compra azioni (dell'azienda specificata, impegnano il prezzo totale
   *   specificato),
   * - s vende azioni (dell'azienda specificata, nel numero specificato).
   *
   * Al termine della lettura il programma emette nel flusso d'uscita l'elenco
   * delle azioni (in ordine alfabetico) seguite dal prezzo (separato da una
   * virgola).
   */
  public static void main(String[] args) {
    String nomeBorsa = args[0];
    Borsa nuovaBorsa = Borsa.of(nomeBorsa);
    int valore = Integer.parseInt(args[1]);
    if (valore > 0) {
      IncrementoCostante incremento = new IncrementoCostante(valore);
      nuovaBorsa.politicaPrezzo(incremento);
    } else if (valore < 0) {
      DecrementoCostante decremento = new DecrementoCostante(Math.abs(valore));
      nuovaBorsa.politicaPrezzo(decremento);
    }
    Operatore operatore = Operatore.of(args[2]);
    operatore.deposita(Integer.parseInt(args[3]));

    try (Scanner scanner = new Scanner(System.in)) {
      while (scanner.hasNext()) {
        String stringaIn = scanner.nextLine();
        if (stringaIn.equals("--")) {
          break;
        }
        String[] dati = stringaIn.split(" ");
        String nomeAzienda = dati[0];
        int numero = Integer.parseInt(dati[1]);
        int prezzoUnitario = Integer.parseInt(dati[2]);
        Azienda azienda = Azienda.of(nomeAzienda);
        azienda.quotazioneInBorsa(nuovaBorsa, numero, prezzoUnitario);
      }
      while (scanner.hasNext()) {
        String stringaIn = scanner.nextLine();
        String[] dati = stringaIn.split(" ");
        String operazione = dati[0];
        String nomeAzienda = dati[1];
        Azienda azienda = null;
          if (operazione.equals("b")) {
            int prezzoTotale = Integer.parseInt(dati[2]);
            Iterator<Borsa.Azione> azioni = nuovaBorsa.azioniQuotate();
            while (azioni.hasNext()) {
              Borsa.Azione a = azioni.next();
              if (a.azienda().nome().equals(nomeAzienda))
                azienda = a.azienda();
              }
            if (azienda != null) 
              nuovaBorsa.acquisto(operatore, azienda, prezzoTotale);
          } else if (operazione.equals("s")) {
            int numeroAzioni = Integer.parseInt(dati[2]);
            Iterator<Borsa.Azione> azioniBorsa = nuovaBorsa.azioniQuotate();
            while (azioniBorsa.hasNext()) {
              Borsa.Azione a = azioniBorsa.next();
              if (a.azienda().nome().equals(nomeAzienda) && operatore.possiedeAzione(a)) {
                nuovaBorsa.vendita(operatore, a, numeroAzioni);
              }
            }
        }
      }
    }
    // Stampa delle azioni
    Iterator<Borsa.Azione> azioni = nuovaBorsa.azioniQuotate();
    while (azioni.hasNext()) {
      Borsa.Azione azione = azioni.next();
      System.out.println(azione.azienda().nome() + ", " + azione.valore());
    }
  }
}
