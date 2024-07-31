import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PortafoglioComponent } from './nuovo-portafoglio/nuovo-portafoglio.component';
import { TransazioneEscrowComponent } from './transazione-escrow/transazione-escrow.component';

const routes: Routes = [
  { path: 'nuovoPortafoglio', component: PortafoglioComponent },
  { path: '', redirectTo: '/nuovoPortafoglio', pathMatch: 'full' },  // Redirect to a default route, if desired
  {path: 'transazioneEscrow', component: TransazioneEscrowComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
