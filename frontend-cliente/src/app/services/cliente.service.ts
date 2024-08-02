import { Injectable } from '@angular/core';
import { Cliente } from '../cliente';
import { Portafoglio } from '../model/portafoglio.model';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  clienteSessione: Cliente | undefined;
  constructor() { }


  getSessionClient(): Cliente | undefined{
    const cliente: string | null = sessionStorage.getItem("cliente");
    if(cliente==null){
      return undefined;
    }else{
      return JSON.parse(cliente);
    }
  }
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
  getSessionTipologia(): string | undefined{
    const cliente: string | null = sessionStorage.getItem("cliente");
    if(cliente==null){
      return undefined;
    }else{
      return JSON.parse(cliente).tipologia;
    }
  }

  getSessionPortafogli():Portafoglio[] | undefined{
    const cliente: string | null = sessionStorage.getItem("cliente");
    if(cliente==null){
      return undefined;
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
  
}
