import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-withdraw',
  templateUrl: './withdraw.component.html',
  styleUrl: './withdraw.component.css'
})
export class WithdrawComponent implements OnInit {
  loading = false;
  requests: any;
  constructor(private apiService: ApiService, private toastService: ToastService) { }

  ngOnInit() {
    this.getWithdrawRequests();
  }

  getWithdrawRequests() {
    this.loading = true;
    this.apiService.getWithdrawRequests().subscribe({
      next: res => {
        this.requests = res;
        this.loading = false;
      },
      error: err => {
        this.loading = false;
        this.toastService.showError(err.error);
      }
    })
  }

  cancel(id: number, importo: number) {
    this.apiService.cancelWithdrawRequests(id).subscribe({
      next: res => {
        this.toastService.showSuccess(`Richiesta ${id} con importo ${importo} cancellata con successo.`);
        this.getWithdrawRequests();
      },
      error: err => {
        this.toastService.showError(err.error);
      }
    })
  }
  approve(id: number, importo: number) {
    this.toastService.showSuccess(`Richiesta ${id} con importo ${importo} approvata con successo.`);
  }
}
