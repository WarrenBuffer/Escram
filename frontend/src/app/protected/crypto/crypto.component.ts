import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { ToastService } from '../../services/toast.service';
import { Crypto } from '../../models/crypto';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-crypto',
  templateUrl: './crypto.component.html',
  styleUrl: './crypto.component.css'
})
export class CryptoComponent implements OnInit {
  loading = false;
  crypto!: Crypto[];
  addVisible: boolean = false;
  updateVisible: boolean = false;
  selectedCrypto: Crypto | undefined;
  form = new FormGroup({
    simbolo: new FormControl({ value: '', disabled: true }, [Validators.required, Validators.pattern("^[a-zA-Z]$")]),
    nome: new FormControl('', [Validators.required, Validators.pattern("^[a-zA-Z]{3,}$")]),
    url: new FormControl('', [Validators.required, Validators.pattern("^[a-zA-Z0-9/:.]{3,}$")]),
  })

  constructor(private apiService: ApiService, private toastService: ToastService) { }

  ngOnInit(): void {
    this.getCrypto();
  }

  getCrypto() {
    this.loading = true;
    this.apiService.getCrypto().subscribe({
      next: res => {
        this.crypto = res;
        this.loading = false;
      },
      error: err => {
        this.toastService.showError(err.error);
        this.loading = false;
      }
    })
  }

  showUpdateDialog(crypto: Crypto) {
    this.selectedCrypto = crypto;
    this.updateVisible = true;
  }

  updateCrypto(form: any, simbolo: string | undefined) {
    if (simbolo)
      this.apiService.updateCrypto(simbolo, form.nome, form.url).subscribe({
        next: res => {
          this.toastService.showSuccess(`Crypto ${simbolo} aggiornata con successo.`)
          this.getCrypto();
        },
        error: err => this.toastService.showError(err.error),
      });

    this.updateVisible = false;
    this.form.reset();
  }

  deleteCrypto(simbolo: string) {
    this.apiService.deleteCrypto(simbolo).subscribe({
      next: res => {
        this.getCrypto();
        this.toastService.showSuccess(`Crypto ${simbolo} eliminata con successo.`)
      },
      error: err => this.toastService.showError(err.error),
    });
  }
}
