import { Invoice } from "./invoice";
import { Notifica } from "./notifica";
import { Portafoglio } from "./portafoglio";

export class Cliente {
    nome!: string;
    cognome!: string;
    email!: string;
    blocked!: boolean;
    tipologia!: string;
    portafogli!: Portafoglio[];
    invoices!: Invoice[];
    notifiche!: Notifica[];
}
