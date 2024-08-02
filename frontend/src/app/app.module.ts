import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { ToolbarModule } from 'primeng/toolbar';
import { PasswordModule } from 'primeng/password';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { MessageModule } from 'primeng/message';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ChipModule } from 'primeng/chip';
import { TagModule } from 'primeng/tag';
import { TooltipModule } from 'primeng/tooltip';
import { TableModule } from 'primeng/table';
import { DropdownModule } from 'primeng/dropdown';
import { DialogModule } from 'primeng/dialog';
import { SelectButtonModule } from 'primeng/selectbutton';
import { ChartModule } from 'primeng/chart';
import { MenubarModule } from 'primeng/menubar';
import { ImageModule } from 'primeng/image';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './protected/home/home.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { ToggleButtonModule } from 'primeng/togglebutton';
import { StatsComponent } from './protected/stats/stats.component';
import { CryptoComponent } from './protected/crypto/crypto.component';
import { LoadingComponent } from './shared/loading/loading.component';
import { PanelModule } from 'primeng/panel';
import { DisputeComponent } from './protected/dispute/dispute.component';
import { WithdrawComponent } from './protected/withdraw/withdraw.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    NavbarComponent,
    StatsComponent,
    CryptoComponent,
    LoadingComponent,
    DisputeComponent,
    WithdrawComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ToastModule,
    BrowserAnimationsModule,
    ButtonModule,
    CardModule,
    ToolbarModule,
    PasswordModule,
    FormsModule,
    ReactiveFormsModule,
    InputTextModule,
    MessageModule,
    ProgressSpinnerModule,
    MenubarModule,
    ToggleButtonModule,
    TableModule,
    ChartModule,
    DialogModule,
    PanelModule
  ],
  providers: [
    provideClientHydration(),
    provideHttpClient(withFetch()),
    MessageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
