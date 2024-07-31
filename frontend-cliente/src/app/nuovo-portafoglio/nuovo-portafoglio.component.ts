import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-nuovo-portafoglio',
  templateUrl: './nuovo-portafoglio.component.html',
  styleUrl: './nuovo-portafoglio.component.css'
})
export class PortafoglioComponent implements OnInit{
  form!: FormGroup;
  crypto!: { name: string; code: string }[];

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      crypto: [null, Validators.required]
    });

    this.crypto = [
      { name: 'Bitcoin', code: 'BTC' },
      { name: 'Ethereum', code: 'ETH' },
      { name: 'Ripple', code: 'XRP' },
      { name: 'Litecoin', code: 'LTC' }
    ];
  }

  onSubmit() {
    if (this.form.valid) {
      const walletData = this.form.value;
      console.log('Dati Wallet:', walletData);
      // Invia i dati al server o gestisci l'operazione di creazione del wallet
    }
  }
}
