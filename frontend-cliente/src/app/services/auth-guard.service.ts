import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, UrlTree } from '@angular/router';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService {
  private token:string='';
  constructor(private _router: Router, private authenticationService: AuthenticationService) { }

  canActivate(route: ActivatedRouteSnapshot):boolean | UrlTree{
    this.token=this.authenticationService.getToken();
    for(let i=0; i<route.url.length; i++){
      const path=route.url[i].path;
      switch (path) {
        case 'loginEscram': return this.token !== '' ? this._router.createUrlTree(['/home']) : true;
        case 'signupEscram': return this.token !== '' ? this._router.createUrlTree(['/home']) : true;
        case 'nuovoPortafoglio': return this.token !== '' ? true : this._router.createUrlTree(['/loginEscram']);
        case 'home': return this.token !== '' ? true : this._router.createUrlTree(['/loginEscram']);
        case 'invoice': return this.token !== '' ? true : this._router.createUrlTree(['/loginEscram']);
        case 'transazione': return this.token !== '' ? true : this._router.createUrlTree(['/loginEscram']);
        default: return false;
      }
    }

    
    return this._router.createUrlTree(['/']);
  }
}
