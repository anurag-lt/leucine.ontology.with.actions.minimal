package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.*;import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ActionDAO {

	
	
	/**
	 * Creates a new action record in the system. Used in the 'Object Type Creation' section for dynamically defining new actions related to object types.
	 *
	 * @param actionName The name of the action to be created.
	 * @param actionPurpose A description of what the action achieves and its operational context.
	 * @param isActive The status indicating if the action is currently active or inactive.
	 * @return boolean indicating if the action was successfully created.
	 */
	public boolean createAction(String actionName, String actionPurpose, Action.StatusOptions isActive) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean result = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "INSERT INTO actions (action_name, action_purpose, is_active) VALUES (?, ?, ?::status_options)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, actionName);
	        pstmt.setString(2, actionPurpose);
	        pstmt.setString(3, isActive.toString());
	
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            result = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { Logger.getLogger(this.getClass().getName()).log(Level.WARNING, null, e); }
	        DatabaseUtility.disconnect(conn);
	    }
	    return result;
	}
	
	
	
	/**
	 * Updates an existing action record in the database.
	 * This method is used in the 'Object Type Editing' section to modify details of existing actions associated with object types.
	 *
	 * @param id The ID of the action to update.
	 * @param actionName The new name of the action.
	 * @param actionPurpose The new description of what the action accomplishes.
	 * @param isActive The new status of the action, active or inactive.
	 * @return boolean Indicates whether the update was successful.
	 */
	public boolean updateAction(int id, String actionName, String actionPurpose, Action.StatusOptions isActive) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean updateSuccess = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "UPDATE actions SET action_name = ?, action_purpose = ?, is_active = ? WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, actionName);
	        pstmt.setString(2, actionPurpose);
	        pstmt.setString(3, isActive.name());
	        pstmt.setInt(4, id);
	
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            updateSuccess = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Failed to update action", e);
	    } finally {
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Failed to close PreparedStatement", e);
	            }
	        }
	        DatabaseUtility.disconnect(conn);
	    }
	    return updateSuccess;
	}
	
	/**
	 * Deletes an action from the database using its ID.
	 * @param id The ID of the action to be deleted.
	 * @return true if the action was successfully deleted, false otherwise.
	 */
	public boolean deleteAction(int id) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean isSuccess = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "DELETE FROM actions WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, id);
	
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            isSuccess = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error deleting action", e);
	    } finally {
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Failed to close PreparedStatement", e);
	            }
	        }
	        DatabaseUtility.disconnect(conn);
	    }
	    return isSuccess;
	}
	
	
	/**
	 * Retrieves detailed information of a specific action by its ID.
	 * Essential for editing or detailed viewing in the 'Object Management Dashboard'.
	 *
	 * @param id The ID of the action to retrieve.
	 * @return The action object retrieved, or null if no action found.
	 */
	public Action fetchActionById(int id) {
	    Connection connection = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    Action action = null;
	    try {
	        connection = DatabaseUtility.connect();
	        String query = "SELECT * FROM actions WHERE id = ?";
	        pstmt = connection.prepareStatement(query);
	        pstmt.setInt(1, id);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            action = new Action();
	            action.setId(rs.getInt("id"));
	            action.setActionName(rs.getString("action_name"));
	            action.setActionPurpose(rs.getString("action_purpose"));
	            action.setIsActive(Action.StatusOptions.valueOf(rs.getString("is_active").toLowerCase()));
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error fetching action by ID", e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e); }
	        if (rs != null) try { rs.close(); } catch (SQLException e) { Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e); }
	    }
	    return action;
	}
	
	
	/**
	 * Fetches all actions from the system, typically used for listing in dashboards or selection menus within the management panels.
	 * 
	 * @return List of all actions in the system.
	 */
	public List<Action> listAllActions() {
	    List<Action> actions = new ArrayList<>();
	    String sql = "SELECT id, action_name, action_purpose, is_active FROM actions";
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseUtility.connect();
	        stmt = conn.prepareStatement(sql);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	            Action action = new Action();
	            action.setId(rs.getInt("id"));
	            action.setActionName(rs.getString("action_name"));
	            action.setActionPurpose(rs.getString("action_purpose"));
	            action.setIsActive(Action.StatusOptions.valueOf(rs.getString("is_active")));
	            actions.add(action);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        try { if (rs != null) rs.close(); } catch (SQLException e) { Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e); }
	        try { if (stmt != null) stmt.close(); } catch (SQLException e) { Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e); }
	        DatabaseUtility.disconnect(conn);
	    }
	    return actions;
	}
}
