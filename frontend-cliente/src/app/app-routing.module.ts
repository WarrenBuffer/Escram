import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PortafoglioComponent } from './nuovo-portafoglio/nuovo-portafoglio.component';
import { TransazioneEscrowComponent } from './transazione-escrow/transazione-escrow.component';
import { TransazioneComponent } from './transazione/transazione.component';

const routes: Routes = [
  { path: 'nuovoPortafoglio', component: PortafoglioComponent },
  { path: '', redirectTo: '/nuovoPortafoglio', pathMatch: 'full' },  // Redirect to a default route, if desired
  {path: 'transazioneEscrow', component: TransazioneEscrowComponent},
  {path: 'transazione', component: TransazioneComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
