import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisputeComponent } from './dispute.component';

describe('DisputeComponent', () => {
  let component: DisputeComponent;
  let fixture: ComponentFixture<DisputeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DisputeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DisputeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
