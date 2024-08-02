
// app.module.ts
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { PortafoglioComponent } from './nuovo-portafoglio/nuovo-portafoglio.component'; 
import { InputTextModule } from 'primeng/inputtext';
import { InvoiceComponent } from './invoice/invoice.component';
import { AccordionModule } from 'primeng/accordion';


// PrimeNG Modules
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { DropdownModule } from 'primeng/dropdown';
import { MessageModule } from 'primeng/message';
import { TableModule } from 'primeng/table';
import { TransazioneComponent } from './transazione/transazione.component';
import { HomeComponent } from './home/home.component';
import { SelectButtonModule } from 'primeng/selectbutton';
import { DialogModule } from 'primeng/dialog';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { ToastModule } from 'primeng/toast';

import { LoginComponent } from './login/login.component';
import { MessageService } from 'primeng/api';
import { HttpClient, provideHttpClient, withFetch } from '@angular/common/http';
import { SignUpComponent } from './sign-up/sign-up.component';
import { LoadingComponent } from './shared/loading/loading.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { MenubarModule } from 'primeng/menubar';
import { ImageModule } from 'primeng/image';
import { ProgressSpinnerModule } from 'primeng/progressspinner';

@NgModule({
  declarations: [
    AppComponent,
    PortafoglioComponent,
    InvoiceComponent,
    TransazioneComponent,
    LoginComponent,
    SignUpComponent,
    LoadingComponent,
    NavbarComponent,
    HomeComponent,
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
    TableModule,
    AppRoutingModule,
    ToastModule,
    CardModule,
    ButtonModule,
    InputTextModule,
    FormsModule,
    ReactiveFormsModule,
    MessageModule,
    MenubarModule,
    ImageModule,
    ProgressSpinnerModule,
    AccordionModule,
    SelectButtonModule,
    InputTextModule,
    DialogModule
  ],
  providers: [
    provideClientHydration(),
    provideHttpClient(withFetch()),
    MessageService, HttpClient],
  bootstrap: [AppComponent]
})
export class AppModule { }
