package sample;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.stream.Stream;

public class WebScraper
{

    private String mNFLUrl;
    private String mNFLDataFile;
    private String mNFLDataFileTemp;

    public String getmNFLUrl()
    {
        return mNFLUrl;
    }


    public WebScraper()
    {
        MySettings mset = new MySettings();
        mNFLUrl = mset.getmNFLScoreURL();
        mNFLDataFile = mset.getmNFLDataFile();
        mNFLDataFileTemp = mset.getmNFLDataFileTemp();
    }

    public void DownloadPage() throws IOException
    {
        Utilities ut = new Utilities();
        ut.DownLoadWebPage(mNFLUrl, mNFLDataFileTemp);
    }

    public void PopulateCleanDataFile(String NFLDataFile)
    {
        try
        {
            FileWriter myWriter = new FileWriter(NFLDataFile);
            //FileWriter myWriter = new FileWriter("D:\\Dropbox\\COP2800\\JavaFinal\\NFLGames-Version1.5\\data\\TestFile.html");
            myWriter.write("<Games>\n");

            StringBuilder sbFileContents = new StringBuilder();
            try (Stream<String> stream = Files.lines( Paths.get(mNFLDataFileTemp), StandardCharsets.UTF_8))
            {
                stream.forEach(s -> sbFileContents.append(s).append("\n"));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            String temp = sbFileContents.toString();
            temp = TrimLeft(temp, "Week");
            temp = TrimRight(temp, "<thead><tr>");

            String AwayTeam = "";
            String HomeTeam = "";
            String Time = "";

            while(temp.contains("matchdate"))
            {
                temp = TrimLeft_RemoveValue(temp, "matchdate");
                temp = TrimLeft_RemoveValue(temp, ">");
                Time = temp;
                Time = TrimRight(Time, "</div>");

                temp = TrimLeft(temp, "teamname");
                temp = TrimLeft_RemoveValue(temp, "<div>");
                AwayTeam = temp;
                AwayTeam = TrimRight(AwayTeam, "</div>");

                temp = TrimLeft_RemoveValue(temp, "teamsseparator");
                temp = TrimLeft_RemoveValue(temp, "<td class");
                temp = TrimLeft_RemoveValue(temp, "<td class");
                temp = TrimLeft_RemoveValue(temp, ">");
                HomeTeam = temp;
                HomeTeam = TrimRight(HomeTeam, "<div");

                myWriter.write("<Game>\n");
                myWriter.write("<AwayTeam>" + AwayTeam.trim() + "</AwayTeam>\n");
                myWriter.write("<HomeTeam>" + HomeTeam.trim() + "</HomeTeam>\n");
                myWriter.write("<GameTime>" + Time.trim() + "</GameTime>\n");
                //myWriter.write("<GameTime>1</GameTime>\n");
                myWriter.write("</Game>\n");
           }

            //myWriter.write(temp);

            /*

            myWriter.write("<Game>");
            myWriter.write("<AwayTeam>Jets</AwayTeam>");
            myWriter.write("<HomeTeam>Dolphins</HomeTeam>");
            myWriter.write("<GameTime>1</GameTime>");
            myWriter.write("</Game>");

            myWriter.write("<Game>");
            myWriter.write("<AwayTeam>Bengals</AwayTeam>");
            myWriter.write("<HomeTeam>Browns</HomeTeam>");
            myWriter.write("<GameTime>4</GameTime>");
            myWriter.write("</Game>");

            myWriter.write("<Game>");
            myWriter.write("<AwayTeam>Eagles</AwayTeam>");
            myWriter.write("<HomeTeam>Giants</HomeTeam>");
            myWriter.write("<GameTime>8</GameTime>");
            myWriter.write("</Game>");

            */

            myWriter.write("</Games>");
            myWriter.close();
        }
        catch (IOException e)
        {
            System.out.println("An error occurred while writing to NFLDataFile:." + NFLDataFile);
            e.printStackTrace();
        }
    }

    private String TrimLeft_RemoveValue(String input, String value)
    {
        int indexvalue = input.indexOf(value);
        input = input.substring(indexvalue+value.length());
        return input;
    }

    private String TrimLeft(String input, String value)
    {
        int indexvalue = input.indexOf(value);
        input = input.substring(indexvalue);
        return input;
    }

    private String TrimRight(String input, String value)
    {
        int indexvalue = input.indexOf(value);
        input = input.substring(0, indexvalue);
        return input;
    }

}
