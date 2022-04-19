import mpi.*;

public class MPJSendReceive {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MPI.Init(args);
		
		int me = MPI.COMM_WORLD.Rank();
		int size = MPI.COMM_WORLD.Size();
		
		int[] Buf = new int[1];
		if (me == 0)
		{
			for (int i=1; i<size; i++)
			{
				Buf[0] = i;
				MPI.COMM_WORLD.Send(Buf, 0, Buf.length, MPI.INT, i, 0);
			}
		
		}
		else
		{
			MPI.COMM_WORLD.Recv(Buf, 0, Buf.length, MPI.INT, 0, 0);
			
			System.out.println(Buf[0]);
		}
		
		MPI.Finalize();
	}

}
