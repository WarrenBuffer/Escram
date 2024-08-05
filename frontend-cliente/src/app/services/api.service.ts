import { Injectable } from '@angular/core';
import { Cliente } from '../cliente';
import { HttpClient, HttpHeaders, HttpParamsOptions } from '@angular/common/http';
import { ToastService } from './toast.service';
import { Router } from '@angular/router';
import { catchError, map, Observable, of } from 'rxjs';
import { ClienteService } from './cliente.service';
import { ClientWithdrawRequest } from '../model/client-withdraw-request.model';
import { Crypto } from '../model/crypto.model';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private basePath = "http://localhost:8080/cliente";

  constructor(private _http: HttpClient, private toastService: ToastService, private _router: Router) { }

  signup(cliente: Cliente) {
    this._http.post(`${this.basePath}/signup`, cliente, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': sessionStorage.getItem('bearer') ?? ''
      }),
      responseType: 'text'
    }).subscribe({
      next: v => {
        this.toastService.showSuccess("Registrazione effettuata con successo.")
        this._router.navigate(['/login']);
      },
      error: err => this.toastService.showError(err.error)
    })
  }
  getCliente(): Observable<Cliente> {
    return this._http.get<any>(`${this.basePath}/getCliente`, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': sessionStorage.getItem('bearer') ?? ''
      }),
    }).pipe(
      catchError((err) => {
        this.toastService.showError("Errore interno del server\n" + err.error);
        return of(new Cliente());
      }),
    )
  }
  
  creaPortafoglio(simbolo: string, label: string) {
    return this._http.post(`${this.basePath}/creaPortafoglio/${simbolo}`, { label }, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': sessionStorage.getItem('bearer') ?? ''
      }),
      responseType: 'text'
    }).pipe(
      catchError((err) => {
        this.toastService.showError("Errore interno del server\n" + err.error);
        return of([new Crypto()]);
      }),
    )
  }

  getCrypto(): Observable<Crypto[]> {
    return this._http.get<Crypto[]>(`${this.basePath}/getCrypto`, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': sessionStorage.getItem('bearer') ?? ''
      }),
    }).pipe(
      catchError((err) => {
        this.toastService.showError("Errore interno del server\n" + err.error);
        return of([new Crypto()]);
      }),
    )
  }

  requestWithdraw(request: ClientWithdrawRequest) {
    return this._http.post(`${this.basePath}/requestWithdraw`, request, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': sessionStorage.getItem('bearer') ?? ''
      }),
      responseType: 'text'
    }).pipe(
      catchError((err) => {
        this.toastService.showError("Errore interno del server\n" + err.error);
        return of('');
      }),
    )
  }

  newInvoice(invoice: any): Observable<any> {
    return this._http.post(`${this.basePath}/createInvoice/${invoice.simbolo.simbolo}`, {
      toEmail: invoice.toEmail,
      descrizione: invoice.descrizione,
      amount: invoice.amount
    }, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': sessionStorage.getItem('bearer') ?? ''
      }),
    }).pipe(
      catchError((err) => {
        this.toastService.showError("Errore interno del server\n" + err.error);
        return of(undefined);
      }),
    )
  }

  updateInvoiceStatus(simbolo: string, invoiceId: string): Observable<any> {
    return this._http.post(`${this.basePath}/getInvoice/${simbolo}`, {
      invoiceId: invoiceId
    }, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': sessionStorage.getItem('bearer') ?? ''
      }),
    }).pipe(
      catchError((err) => {
        this.toastService.showError("Errore interno del server\n" + err.error);
        return of(undefined);
      }),
    )
  }

  annullaInvoice(invoiceId: string): Observable<any> {
    return this._http.post(`${this.basePath}/annullaInvoice`, {
      invoiceId: invoiceId
    }, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': sessionStorage.getItem('bearer') ?? ''
      }),
      responseType: 'text'
    }).pipe(
      catchError((err) => {
        this.toastService.showError("Errore interno del server\n" + err.error);
        return of(undefined);
      }),
    )
  }
  
  confermaInvoice(invoiceId: string): Observable<any> {
    return this._http.post(`${this.basePath}/confermaInvoice`, {
      invoiceId: invoiceId
    }, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': sessionStorage.getItem('bearer') ?? ''
      }),
      responseType: 'text'
    }).pipe(
      catchError((err) => {
        this.toastService.showError("Errore interno del server\n" + err.error);
        return of(undefined);
      }),
    )
  }
}
