export class Transazione {
    id!: string;
    amount!: number;
    date!: Date;
    fees!: number;
    tipo!: string;
    toAddress: string | undefined;
    txId!: string;
}
