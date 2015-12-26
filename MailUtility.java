package utility;
// Java Utilities
import java.util.Date;
import java.util.Properties;
// JavaMail
import javax.mail.*;
import javax.mail.internet.MimeMessage;
// JavaFX
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class MailUtility extends Application {
    public static void main(String[] args) {
         
        launch(args);
         
    }
     
    @Override
    public void start(Stage primaryStage) {
        // Set Window Title
        primaryStage.setTitle("SMTP Mail Tester Utility");
         
        // Create Grid Layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(12);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(23);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(65);
        grid.getColumnConstraints().addAll(col1,col2,col3);
        // Add Labels and Fields to Grid
        Text scenetitle = new Text("SMTP Mail Tester Utility");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        // Mail Server
        Label lblMailServer = new Label("SMTP Server Host:");
        grid.add(lblMailServer, 0, 2);
        TextField mailServer = new TextField("mail.example.com");
        grid.add(mailServer, 1, 2);
        // Mail Port
        Label lblMailPort = new Label("SMTP Port:");
        grid.add(lblMailPort, 0, 3);
        TextField mailPort = new TextField("25");
        grid.add(mailPort, 1, 3);
         
        // Email Sender
        Label lblSender = new Label("Email Sender (From):");
        grid.add(lblSender, 0, 4);
        TextField sender = new TextField("no-reply@example.com");
        grid.add(sender, 1, 4);
         
        // Email Recipient
        Label lblRecipient = new Label("Email Recipient (To):");
        grid.add(lblRecipient, 0, 5);
        TextField recipient = new TextField("my-email-address@example.com");
        grid.add(recipient, 1, 5);
         
        // Email Subject
        Label lblSubject = new Label("Email Subject:");
        grid.add(lblSubject, 0, 6);
        TextField subject = new TextField("SMTP Test Email");
        grid.add(subject, 1, 6);
         
        // Email Message Body
        Label lblMessage = new Label("Email Message Body:");
        grid.add(lblMessage, 0, 7);
        TextArea message = new TextArea("This is a test email sent by the SMTP Tester Utility.");
        message.setWrapText(true);
        grid.add(message, 1, 7);
         
        // Debug Output Area
        Label lblDebug = new Label("SMTP Test Output:");
        grid.add(lblDebug, 2, 1);
        TextArea debug = new TextArea();
        debug.setEditable(false);
        grid.add(debug, 2, 2, 1, 6);
        // Send Button
        Button btn = new Button("Send Test Email");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 8);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                 
                System.out.println(mailServer.getText());
                 
                Properties props = new Properties();
                props.put("mail.transport.protocol", "smtp");
                props.put("mail.host", mailServer.getText());
                Session session = Session.getInstance(props, null);
                debug.setText("Sending Email...");
                 
                boolean success = false;
                 
                try {
                    MimeMessage msg = new MimeMessage(session);
                    msg.setFrom(sender.getText());
                    msg.setRecipients(Message.RecipientType.TO, recipient.getText());
                    msg.setSubject(subject.getText());
                    msg.setSentDate(new Date());
                    msg.setText(message.getText());
                    Transport.send(msg);
                    success = true;
                } catch (MessagingException mex) {
                    debug.setText(debug.getText() + "\nSend Failed - SendMail Exception Caught:\n" + mex);
                    System.out.println("send failed, exception: " + mex);
                }
                 
                if (success) {
                    debug.setText(debug.getText() + "\nEmail Sent - No Exceptions Encountered");
                }
            }
        });
        // Add Grid Layout to Scene
        Scene scene = new Scene(grid, 1000, 500);
         
        // Add Scene to Stage (Window)
        primaryStage.setScene(scene);
         
        // Open Window
        primaryStage.show();
    }
}
