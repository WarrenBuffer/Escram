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
export class NavbarComponent implements OnInit{
  items: MenuItem[] | undefined;
  visible: boolean= false;
  widthPercent: number=100;
  invoiceVisible:boolean=true;
  constructor(private authService: AuthenticationService, private apiService:ApiService, private clienteSession: ClienteService) {
  }

  ngOnInit(): void {
    const cliente=this.clienteSession.getSessionClient();
    if(cliente !== undefined){
      if (cliente.tipologia== 'COMPRATORE') {
        console.log('Questo cliente è un compratore')
        this.invoiceVisible=false;
      }
    }
    this.items = [
      {
        label: '',
        icon: '',
        routerLink: ''
      },
      {
        label: 'Transazioni',
        icon: 'pi pi-list',
        routerLink: '/transazioni'
      },
      {
        label: 'Invoice',
        icon: 'pi pi-dollar',
        routerLink: '/invoice',
        visible: this.invoiceVisible
      },
      {
        label: 'Nuovo wallet',
        icon: 'pi pi-wallet',
        routerLink: '/nuovoPortafoglio'
      },
    ]
    this.visible=false;
    this.widthPercent=100;
  }
  
  getName() {
    return this.clienteSession.getSessionNome() ?? '';
  }
  
  isAuthenticated() {
    return this.authService.getBearer() !== '';
  }
  
  logout() {
    this.authService.logout();
    this.visible=false;
    this.invoiceVisible=true;
  }
  showDialog() {
    this.visible = !this.visible;
  } 
  
  getNumeroNotifiche(): string|undefined {
    const length = this.clienteSession.getSessionNotifiche()?.length ?? 0;
    return length > 0 ? length.toString() : '';
  }



}
