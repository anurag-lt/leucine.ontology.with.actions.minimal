package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.*;

public class UseCaseActionDAO {

	
	
	/**
	 * Inserts a new UseCaseAction into the database.
	 * @param actionDescription Description of the action to be performed as part of a use case.
	 * @param useCase The use case object to which the action is associated.
	 * @param action The action object this use case action is linking to.
	 * @return boolean True if the insertion is successful, false otherwise.
	 */
	public boolean createUseCaseAction(String actionDescription, UseCase useCase, Action action) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean success = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "INSERT INTO use_case_actions (action_description, fk_use_case_id, fk_action_id) VALUES (?, ?, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, actionDescription);
	        pstmt.setInt(2, useCase.getId());
	        pstmt.setInt(3, action.getId());
	
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            success = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error inserting UseCaseAction", e);
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error closing PreparedStatement", e);
	            }
	        }
	    }
	    return success;
	}
	
	
	/**
	 * Updates the details of an existing UseCaseAction in the database.
	 * @param id The identifier of the UseCaseAction to update.
	 * @param actionDescription An updated description of the action for a particular use case.
	 * @return boolean True if the update was successful, False otherwise.
	 */
	public boolean updateUseCaseAction(int id, String actionDescription) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean updateSuccess = false;
	    String sql = "UPDATE use_case_actions SET action_description = ? WHERE id = ?;";
	    try {
	        conn = DatabaseUtility.connect();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, actionDescription);
	        pstmt.setInt(2, id);
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            updateSuccess = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error updating UseCaseAction", e);
	    } finally {
	        try {
	            if (pstmt != null) {
	                pstmt.close();
	            }
	        } catch (SQLException e) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error closing PreparedStatement", e);
	        }
	        DatabaseUtility.disconnect(conn);
	    }
	    return updateSuccess;
	}
	
	/**
	 * Fetches all the UseCaseActions associated with a given use case ID from the database.
	 * This method queries the database for all actions linked to a particular use case,
	 * which is used in the 'Use Case Association: Submit Use Case' to display relevant actions.
	 *
	 * @param useCaseId The unique identifier of the use case for which actions are being retrieved.
	 * @return A list of UseCaseAction objects associated with the given use case ID.
	 */
	public List<UseCaseAction> fetchUseCaseActionByCaseId(int useCaseId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<UseCaseAction> actions = new ArrayList<>();
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "SELECT * FROM use_case_actions WHERE fk_use_case_id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, useCaseId);
	        rs = pstmt.executeQuery();
	
	        while (rs.next()) {
	            UseCaseAction action = new UseCaseAction();
	            action.setId(rs.getInt("id"));
	            action.setActionDescription(rs.getString("action_description"));
	            // Assuming UseCase and Action are initialized elsewhere and set here accordingly
	            // action.setUseCase(useCase);
	            // action.setAction(actionObj);
	            actions.add(action);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            DatabaseUtility.disconnect(conn);
	        } catch (SQLException e) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        }
	    }
	    return actions;
	}
	
	/**
	 * Deletes a UseCaseAction from the database based on its ID.
	 * 
	 * @param id The unique identifier of the UseCaseAction to be deleted.
	 * @return true if the deletion was successful, false otherwise.
	 */
	public boolean deleteUseCaseAction(int id) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean isDeleted = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "DELETE FROM use_case_actions WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, id);
	
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            isDeleted = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to delete use case action", e);
	    } finally {
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to close PreparedStatement", e);
	            }
	        }
	        DatabaseUtility.disconnect(conn);
	    }
	    return isDeleted;
	}
	
}
