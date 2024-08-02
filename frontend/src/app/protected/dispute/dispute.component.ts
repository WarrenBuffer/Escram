import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { Invoice } from '../../models/invoice';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-dispute',
  templateUrl: './dispute.component.html',
  styleUrl: './dispute.component.css'
})
export class DisputeComponent implements OnInit{
  loading = false;
  invoices!: Invoice[];
  constructor(private apiService: ApiService, private toastService: ToastService) {}
  
  ngOnInit(): void {
    this.getDispute();
  }

  getDispute() {
    this.loading = true;
    this.apiService.getDispute().subscribe({
      next: res => {
        this.loading = false;
        this.invoices = JSON.parse(res);
        console.log(this.invoices);
      }, 
      error: err => {
        this.loading = false;
        this.toastService.showError(err.error);
      }
    })
  }
}
