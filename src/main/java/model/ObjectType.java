package model;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Represents the various object types present in the system.
 * It encapsulates the detailed information and status about each object type.
 */
public class ObjectType {

    private int id;
    private String name;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private StatusOption statusOption;

    public ObjectType() {
    }

    public ObjectType(int id, String name, String description, Timestamp createdAt, Timestamp updatedAt, StatusOption statusOption) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.statusOption = statusOption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public StatusOption getStatusOption() {
        return statusOption;
    }

    public void setStatusOption(StatusOption statusOption) {
        this.statusOption = statusOption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectType)) return false;
        ObjectType that = (ObjectType) o;
        return getId() == that.getId() && Objects.equals(getName(), that.getName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getCreatedAt(), that.getCreatedAt()) && Objects.equals(getUpdatedAt(), that.getUpdatedAt()) && getStatusOption() == that.getStatusOption();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getCreatedAt(), getUpdatedAt(), getStatusOption());
    }

    @Override
    public String toString() {
        return "ObjectType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", statusOption=" + statusOption +
                '}';
    }

    /**
     * Enum representing the status options for object types.
     */
    public enum StatusOption {
        ACTIVE,
        INACTIVE
    }
}