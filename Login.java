package quickchatt;

import java.util.regex.Pattern;

public class Login {
    // Stores user details
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String cellPhoneNumber;

    // Stores whether login was successful
    private boolean loginStatus;

    // Constructor
    public Login() {
        firstName = "";
        lastName = "";
        userName = "";
        password = "";
        cellPhoneNumber = "";
        loginStatus = false;
    }

    // Saves the user's first name
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Saves the user's last name
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Saves the username
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Saves the password
    public void setPassword(String password) {
        this.password = password;
    }

    // Saves the cell phone number
    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // Checks username rules
    public boolean checkUserName() {
        return userName.contains("_") && userName.length() <= 5;
    }

    // Checks password complexity rules
    public boolean checkPasswordComplexity() {
        if (password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (Character.isUpperCase(ch)) {
                hasCapital = true;
            }

            if (Character.isDigit(ch)) {
                hasNumber = true;
            }

            if (!Character.isLetterOrDigit(ch)) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    // Checks South African phone number format
    public boolean checkCellPhoneNumber() {
        String regex = "^\\+27\\d{9}$";
        return Pattern.matches(regex, cellPhoneNumber);
    }

    // Returns username validation message
    public String getUserNameMessage() {
        if (checkUserName()) {
            return "Username successfully captured.";
        } else {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
    }

    // Returns password validation message
    public String getPasswordMessage() {
        if (checkPasswordComplexity()) {
            return "Password successfully captured.";
        } else {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
    }

    // Returns phone validation message
    public String getCellPhoneMessage() {
        if (checkCellPhoneNumber()) {
            return "Cell phone number successfully added.";
        } else {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
    }

    // Checks full registration
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        return "User has been registered successfully.";
    }

    // Checks login details
    public boolean loginUser(String enteredUserName, String enteredPassword) {
        loginStatus = enteredUserName.equals(userName) && enteredPassword.equals(password);
        return loginStatus;
    }

    // Returns login status message
    public String returnLoginStatus() {
        if (loginStatus) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
