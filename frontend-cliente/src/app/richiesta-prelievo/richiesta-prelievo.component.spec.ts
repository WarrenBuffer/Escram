import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RichiestaPrelievoComponent } from './richiesta-prelievo.component';

describe('RichiestaPrelievoComponent', () => {
  let component: RichiestaPrelievoComponent;
  let fixture: ComponentFixture<RichiestaPrelievoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RichiestaPrelievoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RichiestaPrelievoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
