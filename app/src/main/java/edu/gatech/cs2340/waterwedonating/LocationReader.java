package edu.gatech.cs2340.waterwedonating;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to read location data
 */
public class LocationReader {
    InputStream inputStream;

    /**
<<<<<<< HEAD
     * Constructor used to initialize InputStream
=======
     * COnstructor used to initialize InputStream
>>>>>>> master
     * @param inputStream accepts InputStream
     */
    public LocationReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * Parses data
     * @return a list of locations
     */
    public List read() {
        List resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                resultList.add(row);
            }
        }
        catch (IOException ex1) {
            throw new RuntimeException("Error in reading the CSV file." + ex1);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException ex2) {
                throw new RuntimeException("Error while closing input stream" + ex2);
            }
        }
        return resultList;
    }


}
