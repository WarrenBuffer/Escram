import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { ToastService } from '../../services/toast.service';
import { StorageService } from '../../services/storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  clienti: any;
  loading = false;

  constructor(private apiService: ApiService, private toastService: ToastService, private storageService: StorageService) { }

  ngOnInit(): void {
    this.getClienti();
  }
  
  getClienti() {
    this.loading = true;
    this.apiService.getClienti().subscribe({
      next: res => {
        this.clienti = res;
        this.storageService.setItem('clienti', JSON.stringify(res));
        this.loading = false;
      }, 
      error: err => {
        this.loading = false;
        this.toastService.showError(err.error);
      }
    })
  }

  lockUnlock(email: string) {
    this.apiService.lockUnlock(email).subscribe({
      next: res => {
        this.toastService.showSuccess(`Cliente ${email} bloccato/sbloccato con successo.`);
        this.getClienti();
      },
      error: err => this.toastService.showError(err.error)
    });
  }
}
