import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  items: MenuItem[] = [
    {
      label: 'Home',
      icon: 'pi pi-home',
      routerLink: '/home',
    },
    {
      label: 'Statistiche',
      icon: 'pi pi-chart-bar',
      routerLink: '/stats',
    },
    {
      label: 'Crypto',
      icon: 'pi pi-bitcoin',
      routerLink: '/crypto',
    },
    {
      label: 'Dispute',
      icon: 'pi pi-hammer',
      routerLink: '/dispute',
    },
    {
      label: 'Withdraw',
      icon: 'pi pi-dollar',
      routerLink: '/withdraw',
    },
  ];

  constructor(private authService: AuthService) { }

  logout() {
    this.authService.logout();
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }
}
