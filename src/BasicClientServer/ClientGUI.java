package BasicClientServer;

import ObjectsToPass.QueriesClass;
import ObjectsToPass.User;
import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/*
<@  bt: button
    ta: Text Area
    txt: text or label
 */
public class ClientGUI extends Application
{
    private final int MINHEIGHT = 550;
    private final int MINWIDTH = MINHEIGHT;
    private final Background BG = new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY));
    private final String BTGRAY = "-fx-background-color: #cbccd1";
    private final String BTGREEN = "-fx-background-color: #92f095";
    private User user;
    private QueriesClass sendIt;
    private boolean usefulInfo = true; // ToDo FIX LATER != True
    private Client client;
    private String ip;
    private int port;

    @Override
    public void start(Stage primaryStage)
    {
        disconnected();
    }
    private void disconnected()
    {
        Stage disconnectedStage = new Stage();
        GridPane connectPane = new GridPane();
        connectPane.setBackground(BG);
        disconnectedStage.setHeight(200);
        disconnectedStage.setWidth(400);
        disconnectedStage.setResizable(false);

        // ** Set up text areas
        TextArea taIpAddress = new TextArea();
        taIpAddress.setMaxHeight(10);
        TextArea taPortNum = new TextArea();
        taPortNum.setMaxHeight(10);

        // ** Labels
        Label txtIpName = new Label("IP Address: ");
        Label txtPortName = new Label("Port #: ");

        GridPane.setHalignment(txtIpName, HPos.RIGHT);
        GridPane.setHalignment(txtPortName, HPos.RIGHT);
        // Set up Connect Button
        Button btConnect = new Button("Connect");
        btConnect.setStyle(BTGRAY);
        btConnect.setOnAction(actionEvent ->
        {
            ip = taIpAddress.getText();
            try
            {
                port = Integer.parseInt(taPortNum.getText());
                client = new Client(ip, port);
                login();
                disconnectedStage.close();
            }catch(NumberFormatException numberFormatException)
            {
                System.out.println("Could not parse int");
            }
            //  <@  TODO Add exception for when there is no ip and port to connect to ??????
            //  <@  Exception for ^ already exists in network access, TODO only if we have time after everything else
            taIpAddress.clear();
            taPortNum.clear();
        });

        connectPane.add(txtIpName,0,0);
        connectPane.add(txtPortName,0,1);
        connectPane.add(taIpAddress,1,0, 2, 1);
        connectPane.add(taPortNum,1,1, 2, 1);
        connectPane.add(btConnect,1,2);
        Scene scene = new Scene(connectPane, 400, 400);
        disconnectedStage.setTitle("Client");
        disconnectedStage.setScene(scene);
        disconnectedStage.show();


        // ** ColumnConstraints
        ColumnConstraints column0, column1;
        column0 = column1 = new ColumnConstraints();
        column0.setPercentWidth(25);
        column1.setPercentWidth(75);
        connectPane.getColumnConstraints().addAll(column0, column1);

        // ** Row constraints
        RowConstraints row0, row1, row2;
        row0 = row1 = row2 = new RowConstraints();
        row0.setPercentHeight(33);
        row1.setPercentHeight(33);
        row2.setPercentHeight(33);
        connectPane.getRowConstraints().addAll(row0,row1,row2);

        GridPane.setHalignment(btConnect, HPos.LEFT);
        GridPane.setValignment(btConnect, VPos.TOP);


    }
    private void login()
    {
        //  <@  Set up stage
        Stage loginStage = new Stage();
        loginStage.setMinHeight(MINHEIGHT);
        loginStage.setMinWidth(MINWIDTH);

        //  <@  Set panes
        GridPane loginWindowPane = new GridPane();
        loginWindowPane.setBackground(BG);
        Scene scene = new Scene(loginWindowPane);

        //  <@  Set up text areas
        TextArea taLogUn = new TextArea();
        taLogUn.setMaxHeight(10);
        TextArea taLogPw = new TextArea();
        taLogPw.setMaxHeight(10);

        //  <@  Set up buttons
        Button btDisconnect = new Button("Disconnect");
        Button btLogin = new Button("Login");
        Button btFPw = new Button("Forgot Password");
        Button btReg = new Button("Register");
        //  <@  Light green
        btLogin.setStyle(BTGREEN);
        //  <@  Light yellow
        btFPw.setStyle("-fx-background-color: #fff380");
        //  <@  Light red
        btReg.setStyle("-fx-background-color: #f09292");
        //  <@  Light gray
        btDisconnect.setStyle(BTGRAY);
        btDisconnect.setOnAction(ActionEvent ->
        {
            client.disconnect();
            disconnected();
            loginStage.close();
        });
        btLogin.setOnAction(actionEvent ->
        {
            if (!(taLogPw.getText().equals("")) && !(taLogUn.getText().equals(""))){
                user = new User();
                user.setAction(1);
                user.setUsername(taLogUn.getText());
                user.setPassword(taLogPw.getText());
                Object reply = client.sendObject(user);

                if (reply.equals("loginSuccess")) {
                    home();
                    loginStage.close();
                }
                else {
                    Stage loginOops = new Stage();
                    loginOops.setMaxHeight(300);
                    loginOops.setMaxWidth(600);
                    BorderPane oopsPane = new BorderPane();
                    Scene oopsScene = new Scene(oopsPane);
                    oopsPane.setTop(new Text("You made a login oops. Try again."));
                    Button oopsButton = new Button("ok");
                    oopsButton.setOnAction(actionEvent1 -> loginOops.close());
                    oopsPane.setBottom(oopsButton);
                    loginOops.setTitle("Oops");
                    loginOops.setScene(oopsScene);
                    loginOops.show();
                }

            }
        });
        btFPw.setOnAction(actionEvent ->
        {
             forgotPassword();
             loginStage.close();

        });
        btReg.setOnAction(actionEvent ->
        {
             register();
             loginStage.close();
        });

        //  <@  Column constraints
        ColumnConstraints column0, column1, column2, column3, column4;
        column0 = column1 = column2 = column3 = column4 = new ColumnConstraints();
        column0.setPercentWidth(20);
        column1.setPercentWidth(20);
        column2.setPercentWidth(20);
        column3.setPercentWidth(20);
        column4.setPercentWidth(20);
        loginWindowPane.getColumnConstraints().addAll(column0, column1, column2, column3, column4);

        //  <@  Row constraints
        RowConstraints row0, row1, row2, row3, row4, row5, row6;
        row0 = row1 = row2 = row3 = row4 = row5 = row6 = new RowConstraints();
        row0.setPercentHeight(33);
        row1.setPercentHeight(6);
        row2.setPercentHeight(7);
        row3.setPercentHeight(7);
        row4.setPercentHeight(7);
        row5.setPercentHeight(7);
        row6.setPercentHeight(33);
        loginWindowPane.getRowConstraints().addAll(row0, row1, row2, row3, row4, row5, row6);

        //  <@  Layout
        loginWindowPane.add(new Text("USERNAME:"), 1, 1);
        loginWindowPane.add(taLogUn, 1, 2, 3, 1);
        loginWindowPane.add(new Text("PASSWORD:"), 1,3);
        loginWindowPane.add(taLogPw, 1, 4, 3, 1);
        loginWindowPane.add(btLogin, 1, 5);
        GridPane.setHalignment(btLogin, HPos.CENTER);
        GridPane.setValignment(btLogin, VPos.CENTER);
        loginWindowPane.add(btFPw, 2, 5);
        GridPane.setHalignment(btFPw, HPos.CENTER);
        GridPane.setValignment(btFPw, VPos.CENTER);
        loginWindowPane.add(btReg, 3, 5);
        GridPane.setHalignment(btReg, HPos.CENTER);
        GridPane.setValignment(btReg, VPos.CENTER);
        loginWindowPane.add(btDisconnect, 4, 0);
        GridPane.setHalignment(btDisconnect, HPos.RIGHT);
        GridPane.setValignment(btDisconnect, VPos.TOP);

        //  <@  Set up new stage
        loginStage.setTitle("Login");
        loginStage.setScene(scene);
        loginStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEventElse);
        loginStage.show();

    }
    private void home()
    {
        //  <@  Set up stage
        Stage homeStage = new Stage();
        homeStage.setMinHeight(MINHEIGHT);
        homeStage.setMinWidth(MINWIDTH);

        //  <@  Set up pane
        GridPane homePane = new GridPane();
        homePane.setBackground(BG);
        Scene scene = new Scene(homePane);

        //  <@  Set up text areas and text
        TextArea taSearch = new TextArea();
        taSearch.setMaxHeight(8);
        Text txtSearch = new Text("SEARCH:");
        TextArea taResults = new TextArea();

        //  <@  Set up buttons
        Button btLogout = new Button("Logout");
        Button btLD = new Button("Logout & Disconnect");
        Button btPwCh = new Button("Change Password");
        Button btSearch = new Button("Search");
        btLogout.setStyle(BTGRAY);
        btLD.setStyle(BTGRAY);
        btPwCh.setStyle(BTGRAY);
        btSearch.setStyle(BTGRAY);
        btLogout.setOnAction(actionEvent ->
        {
            //  <@  Logout
            user = new User();
            user.setAction(0);
            Object reply = client.sendObject("u/o/thisuser");
            if(reply.equals("logoutSuccess"))
            {
                login();
                homeStage.close();
            }
            else
            {
                Stage logoutOops = new Stage();
                logoutOops.setMaxHeight(300);
                logoutOops.setMaxWidth(600);
                BorderPane oopsPane = new BorderPane();
                Scene oopsScene = new Scene(oopsPane);
                oopsPane.setTop(new Text("We've made a logout oops. You are not logged out yet. Please try again."));
                Button oopsButton = new Button("ok");
                oopsButton.setOnAction(actionEvent1 -> logoutOops.close());
                oopsPane.setBottom(oopsButton);
                logoutOops.setTitle("Oops");
                logoutOops.setScene(oopsScene);
                logoutOops.show();
            }
        });
        btLD.setOnAction(actionEvent ->
        {
            //  <@  Logout and disconnect
            user = new User();
            user.setAction(2);
            Object reply = client.sendObject(user);

            if(reply.equals("logoutSuccess"))
            {
                client.disconnect();
                disconnected();
                homeStage.close();
            }
            else
            {
                Stage logoutOops = new Stage();
                logoutOops.setMaxHeight(300);
                logoutOops.setMaxWidth(600);
                BorderPane oopsPane = new BorderPane();
                Scene oopsScene = new Scene(oopsPane);
                oopsPane.setTop(new Text("We've made a logout oops. You are not logged out yet. Please try again."));
                Button oopsButton = new Button("ok");
                oopsButton.setOnAction(actionEvent1 -> logoutOops.close());
                oopsPane.setBottom(oopsButton);
                logoutOops.setTitle("Oops");
                logoutOops.setScene(oopsScene);
                logoutOops.show();
            }
        });
        btPwCh.setOnAction(actionEvent ->
        {
            //  <@  Change password
            passwordChange();
            homeStage.close();
        });
        btSearch.setOnAction(actionEvent ->
        {
            sendIt = new QueriesClass();
            Object reply = client.sendObject(taSearch.getText());
        });


        //  <@  Column constraints
        ColumnConstraints column0, column1, column2;
        column0 = column1 = column2 = new ColumnConstraints();
        homePane.getColumnConstraints().addAll(column0, column1, column2);

        //  <@  Row constraints
        RowConstraints row0, row1, row2, row3, row4, row5, row6;
        row0 = row1 = row2 = row3 = row4 = row5 = row6 = new RowConstraints();
        row0.setPercentHeight(6);
        row1.setPercentHeight(6);
        row2.setPercentHeight(11);
        row3.setPercentHeight(10);
        row4.setPercentHeight(6);
        row5.setPercentHeight(6);
        row6.setPercentHeight(55);
        homePane.getRowConstraints().addAll(row0, row1, row2, row3, row4, row5, row6);

        //  <@  Layout
        //      Buttons in upper right
        homePane.add(btLogout, 2, 0);
        GridPane.setValignment(btLogout, VPos.BOTTOM);
        GridPane.setHalignment(btLogout, HPos.RIGHT);
        homePane.add(btLD, 2, 1);
        GridPane.setHalignment(btLD, HPos.RIGHT);
        homePane.add(btPwCh, 2, 2);
        GridPane.setValignment(btPwCh, VPos.TOP);
        GridPane.setHalignment(btPwCh, HPos.RIGHT);

        //  <@  Search text and bar
        homePane.add(txtSearch, 1, 2);
        GridPane.setValignment(txtSearch, VPos.BOTTOM);
        homePane.add(taSearch, 1, 3);
        homePane.add(btSearch, 1, 4);
        homePane.add(new Text("RESULTS: "), 1, 5);
        homePane.add(taResults, 1, 6);

        homeStage.setTitle("Home");
        homeStage.setScene(scene);
        homeStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEventLoggedIn);
        homeStage.show();

    }
    private void passwordChange()
    {
        //  <@  Set up stage
        Stage pwChangeStage = new Stage();
        pwChangeStage.setMinHeight(MINHEIGHT);
        pwChangeStage.setMinWidth(MINWIDTH);

        //  <@  Set up pane
        GridPane pwChangePane = new GridPane();
        pwChangePane.setBackground(BG);
        Scene scene = new Scene(pwChangePane);

        //  <@  Set up text areas
        TextArea taOldPass = new TextArea();
        TextArea taNewPass = new TextArea();
        taOldPass.setMaxHeight(10);
        taNewPass.setMaxHeight(10);

        //  <@  Set up text
        Text txtOldPass = new Text("Old password: ");
        Text txtNewPass = new Text("New Password: ");

        //  <@  Set up buttons
        Button btConfirm = new Button("Confirm");
        Button btBack = new Button("Back");
        btBack.setStyle(BTGRAY);
        btConfirm.setStyle(BTGREEN);
        btBack.setOnAction(actionEvent ->
        {
            //  <@  Nothing else needs to be done here, as nothing has been done
            home();
            pwChangeStage.close();
        });
        btConfirm.setOnAction(actionEvent ->
        {

            if(!taOldPass.getText().equals(taNewPass.getText()))
            {
                String reply = client.sendString("u/p/thisuser/" + taOldPass.getText() + "/" + taNewPass.getText());
                if(reply.equals("passwordChangeSuccess"))
                {
                    Stage passwordYay = new Stage();
                    passwordYay.setMaxHeight(300);
                    passwordYay.setMaxWidth(600);
                    BorderPane yayPane = new BorderPane();
                    Scene oopsScene = new Scene(yayPane);
                    yayPane.setTop(new Text("Password successfully changed."));
                    Button oopsButton = new Button("ok");
                    oopsButton.setOnAction(actionEvent1 -> passwordYay.close());
                    yayPane.setBottom(oopsButton);
                    passwordYay.setTitle("Yay");
                    passwordYay.setScene(oopsScene);
                    passwordYay.show();
                    home();
                    pwChangeStage.close();
                }
                else
                {
                    Stage passwordOops = new Stage();
                    passwordOops.setMaxHeight(300);
                    passwordOops.setMaxWidth(600);
                    BorderPane oopsPane = new BorderPane();
                    Scene oopsScene = new Scene(oopsPane);
                    oopsPane.setTop(new Text("We've hit an error. Please try again."));
                    Button oopsButton = new Button("ok");
                    oopsButton.setOnAction(actionEvent1 -> passwordOops.close());
                    oopsPane.setBottom(oopsButton);
                    passwordOops.setTitle("Oops");
                    passwordOops.setScene(oopsScene);
                    passwordOops.show();
                }

            }
            else
            {
                Stage passwordOops = new Stage();
                passwordOops.setMaxHeight(300);
                passwordOops.setMaxWidth(600);
                BorderPane oopsPane = new BorderPane();
                Scene oopsScene = new Scene(oopsPane);
                oopsPane.setTop(new Text("Your passwords match. Please try again."));
                Button oopsButton = new Button("ok");
                oopsButton.setOnAction(actionEvent1 -> passwordOops.close());
                oopsPane.setBottom(oopsButton);
                passwordOops.setTitle("Oops");
                passwordOops.setScene(oopsScene);
                passwordOops.show();
            }
        });

        //  <@  Add column constraints
        ColumnConstraints column0, column1, column2;
        column0 = column1 = column2 = new ColumnConstraints();
        column0.setPercentWidth(20);
        column1.setPercentWidth(60);
        column2.setPercentWidth(20);
        pwChangePane.getColumnConstraints().addAll(column0, column1, column2);

        //  <@  Add row constraints
        RowConstraints row0, row1, row2, row3, row4;
        row0 = row1 = row2 = row3 = row4 = new RowConstraints();
        row0.setPercentHeight(30);
        row1.setPercentHeight(10);
        row2.setPercentHeight(10);
        row3.setPercentHeight(30);
        row4.setPercentHeight(20);
        pwChangePane.getRowConstraints().addAll(row0, row1, row2, row3, row4);

        //  <@  Layout
        //      Back button
        pwChangePane.add(btBack, 2, 0);
        GridPane.setValignment(btBack, VPos.TOP);
        GridPane.setHalignment(btBack, HPos.RIGHT);
        //  <@  Old password
        pwChangePane.add(txtOldPass, 1, 0);
        GridPane.setValignment(txtOldPass, VPos.BOTTOM);
        GridPane.setHalignment(txtOldPass, HPos.LEFT);
        pwChangePane.add(taOldPass, 1, 1);
        //  <@  New password
        pwChangePane.add(txtNewPass, 1, 2);
        GridPane.setValignment(txtNewPass, VPos.BOTTOM);
        GridPane.setHalignment(txtNewPass, HPos.LEFT);
        pwChangePane.add(taNewPass, 1, 3);
        GridPane.setValignment(taNewPass, VPos.TOP);
        //  <@  Confirm button
        pwChangePane.add(btConfirm, 1, 3);
        GridPane.setValignment(btConfirm, VPos.BOTTOM);
        GridPane.setHalignment(btConfirm, HPos.CENTER);



        pwChangeStage.setTitle("Change Password");
        pwChangeStage.setScene(scene);
        pwChangeStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEventLoggedIn);
        pwChangeStage.show();

    }
    private void forgotPassword()
    {
        //  <@  Set up stage
        Stage fPwStage = new Stage();
        fPwStage.setMinHeight(MINHEIGHT);
        fPwStage.setMinWidth(MINWIDTH);

        //  <@  Set up pane
        GridPane fPwPane = new GridPane();
        fPwPane.setBackground(BG);
        Scene scene = new Scene(fPwPane);

        //  <@  Set up text area
        TextArea taEmail = new TextArea();
        taEmail.setMaxHeight(10);

        //  <@  Set up buttons
        Button btBack = new Button("Back");
        Button btEmail = new Button("Confirm");
        btBack.setStyle(BTGRAY);
        btEmail.setStyle(BTGRAY);
        btBack.setOnAction(actionEvent ->
        {
            //  <@  Nothing to do
            login();
            fPwStage.close();
        });
        btEmail.setOnAction(actionEvent ->
        {
            String reply = client.sendString("u/f/" + taEmail.getText());
            if(reply.equals("forgotPasswordSuccessful"))
            {
                Stage emailYay = new Stage();
                emailYay.setMaxHeight(300);
                emailYay.setMaxWidth(600);
                BorderPane yayPane = new BorderPane();
                Scene yayScene = new Scene(yayPane);
                yayPane.setTop(new Text("A new password has been sent to your email."));
                Button yayButton = new Button("yay");
                yayButton.setOnAction(actionEvent1 -> emailYay.close());
                yayPane.setBottom(yayButton);
                emailYay.setTitle("Yay!");
                emailYay.setScene(yayScene);
                emailYay.show();
                login();
                fPwStage.close();
            }
            else
            {
                Stage emailOops = new Stage();
                emailOops.setMaxHeight(300);
                emailOops.setMaxWidth(600);
                BorderPane oopsPane = new BorderPane();
                Scene oopsScene = new Scene(oopsPane);
                oopsPane.setTop(new Text("A new password has been sent to your email."));
                Button oopsButton = new Button("ok");
                oopsButton.setOnAction(actionEvent1 -> emailOops.close());
                oopsPane.setBottom(oopsButton);
                emailOops.setTitle("Oops");
                emailOops.setScene(oopsScene);
                emailOops.show();
            }
        });

        //  <@  Column constraints
        ColumnConstraints column0, column1, column2, column3, column4;
        column0 = column1 = column2 = column3 = column4 = new ColumnConstraints();
        column0.setPercentWidth(20);
        column1.setPercentWidth(20);
        column2.setPercentWidth(20);
        column3.setPercentWidth(20);
        column4.setPercentWidth(20);
        fPwPane.getColumnConstraints().addAll(column0, column1, column2, column3, column4);

        //  <@  Row constraints
        RowConstraints row0, row1, row2, row3, row4, row5;
        row0 = row1 = row2 = row3 = row4 = row5 = new RowConstraints();
        row0.setPercentHeight(21);
        row1.setPercentHeight(15);
        row2.setPercentHeight(21);
        row3.setPercentHeight(7);
        row4.setPercentHeight(15);
        row5.setPercentHeight(21);
        fPwPane.getRowConstraints().addAll(row0, row1, row2, row3, row4, row5);

        //  <@  Layout (column first, then row)
        fPwPane.add(btBack, 4,0);
        fPwPane.add(new Text("Please enter your Username." +
                "\nA temporary password will be sent to your registered email."), 1, 1, 3, 1);
        fPwPane.add(new Text("Username:"), 1,2);
        fPwPane.add(taEmail, 1, 3, 3, 1);
        fPwPane.add(btEmail, 2, 4);

        fPwStage.setTitle("Forgotten Password");
        fPwStage.setScene(scene);
        fPwStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEventElse);
        fPwStage.show();


    }
    private void register()
    {
        // ** Set up stage
        Stage registerStage = new Stage();
        registerStage.setMinHeight(MINHEIGHT);
        registerStage.setMinWidth(MINWIDTH);

        // ** Set up pane
        GridPane registerPane = new GridPane();
        registerPane.setBackground(BG);
        Scene scene = new Scene(registerPane,600,600);

        // ** Set up text area
        TextArea taEmail = new TextArea();
        taEmail.setMaxHeight(10);

        TextArea taUsername = new TextArea();
        taUsername.setMaxHeight(10);

        TextArea taPw = new TextArea();
        taPw.setMaxHeight(10);

        // ** Set up Labels

        Label txtUserLabel = new Label("Choose username: ");
        Label txtPassLabel = new Label("Create password: ");
        Label txtEmailLabel = new Label("Enter email: ");

        // ** Set Up Buttons
        Button btRegister = new Button("Register");
        Button btBack = new Button("Back");
        btRegister.setStyle(BTGREEN);
        btBack.setStyle(BTGRAY);
        btRegister.setOnAction(actionEvent ->
        {


            if(!(taUsername.getText().equals("")) && !(taPw.getText().equals("")) && !(taEmail.getText().equals(""))){
                sendIt = "u/R/" + taUsername.getText() + "/" + taPw.getText() + "/" + taEmail.getText();
                String reply = client.sendString(sendIt);
                System.out.println(reply);
                if(reply.equals("RegistrationSuccessful"))
                {
                    login();
                    registerStage.close();
                }
                else {
                    Stage registrationOops = new Stage();
                    registrationOops.setMaxHeight(300);
                    registrationOops.setMaxWidth(600);
                    BorderPane oopsPane = new BorderPane();
                    Scene oopsScene = new Scene(oopsPane);
                    oopsPane.setTop(new Text("You made a registration oops. Try again."));
                    Button oopsButton = new Button("ok");
                    oopsButton.setOnAction(actionEvent1 -> registrationOops.close());
                    oopsPane.setBottom(oopsButton);
                    registrationOops.setTitle("Oops");
                    registrationOops.setScene(oopsScene);
                    registrationOops.show();
                }
            }
        });
        btBack.setOnAction(actionEvent ->
        {
            login();
            registerStage.close();
        });

        // ** Column constraints
        ColumnConstraints column0, column1, column2, column3, column4, column5;
        column0 = column1 = column2 = column3 = column4 = column5 = new ColumnConstraints();
        column0.setPercentWidth(20);
        column1.setPercentWidth(20);
        column2.setPercentWidth(20);
        column3.setPercentWidth(20);
        column4.setPercentWidth(20);
        registerPane.getColumnConstraints().addAll(column0, column1, column2,column3,column4,column5);

        // ** Row constraints
        RowConstraints row0, row1, row2, row3, row4, row5, row6, row7;
        row0 = row1 = row2 = row3 = row4 = row5 = row6 = row7 = new RowConstraints();
        row0.setPercentHeight(15);
        row1.setPercentHeight(8);
        row2.setPercentHeight(15);
        row3.setPercentHeight(8);
        row4.setPercentHeight(15);
        row5.setPercentHeight(8);
        row6.setPercentHeight(15);
        row7.setPercentHeight(16);
        registerPane.getRowConstraints().addAll(row0,row1,row2,row3,row4,row5, row6, row7);

        // ** Add all to pane
        registerPane.add(taUsername,1,1,3,1);
        registerPane.add(txtUserLabel,1,0, 3, 1);
        GridPane.setValignment(txtUserLabel, VPos.BOTTOM);

        registerPane.add(taPw,1,3,3,1);
        registerPane.add(txtPassLabel,1,2, 3, 1);
        GridPane.setValignment(txtPassLabel, VPos.BOTTOM);

        registerPane.add(taEmail,1,5,3,1);
        registerPane.add(txtEmailLabel,1,4, 3, 1);
        GridPane.setValignment(txtEmailLabel, VPos.BOTTOM);
        registerPane.add(new Text("* Password must be 8-16 characters. \n ** Password must contain " +
                "at least \n one letter and one number."), 1, 6, 3, 1);

        registerPane.add(btRegister,2,7);
        GridPane.setValignment(btRegister, VPos.TOP);
        GridPane.setHalignment(btRegister, HPos.CENTER);
        registerPane.add(btBack, 4,0);
        GridPane.setValignment(btBack, VPos.TOP);
        GridPane.setHalignment(btBack, HPos.RIGHT);

        registerStage.setTitle("New User Registration");
        registerStage.setScene(scene);
        registerStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEventElse);
        registerStage.show();


    }

    private <T extends Event> void closeWindowEventLoggedIn(T t)
    {
        String reply = client.sendString("u/o/thisuser");
        client.disconnect();
        System.exit(0);
    }
    private <T extends Event> void closeWindowEventElse(T t)
    {
        client.disconnect();
        System.exit(0);
    }
}
