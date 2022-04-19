import java.util.Random;

import mpi.*;

public class MnozenjeMatrikMPJ {

	public static int N = 100;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MPI.Init(args);
		
		double t0 = System.currentTimeMillis();
		
		int me = MPI.COMM_WORLD.Rank();
		int size = MPI.COMM_WORLD.Size();
		
		int kolikoVrstic = N / size;
		
		int[] A = new int[N*N];
		int[] B = new int[N*N];
		int[] C = new int[N*N];
		
		// Generiraj
		if (me == 0)
		{
			Random rand = new Random();
			
			for (int i=0; i<N; i++)
			{
				for (int j=0; j<N; j++)
				{
					A[i*N + j] = rand.nextInt(10000);
				}
			}
			
			for (int i=0; i<N; i++)
			{
				for (int j=0; j<N; j++)
				{
					B[i*N + j] = rand.nextInt(10000);
				}
			}
		}
		
		// Poslji
		MPI.COMM_WORLD.Bcast(A, 0, A.length, MPI.INT, 0);
		MPI.COMM_WORLD.Bcast(B, 0, B.length, MPI.INT, 0);
		
		int[] NasaVrstica = new int[N*kolikoVrstic];
		
		// Izracunaj
		for (int i=0; i<kolikoVrstic; i++)
		{
			for (int j=0; j<N; j++)
			{
				for (int k=0; k<N; k++)
				{
					NasaVrstica[i*N+j] += A[(me*kolikoVrstic+i)*N + k] * B[k*N + j];
				}
			}
		}
		
		// Nekako poslji na root
		MPI.COMM_WORLD.Gather(	NasaVrstica, 0, NasaVrstica.length, MPI.INT, 
								C, 0, NasaVrstica.length, MPI.INT, 0);
		
		// Preveri rezultat
		if (me == 0)
		{
			t0 = System.currentTimeMillis() - t0;
			double CasPorazdeljeni = t0;
			
			double CasZaporedni = System.currentTimeMillis();
			int CZap[] = new int[N*N];
			for (int i=0; i<N; i++)
			{
				for (int j=0; j<N; j++)
				{
					for (int k=0; k<N; k++)
					{
						CZap[i*N + j] += A[i*N + k] * B[k*N + j];
					}
				}
			}
			
			CasZaporedni = System.currentTimeMillis() - CasZaporedni;
			
			System.out.println("Cas porazdeljeni: " + CasPorazdeljeni);
			System.out.println("Cas zaporedni: " + CasZaporedni);
			
			boolean StaEnaki = true;
			
			for (int i=0; i<N; i++)
			{
				for (int j=0; j<N; j++)
				{
					if (C[i*N + j] != CZap[i*N + j])
					{
						StaEnaki = false;
					}
				}
			}
			
			System.out.println("Matriki sta enaki: " + StaEnaki);
		}
		
		MPI.Finalize();
	}

}