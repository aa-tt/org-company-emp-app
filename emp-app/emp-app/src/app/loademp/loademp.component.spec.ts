import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadempComponent } from './loademp.component';

describe('LoadempComponent', () => {
  let component: LoadempComponent;
  let fixture: ComponentFixture<LoadempComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadempComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadempComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
