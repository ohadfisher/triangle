package triangle.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class TimestampEntity {
    private static final Logger log = LoggerFactory.getLogger(TimestampEntity.class);


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false)
    private Date updated;

    //for Hibernate
    public TimestampEntity() {}

    public TimestampEntity(TimestampEntity timestampEntity) {
        this.created = timestampEntity.created;
    }

    @PrePersist
    protected void onCreate() {
        updated = created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }

    //use clone to make sore date stay as is and will not change (TimestampEntity stay immutable)
    public Date getCreated() {
        if (created != null) {
           return  (Date) created.clone();
        } else {
            log.warn("created filed is null");
            return null;
        }
    }

    //use clone to make sore date stay as is and will not change (TimestampEntity stay immutable)
    public Date getUpdated() {
        if (updated != null) {
            return  (Date) updated.clone();
        } else {
            log.warn("updated filed is null");
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimestampEntity that = (TimestampEntity) o;

        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        return updated != null ? updated.equals(that.updated) : that.updated == null;
    }

    @Override
    public int hashCode() {
        int result = created != null ? created.hashCode() : 0;
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TimestampEntity{" +
                "created=" + created +
                ", updated=" + updated +
                '}';
    }
}

