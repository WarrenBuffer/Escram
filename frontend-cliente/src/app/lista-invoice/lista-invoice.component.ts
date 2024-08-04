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

  constructor(private _apiService: ApiService, private toastService: ToastService) {}

  ngOnInit(): void {
    this._apiService.getCliente().subscribe({
      next: res => {
        this.invoices = res.invoices;
        console.log(this.invoices)
      },
      error: err => {
        this.toastService.showError(err.error)
      }
    })
  }


}
