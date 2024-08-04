import { Component, OnInit } from '@angular/core';
import { Portafoglio } from '../model/portafoglio.model';
import { Crypto } from '../model/crypto.model';
import { ClienteService } from '../services/cliente.service';
import { ApiService } from '../services/api.service';
import { Transazione } from '../model/transazione.model';
import { ToastService } from '../services/toast.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  listaPortafogli: Portafoglio[] = [];
  cryptocurrencies!: Crypto[];
  selectedCrypto: Crypto | null = null;
  loading = false;
  selectedPortafoglio!: Portafoglio;
  visible: boolean = false;

  items = [
    {
      label: 'Dettagli',
      icon: 'pi pi-search',
      command: () => {
        this.showDialog();
      }
    },
    {
      label: 'Elimina',
      icon: 'pi pi-times',
      command: () => {
        this.toastService.showError("Non ancora implementato")
      }
    },
  ]

  constructor(private _apiService: ApiService, private toastService: ToastService, private _clientService: ClienteService) { }

  ngOnInit(): void {
    this.loading = true;

    this._apiService.getCliente().subscribe({
      next: res => {
        this.listaPortafogli = res.portafogli;
      }, 
      error: err => {
        this.toastService.showError(err.error);
      }
    })

    this._apiService.getCrypto().subscribe({
      next: (cryptos: any) => {
        this.cryptocurrencies = cryptos;
        this.loading = false;
      },
      error: (error: any) => {
        this.toastService.showError(error.error)
        this.loading = false;
      }
    });
  }

  select(portafoglio: Portafoglio) {
    this.selectedPortafoglio = portafoglio;
  }

  showDialog() {
    this.visible = !this.visible;
  }
}
