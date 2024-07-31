import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Portafoglio } from '../model/portafoglio.model';

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrl: './invoice.component.css'
})
export class InvoiceComponent implements OnInit {
  listaPortafogli: Portafoglio[] = [];
  form!: FormGroup;
  saldo!: number;
  indirizzo!: string;
  descrizione!: string;
  portafoglioSelezionato: Portafoglio | null = null;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.listaPortafogli = [
      {
        simbolo: 'BTC',
        indirizzo: '1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa',
        blocked: false,
        idCliente: 20,
        qrCode: 'code',
        creazione: new Date(),
        saldo: 50,
      },
      {
        simbolo: 'ETH',
        indirizzo: '0x742d35Cc6634C0532925a3b844Bc454e4438f44e',
        blocked: true,
        idCliente: 20,
        qrCode: 'code',
        creazione: new Date(),
        saldo: 20,
      },
      {
        simbolo: 'XRP',
        indirizzo: 'rEb8TK3gBgk5auZkwc6sHnwrGVJH8DuaLh',
        blocked: false,
        idCliente: 20,
        qrCode: 'code',
        creazione: new Date(),
        saldo: 300,
      },
  ]

    this.form = this.fb.group ({
      saldo: ['', Validators.required],
      indirizzo: ['', Validators.required],
      descrizione: ['', Validators.required]
    })
  }

  onSubmit() {
    if (this.form.valid) {
      const transazioneData = this.form.value;
      console.log('Dati Transazione:', transazioneData);
      console.log("provenienti da portafaglio: " + this.portafoglioSelezionato?.simbolo)
      // Invia i dati al server o gestisci l'operazione di creazione del wallet
    }
  }

  selezionaRiga(portafoglio: Portafoglio): void {
    // Previene la selezione del portafoglio bloccato
    if (!portafoglio.blocked) {
      // Verifica se deseleziona la riga o meno
      if (this.portafoglioSelezionato === portafoglio) {
        this.portafoglioSelezionato = null;
      } else {
        this.portafoglioSelezionato = portafoglio;
      }
    }
  }
}
