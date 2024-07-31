import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ToastService } from './toast.service';
import { jsDocComment } from '@angular/compiler';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    }),
  };
  private basePath = "http://localhost:8080";
  constructor(private _http: HttpClient, private _router: Router, private toastService: ToastService) { }

  getToken() {
    return sessionStorage.getItem("token") ?? '';
  }

  login(email: string, password: string) {
    this._http.post(`${this.basePath}/cliente/login`, {
      email: email,
      password: password
    }, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      responseType: 'text'
    }).subscribe({
      next: (v: String) => {
        console.log(v)
        sessionStorage.setItem("token", v.toString())
      },
      error: err => {
        this.toastService.showError(err.message)
        console.log(err.message)
      }
    })
  }

  logout() {
    this._http.get(`${this.basePath}/logout`, this.httpOptions).subscribe({
      next: v => {
        this._router.navigate(['/loginEscram']);
        sessionStorage.removeItem("token");
      },
      error: err => { this.toastService.showError("Errore interno del server\n" + err.message) }
    });
  }
}
