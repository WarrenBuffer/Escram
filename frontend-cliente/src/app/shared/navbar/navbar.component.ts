import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { Router } from '@angular/router';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit{
  items: MenuItem[] | undefined;

  constructor(private authService: AuthenticationService, private _router:Router) {
  }
  ngOnInit(): void {
    this.items = [
      {
        label: 'Transazioni',
        icon: 'pi pi-search',
        routerLink: '/transazioni'
      },
      {
        label: 'Nuovo portafoglio',
        icon: 'pi pi-users',
        routerLink: '/nuovoPortafoglio'
      },
      {
        label: 'Invoice',
        icon: 'pi pi-user-plus',
        routerLink: '/invoice'
      },
    ]
  }

  getName() {
    const admin = this.authService.getToken(); 
    if (admin !== '') {
      return admin;
    }
    return 'admin';
  }

  isAuthenticated() {
    return this.authService.getToken() !== '';
  }

  logout() {
    this._router.navigate(['/logout']);
  }
  

}
