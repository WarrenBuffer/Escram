import { Injectable } from '@angular/core';
import { Cliente } from '../cliente';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ToastService } from './toast.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private cliente!: Cliente;
  private basePath = "http://localhost:8080/api";
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    })
  };
  setter(cliente: Cliente) {
    this.cliente = cliente;
  }
  getter() {
    return this.cliente;
  }

  constructor(private _http: HttpClient, private toastService: ToastService, private _router:Router) { }

  signup(cliente: Cliente) {
    this._http.post(`${this.basePath}/signup`, cliente, this.httpOptions).subscribe({
      next: v => {
        this._router.navigate(['/loginEscram']);
      },
      error: err => this.toastService.showError(err.message)
    })
  }
}
