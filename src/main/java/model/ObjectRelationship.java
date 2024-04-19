package model;

import java.sql.Timestamp;

/**
 * Represents the relationship between two different object types within the system.
 */
public class ObjectRelationship {
    /**
     * The unique identifier for the object relationship.
     */
    private int id;
    /**
     * The name of the relationship, describing how two object types are connected.
     */
    private String relationshipName;
    /**
     * The type of relationship, such as one-to-one, one-to-many, etc.
     */
    private RelationshipType relationshipType;
    /**
     * A detailed explanation of the relationship and its purpose within the application.
     */
    private String description;
    /**
     * The primary object type involved in the relationship.
     */
    private ObjectType objectType;
    /**
     * The secondary object type that is involved in the relationship with the primary object type.
     */
    private ObjectType relatedObjectType;

    // Constructors, getters, setters, and toString() method

    public ObjectRelationship() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelationshipName() {
        return relationshipName;
    }

    public void setRelationshipName(String relationshipName) {
        this.relationshipName = relationshipName;
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public ObjectType getRelatedObjectType() {
        return relatedObjectType;
    }

    public void setRelatedObjectType(ObjectType relatedObjectType) {
        this.relatedObjectType = relatedObjectType;
    }

    @Override
    public String toString() {
        return "ObjectRelationship{" +
                "id=" + id +
                ", relationshipName='" + relationshipName + '\'' +
                ", relationshipType=" + relationshipType +
                ", description='" + description + '\'' +
                ", objectType=" + objectType +
                ", relatedObjectType=" + relatedObjectType +
                '}';
    }

    /**
     * Defines the possible types of relationships between object types.
     */
    public enum RelationshipType {
        ONE_TO_ONE,
        ONE_TO_MANY,
        MANY_TO_ONE,
        MANY_TO_MANY
    }
}