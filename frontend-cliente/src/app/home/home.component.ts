import { Component, OnInit } from '@angular/core';
import { Portafoglio } from '../model/portafoglio.model';

interface Cryptocurrency {
  label: string;
  value: string;
  image: string;
}
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  listaPortafogli: Portafoglio[] = [];

  cryptocurrencies: Cryptocurrency[] = [
    { label: 'Bitcoin', value: 'BTC', image: 'assets/crypto-icons/bitcoin.png' },
    { label: 'Ethereum', value: 'ETH', image: 'assets/crypto-icons/ethereum.png' },
    { label: 'Ripple', value: 'XRP', image: 'assets/crypto-icons/ripple.png' },
    { label: 'Litecoin', value: 'LTC', image: 'assets/crypto-icons/litecoin.png' },
    { label: 'Cardano', value: 'ADA', image: 'assets/crypto-icons/cardano.png' },
    { label: 'Solana', value: 'SOL', image: 'assets/crypto-icons/solana.png' },
    { label: 'Dogecoin', value: 'DOGE', image: 'assets/crypto-icons/dogecoin.png' },
    { label: 'Polkadot', value: 'DOT', image: 'assets/crypto-icons/polkadot.png' },
    { label: 'Chainlink', value: 'LINK', image: 'assets/crypto-icons/chainlink.png' }
  ];

  selectedCrypto: Cryptocurrency | null = null;

  ngOnInit(): void {
    this.listaPortafogli = [
      {
        simbolo: 'BTC',
        indirizzo: '1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa',
        blocked: false,
        idCliente: 20,
        qrCode: 'code',
        creazione: new Date(),
        saldo: 50,
      },
      {
        simbolo: 'ETH',
        indirizzo: '0x742d35Cc6634C0532925a3b844Bc454e4438f44e',
        blocked: true,
        idCliente: 20,
        qrCode: 'code',
        creazione: new Date(),
        saldo: 20,
      },
      {
        simbolo: 'XRP',
        indirizzo: 'rEb8TK3gBgk5auZkwc6sHnwrGVJH8DuaLh',
        blocked: false,
        idCliente: 20,
        qrCode: 'code',
        creazione: new Date(),
        saldo: 300,
      },
    ]
  }

}
