package model;

import java.util.Objects;

/**
 * Represents a company with various attributes detailing its information.
 */
public class Company {
    
    /**
     * This parameter serves as the unique identifier for each company within the database, facilitating efficient data retrieval and management.
     */
    private int id;
    
    /**
     * The name of the company is a fundamental attribute that helps in identifying the entity across the system.
     */
    private String companyName;
    
    /**
     * Categorizing companies by sector (e.g., pharma, biotech) aids in sector-specific analysis and reporting.
     */
    private CompanySector companySectors;
    
    /**
     * This parameter indicates the size of the company, which could influence strategic decisions, resource allocation, and regulatory compliance obligations.
     */
    private int companySize;
    
    /**
     * Recording the headquarters address provides a physical location for the company, essential for compliance, contact, and geographical analysis.
     */
    private String headquartersAddress;
    
    /**
     * The primary contact person's name is crucial for communication, troubleshooting, and relationship management.
     */
    private String contactName;
    
    /**
     * The email address facilitates electronic communication, which is essential for operational correspondence, digital marketing, and direct contact strategies.
     */
    private String email;
    
    /**
     * The phone number is a critical attribute for immediate communication needs, verifying information, and facilitating rapid response for business operations.
     */
    private String phone;

    // Getters and Setters
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public CompanySector getCompanySectors() {
        return companySectors;
    }

    public void setCompanySectors(CompanySector companySectors) {
        this.companySectors = companySectors;
    }

    public int getCompanySize() {
        return companySize;
    }

    public void setCompanySize(int companySize) {
        this.companySize = companySize;
    }

    public String getHeadquartersAddress() {
        return headquartersAddress;
    }

    public void setHeadquartersAddress(String headquartersAddress) {
        this.headquartersAddress = headquartersAddress;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // toString method
    
    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", companySectors=" + companySectors +
                ", companySize=" + companySize +
                ", headquartersAddress='" + headquartersAddress + '\'' +
                ", contactName='" + contactName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    // CompanySector enum    
    public enum CompanySector {
        PHARMA, BIOTECH, HEALTHCARE, RESEARCH_AND_DEVELOPMENT, MANUFACTURING
    }
    
    // equals and hashCode based on `id` field for proper functioning in collections
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return getId() == company.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}