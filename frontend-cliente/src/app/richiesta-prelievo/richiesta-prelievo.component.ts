import { Component, OnInit } from '@angular/core';
import { Portafoglio } from '../model/portafoglio.model';
import { ClienteService } from '../services/cliente.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ClientWithdrawRequest } from '../model/client-withdraw-request.model';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-richiesta-prelievo',
  templateUrl: './richiesta-prelievo.component.html',
  styleUrl: './richiesta-prelievo.component.css'
})
export class RichiestaPrelievoComponent implements OnInit {
  listaPortafogli: Portafoglio[] = [];
  portafoglioSelezionato: Portafoglio | null = null;
  form!: FormGroup;
  ammontare!: number;
  indirizzoDestinatario!: string;
  request!: ClientWithdrawRequest;

  constructor(private _clienteService: ClienteService, private _apiService: ApiService) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      ammontare: new FormControl(null, [Validators.required]),
      indirizzoDestinatario: new FormControl(null, [Validators.required])
    });

    this.listaPortafogli = this._clienteService.getSessionPortafogli() || [];
  }

  selezionaRiga(portafoglio: Portafoglio): void {
    // Verifica se deseleziona la riga o meno
    if (this.portafoglioSelezionato === portafoglio) {
      this.portafoglioSelezionato = null;
    } else {
      this.portafoglioSelezionato = portafoglio;
      this.request.fromIndirizzo = portafoglio.indirizzo;
    }
  }

  onSubmit(form: any) {
    this.request.toIndirizzo= form.indirizzoDestinatario;
    this.request = form.ammontare;
    this._apiService.requestWithdraw(this.request);
  }
}
