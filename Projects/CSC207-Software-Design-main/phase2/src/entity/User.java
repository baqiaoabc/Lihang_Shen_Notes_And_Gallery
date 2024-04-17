package entity;

/**
 * The User class
 */
public abstract class User implements java.io.Serializable{
    private final int uid;
    private String username;
    private String password;

    /**
     * Constructor for the user object
     *
     * @param uid the user id
     * @param password the password
     * @param name the user name
     */
    public User(int uid, String password, String name){
        this.uid = uid;
        this.username = name;
        this.password = password;
    }

    /**
     * A setter for the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * A getter for the user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return uid;
    }

    /**
     * A getter for the user name
     *
     * @return the user name
     */
    public String getUserName() {
        return username;
    }

    /**
     * A getter for the password
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}