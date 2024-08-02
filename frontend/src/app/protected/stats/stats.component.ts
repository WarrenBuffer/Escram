import { Component, OnInit } from '@angular/core';
import { StorageService } from '../../services/storage.service';
import { Cliente } from '../../models/cliente';

@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrl: './stats.component.css'
})
export class StatsComponent implements OnInit {
  loading = false;
  clienti!: Cliente[];
  stats: any = {
    clienti: 0,
    compratori: 0,
    venditori: 0,
    portafogli: 0,
    saldo: 0,
    invoices: 0,
    pending: 0,
    paid: 0,
    expired: 0,
    canceledSrc: 0,
    canceledDst: 0,
    confirmedSrc: 0,
    confirmedDst: 0,
    inAttesa: 0,
  };

  pieData: any;
  pieOptions: any;
  barData: any;
  barOptions: any;
  // TODO: Calcola statistiche con C3 e compila in webassembly
  private yellow = '--yellow-400';
  private yellowHover = '--yellow-300';
  private green = '--green-400';
  private greenHover = '--green-300';
  private red = '--red-400';
  private redHover = '--red-300';
  private orange = '--orange-400';
  private orangeHover = '--orange-300';
  private lightGreen = '#52b788';
  private lightGreenHover = '#52b788';

  constructor(private storageService: StorageService) { }

  ngOnInit(): void {
    const clienti = this.storageService.getItem('clienti');
    clienti !== null ? this.clienti = JSON.parse(clienti) : null;
    this.calc();
    this.initPieChart();
    this.initBarChart();

  }

  initPieChart() {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');

    this.pieData = {
      labels: ['Pending', 'Paid', 'Expired'],
      datasets: [
        {
          data: [this.stats.pending, this.stats.paid, this.stats.expired],
          backgroundColor: [documentStyle.getPropertyValue(this.yellow), documentStyle.getPropertyValue(this.green), documentStyle.getPropertyValue(this.red)],
          hoverBackgroundColor: [documentStyle.getPropertyValue(this.yellowHover), documentStyle.getPropertyValue(this.greenHover), documentStyle.getPropertyValue(this.redHover)]
        }
      ]
    };

    this.pieOptions = {
      plugins: {
        legend: {
          labels: {
            usePointStyle: true,
            color: textColor
          }
        }
      },
      responsive: false,
      maintainAspectRatio: false
    };
  }

  initBarChart() {
    const documentStyle = getComputedStyle(document.documentElement);

    this.barData = {
      labels: ['In attesa', 'Venditore confermato', 'Venditore annullato', 'Compratore confermato', 'Compratore annullato'],
      datasets: [
        {
          data: [this.stats.inAttesa, this.stats.confirmedSrc, this.stats.canceledSrc, this.stats.confirmedDst, this.stats.canceledDst],
          backgroundColor: [documentStyle.getPropertyValue(this.yellow), documentStyle.getPropertyValue(this.green), documentStyle.getPropertyValue(this.red), this.lightGreen, documentStyle.getPropertyValue(this.orange)],
          hoverBackgroundColor: [documentStyle.getPropertyValue(this.yellowHover), documentStyle.getPropertyValue(this.greenHover), documentStyle.getPropertyValue(this.redHover), this.lightGreenHover, documentStyle.getPropertyValue(this.orangeHover)]
        }
      ]
    };

    this.barOptions = {
      plugins: {
        legend: {
          display: false
        }
      },
      scales: {
        y: {
          ticks: {
            stepSize: 1
          }
        }
      },
      responsive: false,
      maintainAspectRatio: false
    };
  }


  calc() {
    this.stats.clienti = this.clienti.length;

    this.clienti.forEach((cliente: Cliente) => {
      cliente.tipologia === 'COMPRATORE' ? this.stats.compratori++ : this.stats.venditori++;
      this.stats.portafogli += cliente.portafogli.length;

      cliente.portafogli.forEach(portafoglio => {
        this.stats.saldo += portafoglio.saldo;
      });

      this.stats.invoices += cliente.invoices.length;
      cliente.invoices.forEach(invoice => {
        if (invoice.status === 'Pending') this.stats.pending++;
        else if (invoice.status === 'Paid') this.stats.paid++;
        else if (invoice.status === 'Expired') this.stats.expired++;

        if (invoice.statoSrc === 'ANNULLATO') this.stats.canceledSrc++;
        else if (invoice.statoSrc === 'CONFERMATO') this.stats.confirmedSrc++;
        if (invoice.statoDst === 'ANNULLATO') this.stats.canceledDst++;
        else if (invoice.statoDst === 'CONFERMATO') this.stats.confirmedDst++;

        if (invoice.statoSrc === 'IN_ATTESA' && invoice.statoDst === 'IN_ATTESA') this.stats.inAttesa++;
      });
    });
  }

}
