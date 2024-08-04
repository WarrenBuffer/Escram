import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ToastService } from './toast.service';
import { ApiService } from './api.service';
import { Cliente } from '../cliente';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  cliente!: Cliente;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    }),
  };
  private basePath = "http://localhost:8080";
  constructor(private _http: HttpClient, private _router: Router, private toastService: ToastService, private apiService: ApiService) { }

  getBearer() {
    return sessionStorage.getItem("bearer") ?? '';
  }

  login(email: string, password: string): Observable<any> {
    return this._http.post(`${this.basePath}/cliente/login`, {
      email: email,
      password: password
    }, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      responseType: 'text'
    });
  }

  logout() {
    sessionStorage.removeItem("bearer");
    sessionStorage.removeItem("cliente");
    this._router.navigate(['/login']);
  }
}
