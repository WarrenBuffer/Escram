import { Component } from '@angular/core';
import { Cliente } from '../cliente';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from '../services/authentication.service';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.css'
})
export class SignUpComponent {
  private cliente: Cliente = new Cliente();
  form = new FormGroup({
    nome: new FormControl('', [Validators.required, Validators.pattern("^[a-zA-Z ,.'-]{4,30}$")]),
    cognome: new FormControl('', [Validators.required, Validators.pattern("^[a-zA-Z ,.'-]{2,30}$")]),
    email: new FormControl('', [Validators.required, Validators.pattern("^[\\w.%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,40}$")]),
    password: new FormControl('', [Validators.required, Validators.pattern("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#&%^$?=])[a-zA-Z0-9@#&%^$?=]{8,32}$")]),
  })

  constructor(private authenticationService: AuthenticationService, private apiService: ApiService) {
  }

  submit(form: any) {
    this.cliente.cognome=form.cognome;
    this.cliente.password=form.password;
    this.cliente.nome=form.nome;
    this.cliente.email=form.email;
    console.log(this.cliente);
    this.apiService.signup(this.cliente);
  }
}
