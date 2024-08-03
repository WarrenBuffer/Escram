import { Component } from '@angular/core';
import { Portafoglio } from '../model/portafoglio.model';
import { ApiService } from '../services/api.service';
import { ClienteService } from '../services/cliente.service';

@Component({
  selector: 'app-lista-portafogli',
  templateUrl: './lista-portafogli.component.html',
  styleUrl: './lista-portafogli.component.css'
})
export class ListaPortafogliComponent {
  listaPortafogli: Portafoglio[] = [];

  constructor(private _apiService: ApiService, private _clienteService: ClienteService) { }


  ngOnInit(): void {
    this.listaPortafogli = this._clienteService.getSessionPortafogli() || [];

  }
}
