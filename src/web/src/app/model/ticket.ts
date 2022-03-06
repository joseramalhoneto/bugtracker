export class Ticket{
  ticketId?: number;
  ticketNumber?: number;
  project?: string;
  issueType?: string;
  summary?: string;
  description?: string;
  severity?: number;
  reporter?: string;

  constructor(
    ticketId?: number,
    ticketNumber?: number,
    project?: string,
    issueType?: string,
    description?: string,
    summary?: string,
    severity?: number,
    reporter?: string,
  ) {
      this.ticketId = ticketId,
      this.ticketNumber = ticketNumber,
      this.project = project,
      this.issueType = issueType,
      this.description = description,
      this.summary = summary,
      this.severity = severity,
      this.reporter = reporter
    }
}


