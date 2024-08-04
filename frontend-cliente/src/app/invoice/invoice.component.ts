import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Portafoglio } from '../model/portafoglio.model';
import { ApiService } from '../services/api.service';
import { ToastService } from '../services/toast.service';
import { Crypto } from '../model/crypto.model';

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrl: './invoice.component.css'
})
export class InvoiceComponent implements OnInit{
  crypto!: Crypto[];
  loading = false;
  form = new FormGroup ({
    toEmail : new FormControl('', [Validators.required, Validators.pattern("^[\\w.%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")]),
    descrizione : new FormControl('', [Validators.required]),
    simbolo : new FormControl('', [Validators.required]),
    amount : new FormControl('', [Validators.required, Validators.min(1)]),
  })
  
  constructor(private _apiService: ApiService, private toastService: ToastService) { }
  ngOnInit(): void {
    this.loading = true;
    this._apiService.getCrypto().subscribe({
      next: res => this.crypto = res,
      error: err => this.toastService.showError(err.error),
      complete: () => this.loading = false
    })
  }

  onSubmit(form: any) {
    this._apiService.newInvoice(form).subscribe({
      next: res => {
        console.log(res)
      },
      error: err => {
        this.toastService.showError(err.error)
      }
    });
  }

}

