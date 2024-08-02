import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransazioneComponent } from './transazione.component';

describe('TransazioneComponent', () => {
  let component: TransazioneComponent;
  let fixture: ComponentFixture<TransazioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TransazioneComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TransazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
