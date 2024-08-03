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
        case 'loginEscram': return this.bearer !== '' ? this._router.createUrlTree(['/home']) : true;
        case 'signupEscram': return this.bearer !== '' ? this._router.createUrlTree(['/home']) : true;
        case 'nuovoPortafoglio': return this.bearer !== '' ? true : this._router.createUrlTree(['/loginEscram']);
        case 'home': return this.bearer !== '' ? true : this._router.createUrlTree(['/loginEscram']);
        case 'invoice': return this.bearer !== '' ? true : this._router.createUrlTree(['/loginEscram']);
        case 'transazioni': return this.bearer !== '' ? true : this._router.createUrlTree(['/loginEscram']);
        case 'listaPortafogli': return this.bearer !== '' ? true : this._router.createUrlTree(['/loginEscram']);
        default: return false;
      }
    }
    return this._router.createUrlTree(['/']);
  }
}
