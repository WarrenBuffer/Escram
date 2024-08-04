import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, UrlTree } from '@angular/router';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService {
  
  private bearer:string='';
  constructor(private _router: Router, private authenticationService: AuthenticationService) { }

  canActivate(route: ActivatedRouteSnapshot):boolean | UrlTree{
    this.bearer=this.authenticationService.getBearer();
    for(let i=0; i<route.url.length; i++){
      const path=route.url[i].path;
      switch (path) {
        // non loggato
        case 'login': case 'signup':
           return this.bearer !== '' ? this._router.createUrlTree(['/home']) : true;

        // loaggato
        case 'nuovoPortafoglio': case 'home': case 'nuovoInvoice': case 'transazioni': case 'listaPortafogli': case 'listaInvoice':
          return this.bearer !== '' ? true : this._router.createUrlTree(['/login']);
      }
    }
    return this._router.createUrlTree(['/']);
  }
}
