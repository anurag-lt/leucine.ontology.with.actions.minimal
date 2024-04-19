package model;

public class DashboardPreference {

    /**
     * A unique identifier for each dashboard preference record. This primary key facilitates easy identification, retrieval, and management of preference settings for individual users.
     */
    private int id;
    
    /**
     * This parameter will link the preference to a specific user. While it may have a foreign key-like aspect, this parameter is crucial for ensuring that dashboard preferences are personalized and retrievable on a per-user basis.
     */
    private int userId;  // Assuming the relation can be tracked by an integer ID for simplicity.
    
    /**
     * Identifies the type of preference being stored (e.g., layout, filter, etc.). This helps in categorizing preferences for quicker access and management within the system, providing insights into user behavior and system usage patterns.
     */
    private String preferenceType;
    
    /**
     * Stores the specific setting or value chosen by the user for the associated preference type. This could range from layout configurations to filter settings, essentially capturing user-selected options to customize their dashboard experience.
     */
    private String preferenceValue;

    // Constructor(s)
    public DashboardPreference() {}

    public DashboardPreference(int id, int user_id, String preferenceType, String preferenceValue) {
        this.id = id;
        this.userId = user_id;
        this.preferenceType = preferenceType;
        this.preferenceValue = preferenceValue;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getPreferenceType() {
        return preferenceType;
    }

    public String getPreferenceValue() {
        return preferenceValue;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPreferenceType(String preferenceType) {
        this.preferenceType = preferenceType;
    }

    public void setPreferenceValue(String preferenceValue) {
        this.preferenceValue = preferenceValue;
    }

    // toString
    @Override
    public String toString() {
        return "DashboardPreference{" +
                "id=" + id +
                ", userId=" + userId +
                ", preferenceType='" + preferenceType + '\'' +
                ", preferenceValue='" + preferenceValue + '\'' +
                '}';
    }
}