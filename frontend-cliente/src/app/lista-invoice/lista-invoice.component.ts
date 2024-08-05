import { Component, OnInit } from '@angular/core';
import { ApiService } from '../services/api.service';
import { ToastService } from '../services/toast.service';

@Component({
  selector: 'app-lista-invoice',
  templateUrl: './lista-invoice.component.html',
  styleUrl: './lista-invoice.component.css'
})
export class ListaInvoiceComponent implements OnInit{
  loading = false;
  invoices: any;
  selectedInvoice: any;
  visible = false;

  constructor(private _apiService: ApiService, private toastService: ToastService) {}

  ngOnInit(): void {
    this.getInvoices();
  }

  getInvoices() {
    this.loading = true;
    this._apiService.getCliente().subscribe({
      next: res => {
        this.invoices = res.tipologia === 'COMPRATORE' ? res.invoicesDst : res.invoicesSrc;
      },
      error: err => {
        this.toastService.showError(err.error)
      },
      complete: () => {
        this.loading = false;
      }
    })
  }

  showDialog(invoice: any) {
    this.selectedInvoice = invoice;
    this.visible = !this.visible;
  }

  updateInvoice() {
    this.visible = !this.visible;
    this.loading = true;
    this._apiService.updateInvoiceStatus(this.selectedInvoice.coin, this.selectedInvoice.invoiceId).subscribe({
      next: res => {
        this.getInvoices();
      },
      error: err => {
        this.toastService.showError(err.error)
      }
    })
  }

  annullaInvoice() {
    this.visible = !this.visible;
    this.loading = true;
    this._apiService.annullaInvoice(this.selectedInvoice.invoiceId).subscribe({
      next: res => {
        this.toastService.showSuccess(res);
        this.getInvoices();
      },
      error: err => {
        this.toastService.showError(err.error)
      }
    })
  }

  confermaInvoice() {
    this.visible = !this.visible;
    this.loading = true;
    this._apiService.confermaInvoice(this.selectedInvoice.invoiceId).subscribe({
      next: res => {
        this.toastService.showSuccess(res);
        this.getInvoices();
      },
      error: err => {
        this.toastService.showError(err.error)
      }
    })
  }

}
