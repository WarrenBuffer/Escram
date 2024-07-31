import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Portafoglio } from '../model/portafoglio.model';

@Component({
  selector: 'app-transazione-escrow',
  templateUrl: './transazione-escrow.component.html',
  styleUrl: './transazione-escrow.component.css'
})
export class TransazioneEscrowComponent implements OnInit {
  listaPortafogli: Portafoglio[] = [];
  form!: FormGroup;
  saldo!: number;
  indirizzo!: string;
  descrizione!: string;
  portafoglioSelezionato: Portafoglio | null = null;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.listaPortafogli = [{
      simbolo: 'BTC',
      indirizzo: "indirizzo",
      idCliente: 20,
      blocked: false,
      creazione: new Date(),
      saldo: 100,
      qrCode: undefined
    }
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
    this.portafoglioSelezionato = portafoglio;
    console.log("portafoglio scelto: " , portafoglio.simbolo);
  }
}
