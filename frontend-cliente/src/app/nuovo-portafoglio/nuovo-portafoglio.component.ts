import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ClienteService } from '../services/cliente.service';
import { ApiService } from '../services/api.service';
import { Crypto } from '../model/crypto.model';
import { ToastService } from '../services/toast.service';

@Component({
  selector: 'app-nuovo-portafoglio',
  templateUrl: './nuovo-portafoglio.component.html',
  styleUrl: './nuovo-portafoglio.component.css'
})
export class PortafoglioComponent implements OnInit {
  form!: FormGroup;
  crypto!: Crypto[];
  loading = false;

  constructor(private apiService: ApiService, private toastService: ToastService) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      crypto: new FormControl(null, [Validators.required]),
      label: new FormControl('', [Validators.required, Validators.maxLength(20)])
    });

    this.loading = true;

    this.apiService.getCrypto().subscribe({
      next: res => {
        this.crypto = res;
      },
      error: err => {
        this.toastService.showError(err.error)
      },
      complete: () => this.loading = false
    })
  }

  onSubmit(form: any) {
    this.loading = true;
    this.apiService.creaPortafoglio(form.crypto.simbolo, form.label).subscribe({
      next: res => {
        this.toastService.showSuccess("Portafoglio creato con successo.")
      },
      error: err => {
        this.toastService.showError(err.error)
      },
      complete: () => this.loading = false
    });
  }
}
