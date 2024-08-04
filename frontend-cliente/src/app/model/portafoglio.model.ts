import { Crypto } from "./crypto.model";

export class Portafoglio {
    indirizzo!: string;
    idCliente!: number;
    blocked!: boolean;
    saldo!: number;
    creazione!: Date;
    scadenza!: Date;
    qrCode!: string;
    crypto!: Crypto;
    transazioni!: any[];
    richiesteWithdraw!: any[];
}
