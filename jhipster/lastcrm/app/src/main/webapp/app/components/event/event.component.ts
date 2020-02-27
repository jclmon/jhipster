import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'jhi-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.scss']
})
export class EventComponent implements OnInit {
  @Input() value: any;

  constructor() {}

  ngOnInit() {}
}
