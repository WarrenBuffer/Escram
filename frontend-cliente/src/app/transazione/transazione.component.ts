import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Portafoglio } from '../model/portafoglio.model';
import { Transazione } from '../model/transazione.model';
import { ClienteService } from '../services/cliente.service';

@Component({
  selector: 'app-transazione',
  templateUrl: './transazione.component.html',
  styleUrl: './transazione.component.css'
})
export class TransazioneComponent {
  listaPortafogli: Portafoglio[] = [];
  portafoglioSelezionato: Portafoglio | null = null;
  listaTransazioni: Transazione[] = [];

  constructor(private _clienteService: ClienteService) { }

  ngOnInit(): void {
    this.listaPortafogli = this._clienteService.getSessionPortafogli() || [];
  }

  selezionaRiga(portafoglio: Portafoglio): void {
    // Verifica se deseleziona la riga o meno
    if (this.portafoglioSelezionato === portafoglio) {
      this.portafoglioSelezionato = null;
    } else {
      this.portafoglioSelezionato = portafoglio;
      this.listaPortafogli = JSON.parse(this.portafoglioSelezionato.toString()).transazioni;
    }

  }
}
