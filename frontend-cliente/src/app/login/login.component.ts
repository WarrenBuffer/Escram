import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Cliente } from '../cliente';
import { AuthenticationService } from '../services/authentication.service';
import { ApiService } from '../services/api.service';
import { MessagesModule } from 'primeng/messages';

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

  constructor(private authenticationService: AuthenticationService, private apiService: ApiService) {
  }

  submit(form: any) {
    this.cliente.email = form.email;
    this.cliente.password = form.password;

    this.apiService.setter(this.cliente);

    this.authenticationService.login(form.email, form.password);
  }
}
