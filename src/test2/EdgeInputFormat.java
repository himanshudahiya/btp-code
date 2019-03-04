package test2;

import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.conf.LongConfOption;
import org.apache.giraph.edge.Edge;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;

import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.apache.giraph.edge.Edge;
import org.apache.giraph.edge.EdgeFactory;
import org.apache.giraph.io.EdgeReader;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import org.apache.giraph.utils.ArrayWritable;
//import org.apache.hadoop.io.ArrayWritable;
public class EdgeInputFormat extends
org.apache.giraph.io.formats.TextEdgeInputFormat<LongWritable,
ArrayWritable> {

public EdgeInputFormat() {

super();

}

protected class mTextEdgeReaderFromEachLine extends

TextEdgeReaderFromEachLine {

protected LongWritable getSourceVertexId(org.apache.hadoop.io.Text
line) {
	
return new LongWritable(

Long.parseLong(line.toString().split(":")[0]));


}


protected LongWritable getTargetVertexId(org.apache.hadoop.io.Text
line) {
	
	

return new LongWritable(

Long.parseLong(line.toString().split(":")[1]));

}


protected ArrayWritable getValue(org.apache.hadoop.io.Text line) {
	String str = line.toString().split(":")[2];
	
	String[] intArray = str.split(",");
	
	
	IntWritable[] intWriArray = new IntWritable[intArray.length];
	for (int i=0;i<intArray.length;i++) {
		intWriArray[i] = new IntWritable(Integer.parseInt(intArray[i]));
	}
		    

	ArrayWritable p = new ArrayWritable(IntWritable.class,intWriArray);

	
	return p;
	
}

}


public EdgeReader<LongWritable, ArrayWritable> createEdgeReader(

InputSplit split, TaskAttemptContext context) throws IOException {

return new mTextEdgeReaderFromEachLine();

}

}

