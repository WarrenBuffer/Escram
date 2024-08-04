import { NgModule } from '@angular/core';
import { PortafoglioComponent } from './nuovo-portafoglio/nuovo-portafoglio.component';
import { InvoiceComponent } from './invoice/invoice.component';
import { HomeComponent } from './home/home.component';

import { mapToCanActivate, RouterModule, Routes } from '@angular/router';
import { AuthGuardService } from './services/auth-guard.service';
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { ListaInvoiceComponent } from './lista-invoice/lista-invoice.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full'},
  { path: 'login', canActivate: mapToCanActivate([AuthGuardService]), component: LoginComponent},
  { path: 'signup', canActivate: mapToCanActivate([AuthGuardService]), component: SignUpComponent},
  { path: 'nuovoPortafoglio', canActivate: mapToCanActivate([AuthGuardService]), component: PortafoglioComponent },
  { path: 'nuovoInvoice',canActivate: mapToCanActivate([AuthGuardService]), component: InvoiceComponent},
  { path: 'listaInvoice',canActivate: mapToCanActivate([AuthGuardService]), component: ListaInvoiceComponent},
  { path: 'home',canActivate: mapToCanActivate([AuthGuardService]), component: HomeComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
