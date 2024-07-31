import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransazioneEscrowComponent } from './transazione-escrow.component';

describe('TransazioneEscrowComponent', () => {
  let component: TransazioneEscrowComponent;
  let fixture: ComponentFixture<TransazioneEscrowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TransazioneEscrowComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TransazioneEscrowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
