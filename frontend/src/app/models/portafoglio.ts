export class Portafoglio {
    indirizzo!: string;
    emailCliente!: string;
    creazione!: number;
    scadenza!: number;
    qrCode!: string;
    saldo!: number;
    blocked!: boolean;
    crypto!: {
        nome: string,
        simbolo: string,
        urlImmagine: string
    };
    transazioni: any;
}
