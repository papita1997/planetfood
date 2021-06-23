/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetfood.pojo;

/**
 *
 * @author pawan
 */
public class UserProfile {
    private static String username;
    private static String userType;
    private static String userid;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserProfile.username = username;
    }

    public static String getUserType() {
        return userType;
    }

    public static void setUserType(String userType) {
        UserProfile.userType = userType;
    }

    public static String getUserid() {
        return userid;
    }

    public static void setUserid(String userid) {
        UserProfile.userid = userid;
    }
    
    
}
