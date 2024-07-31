import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PortafoglioComponent } from './nuovo-portafoglio/nuovo-portafoglio.component';
import { InvoiceComponent } from './invoice/invoice.component';
import { TransazioneComponent } from './transazione/transazione.component';

const routes: Routes = [
  { path: 'nuovoPortafoglio', component: PortafoglioComponent },
  { path: '', redirectTo: '/nuovoPortafoglio', pathMatch: 'full' },  // Redirect to a default route, if desired
  {path: 'invoice', component: InvoiceComponent},
  {path: 'transazione', component: TransazioneComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
