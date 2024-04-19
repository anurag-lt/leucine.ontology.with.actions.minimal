package model;

import java.util.Date;

/**
 * Represents the documents associated with a manufacturing facility, detailing regulatory compliance, operational guidelines, and other vital documentation.
 */
public class FacilityDocument {

    /**
     * The unique identifier for the Facility Document.
     */
    private int id;

    /**
     * Describes the category or type of document, vital for compliance and operational clarity.
     */
    private DocumentType documentType;

    /**
     * The path or URI where the document is physically or digitally stored.
     */
    private String documentPath;

    /**
     * The date the document was officially issued.
     */
    private Date issueDate;

    /**
     * The expiration date of the document, post which it may no longer be valid.
     */
    private Date expiryDate;

    /**
     * A descriptive title or name for the document.
     */
    private String documentName;

    /**
     * The manufacturing facility to which this document is linked.
     */
    private ManufacturingFacility facility;

    // Enum for document types
    public enum DocumentType {
        FDA_APPROVAL, GMP_CERTIFICATE, ISO_CERTIFICATION, EHS_COMPLIANCE, OPERATIONAL_MANUAL, QUALITY_ASSURANCE_PROTOCOL, ENVIRONMENTAL_PERMIT
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public ManufacturingFacility getFacility() {
        return facility;
    }

    public void setFacility(ManufacturingFacility facility) {
        this.facility = facility;
    }

    // toString method
    @Override
    public String toString() {
        return "FacilityDocument{" +
                "id=" + id +
                ", documentType=" + documentType +
                ", documentPath='" + documentPath + '\'' +
                ", issueDate=" + issueDate +
                ", expiryDate=" + expiryDate +
                ", documentName='" + documentName + '\'' +
                ", facility=" + facility +
                '}';
    }
}