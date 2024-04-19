package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.*;

public class DashboardPreferenceDAO {

	
	
	/**
	 * Retrieves all dashboard preferences associated with a specific user.
	 * Used in sections where user-specific dashboard settings need to be fetched to set up the user interface according to stored preferences.
	 *
	 * @param userId The unique identifier of the user whose dashboard preferences are to be retrieved.
	 * @return List<DashboardPreference> A list of DashboardPreference objects containing the preferences of the specified user.
	 */
	public List<DashboardPreference> fetchDashboardPreferencesByUserId(int userId) {
	    List<DashboardPreference> preferences = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "SELECT * FROM dashboard_preferences WHERE user_id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, userId);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            DashboardPreference preference = new DashboardPreference();
	            preference.setId(rs.getInt("id"));
	            preference.setUserId(rs.getInt("user_id"));
	            preference.setPreferenceType(rs.getString("preference_type"));
	            preference.setPreferenceValue(rs.getString("preference_value"));
	            preferences.add(preference);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error fetching dashboard preferences by user ID", e);
	    } finally {
	        if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignored */ }
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { /* ignored */ }
	        DatabaseUtility.disconnect(conn);
	    }
	    return preferences;
	}
	
	/**
	     * Updates an existing dashboard preference record in the database. This method is used when a user modifies their dashboard settings in the UI, and these changes need to be persisted in the system.
	     * @param dashboardPreference The DashboardPreference object containing the updated values for a specific dashboard preference record, typically including user_id, preference_type, and preference_value.
	     * @return boolean True if the update was successful, false otherwise.
	     */
	    public boolean updateDashboardPreference(DashboardPreference dashboardPreference) {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        boolean updateSuccess = false;
	        try {
	            conn = DatabaseUtility.connect();
	            String sql = "UPDATE dashboard_preferences SET user_id=?, preference_type=?, preference_value=? WHERE id=?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, dashboardPreference.getUserId());
	            pstmt.setString(2, dashboardPreference.getPreferenceType());
	            pstmt.setString(3, dashboardPreference.getPreferenceValue());
	            pstmt.setInt(4, dashboardPreference.getId());
	            int affectedRows = pstmt.executeUpdate();
	            updateSuccess = affectedRows > 0;
	        } catch (SQLException e) {
	            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error updating dashboard preference", e);
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
	        return updateSuccess;
	    }
	
	
	/**
	 * Creates a new dashboard preference record in the database. Used in scenarios where a user sets up new preferences for their dashboard for the first time or adds additional preference settings.
	 *
	 * @param dashboardPreference The DashboardPreference object to be created and stored in the database, including user_id, preference_type, and preference_value.
	 */
	public void createDashboardPreference(DashboardPreference dashboardPreference) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "INSERT INTO dashboard_preferences (user_id, preference_type, preference_value) VALUES (?, ?, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, dashboardPreference.getUserId());
	        pstmt.setString(2, dashboardPreference.getPreferenceType());
	        pstmt.setString(3, dashboardPreference.getPreferenceValue());
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error when attempting to insert dashboard preference", e);
	    } finally {
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error when closing preparedStatement", e);
	            }
	        }
	        DatabaseUtility.disconnect(conn);
	    }
	}
	
	
	/**
	 * Deletes a specific dashboard preference record from the database.
	 * This method is used when a user decides to remove some of their stored dashboard preferences.
	 *
	 * @param dashboardPreferenceId The identifier of the dashboard preference record to be deleted.
	 * @return boolean indicating success or failure of the operation.
	 */
	public boolean deleteDashboardPreference(int dashboardPreferenceId) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    boolean success = false;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "DELETE FROM dashboard_preferences WHERE id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, dashboardPreferenceId);
	        int affectedRows = pstmt.executeUpdate();
	        success = affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error deleting dashboard preference", e);
	    } finally {
	        try {
	            if (pstmt != null) {
	                pstmt.close();
	            }
	            DatabaseUtility.disconnect(conn);
	        } catch (SQLException ex) {
	            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error closing resources", ex);
	        }
	    }
	    return success;
	}
	
}
