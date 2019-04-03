package test2;


import org . apache . giraph . conf . GiraphConfiguration ;
import org . apache . giraph . examples .
SimpleShortestPathsComputation ;
import org . apache . giraph . io . formats .*;
import org . apache . giraph . job . GiraphJob ;
import org . apache . hadoop . conf . Configuration ;
import org . apache . hadoop . fs . Path ;
import org . apache . hadoop . mapreduce . lib . output .
FileOutputFormat ;
import org . apache . hadoop . util . Tool ;
import org . apache . hadoop . util . ToolRunner ;
import test2.ShortestPath;
import test2.EdgeInputFormat;
import org.apache.giraph.utils.ArrayWritable;
public class GiraphDemoRunner implements Tool {

	private Configuration conf ;
	public Configuration getConf () {
		return conf ;
	}
	public void setConf ( Configuration conf ) {
		this . conf = conf ;
	}
	
	public int run ( String [] arg0 ) throws Exception {

		String inputPath ="/home/lab/Downloads/testfile.txt";
		String outputPath ="/home/lab/Desktop/testfile_output1_141";

		
		GiraphConfiguration giraphConf = new GiraphConfiguration ( getConf () );
		giraphConf . setComputationClass (ShortestPath. class );
		giraphConf.setEdgeInputFormatClass(EdgeInputFormat.class);
		GiraphFileInputFormat .addEdgeInputPath ( giraphConf ,new Path ( inputPath ));
		giraphConf . setVertexOutputFormatClass (IdWithValueTextOutputFormat . class );
		giraphConf . setLocalTestMode ( true );
		giraphConf . setWorkerConfiguration (1 , 1, 100) ;
		giraphConf . SPLIT_MASTER_WORKER . set ( giraphConf , false );
		InMemoryVertexOutputFormat . initializeOutputGraph (giraphConf );
		GiraphJob giraphJob = new GiraphJob ( giraphConf ,"GiraphDemo ");
	
		FileOutputFormat . setOutputPath ( giraphJob.getInternalJob () , new Path ( outputPath ));
		giraphJob . run ( true );
		return 0;
	}
	
	public static void main ( String [] args ) throws Exception {
		ToolRunner . run ( new GiraphDemoRunner () , args );
	}

}
