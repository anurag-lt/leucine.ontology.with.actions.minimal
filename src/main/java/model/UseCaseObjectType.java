package model;

import java.util.Date;
import java.sql.Timestamp;

/**
 * Represents the association between use cases and object types within the system, facilitating structured application of object management within different operational contexts.
 */
public class UseCaseObjectType {
    /**
     * The unique identifier for the association between a use case and object types.
     */
    private int id;

    /**
     * Provides a comprehensive explanation of the use case, supporting decision-making by clarifying the use case's relevance.
     */
    private String useCaseDescription;

    /**
     * Enum representing the custom object types suggested for association with a use case.
     */
    private RelevantObjectTypes relevantObjectTypes;

    /**
     * Describes how associated object types contribute to the functionality intended by the use case.
     */
    private String associatedFunctionality;

    /**
     * Categorizes the level of impact that the use case has on operational processes.
     */
    private ImpactLevel impactLevel;

    /**
     * Records the date when the association between a use case and object types was created.
     */
    private Date creationDate;

    /**
     * Indicates the most recent update to the record.
     */
    private Timestamp lastUpdated;

    /**
     * The object type involved in the association.
     */
    private ObjectType objectType;

    /**
     * The use case involved in the association.
     */
    private UseCase useCase;

    public UseCaseObjectType() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUseCaseDescription() {
        return useCaseDescription;
    }

    public void setUseCaseDescription(String useCaseDescription) {
        this.useCaseDescription = useCaseDescription;
    }

    public RelevantObjectTypes getRelevantObjectTypes() {
        return relevantObjectTypes;
    }

    public void setRelevantObjectTypes(RelevantObjectTypes relevantObjectTypes) {
        this.relevantObjectTypes = relevantObjectTypes;
    }

    public String getAssociatedFunctionality() {
        return associatedFunctionality;
    }

    public void setAssociatedFunctionality(String associatedFunctionality) {
        this.associatedFunctionality = associatedFunctionality;
    }

    public ImpactLevel getImpactLevel() {
        return impactLevel;
    }

    public void setImpactLevel(ImpactLevel impactLevel) {
        this.impactLevel = impactLevel;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public UseCase getUseCase() {
        return useCase;
    }

    public void setUseCase(UseCase useCase) {
        this.useCase = useCase;
    }

    // Enum for RelevantObjectTypes
    public enum RelevantObjectTypes {
        CUSTOMER, ORDER, PRODUCT, MANUFACTURING_FACILITY, COMPLIANCE_DOCUMENT, DASHBOARD_PREFERENCE, ACTION, INPUT_TYPE, ATTRIBUTE, FILTER, RELATIONSHIP, USE_CASE
    }

    // Enum for ImpactLevel
    public enum ImpactLevel {
        HIGH, MEDIUM, LOW
    }

    @Override
    public String toString() {
        return "UseCaseObjectType{" +
                "id=" + id +
                ", useCaseDescription='" + useCaseDescription + '\'' +
                ", relevantObjectTypes=" + relevantObjectTypes +
                ", associatedFunctionality='" + associatedFunctionality + '\'' +
                ", impactLevel=" + impactLevel +
                ", creationDate=" + creationDate +
                ", lastUpdated=" + lastUpdated +
                ", objectType=" + objectType +
                ", useCase=" + useCase +
                '}';
    }
}