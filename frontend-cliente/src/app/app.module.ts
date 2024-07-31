// app.module.ts
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { PortafoglioComponent } from './nuovo-portafoglio/nuovo-portafoglio.component'; 
import { InputTextModule } from 'primeng/inputtext';
import { InvoiceComponent } from './invoice/invoice.component';

// PrimeNG Modules
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { DropdownModule } from 'primeng/dropdown';
import { MessageModule } from 'primeng/message';
import { TableModule } from 'primeng/table';
import { TransazioneComponent } from './transazione/transazione.component';


@NgModule({
  declarations: [
    AppComponent,
    PortafoglioComponent,
    InvoiceComponent,
    TransazioneComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    ButtonModule,
    CardModule,
    DropdownModule,
    MessageModule,
    AppRoutingModule,
    InputTextModule,
    TableModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
