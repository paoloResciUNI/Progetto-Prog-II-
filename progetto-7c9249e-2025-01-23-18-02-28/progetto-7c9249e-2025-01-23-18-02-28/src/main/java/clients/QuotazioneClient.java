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

import borsanova.*;
import java.util.*;

/** Client di test per alcune funzionalità relative alle <strong>quotazioni</strong>. */
public class QuotazioneClient {

  /** . */
  private QuotazioneClient() {}

  /*-
   * Scriva un {@code main} che legge dal flusso di ingresso una sequenza di
   * linee della forma
   *
   *    nome_azienda nome_borsa quantità prezzo
   *
   * Assuma che i nomi non contengano spazi. Dopo aver quotato le aziende nelle
   * borse emette nel flusso d'uscita
   *
   * - per ciascuna azienda, l'elenco delle borse in cui è quotata, poi
   * - per ciascuna borsa, l'elenco di aziende in essa quotate;
   *
   * I nomi delle borse e delle aziende devono essere uno per linea, in ordine
   * alfabetico; i nomi di borsa nel primo elenco e i azienda nel secondo devono
   * essere prefissati da "- ".
   */
  public static void main(String[] args) {
    Set<Azienda> aziende = new TreeSet<>();
    Set<Borsa> borse = new TreeSet<>();
    try (Scanner scanner = new Scanner(System.in)) {
      while (scanner.hasNext()) {
        String stringaIn = scanner.nextLine();
        String[] dati = stringaIn.split(" ");
        String nomeAzienda = dati[0];
        String nomeBorsa = dati[1];
        int quantita = Integer.parseInt(dati[2]);
        int prezzo = Integer.parseInt(dati[3]);
        Azienda azienda = null;
        Borsa borsa = null;
        try {  
          azienda = Azienda.of(nomeAzienda);
        } catch (IllegalArgumentException e) {
          for (Azienda a : aziende) {
            if (a.nome().equals(nomeAzienda)) {
              azienda = a;
              break;
            }
          }            
        }
        try {
          borsa = Borsa.of(nomeBorsa);
        } catch (IllegalArgumentException e) {
          for (Borsa b : borse) {
            if (b.nome().equals(nomeBorsa)) {
              borsa = b;
              break;
            }
          }
        }
        azienda.quotazioneInBorsa(borsa, quantita, prezzo);
        aziende.add(azienda);
        borse.add(borsa);
      }
    } 
    for (Azienda azienda : aziende) {
      System.out.println(azienda.nome());
      Iterator<Borsa> borseQutazione = azienda.borseQuotate(); 
      while (borseQutazione.hasNext()) {
        System.out.println("- " + borseQutazione.next().nome());
      }
    }
    for (Borsa b : borse) {
      System.out.println(b.nome());
      Iterator<Borsa.Azione> aziendeBorsa = b.aziendeQuotate();
      while (aziendeBorsa.hasNext()) {
        System.out.println("- " + aziendeBorsa.next().azienda().nome());
      }
    }
  }
}
