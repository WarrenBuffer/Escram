import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PortafoglioComponent } from './nuovo-portafoglio.component';

describe('PortafoglioComponent', () => {
  let component: PortafoglioComponent;
  let fixture: ComponentFixture<PortafoglioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PortafoglioComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PortafoglioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
