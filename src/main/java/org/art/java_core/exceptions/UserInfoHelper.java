package org.art.java_core.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;

/**
 * User info helper class.
 */
public class UserInfoHelper {

    private static final Logger LOG = LogManager.getLogger(UserInfoHelper.class);

    public static final String OUTPUT_FILE_PATH = "C:\\Users\\ahlukhouski\\IdeaProjects\\JavaLabs\\JD_Tasks\\src\\main\\resources\\files\\exceptions\\user-info.txt";

    /**
     * Saves user info to a file.
     *
     * @throws UserInfoSavingException if some problems appear during the saving process
     */
    public static boolean saveUserInfo(User user) throws UserInfoSavingException {
        boolean isSaved = false;
        if (user != null) {
            //Using try with resources feature to avoid finally section with close invocation
            try (FileWriter out = new FileWriter(OUTPUT_FILE_PATH, true)) {
                StringBuilder sb = new StringBuilder();
                long userId = user.getUserId();
                String userFullName = user.getFullName().toUpperCase();     //NPE could appear here! (Is made on purpose)
                sb.append("User ID: ")
                        .append(userId)
                        .append(", ")
                        .append(" user full name: ")
                        .append(userFullName)
                        .append(".");
                char[] buffer = sb.toString().toCharArray();
                out.write(buffer);
                isSaved = true;
            } catch (Exception e) {
                LOG.error("Exception while user info saving, " + user.toString());
                throw new UserInfoSavingException("Exception while user info saving, " + user.toString(), e);
            }
        }
        return isSaved;
    }
}
