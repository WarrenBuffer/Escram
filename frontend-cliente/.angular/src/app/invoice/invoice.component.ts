import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Portafoglio } from '../model/portafoglio.model';

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrl: './invoice.component.css'
})
export class InvoiceComponent implements OnInit{
  crypto: string[]=[];
  form = new FormGroup ({
    toEmail : new FormControl('', [Validators.required, Validators.pattern("^[\\w.%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")]),
    descrizione : new FormControl('', [Validators.required]),
    simbolo : new FormControl('', [Validators.required]),
    amount : new FormControl('', [Validators.required]),
  })
  
  constructor(private fb: FormBuilder) { }
  ngOnInit(): void {


  }

  onSubmit(form: any) {
    
  }

}

