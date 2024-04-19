package model;

public class ActionsInputType {

    /**
     * Acts as a unique identifier for each record in the 'actions_input_types' table, ensuring each input parameter associated with an action is distinctly identifiable.
     */
    private Integer id;

    /**
     * Specifies the expected data type for action input parameters, ranging from integers, floats, text, to enums.
     */
    private DataType inputDataType;

    /**
     * Specifies which action this input type is associated with, enabling the system to validate the inputs for specific actions efficiently.
     */
    private Action action;

    public ActionsInputType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DataType getInputDataType() {
        return inputDataType;
    }

    public void setInputDataType(DataType inputDataType) {
        this.inputDataType = inputDataType;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "ActionsInputType{" +
                "id=" + id +
                ", inputDataType=" + inputDataType +
                ", action=" + action +
                '}';
    }

    public enum DataType {
        INTEGER, FLOAT, VARCHAR, TEXT, DATE, DATETIME, ENUM
    }
}