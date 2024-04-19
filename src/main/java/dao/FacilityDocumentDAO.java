package dao;


import model.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DatabaseUtility;import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Statement;



public class FacilityDocumentDAO {

	
	/**
	 * Creates a new FacilityDocument in the database.
	 * @param documentType The type of the document being created.
	 * @param documentPath The path or URI where the document is stored.
	 * @param issueDate The issuance date of the document.
	 * @param expiryDate The expiry date of the document.
	 * @param documentName A descriptive name for the document.
	 * @param facilityId The identifier of the manufacturing facility related to the document.
	 * @return boolean Indicates if the document creation was successful.
	 */
	public boolean createFacilityDocument(FacilityDocument.DocumentType documentType, String documentPath, Date issueDate, Date expiryDate, String documentName, int facilityId) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    try {
	        connection = DatabaseUtility.connect();
	        String sql = "INSERT INTO facility_documents (document_type, document_path, issue_date, expiry_date, document_name, fk_facility_id) VALUES (?, ?, ?, ?, ?, ?)";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, documentType.name());
	        preparedStatement.setString(2, documentPath);
	        preparedStatement.setDate(3, issueDate);
	        preparedStatement.setDate(4, expiryDate);
	        preparedStatement.setString(5, documentName);
	        preparedStatement.setInt(6, facilityId);
	        int result = preparedStatement.executeUpdate();
	        return result > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	            }
	        }
	    }
	}
	
	
	/**
	 * Fetches all documents associated with a specific manufacturing facility.
	 * @param facilityId The identifier of the manufacturing facility for which to retrieve documents.
	 * @return A list of FacilityDocument objects.
	 */
	public List<FacilityDocument> fetchFacilityDocuments(int facilityId) {
	    List<FacilityDocument> documents = new ArrayList<>();
	    String query = "SELECT id, document_type, document_path, issue_date, expiry_date, document_name FROM facility_documents WHERE fk_facility_id = ?";
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    try {
	        connection = DatabaseUtility.connect();
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, facilityId);
	        resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	            FacilityDocument document = new FacilityDocument();
	            document.setId(resultSet.getInt("id"));
	            document.setDocumentType(FacilityDocument.DocumentType.valueOf(resultSet.getString("document_type")));
	            document.setDocumentPath(resultSet.getString("document_path"));
	            document.setIssueDate(resultSet.getDate("issue_date"));
	            document.setExpiryDate(resultSet.getDate("expiry_date"));
	            document.setDocumentName(resultSet.getString("document_name"));
	            documents.add(document);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error fetching facility documents", e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error closing prepared statement", e);
	            }
	        }
	        if (resultSet != null) {
	            try {
	                resultSet.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error closing result set", e);
	            }
	        }
	    }
	    return documents;
	}
	
	/**
	 * Updates an existing document's details in the 'facility_documents' table.
	 * @param documentId The identifier of the document to be updated.
	 * @param documentType Updated document type such as FDA approval, ISO certification, etc.
	 * @param documentPath Updated file path or URI of the stored document.
	 * @param issueDate Updated issue date of the document.
	 * @param expiryDate Updated expiry date of the document.
	 * @param documentName Updated name of the document.
	 * @return boolean Indicates whether the update was successful.
	 */
	public boolean updateFacilityDocument(int documentId, FacilityDocument.DocumentType documentType, String documentPath, Date issueDate, Date expiryDate, String documentName) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "UPDATE facility_documents SET document_type = ?, document_path = ?, issue_date = ?, expiry_date = ?, document_name = ? WHERE id = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, documentType.name());
	        stmt.setString(2, documentPath);
	        stmt.setDate(3, issueDate);
	        stmt.setDate(4, expiryDate);
	        stmt.setString(5, documentName);
	        stmt.setInt(6, documentId);
	
	        int rowsUpdated = stmt.executeUpdate();
	        return rowsUpdated > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error updating facility document", e);
	        return false;
	    } finally {
	        if (stmt != null) {
	            try {
	                stmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error closing statement", e);
	            }
	        }
	        DatabaseUtility.disconnect(conn);
	    }
	}
	
	/**
	 * Deletes a facility document from the database based on the document ID.
	 * @param documentId The identifier of the document to be deleted.
	 * @return boolean indicating success or failure of the deletion.
	 */
	public boolean deleteFacilityDocument(int documentId) {
	    String sql = "DELETE FROM facility_documents WHERE id = ?";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean isSuccess = false;
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, documentId);
	        int affectedRows = pstmt.executeUpdate();
	        isSuccess = affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to delete facility document", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error closing PreparedStatement", e);
	            }
	        }
	    }
	    return isSuccess;
	}
}
