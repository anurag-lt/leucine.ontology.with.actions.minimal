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

import model.UseCaseObjectType.RelevantObjectTypes;
import model.UseCaseObjectType.ImpactLevel;


public class UseCaseObjectTypeDAO {

	
	/**
	 * Creates a new record in the use_case_object_types table in the database.
	 * This method maps the data from the use case and object type association to the corresponding table structure.
	 * @param useCaseDescription The detailed description of the use case.
	 * @param relevantObjectTypes The enum of object types relevant to the use case.
	 * @param associatedFunctionality How the object types contribute to the intended functionality of the use case.
	 * @param impactLevel The impact level of the use case on operational processes.
	 * @param objectType The object type involved in the association.
	 * @param useCase The use case involved in the association.
	 * @return boolean true if the insert operation is successful, false otherwise.
	 */
	 public boolean createUseCaseObjectType(String useCaseDescription, UseCaseObjectType.RelevantObjectTypes relevantObjectTypes, String associatedFunctionality, UseCaseObjectType.ImpactLevel impactLevel, ObjectType objectType, UseCase useCase) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "INSERT INTO use_case_object_types (use_case_description, relevant_object_types, associated_functionality, impact_level, creation_date, last_updated, fk_object_type_id, fk_use_case_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, useCaseDescription);
	        pstmt.setString(2, relevantObjectTypes.name());
	        pstmt.setString(3, associatedFunctionality);
	        pstmt.setString(4, impactLevel.name());
	        pstmt.setDate(5, new Date(System.currentTimeMillis()));
	        pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
	        pstmt.setInt(7, objectType.getId());
	        pstmt.setInt(8, useCase.getId());
	
	        int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(UseCaseObjectTypeDAO.class.getName()).log(Level.SEVERE, null, e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(UseCaseObjectTypeDAO.class.getName()).log(Level.SEVERE, null, e);
	            }
	        }
	    }
	}
	
	
	/**
	 * Updates the details of an existing use case object type association in the database.
	 *
	 * @param id The ID of the use case object type association to update.
	 * @param useCaseDescription Updated detailed description of the use case.
	 * @param relevantObjectTypes Updated enum of object types relevant to the use case.
	 * @param associatedFunctionality Updated description of how the object types contribute to the intended functionality.
	 * @param impactLevel Updated level of impact of the use case on operational processes.
	 * @param objectType Updated object type involved in the association.
	 * @param useCase Updated use case involved in the association.
	 * @return boolean indicating if the update was successful.
	 */
	public boolean updateUseCaseObjectType(int id, String useCaseDescription, RelevantObjectTypes relevantObjectTypes, String associatedFunctionality, ImpactLevel impactLevel, ObjectType objectType, UseCase useCase) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "UPDATE use_case_object_types SET use_case_description = ?, relevant_object_types = ::relevant_object_types, associated_functionality = ?, impact_level = ::impact_level, fk_object_type_id = ?, fk_use_case_id = ? WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, useCaseDescription);
	        pstmt.setString(2, relevantObjectTypes.name());
	        pstmt.setString(3, associatedFunctionality);
	        pstmt.setString(4, impactLevel.name());
	        pstmt.setInt(5, objectType.getId());
	        pstmt.setInt(6, useCase.getId());
	        pstmt.setInt(7, id);
	
	        int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException ex) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        try {
	            if (pstmt != null) { pstmt.close(); }
	        } catch (SQLException ex) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	}
}
