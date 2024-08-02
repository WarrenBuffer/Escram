import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ToastService } from './toast.service';
import { AuthService } from './auth.service';
import { catchError, Observable } from 'rxjs';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private basePath = "http://localhost:8080/admin";

  constructor(private _http: HttpClient, private storageService: StorageService, private authService: AuthService) { }

  getClienti(): Observable<any> {
    return this._http.get(`${this.basePath}/getClienti`, {
      headers: {
        'Authorization': this.storageService.getItem('token') ?? ''
      }
    }).pipe(
      catchError((err) => {
        if (err.status === 401) 
          this.authService.logout();
        return err;
      })
    );
  };

  lockUnlock(email: string): Observable<any> {
    return this._http.post(`${this.basePath}/lockUnlock`,{email: email}, {
      headers: {
        'Authorization': this.storageService.getItem('token') ?? ''
      }
    }).pipe(
      catchError((err) => {
        if (err.status === 401) 
          this.authService.logout();
        return err;
      })
    );
  }
  
  getCrypto(): Observable<any> {
    return this._http.get(`${this.basePath}/getcrypto`, {
      headers: {
        'Authorization': this.storageService.getItem('token') ?? ''
      }
    }).pipe(
      catchError((err) => {
        if (err.status === 401) 
          this.authService.logout();
        return err;
      })
    );
  }
  
  updateCrypto(simbolo: string, nome: string, url: string): Observable<any> {
    return this._http.post(`${this.basePath}/updatecrypto`,{simbolo: simbolo, nome: nome, urlImmagine: url}, {
      headers: {
        'Authorization': this.storageService.getItem('token') ?? ''
      },
      responseType: 'text'
    }).pipe(
      catchError((err) => {
        if (err.status === 401) 
          this.authService.logout();
        return err;
      })
    );
  }
  
  deleteCrypto(simbolo: string): Observable<any> {
    return this._http.post(`${this.basePath}/deletecrypto`,{simbolo: simbolo}, {
      headers: {
        'Authorization': this.storageService.getItem('token') ?? ''
      },
      responseType: 'text'
    }).pipe(
      catchError((err) => {
        if (err.status === 401) 
          this.authService.logout();
        return err;
      })
    );
  }

  getDispute(): Observable<any> {
    return this._http.get(`${this.basePath}/irrisolte`, {
      headers: {
        'Authorization': this.storageService.getItem('token') ?? ''
      },
      responseType: 'text'
    }).pipe(
      catchError((err) => {
        if (err.status === 401) 
          this.authService.logout();
        return err;
      })
    );
  }

  getWithdrawRequests() {
    
  }
}
