package model;

import java.util.Objects;

public class UseCase {
    /**
     * Serves as the primary key for the 'use_cases' table, uniquely identifying each use case.
     * It aids in maintaining the integrity of use case data, enabling efficient indexing and retrieval
     * of specific operational scenarios.
     */
    private int id;

    /**
     * Describes the operational scenario or purpose of the use case in detail.
     * This attribute is crucial for understanding the scope and objective of each use case,
     * facilitating accurate association with manufacturing facilities and enabling tailored
     * operational planning.
     */
    private String useCaseDescription;

    /**
     * Indicates the type of manufacturing facility associated with the use case.
     * Using the 'facility_types' enum helps in categorizing use cases based on operational
     * functions (e.g., 'research_and_development', 'production').
     * It's essential for targeted operational planning and compliance management, making it easier
     * to apply relevant use cases to facilities with matching functions.
     */
    private FacilityType facilityType;

    /**
     * Reflects the compliance status relevant to the use case.
     * Associating use cases with specific 'compliance_status_options' can help in identifying
     * operational scenarios that are compliant with regulatory standards, aiding in compliance
     * tracking and improvement plans.
     */
    private ComplianceStatusOption complianceStatus;

    /**
     * Describes the current status of the use case, such as 'active' or 'inactive',
     * providing a quick reference to whether the use case is currently applicable.
     * This parameter is essential for managing the lifecycle of use cases, supporting effective
     * and timely decision-making regarding their activation or deactivation in response
     * to evolving operational needs or compliance requirements.
     */
    private StatusOption status;

    /**
     * Links each use case to a specific manufacturing facility, enabling tracking and customization
     * of operational scenarios for individual facilities.
     */
    private ManufacturingFacility facility;

    // Enum for FacilityType
    public enum FacilityType {
        RESEARCH_AND_DEVELOPMENT, PRODUCTION, WAREHOUSE, QUALITY_CONTROL, PACKAGING
    }

    // Enum for ComplianceStatusOption
    public enum ComplianceStatusOption {
        FDA_APPROVED, GMP_CERTIFIED, ISO_CERTIFIED, EHS_COMPLIANT, NOT_COMPLIANT
    }

    // Enum for StatusOption
    public enum StatusOption {
        ACTIVE, INACTIVE
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

    public FacilityType getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(FacilityType facilityType) {
        this.facilityType = facilityType;
    }

    public ComplianceStatusOption getComplianceStatus() {
        return complianceStatus;
    }

    public void setComplianceStatus(ComplianceStatusOption complianceStatus) {
        this.complianceStatus = complianceStatus;
    }

    public StatusOption getStatus() {
        return status;
    }

    public void setStatus(StatusOption status) {
        this.status = status;
    }

    public ManufacturingFacility getFacility() {
        return facility;
    }

    public void setFacility(ManufacturingFacility facility) {
        this.facility = facility;
    }

    // toString
    @Override
    public String toString() {
        return "UseCase{" +
                "id=" + id +
                ", useCaseDescription='" + useCaseDescription + '\'' +
                ", facilityType=" + facilityType +
                ", complianceStatus=" + complianceStatus +
                ", status=" + status +
                ", facility=" + facility +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UseCase)) return false;
        UseCase useCase = (UseCase) o;
        return getId() == useCase.getId() && Objects.equals(getUseCaseDescription(), useCase.getUseCaseDescription()) && getFacilityType() == useCase.getFacilityType() && getComplianceStatus() == useCase.getComplianceStatus() && getStatus() == useCase.getStatus() && Objects.equals(getFacility(), useCase.getFacility());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUseCaseDescription(), getFacilityType(), getComplianceStatus(), getStatus(), getFacility());
    }
}