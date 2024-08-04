import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { MenuItem } from 'primeng/api';
import { ApiService } from '../../services/api.service';
import { ClienteService } from '../../services/cliente.service';
import { Cliente } from '../../cliente';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  items: MenuItem[] | undefined;
  visible: boolean = false;
  widthPercent: number = 100;
  invoiceVisible: boolean = true;
  constructor(private authService: AuthenticationService, private apiService: ApiService, private clienteSession: ClienteService) {
  }

  ngOnInit(): void {
    const cliente = this.clienteSession.getSessionClient();

    this.items = [
      {
        label: 'Home',
        icon: 'pi pi-home',
        routerLink: '/home'
      },
      {
        label: 'Lista invoice',
        icon: 'pi pi-receipt',
        routerLink: '/listaInvoice'
      },
      {
        label: 'Nuovo invoice',
        icon: 'pi pi-dollar',
        routerLink: '/nuovoInvoice',
        visible: cliente?.tipologia == 'VENDITORE'
      },
      {
        label: 'Nuovo wallet',
        icon: 'pi pi-wallet',
        routerLink: '/nuovoPortafoglio'
      },
    ]
    this.visible = false;
    this.widthPercent = 100;
  }

  getName() {
    return this.clienteSession.getSessionClient()?.nome ?? "";
  }

  isAuthenticated() {
    return this.authService.getBearer() !== '';
  }

  logout() {
    this.authService.logout();
    this.visible = false;
    this.invoiceVisible = true;
  }
  showDialog() {
    this.visible = !this.visible;
  }

  getNumeroNotifiche() {
    // this.clienteSession.getSessionClient()?.notifiche.length ?? 0;
    return length > 0 ? length.toString() : '';
  }



}
