import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Cliente } from '../cliente';
import { AuthenticationService } from '../services/authentication.service';
import { ApiService } from '../services/api.service';
import { Router } from '@angular/router';
import { ToastService } from '../services/toast.service';
import { NavbarComponent } from '../shared/navbar/navbar.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  private cliente: Cliente = new Cliente();
  form = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.pattern("^[\\w.%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")]),
    password: new FormControl('', [Validators.required, Validators.pattern("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#&%^$?!=])[a-zA-Z0-9@#&%^$?!=]{7,32}$")]),
  })

  constructor(private authenticationService: AuthenticationService, private apiService: ApiService, private _router: Router, private toastService: ToastService) {
  }

  submit(form: any) {
    this.authenticationService.login(form.email, form.password).subscribe({
      next: (v: string) => {
        sessionStorage.setItem("bearer", v.toString());
        this.apiService.getCliente(form.email).subscribe({
          next: v => {
            sessionStorage.setItem("cliente", v);
            this._router.navigate(["/home"]);
          }
        })
      },
      error: err => {
        console.log(err);
        this.toastService.showError(err.error)
      }
    });
  }
}
