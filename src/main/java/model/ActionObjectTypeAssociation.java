package model;

/**
 * Represents an association between actions and object types, detailing how specific actions can be applied to various object types within the system.
 */
public class ActionObjectTypeAssociation {

    /**
     * Primary key for the action_object_type_associations table, uniquely identifying each association.
     */
    private int id;

    /**
     * A descriptive name for the association between action and object type.
     */
    private String associationName;

    /**
     * Defines the nature of the association between the action and the object type.
     */
    private String associationType;

    /**
     * Indicates whether the association is currently active and thus whether the action can be applied to the object type.
     */
    private boolean isActive;

    /**
     * This relation links the action object type association to its corresponding action.
     */
    private Action fkAction;

    /**
     * This establishes a connection from the association to the specific object types involved.
     */
    private ObjectType fkObjectType;

    public ActionObjectTypeAssociation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssociationName() {
        return associationName;
    }

    public void setAssociationName(String associationName) {
        this.associationName = associationName;
    }

    public String getAssociationType() {
        return associationType;
    }

    public void setAssociationType(String associationType) {
        this.associationType = associationType;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Action getFkAction() {
        return fkAction;
    }

    public void setFkAction(Action fkAction) {
        this.fkAction = fkAction;
    }

    public ObjectType getFkObjectType() {
        return fkObjectType;
    }

    public void setFkObjectType(ObjectType fkObjectType) {
        this.fkObjectType = fkObjectType;
    }

    @Override
    public String toString() {
        return "ActionObjectTypeAssociation{" +
                "id=" + id +
                ", associationName='" + associationName + '\'' +
                ", associationType='" + associationType + '\'' +
                ", isActive=" + isActive +
                ", fkAction=" + (fkAction != null ? fkAction.toString() : "null") +
                ", fkObjectType=" + (fkObjectType != null ? fkObjectType.toString() : "null") +
                '}';
    }
}