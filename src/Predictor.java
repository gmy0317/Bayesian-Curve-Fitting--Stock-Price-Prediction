import java.util.ArrayList;

public class Predictor {
    public static void main(String[] argv)
    {
    	// Get the file information.
        String fileName = new String(argv[0]);
        FileParser fileReader = new FileParser(fileName);
        ArrayList<Double> priceArrayOri = fileReader.parseData();
        int day = priceArrayOri.size();
        
        ArrayList<Double> priceArray = new ArrayList<Double>();
        Double predictValue = 0.0;
        
        // Read prices for $day - 1 days.
        for (int i = 0; i < priceArrayOri.size() - 1; i++) 
        {
        	priceArray.add( priceArrayOri.get(i) );
        }
        
        // Predict price of day $day
        BayesianCurveFitting bayesianCurveFitting = new BayesianCurveFitting(priceArray);
        predictValue = bayesianCurveFitting.predictPrice();
            
        System.out.println("The predicted price of day "+ (day) + " is: ");
        System.out.println(predictValue);
        System.out.println("");
        System.out.println("The actural price of day "+ (day) + " is: ");
        System.out.println( priceArrayOri.get(priceArrayOri.size() - 1) );
    }
}
