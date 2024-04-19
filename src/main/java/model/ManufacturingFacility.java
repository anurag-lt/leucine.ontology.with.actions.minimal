package model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * This class represents the manufacturing facilities within the system. It holds details about each facility, including its name, location, capacity, type, compliance status, associated company, and facility-specific documents.
 */
public class ManufacturingFacility {

    /**
     * Unique identifier for the Manufacturing Facility.
     */
    private int id;

    /**
     * The name of the manufacturing facility.
     */
    private String name;

    /**
     * Geographical location of the manufacturing facility.
     */
    private String location;

    /**
     * Production capacity of the manufacturing facility.
     */
    private BigDecimal capacity;

    /**
     * Type of the manufacturing facility.
     */
    private FacilityType facilityType;

    /**
     * Regulatory compliance status of the manufacturing facility.
     */
    private ComplianceStatus complianceStatus;

    /**
     * The company to which the manufacturing facility belongs.
     */
    private Company company;

    // Constructors, getters, setters, and toString() method

    public ManufacturingFacility() {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public FacilityType getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(FacilityType facilityType) {
        this.facilityType = facilityType;
    }

    public ComplianceStatus getComplianceStatus() {
        return complianceStatus;
    }

    public void setComplianceStatus(ComplianceStatus complianceStatus) {
        this.complianceStatus = complianceStatus;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManufacturingFacility)) return false;
        ManufacturingFacility that = (ManufacturingFacility) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(location, that.location) &&
                Objects.equals(capacity, that.capacity) &&
                facilityType == that.facilityType &&
                complianceStatus == that.complianceStatus &&
                Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, location, capacity, facilityType, complianceStatus, company);
    }

    @Override
    public String toString() {
        return "ManufacturingFacility{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", capacity=" + capacity +
                ", facilityType=" + facilityType +
                ", complianceStatus=" + complianceStatus +
                ", company=" + company +
                '}';
    }

    /**
     * Enum representing the type of the manufacturing facility.
     */
    public enum FacilityType {
        RESEARCH_AND_DEVELOPMENT, PRODUCTION, WAREHOUSE, QUALITY_CONTROL, PACKAGING
    }

    /**
     * Enum representing the compliance status of the manufacturing facility.
     */
    public enum ComplianceStatus {
        FDA_APPROVED, GMP_CERTIFIED, ISO_CERTIFIED, EHS_COMPLIANT, NOT_COMPLIANT
    }
}