package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import javafx.scene.control.Button;
import javafx.event.EventHandler;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class Main extends Application implements EventHandler<ActionEvent>
{

    TableView<Games> table;
    static String NFLDatafile;
    Button btnTwitter;

    static String APIKey;
    static String APISecretKey;
    static String BearerToken;
    static String AccessToken;
    static String AccessSecretToken;
    static String TwitterUpdateTeam;

    ObservableList<Games> games = FXCollections.observableArrayList();

    @Override
    public void handle(ActionEvent actionEvent)
    {
        if(actionEvent.getSource()==btnTwitter)
        {

            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(APIKey)
                    .setOAuthConsumerSecret(APISecretKey)
                    .setOAuthAccessToken(AccessToken)
                    .setOAuthAccessTokenSecret(AccessSecretToken);
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();

            try
            {
                StringBuilder postStr = new StringBuilder();
                for (Games g : games)
                {
                    String hometeam = g.getM_hometeam();
                    String awayteam = g.getM_awayteam();
                    String time = g.getM_time();
                    if(hometeam.contains(TwitterUpdateTeam) || awayteam.contains(TwitterUpdateTeam))
                    {
                        postStr.append(hometeam + " will play the " + awayteam + " on " + time);
                    }
                }
                twitter.updateStatus(postStr.toString());
            } catch (TwitterException e)
            {
                e.printStackTrace();
            }

            System.out.println("Post was successfull");

        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {

        MySettings mset = new MySettings();

        APIKey = mset.getmAPIKey();
        APISecretKey = mset.getmAPISecretKey();
        BearerToken = mset.getmBearerToken();
        AccessToken = mset.getmAccessToken();
        AccessSecretToken = mset.getmAccessSecretToken();
        TwitterUpdateTeam = mset.getmTwitterUpdateTeam();

        WebScraper wbsp = new WebScraper();
        wbsp.DownloadPage();

        NFLDatafile = mset.getmNFLDataFile();
        wbsp.PopulateCleanDataFile(NFLDatafile);

        btnTwitter = new Button();
        btnTwitter.setText("Post to Twitter");
        btnTwitter.setOnAction(this);

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("NFL Games");
        primaryStage.setHeight(600);
        primaryStage.setWidth(600);

        TableColumn<Games, String> awayColumn = new TableColumn<>("AwayTeam");
        TableColumn<Games, String> homeColumn = new TableColumn<>("HomeTeam");
        TableColumn<Games, String> timeColumn = new TableColumn<>("Time");

        awayColumn.setMinWidth(200);
        awayColumn.setCellValueFactory(new PropertyValueFactory<>("m_awayteam"));

        homeColumn.setMinWidth(200);
        homeColumn.setCellValueFactory(new PropertyValueFactory<>("m_hometeam"));

        timeColumn.setMinWidth(100);
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("m_time"));

        table = new TableView<>();
        table.setItems(getGames());
        table.getColumns().addAll(awayColumn, homeColumn, timeColumn);
        table.setMinHeight(500);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table);
        vBox.getChildren().addAll(btnTwitter);

        Scene scene = new Scene(vBox);
        //primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public ObservableList<Games> getGames()
    {

        //games.add(new Games("hometeam","awayteam",1));
        //return games;

        Utilities util = new Utilities();
        Document doc = util.FileToDocument(NFLDatafile);

        //Creating a NodeList object
        NodeList nodeList = doc.getElementsByTagName("Game");
        // NodeList is not iterable, so we are using for loop
        for (int itr = 0; itr < nodeList.getLength(); itr++)
        {
            Node node = nodeList.item(itr);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) node;
                String awayteam = eElement.getElementsByTagName("AwayTeam").item(0).getTextContent();
                String hometeam = eElement.getElementsByTagName("HomeTeam").item(0).getTextContent();
                String time = eElement.getElementsByTagName("GameTime").item(0).getTextContent();
                //Double time = Double.parseDouble(eElement.getElementsByTagName("GameTime").item(0).getTextContent());
                games.add(new Games(awayteam, hometeam,time));
            }
        }

        return games;

    }



    public static void main(String[] args)
    {

        launch(args);

    }

}
