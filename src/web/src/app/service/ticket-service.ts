import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Ticket } from '../model/ticket';

@Injectable()
export class TicketService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public findAllTickets(): Observable<any>{
    return this.http.get(`${this.apiServerUrl}/ticket/find/all`);
  }

  public findTicketById(ticketId: number): Observable<any>{
    return this.http.get(`${this.apiServerUrl}/ticket/find/${ticketId}`);
  }

  public saveTicket(ticket: Ticket): Observable<any>{
    return this.http.post(`${this.apiServerUrl}/ticket/save`, ticket);
  }

  public updateTicket(Ticket: Ticket): Observable<any>{
    return this.http.put(`${this.apiServerUrl}/ticket/update`, Ticket);
  }

  public deleteTicketById(ticketId: number){
    return this.http.delete(`${this.apiServerUrl}/ticket/delete/${ticketId}`);
  }

}
