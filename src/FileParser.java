import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class FileParser
{
	public FileParser(String fileName)
	{
		fileName_ = fileName;
		try
		{
			reader_ = new BufferedReader(new FileReader(fileName_));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
    public ArrayList<Double> parseData()
    {
        ArrayList<Double> priceArray = new ArrayList<Double>();
        try 
        {
            String value = null;
            double price = 0;
            while ((value = reader_.readLine()) != null) 
            {
                price = Double.parseDouble(value);
                priceArray.add(price);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return priceArray;
    }
    
    private BufferedReader reader_;
    private String fileName_;
}