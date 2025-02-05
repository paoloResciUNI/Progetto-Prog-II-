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
import borsanova.Borsa.Azione;

/**
 * Client di test per alcune funzionalità relative agli
 * <strong>operatori</strong>.
 */
public class OperatoreClient {

  /** . */
  private OperatoreClient() {
  }

  /*-
   * Scriva un [@code main} che legge dal flusso in ingresso una sequenza di tre
   * gruppi di linee (separati tra loro dalla linea contenente solo --) ciascuno
   * della forma descritta di seguito:
   *
   *     nome_azienda nome_borsa numero prezzo_unitario
   *     ...
   *     --
   *     nome_operatore budget_iniziale
   *     ...
   *     --
   *     nome_operatore b nome_borsa nome_azienda prezzo_totale
   *     ... [oppure]
   *     nome_operatore s nome_borsa nome_azienda numero_azioni
   *     ... [oppure]
   *     nome_operatore d valore
   *     ... [oppure] nome_operatore w valore
   *
   * Assuma che i nomi non contengano spazi. In base al contenuto del primo
   * blocco, quota le azioni delle aziende nelle borse secondo il numero e
   * prezzo unitario specificati, in base al secondo blocco crea gli operatori
   * specificati con il budget iniziale specificato e in base al terzo blocco
   * esegue le operazioni, a seconda che il carattere che segue il nome
   * dell'operatore sia:
   *
   * - b compra azioni (quotate nella borsa e dell'azienda specificata,
   *   impegnano il prezzo totale specificato),
   * - s vende azioni (quotate nella borsa e dell'azienda specificata, nel
   *   numero specificato).
   * - d deposita denaro (secondo il valore specificato),
   * - w preleva denaro (secondo il valore specificato).
   *
   * Osservi che l'acquisto può determinare un resto, nel caso in cui il prezzo
   * totale non sia un multiplo esatto del prezzo dell'azione; tale resto rimane
   * a disposizione dell'operatore per eventuali operazioni successive.
   *
   * Al termine della lettura il programma emette nel flusso d'uscita l'elenco
   * degli operatori coinvolti (in ordine alfabetico) ciascuno dei quali seguito
   * (sulla stessa linea e separato da virgole) dal suo budget finale e dalla
   * somma del valore delle azioni che possiede, ogni operatore è poi seguito
   * dall'elenco delle azioni che possiede, ciascuna azione va descritta
   * emettendo il nome della borsa (in ordine alfabetico, preceduto da -)
   * seguito da quello dell'azienda e dal numero di azioni possedute (separati
   * da virgole).
   */
  public static void main(String[] args) {
    SortedSet<Borsa> borse = new TreeSet<>();
    SortedSet<Azienda> aziende = new TreeSet<>();
    SortedSet<Operatore> operatori = new TreeSet<>();
    try(Scanner in = new Scanner(System.in)) {
    while (in.hasNext()) {
      String line = in.nextLine();
      if (line.equals("--"))
        break;
      String[] tokens = line.split(" ");
      Borsa borsa = null;
      Azienda azienda = null;
      try {
        borsa = Borsa.of(tokens[1]);
        borse.add(borsa);
      } catch (IllegalArgumentException e) {
        for (Borsa b : borse) {
          if (b.nome().equals(tokens[1])) {
            borsa = b;
          }
        }
      }
      try {
        azienda = Azienda.of(tokens[0]);
        aziende.add(azienda);
      } catch (IllegalArgumentException e) {
        for (Azienda a : aziende) {
          if (a.nome().equals(tokens[0])) {
            azienda = a;
          }
        }
      }
      azienda.quotazioneInBorsa(borsa, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
    }
    while (in.hasNext()) {
      String line = in.nextLine();
      if (line.equals("--"))
        break;
      String[] tokens = line.split(" ");
      Operatore operatore = Operatore.of(tokens[0]);
      operatori.add(operatore);
      operatore.deposita(Integer.parseInt(tokens[1]));
    }
    while (in.hasNext()) {
      String line = in.nextLine();
      String[] tokens = line.split(" ");
      Operatore operatoreDaConsiderare = null;
      Borsa borsaDaConsiderare = null;
      Azienda aziendaDaConsiderare = null;
      for (Operatore operatore : operatori) {
        if (operatore.nome().equals(tokens[0])) {
          operatoreDaConsiderare = operatore;
        }
      }
      for (Borsa borsa : borse) {
        if (borsa.nome().equals(tokens[2])) {
          borsaDaConsiderare = borsa;
        }
      }
      for (Azienda azienda : aziende) {
        if (tokens.length > 3 && azienda.nome().equals(tokens[3])) {
          aziendaDaConsiderare = azienda;
        }
      }
      if (tokens[1].equals("b")) {
        operatoreDaConsiderare.investi(borsaDaConsiderare, aziendaDaConsiderare, Integer.parseInt(tokens[4]));
      } else if (tokens[1].equals("s")) {
        Borsa.Azione azioneDaVendere = null;
        azioneDaVendere = borsaDaConsiderare.cercaAzioneBorsa(aziendaDaConsiderare);
        operatoreDaConsiderare.vendi(borsaDaConsiderare, azioneDaVendere, Integer.parseInt(tokens[4]));
      } else if (tokens[1].equals("d")) {
        operatoreDaConsiderare.deposita(Integer.parseInt(tokens[2]));
      } else if (tokens[1].equals("w")) {
        operatoreDaConsiderare.preleva(Integer.parseInt(tokens[2]));
      }
    }
  }
    for (Operatore o : operatori) {
      System.out.println(o.nome()+", "+o.budget()+", "+ o.valoreAzioni() );
      Iterator<Azione> azioniOperatore = o.elencoAzioni();
      while (azioniOperatore.hasNext()) {
        Azione azioneOp = azioniOperatore.next();
        int numAzione = o.mostraQuantitaAzione(azioneOp.azienda());
        if (numAzione > 0 ) System.out.println("- "+azioneOp.nomeBorsa()+", "+azioneOp.azienda().nome()+", "+ numAzione);
      }
    }
  }
}
