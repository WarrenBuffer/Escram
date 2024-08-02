import { NgModule } from '@angular/core';
import { mapToCanActivate, RouterModule, Routes } from '@angular/router';
import { AuthGuardService } from './services/auth-guard.service';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './protected/home/home.component';
import { StatsComponent } from './protected/stats/stats.component';
import { CryptoComponent } from './protected/crypto/crypto.component';
import { DisputeComponent } from './protected/dispute/dispute.component';
import { WithdrawComponent } from './protected/withdraw/withdraw.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full'},
  { path: 'login', canActivate: mapToCanActivate([AuthGuardService]), component: LoginComponent  },
  { path: 'home', canActivate: mapToCanActivate([AuthGuardService]), component: HomeComponent  },
  { path: 'stats', canActivate: mapToCanActivate([AuthGuardService]), component: StatsComponent  },
  { path: 'crypto', canActivate: mapToCanActivate([AuthGuardService]), component: CryptoComponent  },
  { path: 'dispute', canActivate: mapToCanActivate([AuthGuardService]), component: DisputeComponent  },
  { path: 'withdraw', canActivate: mapToCanActivate([AuthGuardService]), component: WithdrawComponent  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
