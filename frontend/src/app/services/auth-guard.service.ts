import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, UrlTree } from '@angular/router';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService {

  
  constructor(private _router: Router, private storageService: StorageService) {

  }

  canActivate(route: ActivatedRouteSnapshot): boolean | UrlTree {
    const token = this.storageService.getItem('token');
    
    for (let i = 0; i < route.url.length; ++i) {
      const path = route.url[i].path;
      switch (path) {
        case 'login': return token !== null ? this._router.createUrlTree(['/home']) : true;
        case 'home': return token !== null ? true : this._router.createUrlTree(['/']);
        case 'stats': return token !== null ? true : this._router.createUrlTree(['/']);
        case 'crypto': return token !== null ? true : this._router.createUrlTree(['/']);
        case 'dispute': return token !== null ? true : this._router.createUrlTree(['/']);
        case 'withdraw': return token !== null ? true : this._router.createUrlTree(['/']);
      }
    }

    return this._router.createUrlTree(['/']);
  }
}
