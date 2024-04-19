package model;

import java.util.List;

/**
 * Represents an action within the system, capturing both the definition and purpose of a system operation.
 */
public class Action {

    private int id;
    private String actionName;
    private String actionPurpose;
    private StatusOptions isActive; // Enum to represent active or inactive status
    private List<ActionObjectTypeAssociation> actionObjectTypeAssociations; // A list to hold multiple associations
    
    // Enums for the status options
    public enum StatusOptions {
        active, inactive
    }
    
    /**
     * Gets the unique identifier for this Action.
     * 
     * @return The id of this Action.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this Action.
     * 
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the Action.
     * 
     * @return The actionName of this Action.
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * Sets the name of the Action.
     * 
     * @param actionName The actionName to set.
     */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    /**
     * Gets the purpose of the Action as detailed text.
     * 
     * @return The actionPurpose of this Action.
     */
    public String getActionPurpose() {
        return actionPurpose;
    }

    /**
     * Sets the purpose of the Action.
     * 
     * @param actionPurpose The actionPurpose to set.
     */
    public void setActionPurpose(String actionPurpose) {
        this.actionPurpose = actionPurpose;
    }

    /**
     * Gets the activity status of this Action.
     * 
     * @return The isActive status of this Action.
     */
    public StatusOptions getIsActive() {
        return isActive;
    }

    /**
     * Sets the activity status of this Action.
     * 
     * @param isActive The isActive status to set.
     */
    public void setIsActive(StatusOptions isActive) {
        this.isActive = isActive;
    }

    /**
     * Gets the list of actionObjectTypeAssociations linked to this Action.
     *
     * @return The list of ActionObjectTypeAssociations.
     */
    public List<ActionObjectTypeAssociation> getActionObjectTypeAssociations() {
        return actionObjectTypeAssociations;
    }

    /**
     * Sets the list of actionObjectTypeAssociations linked to this Action.
     *
     * @param actionObjectTypeAssociations The list of ActionObjectTypeAssociations to set.
     */
    public void setActionObjectTypeAssociations(List<ActionObjectTypeAssociation> actionObjectTypeAssociations) {
        this.actionObjectTypeAssociations = actionObjectTypeAssociations;
    }
    
    @Override
    public String toString() {
        return "Action{" +
                "id=" + id +
                ", actionName='" + actionName + '\'' +
                ", actionPurpose='" + actionPurpose + '\'' +
                ", isActive=" + isActive +
                ", actionObjectTypeAssociations=" + actionObjectTypeAssociations +
                '}';
    } 
}