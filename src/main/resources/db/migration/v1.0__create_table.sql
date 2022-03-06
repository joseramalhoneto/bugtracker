CREATE TABLE ticket (
    ticket_id INT  PRIMARY KEY,
    project VARCHAR(20),
    issue_type VARCHAR(20),
    summary VARCHAR(20),
    description VARCHAR(40),
    severity INT,
    reporter VARCHAR(40)
)
