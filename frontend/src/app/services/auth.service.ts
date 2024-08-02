import { HttpClient, HttpHeaders, HttpParamsOptions } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ToastService } from './toast.service';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private basePath = "http://localhost:8080/admin";
  private headers = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  constructor(private _http: HttpClient, private _router: Router, private toastService: ToastService, private storageService: StorageService) { }

  login(email: string, password: string) {
    this._http.post(`${this.basePath}/login`, {
      email,
      password
    }, {
      headers: this.headers,
      responseType: 'text'
    }).subscribe({
      next: res => {
        this.storageService.setItem('token', res);
        this.toastService.showSuccess("Log in effettuato con successo.")
        this._router.navigate(['home']);
      },
      error: err => {
        this.toastService.showError(err.error);
      }
    })
  }

  logout() {
    this.storageService.removeItem('token');
    this._router.navigate(['login'])
  }

  isAuthenticated() {
    return this.storageService.getItem('token') !== null;
  }
}
