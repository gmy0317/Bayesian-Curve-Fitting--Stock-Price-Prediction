import Jama.*;  

import java.util.ArrayList;
import java.lang.Double;

public class BayesianCurveFitting
{
    public BayesianCurveFitting(ArrayList<Double> input)
    {
    	priceArray = input;
    	M          = 4; // Use 4 for now
    	alpha      = 0.005;
    	beta       = 11.1;
    	phiT2Matrix();
    }
    
    // Build phi matrix
    public void phi2Matrix(int n)
    {
        double[][] mtx = new double[M + 1][1];
        for (int i=0;i<M+1;i++)
        {
        	mtx[i][0] = Math.pow(n, i);
        }
        phi = new Matrix(mtx);
    }
    
    // Build transpose phi matrix
    public void phiT2Matrix()
    {
        double [][] mtx = new double[1][M + 1];
        for(int i = 0; i <= M; i++)
        {
        	mtx[0][i] = Math.pow(priceArray.size(), i);
        }
        phiT = new Matrix(mtx);
    }
    
    // Get inverse matrix
    public Matrix getSInv()
    {
    	// Build alpha * I
        double [][] alphaTbl = new double[M + 1][M + 1];
        for(int i = 0; i < M + 1; i++)
        {
        	alphaTbl[i][i] = alpha; // alpha == 0.005
        }
        Matrix alphaMtx = new Matrix(alphaTbl);
        
    	// calculate summation
        Matrix sum = new Matrix(M + 1, 1);
        for(int i = 1; i <= priceArray.size() - 1; i++)
        {
        	phi2Matrix(i);
            sum.plusEquals(phi);
        }
        Matrix summation = sum.times(phiT);
        summation.timesEquals(beta); // beta == 11.1
        
        // alpha * I + beta * sum
        Matrix sInv = alphaMtx.plus(summation);
        return sInv;
    }

    // Calculate matrix S
    public Matrix getS(Matrix sInv)
    {
        return sInv.inverse();
    }

    public double predictPrice()
    {
    	double mx=0;
    	// Calcualte S
        Matrix S = getS(getSInv());
        // Calculate sum
        Matrix sum = new Matrix(M + 1, 1);
        for(int i = 1; i <= priceArray.size() - 1; i++)
        {
        	phi2Matrix(i);
            double tn = priceArray.get(i-1);
            sum.plusEquals(phi.times(tn));
        }
        
        // phiT * S
        Matrix product = phiT.times(S);

        // beta * phiT * S * sum(phi * tn)
        Matrix predictedPrice = product.times(sum);
        mx = beta * predictedPrice.get(0, 0);

        // Use mx as the predicted price.
        return mx;
    }
    
    private Matrix            phi;
    private Matrix            phiT;
    private ArrayList<Double> priceArray;
    private int               M;
    private double            alpha;
    private double            beta;
}
