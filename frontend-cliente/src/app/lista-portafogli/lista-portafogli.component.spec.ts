import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaPortafogliComponent } from './lista-portafogli.component';

describe('ListaPortafogliComponent', () => {
  let component: ListaPortafogliComponent;
  let fixture: ComponentFixture<ListaPortafogliComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListaPortafogliComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListaPortafogliComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
