export class Portafoglio {
    indirizzo!: string;
    idCliente!: number;
    blocked!: boolean;
    saldo!: number;
    creazione!: Date;
    qrCode: string | undefined;
    simbolo!: string;
}
