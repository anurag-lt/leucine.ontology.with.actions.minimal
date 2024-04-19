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
import utils.DatabaseUtility;import java.sql.Statement;
import java.sql.Types;



public class ManufacturingFacilityDAO {

	
	/**
	 * Creates a new manufacturing facility in the database.
	 * @param companyName The name of the company to which the facility belongs.
	 * @param facilityName The name of the manufacturing facility.
	 * @param address Geographical location of the manufacturing facility.
	 * @param gpsCoordinates GPS coordinates of the facility's location.
	 * @param capacity Production capacity of the manufacturing facility.
	 * @param facilityType Type of the manufacturing facility based on primary operation.
	 * @param complianceStatus Regulatory compliance status of the manufacturing facility.
	 * @return The generated facility's ID if creation is successful, -1 otherwise.
	 */
	public int createFacility(String companyName, String facilityName, String address, String gpsCoordinates, BigDecimal capacity, ManufacturingFacility.FacilityType facilityType, ManufacturingFacility.ComplianceStatus complianceStatus) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet generatedKeys = null;
	    int facilityId = -1;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "INSERT INTO manufacturing_facilities (name, location, capacity, facility_type, compliance_status, gps_coordinates) VALUES (?, ?, ?, ?, ?, ?)";
	        pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        pstmt.setString(1, facilityName);
	        pstmt.setString(2, address);
	        pstmt.setBigDecimal(3, capacity);
	        pstmt.setString(4, facilityType.name());
	        pstmt.setString(5, complianceStatus.name());
	        pstmt.setString(6, gpsCoordinates);
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Creating facility failed, no rows affected.");
	        }
	        generatedKeys = pstmt.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            facilityId = generatedKeys.getInt(1);
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(ManufacturingFacilityDAO.class.getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        if (generatedKeys != null) try { generatedKeys.close(); } catch (SQLException logOrIgnore) {}
	        if (pstmt != null) try { pstmt.close(); } catch (SQLException logOrIgnore) {}
	        DatabaseUtility.disconnect(conn);
	    }
	    return facilityId;
	}
	
	/**
	 * Updates the details of an existing manufacturing facility in the database.
	 * @param facilityId Unique identifier for the manufacturing facility.
	 * @param companyName Name of the company to which the facility belongs.
	 * @param facilityName Name of the manufacturing facility.
	 * @param address Geographical location of the manufacturing facility.
	 * @param gpsCoordinates GPS coordinates of the facility's location.
	 * @param capacity Production capacity of the manufacturing facility.
	 * @param facilityType Type based on its primary operation.
	 * @param complianceStatus Regulatory compliance status of the manufacturing facility.
	 * @return boolean Return true if the update is successful, otherwise false.
	 */
	public boolean updateFacility(int facilityId, String companyName, String facilityName, String address, String gpsCoordinates, BigDecimal capacity, ManufacturingFacility.FacilityType facilityType, ManufacturingFacility.ComplianceStatus complianceStatus) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    try {
	        conn = DatabaseUtility.connect();
	        String sql = "UPDATE manufacturing_facilities SET company_name = ?, facility_name = ?, address = ?, gps_coordinates = ?, capacity = ?, facility_type = ?, compliance_status = ? WHERE id = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, companyName);
	        stmt.setString(2, facilityName);
	        stmt.setString(3, address);
	        stmt.setString(4, gpsCoordinates);
	        stmt.setBigDecimal(5, capacity);
	        stmt.setString(6, facilityType.name());
	        stmt.setString(7, complianceStatus.name());
	        stmt.setInt(8, facilityId);
	        int affectedRows = stmt.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(ManufacturingFacilityDAO.class.getName()).log(Level.SEVERE, null, e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(conn);
	        if (stmt != null) {
	            try {
	                stmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(ManufacturingFacilityDAO.class.getName()).log(Level.SEVERE, null, e);
	            }
	        }
	    }
	}
}
