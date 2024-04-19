package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.*;import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UseCaseDAO {

	
	
	/**
	 * Retrieves a single UseCase instance from the database using the specified use case ID.
	 * This method is essential for providing detailed views and for editing specific use cases
	 * directly from the list views where use cases are managed or overviewed.
	 *
	 * @param id The unique identifier for the use case to be retrieved.
	 * @return The UseCase object retrieved from the database, or null if no such use case is found.
	 */
	public UseCase fetchUseCaseById(int id) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    UseCase useCase = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "SELECT * FROM use_cases WHERE id = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, id);
	        rs = stmt.executeQuery();
	        if (rs.next()) {
	            useCase = new UseCase();
	            useCase.setId(rs.getInt("id"));
	            useCase.setUseCaseDescription(rs.getString("use_case_description"));
	            useCase.setFacilityType(UseCase.FacilityType.valueOf(rs.getString("facility_types")));
	            useCase.setComplianceStatus(UseCase.ComplianceStatusOption.valueOf(rs.getString("compliance_status_options")));
	            useCase.setStatus(UseCase.StatusOption.valueOf(rs.getString("status_options")));
	            // Assuming ManufacturingFacility is a class with a suitable constructor or setters
	            ManufacturingFacility facility = new ManufacturingFacility();
	            facility.setId(rs.getInt("fk_facility_id"));
	            useCase.setFacility(facility);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (stmt != null) { try { stmt.close(); } catch (SQLException e) { Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e); } }
	        if (rs != null) { try { rs.close(); } catch (SQLException e) { Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e); } }
	    }
	    return useCase;
	}
	
	
	/**
	 * Fetches a list of UseCase instances from the database, supporting pagination. This method is used primarily to fill data tables that list use cases,
	 * allowing administrators to manage use cases effectively.
	 *
	 * @param limit Maximum number of use cases to retrieve.
	 * @param offset Number of use cases to skip before starting to collect the result set.
	 * @return List<UseCase> List of UseCase instances.
	 */
	public List<UseCase> fetchAllUseCases(int limit, int offset) {
	    List<UseCase> useCases = new ArrayList<>();
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    String query = "SELECT * FROM use_cases LIMIT ? OFFSET ?";
	    try {
	        connection = DatabaseUtility.connect();
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, limit);
	        preparedStatement.setInt(2, offset);
	        resultSet = preparedStatement.executeQuery();
	
	        while (resultSet.next()) {
	            UseCase useCase = new UseCase();
	            useCase.setId(resultSet.getInt("id"));
	            useCase.setUseCaseDescription(resultSet.getString("use_case_description"));
	            useCase.setFacilityType(UseCase.FacilityType.valueOf(resultSet.getString("facility_types")));
	            useCase.setComplianceStatus(UseCase.ComplianceStatusOption.valueOf(resultSet.getString("compliance_status_options")));
	            useCase.setStatus(UseCase.StatusOption.valueOf(resultSet.getString("status_options")));
	            // Assume a method to fetch ManufacturingFacility using its ID
	            // useCase.setFacility(fetchFacilityById(resultSet.getInt("fk_facility_id")));
	
	            useCases.add(useCase);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        try {
	            if (resultSet != null) resultSet.close();
	            if (preparedStatement != null) preparedStatement.close();
	            DatabaseUtility.disconnect(connection);
	        } catch (SQLException e) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        }
	    }
	    return useCases;
	}
	
	/**
	 * Updates an existing use case in the database with the values provided in the UseCase object.
	 * This operation is used in contexts where use cases are being modified, ensuring that all
	 * relevant and associated data is kept current.
	 *
	 * @param useCase The UseCase object containing updated values.
	 * @return boolean indicating whether the update was successful.
	 */
	public boolean updateUseCase(UseCase useCase) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean updateSuccess = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "UPDATE use_cases SET use_case_description = ?, facility_types = ?, compliance_status_options = ?, status_options = ? WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, useCase.getUseCaseDescription());
	        pstmt.setString(2, useCase.getFacilityType().name());
	        pstmt.setString(3, useCase.getComplianceStatus().name());
	        pstmt.setString(4, useCase.getStatus().name());
	        pstmt.setInt(5, useCase.getId());
	
	        int result = pstmt.executeUpdate();
	        if (result > 0) {
	            updateSuccess = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error updating use case", e);
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	        } catch (SQLException e) {
	            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Failed to close PreparedStatement", e);
	        }
	        DatabaseUtility.disconnect(conn);
	    }
	    return updateSuccess;
	}
	
	
	/**
	 * Deletes a specific UseCase from the database using the given ID.
	 * This method is crucial for managing the lifecycle of use cases,
	 * allowing for their removal when they are no longer applicable or necessary.
	 *
	 * @param id The ID of the use case to delete.
	 * @return boolean True if the deletion was successful, false otherwise.
	 */
	public boolean deleteUseCaseById(int id) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean success = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "DELETE FROM use_cases WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, id);
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            success = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error deleting use case with ID " + id, e);
	    } finally {
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignored */ }
	        DatabaseUtility.disconnect(conn);
	    }
	    return success;
	}
	
	
	/**
	 * Inserts a new UseCase record into the database based on the provided UseCase object.
	 * This method is used when new use cases are defined and need to be recorded for operational management and planning.
	 *
	 * @param useCase The UseCase object to be inserted into the database.
	 * @return boolean indicating if the insertion was successful.
	 */
	public boolean createUseCase(UseCase useCase) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean success = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "INSERT INTO use_cases (id, use_case_description, facility_types, compliance_status_options, status_options, fk_facility_id) VALUES (?, ?, ?, ?, ?, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, useCase.getId());
	        pstmt.setString(2, useCase.getUseCaseDescription());
	        pstmt.setString(3, useCase.getFacilityType().name());
	        pstmt.setString(4, useCase.getComplianceStatus().name());
	        pstmt.setString(5, useCase.getStatus().name());
	        pstmt.setInt(6, useCase.getFacility().getId());
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            success = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to insert use case", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to close prepared statement", e);
	            }
	        }
	    }
	    return success;
	}
}
