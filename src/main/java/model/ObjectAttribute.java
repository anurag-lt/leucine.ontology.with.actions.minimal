package model;

public class ObjectAttribute {

    /**
     * The primary key of the object_attributes table. It uniquely identifies each attribute associated with custom object types, facilitating easy reference, management, and organization of attributes within the system.
     */
    private int id;

    /**
     * Stores the name of the attribute for object types, serving as a human-readable identifier that allows developers and system administrators to recognize and understand the purpose of each attribute in the context of its object type. This is crucial for ensuring clear documentation and ease of use when managing custom object attributes.
     */
    private String attributeName;

    /**
     * Indicates the data type of the attribute as defined by the 'data_types' enumeration. Specifying the data type is essential for data integrity, validation, and ensuring that attributes are stored in an optimal format for both processing efficiency and retrieval accuracy. This enum-supported parameter helps in enforcing consistency across attribute definitions.
     */
    private DataTypes dataType;

    /**
     * A flag indicating whether an attribute is mandatory for its associated object type. This information is critical for input validation and ensuring data completeness, aiding in maintaining the integrity of the stored data. Understanding whether an attribute is optional or required is vital for process-level decision-making and data modeling.
     */
    private boolean isMandatory;

    /**
     * This parameter optionally holds the default value for an attribute, providing a predefined data piece that can be automatically applied when no other value is specified. Including default values is helpful in streamlining data entry processes and ensuring consistent data quality, especially in task-level operations.
     */
    private String defaultValue;

    /**
     * Contains a description of the attribute, explaining its purpose, relevance, and how it should be used within the context of its object type. This information aids in documentation, improving the understanding of each attribute's role and how it contributes to the object type's data model. It's valuable for both system developers and users by providing clarity and promoting data model transparency.
     */
    private String description;

    /**
     * Specifies the maximum length of the attribute value, particularly relevant for string-type attributes. This ensures that data stored in the database adheres to expected formats and sizes, enhancing data integrity and helping in the optimization of storage space. Setting attribute length is also important for validation and can affect performance optimally for processing and retrieval.
     */
    private int attributeLength;

    /**
     * Indicates the specific object type each attribute belongs to, enabling structured data definitions and facilitating efficient data operations and reporting by grouping attributes under their respective object types.
     */
    private ObjectType objectType;

    public ObjectAttribute() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public DataTypes getDataType() {
        return dataType;
    }

    public void setDataType(DataTypes dataType) {
        this.dataType = dataType;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAttributeLength() {
        return attributeLength;
    }

    public void setAttributeLength(int attributeLength) {
        this.attributeLength = attributeLength;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    @Override
    public String toString() {
        return "ObjectAttribute{" +
               "id=" + id +
               ", attributeName='" + attributeName + '\'' +
               ", dataType=" + dataType +
               ", isMandatory=" + isMandatory +
               ", defaultValue='" + defaultValue + '\'' +
               ", description='" + description + '\'' +
               ", attributeLength=" + attributeLength +
               ", objectType=" + objectType +
               '}';
    }

    public enum DataTypes {
        INTEGER, FLOAT, VARCHAR, TEXT, DATE, DATETIME, ENUM
    }
}