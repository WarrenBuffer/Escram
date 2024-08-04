import { Injectable } from '@angular/core';
import { Cliente } from '../cliente';
import { Portafoglio } from '../model/portafoglio.model';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  clienteSessione: Cliente | undefined;
  constructor() { }

  getSessionClient(): Cliente {
    const cliente: string | null = sessionStorage.getItem("cliente");
    return cliente !== null ? JSON.parse(cliente) : new Cliente();
    // return cliente !== null ? JSON.parse(cliente) : null;
  }
  /*
  getSessionEmail(): string | undefined{
    const cliente: string | null = sessionStorage.getItem("cliente");
    if(cliente==null){
      return undefined;
    }else{
      return JSON.parse(cliente).email;
    }
  }
  getSessionNome(): string | undefined{
    const cliente: string | null = sessionStorage.getItem("cliente");
    if(cliente==null){
      return undefined;
    }else{
      return JSON.parse(cliente).nome;
    }
  }
  getSessionCognome(): string | undefined{
    const cliente: string | null = sessionStorage.getItem("cliente");
    if(cliente==null){
      return undefined;
    }else{
      return JSON.parse(cliente).cognome;
    }
  }
  getSessionTipologia(): string | null{
    const cliente: string | null = sessionStorage.getItem("cliente");
    return cliente !== null ? JSON.parse(cliente).tipologia : null;
  }

  getSessionPortafogli():Portafoglio[]{
    const cliente: string | null = sessionStorage.getItem("cliente");
    if(cliente==null){
      return [];
    }else{
      return JSON.parse(cliente).portafogli;
    }
  }

  getSessionNotifiche():any[] | undefined{
    const cliente: string | null = sessionStorage.getItem("cliente");
    if(cliente==null){
      return undefined;
    }else{
      return JSON.parse(cliente).notifiche;
    }
  }
   */
}
