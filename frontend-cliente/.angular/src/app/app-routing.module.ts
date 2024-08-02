import { NgModule } from '@angular/core';
import { PortafoglioComponent } from './nuovo-portafoglio/nuovo-portafoglio.component';
import { InvoiceComponent } from './invoice/invoice.component';
import { TransazioneComponent } from './transazione/transazione.component';
import { HomeComponent } from './home/home.component';

import { mapToCanActivate, RouterModule, Routes } from '@angular/router';
import { AuthGuardService } from './services/auth-guard.service';
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './sign-up/sign-up.component';

const routes: Routes = [
  { path: '', redirectTo: 'signupEscram', pathMatch: 'full'},
  { path: 'loginEscram', canActivate: mapToCanActivate([AuthGuardService]), component: LoginComponent},
  { path: 'signupEscram', canActivate: mapToCanActivate([AuthGuardService]), component: SignUpComponent},
  { path: 'nuovoPortafoglio', canActivate: mapToCanActivate([AuthGuardService]), component: PortafoglioComponent },
  { path: 'invoice',canActivate: mapToCanActivate([AuthGuardService]), component: InvoiceComponent},

  { path: 'transazioni',canActivate: mapToCanActivate([AuthGuardService]), component: TransazioneComponent},
  { path: 'home',canActivate: mapToCanActivate([AuthGuardService]), component: HomeComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
