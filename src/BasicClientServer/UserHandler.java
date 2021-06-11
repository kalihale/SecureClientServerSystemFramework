package BasicClientServer;

import ObjectsToPass.User;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// -- Download JavaMail API from here: http://www.oracle.com/technetwork/java/javamail/index.html
// -- Download JavaBeans Activation Framework from here: http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-java-plat-419418.html#jaf-1.1.1-fcs-oth-JPR
//    Activation Framework is only needed for earlier versions of Java
// -- Your gmail account must be set to allow "less secure apps" to access it
//    Information is found here: https://devanswers.co/allow-less-secure-apps-access-gmail-account/
// -- javax.mail.jar moved here: https://javaee.github.io/javamail/?xd_co_f=c9a74338-3e62-4b48-9e18-489fa570fd5b
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

public class UserHandler
{
    private String username;
    private String reply;

    public String process(User process)
    {
        this.username = process.getUsername();
        switch(process.getAction())
        {
            case 0:
                //  <@  Logout with stored procedure
                System.out.println("Logged out " + this.username);
                return "logoutSuccess";
            case 1:
                //  <@  Login with stored procedure
                System.out.println("Logged in " + this.username);
                return "loginSuccess";
            case 2:
                //  <@  Change password
                System.out.println("Changed password for " + this.username);
                return "passwordChangeSuccess";
            case 3:
                //  <@  Forgot password
                System.out.println("Forgot password " + process.getEmail());
                return "forgotPasswordSuccessful";
            default:
                break;
        }
        return "error";
    }

    public String process(String process)
    {
        System.out.print(process);
        switch (process.charAt(2))
        {
            case 'L' -> {
                System.out.println("UserHandler sending to UserHandler.login: " + process);
                reply = login(process);
                System.out.println("UserHandler login reply: " + reply);
                return reply;
            }
            case 'R' -> {
                System.out.println("UserHandler sending to UserHandler.registration: " + process);
                reply = userRegistration(process);
                System.out.println("UserHandler registration reply: " + reply);
                return reply;
            }
            case 'o' -> {
                System.out.println("UserHandler sending to UserHandler.logout: " + username);
                reply = logout(username);
                System.out.println("UserHandler logout reply: " + reply);
                return reply;
            }
            case 'p' -> {
                System.out.println("UserHandler sending to UserHandler.changePassword: " + process);
                reply = changePassword(process);
                return reply;
            }
            case 'f' -> {
                System.out.println("UserHandler sending to UserHandler.sendmail: " + process);
                reply = forgotPassword(process);
                return reply;
            }
        }
        return "blah";
    }
    private String login(String userinfo)
    {
        //  <@  We need to first split the string into username and password
        String[] info = userinfo.split("/");
        System.out.println("UserHandler login method");
        username = info[2];
        try{ String reply = UserDataBase.login(username, info[3]);
        System.out.println("UserDataBase reply to UserHandler: " + reply);
        return reply;}
       catch(Exception e){
           System.out.println("UserHandler exception thrown by UserDataBase in login");
           System.out.println(e);
           return "loginFailed";
        }
    }
    private String logout(String username)
    {
            return UserDataBase.logout(username);
    }
    private String userRegistration(String userinfo)
    {
        String[] arr = userinfo.split("/");
        //  <@  We need to split the string into username, password, and email
        if (verifyEmailFormat(arr[4])){
            if (verifyPassFormat(arr[3])) {
                return UserDataBase.registration(arr[2], arr[3], arr[4]);
            }
            else{
                return "Wrong password format";
            }
        }
        else{
            return "Wrong email format";
        }
    }
    private String changePassword(String userinfo)
    {
        String[] arr = userinfo.split("/");
        arr[2] = username;
        if(verifyPassFormat(arr[4]))
        {
                return UserDataBase.changePassword(arr[2], arr[3], arr[4]);
        }
        else
        {
            return"ChangePasswordFailure:WrongPasswordFormat";
        }
    }
    private boolean verifyEmailFormat(String email)
    {
        String emailregex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern emailpattern = Pattern.compile(emailregex);

        Matcher matcher = emailpattern.matcher(email);
        return matcher.find();
    }

    private boolean verifyPassFormat(String password)
    {
        String simplepasswordregex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";
        Pattern simplepasswordpattern = Pattern.compile(simplepasswordregex);

        Matcher matcher = simplepasswordpattern.matcher(password);
        return matcher.find();
    }
    private String forgotPassword(String process)
    {
        String[] info = process.split("/");
        String email = info[2];
        String newPassword = this.generateRandomPassword();
        if(UserDataBase.changePassword(email, newPassword) && sendMail(email, newPassword))
        {
            return "forgotPasswordSuccess";
        }
        else
        {
            return "forgotPasswordFailure";
        }
    }
    private boolean sendMail(String to, String newPassword)
    {
        // -- Configurations for the email connection to the Google SMTP server using TLS
        Properties props = new Properties();
        props.put("mail.smtp.host", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

//        // -- Create a session with required user details
//        //    this is basically logging into the gmail account
//        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("GooeySqwad", "ThisIsGooeySqwad");
//            }
//        });
//        try {
//            //-- create the Message to be sent
//            MimeMessage msg = new MimeMessage(session);
//
//            // -- get the internet addresses for the recipients
//            InternetAddress[] address = InternetAddress.parse(to, true);
//
//            // -- set the recipients
//            msg.setRecipients(Message.RecipientType.TO, address);
//
//            // -- set the subject line (time stamp)
//            Calendar cal = Calendar.getInstance();
//            String timeStamp = new SimpleDateFormat("yyyyMMdd_HH:mm:ss").format(cal.getTime());
//            msg.setSubject("Sample Mail : " + timeStamp);
//            msg.setSentDate(new Date());
//
//            // -- set the message text
//            msg.setText("Here is your new password: " + newPassword);
//            msg.setHeader("XPriority", "1");
//
//            // -- send the message
//            Transport.send(msg);
//
//            System.out.println("Mail has been sent successfully");
//        return true;
//        } catch (MessagingException e) {
//            System.out.println("Unable to send an email" + e);
        return false;
//        }
    }
    private String generateRandomPassword()
    {
        String newpass = "";
        for(int i = 0; i < 6; i++)
        {
            newpass = newpass.concat(String.valueOf((char)(65 + Math.random()*52)));
        }
        for(int i = 0; i < 2; i++)
        {
            newpass = newpass.concat(String.valueOf((int)(Math.random() * 10)));
        }
        return newpass;
    }
}
