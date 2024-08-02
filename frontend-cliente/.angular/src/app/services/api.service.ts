import { Injectable } from '@angular/core';
import { Cliente } from '../cliente';
import { HttpClient, HttpHeaders, HttpParamsOptions } from '@angular/common/http';
import { ToastService } from './toast.service';
import { Router } from '@angular/router';
import { catchError, map, Observable, of } from 'rxjs';
import { ClienteService } from './cliente.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private cliente!: Cliente;
  private basePath = "http://localhost:8080/cliente";

  constructor(private _http: HttpClient, private toastService: ToastService, private _router:Router, private sessionService: ClienteService) { }

  signup(cliente: Cliente) {
    this._http.post(`${this.basePath}/signup`, cliente, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': sessionStorage.getItem('bearer') ?? ''
      }),
      responseType:'text'
    }).subscribe({
      next: v => {
        this._router.navigate(['/loginEscram']);
      },
      error: err => this.toastService.showError(err.error)
    })
  }
  getCliente(email:string): Observable<string>{
    return this._http.post(`${this.basePath}/getCliente`, {email: email}, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': sessionStorage.getItem('bearer') ?? ''
      }),
      responseType:'text'
    }).pipe(
      catchError((err) => {
        this.toastService.showError("Errore interno del server\n" + err.error);
        return of('');
      }),
    )
  }
  creaPortafoglio(simbolo: string, email: string, label: string){
    this._http.post(`${this.basePath}/creaPortafoglio/${simbolo}`, {email, label}, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': sessionStorage.getItem('bearer') ?? ''
      }),
      responseType:'text'
    }).subscribe({
      next: v => {
        this._router.navigate(['/home']);
      },
      error: err => this.toastService.showError(err.error)
    })
  }
}
