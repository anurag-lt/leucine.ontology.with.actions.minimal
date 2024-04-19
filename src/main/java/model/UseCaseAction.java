package model;

public class UseCaseAction {
    /**
     * Serves as the primary key for the 'use_case_actions' table. Each record gets a unique identifier to differentiate it from others. Essential for indexing and referencing specific actionable steps within each use case.
     */
    private int id;

    /**
     * Describes the specific action to be taken in a use case, providing clear guidance on operations to be executed. This descriptive field is crucial for operational clarity and supports detailed action planning at both a process and task level.
     */
    private String actionDescription;

    /**
     * This relation connects each suggested action with a specific use case, enabling tracking and implementation of operational procedures tailored to the needs of each use case. It helps in reporting by allowing analysis of actions planned or executed in the context of their relevant use cases.
     */
    private UseCase useCase;

    /**
     * Links each use case action to a defined action within the system, allowing for a clear understanding of what actions are associated with which use cases. This is essential for operational reporting and analysis, providing insights into which actions are most critical for facility operations.
     */
    private Action action;

    public UseCaseAction() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public UseCase getUseCase() {
        return useCase;
    }

    public void setUseCase(UseCase useCase) {
        this.useCase = useCase;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "UseCaseAction{" +
                "id=" + id +
                ", actionDescription='" + actionDescription + '\'' +
                ", useCase=" + (useCase != null ? useCase.getId() : "null") +
                ", action=" + (action != null ? action.getId() : "null") +
                '}';
    }
}