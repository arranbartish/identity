package com.solvedbysunrise.identity.data.entity.jpa.email;

import com.solvedbysunrise.identity.data.entity.jpa.ReflectiveEntity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "EMAIL_EVENT")
public class EmailEvent extends ReflectiveEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL_ID", updatable = false, insertable = true, nullable = false)
    private Long emailId;

    @Column(name = "EVENT_TYPE", updatable = false, insertable = true, nullable = false)
    private String eventType;

    @Column(name = "REMOTE_DATE", updatable = false, insertable = true, nullable = false)
    private Timestamp remoteDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Timestamp getRemoteDate() {
        return remoteDate;
    }

    public void setRemoteDate(Timestamp remoteDate) {
        this.remoteDate = remoteDate;
    }
}
