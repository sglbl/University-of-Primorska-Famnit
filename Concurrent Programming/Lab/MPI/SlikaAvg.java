import java.net.URL;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.util.*;
import mpi.*;

public class SlikaAvg {
	private static int id;
	private static int size;
		
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		MPI.Init(args);
		id = MPI.COMM_WORLD.Rank();
		size = MPI.COMM_WORLD.Size();
		System.out.println("jaz sem "+id+" od "+size);
		
		int[] slika = new int[0];
		int[] totalLength = new int[1];
		if (id == 0)
		{
	
		    URL url = new URL("https://www.google.si/images/icons/product/chrome-32.png");  
              	    BufferedImage image = ImageIO.read(url);
		    totalLength[0] = image.getWidth()*image.getHeight();
		    slika = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());	
		}

		MPI.COMM_WORLD.Bcast(totalLength, 0, 1, MPI.INT, 0);

		if (id != 0)
			slika = new int[totalLength[0]]; // ceprav tega ne rabimo, moramo inicializirat, ker ce ne bo vrgu MPJ exception.
		
		int[] vrstica = new int[totalLength[0] / size];
		MPI.COMM_WORLD.Scatter(slika, 0, vrstica.length, MPI.INT, vrstica, 0, vrstica.length, MPI.INT, 0);
		
		int r = 0, g = 0, b = 0;
		for (int i=0; i<vrstica.length; i++)
		{
			Color c = new Color(vrstica[i]);
			r += c.getRed();
			g += c.getGreen();
			b += c.getBlue();
		}
		r = r / vrstica.length;
		g = g / vrstica.length;
		b = b / vrstica.length;
		
		int[] zapakirano = new int[1];
		zapakirano[0] = new Color(r, g, b).getRGB();
		
		int[] zdruzeno = new int[size];
		MPI.COMM_WORLD.Gather(zapakirano, 0, 1, MPI.INT, zdruzeno, 0, 1, MPI.INT, 0);

		if (id == 0)
		{
			r = 0;
			g = 0;
			b = 0;
			
			for (int i=0; i<zdruzeno.length; i++)
			{
				Color c = new Color(zdruzeno[i]);
				r += c.getRed();
				g += c.getGreen();
				b += c.getBlue();
			}
			r = r / zdruzeno.length;
			g = g / zdruzeno.length;
			b = b / zdruzeno.length;

			System.out.println("Rdeca: " + r + " Zelena: " + g + " Plava: " + b);
		}
		
		
		MPI.Finalize();
	}


}
