import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaInvoiceComponent } from './lista-invoice.component';

describe('ListaInvoiceComponent', () => {
  let component: ListaInvoiceComponent;
  let fixture: ComponentFixture<ListaInvoiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListaInvoiceComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListaInvoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
