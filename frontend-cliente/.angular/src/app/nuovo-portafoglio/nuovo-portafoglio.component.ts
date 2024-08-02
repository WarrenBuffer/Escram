import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ClienteService } from '../services/cliente.service';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-nuovo-portafoglio',
  templateUrl: './nuovo-portafoglio.component.html',
  styleUrl: './nuovo-portafoglio.component.css'
})
export class PortafoglioComponent implements OnInit{
  form!: FormGroup;
  crypto!: { name: string; code: string }[];

  constructor( private sessionClient: ClienteService, private apiService:ApiService) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      crypto:new FormControl( null, [Validators.required]),
      label:new FormControl ('', [Validators.required, Validators.maxLength(20)])
    });

    this.crypto = [
      { name: 'Bitcoin', code: 'BTC' },
      { name: 'Ethereum', code: 'ETH' },
      { name: 'Ripple', code: 'XRP' },
      { name: 'Litecoin', code: 'LTC' },
      { name: 'TCoin', code: 'TCN' },
    ];
  }

  onSubmit() {
    if (this.form.valid) {
      this.apiService.creaPortafoglio( this.form.value.crypto.code, this.sessionClient.getSessionEmail() ?? '', this.form.value.label);
    }
  }
}
