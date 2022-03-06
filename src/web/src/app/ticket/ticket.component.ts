import { Component, OnInit } from '@angular/core';
import { Ticket } from '../model/ticket';
import { TicketService } from '../service/ticket-service';
import { ColDef, GridApi, ColumnApi } from 'ag-grid-community';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrls: ['./ticket.component.css']
})
export class TicketComponent implements OnInit {

  public tickets: Ticket[] = [];
  public columnDefs: ColDef[] = [];
  private api: GridApi = new GridApi;
  private columnApi: ColumnApi = new ColumnApi;
  public ticket: Ticket = new Ticket();
  private action?: string;

  public defaultColDef: ColDef = {
    editable: false,
    sortable: true,
    flex: 1,
    minWidth: 100,
    filter: true,
    resizable: true,
  };

  constructor(private ticketService: TicketService) {
    this.columnDefs = this.createColumnDefs();
  }

  ngOnInit(): void {
    this.findAllTickets();
  }

  onGridReady(params: any): void {
    this.api = params.api;
    this.columnApi = params.columnApi;
    this.api.sizeColumnsToFit();
    this.api.setDomLayout("autoHeight");
}

private createColumnDefs() {
  return [{
     headerName: "ticketId",
     field: "ticketId",
     hide: true
  },{
      headerName: 'Ticket Number',
      field: 'ticketNumber'
  },{
      headerName: 'Project',
      field: 'project'
  },{
    headerName: 'Issue Type',
    field: 'issueType'
  },{
    headerName: 'Summary',
    field: 'summary'
  },{
    headerName: 'Description',
    field: 'description'
  },{
      headerName: 'Reporter',
      field: 'reporter'
  },{
    headerName: 'Severity',
    field: 'severity'
  }]
  }

  public findAllTickets():void{
    this.ticketService.findAllTickets().subscribe(
      (response: Ticket[]) => {
        this.tickets = response;
      }
    );
  }

  public saveTicket(addForm: NgForm): void {
    this.ticket.ticketNumber = addForm.value.ticketNumber;
    this.ticket.project = addForm.value.project;
    this.ticket.issueType = addForm.value.issueType;
    this.ticket.summary = addForm.value.summary;
    this.ticket.description = addForm.value.description;
    this.ticket.severity = addForm.value.severity;
    this.ticket.reporter = addForm.value.reporter;

    if(this.action == "add"){
      this.addTicket(this.ticket);
    }else{
      this.updateTicket(this.ticket);
    }
    addForm.reset();
  }

  public addTicket(ticket: Ticket): void {
    this.ticketService.saveTicket(ticket).subscribe(
      () => {
        this.findAllTickets();
      },(error: HttpErrorResponse) => {
        alert(error.message);
      }
    );

  }

  public updateTicket(ticket: Ticket): void {
    this.ticketService.updateTicket(this.ticket).subscribe(
      () => {
        this.findAllTickets();
        this.api.refreshCells();
      },(error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public deleteTicket(): void {
    var selectedRows = this.api.getSelectedRows();
    if (selectedRows.length == 0) {
      alert("Please select a ticket");
      return;
    }
    if (confirm("Are you sure you want to delete?")) {
      this.ticketService.deleteTicketById(selectedRows[0].ticketId).subscribe(data => {
        this.findAllTickets();
        this.api.refreshCells();
      });
    }
  }

  setAction(action: string){
    this.action = action;
  }

  setTicket(): void {
    var selectedRow = this.api.getSelectedRows()[0];
    if (selectedRow == null) {
      alert("Please select a ticket");
      return;
    }
    this.ticket = new Ticket(
      selectedRow.ticketId,
      selectedRow.ticketNumber,
      selectedRow.project,
      selectedRow.issueType,
      selectedRow.summary,
      selectedRow.description,
      selectedRow.severity,
      selectedRow.reporter
    )
  }

}
