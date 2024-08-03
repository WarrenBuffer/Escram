import { Component, OnInit } from '@angular/core';
import { Portafoglio } from '../model/portafoglio.model';
import { Crypto } from '../model/crypto.model';
import { ClienteService } from '../services/cliente.service';
import { ApiService } from '../services/api.service';
import { Transazione } from '../model/transazione.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  listaPortafogli: Portafoglio[] = [];
  listaTransazioni: Transazione[] = [];
  cryptocurrencies!: Crypto[];
  selectedCrypto: Crypto | null = null;

  constructor(private _clienteService: ClienteService, private _apiService: ApiService) { }

  ngOnInit(): void {
    this.listaPortafogli = this._clienteService.getSessionPortafogli() || [];

    this._apiService.getCrypto().subscribe({
      next: (cryptos: any) => {
        console.log('Crypto data:', cryptos);
        this.cryptocurrencies = JSON.parse(cryptos);
      },
      error: (error: any) => {
        console.error('Errore durante il recupero dei dati crypto:', error);
      }
    });
    //console.log(this.cryptocurrencies)

    if (this.listaPortafogli.length > 0) {
      for (let index = 0; index < this.listaPortafogli.length; index++) {
          this.listaTransazioni = this.listaTransazioni.concat(JSON.parse(this.listaPortafogli[index].toString()).transazioni) ;
      }
    }
  }
}
