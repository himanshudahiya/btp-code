package test2;


/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.conf.LongConfOption;
import org.apache.giraph.edge.Edge;
import org.apache.giraph.graph.Vertex;
//import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Writable;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.hadoop.io.Text;
//import org.apache.hadoop.io.ArrayWritable;
import org.apache.giraph.utils.ArrayWritable;
/**
 * Demonstrates the basic Pregel shortest paths implementation.
 */

public class ShortestPath extends BasicComputation<
    LongWritable, DoubleWritable,ArrayWritable, DoubleWritable> {
	
  /** The shortest paths id */
   static  int source,time_instant;
	static {
		Scanner input = new Scanner(System.in);
    System.out.print("Enter an source: ");
     source = input.nextInt();
    System.out.print("Enter time instant: ");
     time_instant = input.nextInt();
	}
  
  public static final LongConfOption SOURCE_ID =
      new LongConfOption("SimpleShortestPathsVertex.sourceId", source,
          "The shortest paths id");
  
  public static final LongConfOption TIME_ID =
	      new LongConfOption("SimpleShortestPathsVertex.timeId", time_instant,
	          "The shortest paths timeId");

  /** Class logger */
  private static final Logger LOG =
      Logger.getLogger(ShortestPath.class);

  /**
   * Is this vertex the source id?
   *
   * @param vertex Vertex
   * @return True if the source id
   */
  
  private boolean isSource(Vertex<LongWritable, DoubleWritable, ArrayWritable> vertex) {
    return vertex.getId().get() == SOURCE_ID.get(getConf());
    
  }

  
  @Override
  public void compute(
		 
      Vertex<LongWritable, DoubleWritable, ArrayWritable> vertex,
      Iterable<DoubleWritable> messages) throws IOException {
	  
//	  System.out.println("Enter compute------------------------------------");
  	  
	    if (getSuperstep() == 0) {
	        vertex.setValue(new DoubleWritable(Double.MAX_VALUE));
	      }
	      double minDist = isSource(vertex) ? TIME_ID.get(getConf()) : Double.MAX_VALUE;
	      for (DoubleWritable message : messages) {
	        minDist = Math.min(minDist, message.get());
	      }
	      if (LOG.isDebugEnabled()) {
	        LOG.debug("Vertex " + vertex.getId() + " got minDist = " + minDist +
	            " vertex value = " + vertex.getValue());
	      }
	      if (minDist < vertex.getValue().get()) {
	    
	        vertex.setValue(new DoubleWritable(minDist));
	        for (Edge<LongWritable, ArrayWritable> edge : vertex.getEdges()) {
	        	
	        	
	        	
	        	ArrayWritable intArrays =  edge.getValue();
	        	
	        	IntWritable[] beforearray =   (IntWritable[]) intArrays.get();
	        	
	        	
	        	
	        	double distance =(double)beforearray[(int) minDist].get();
	      
	          sendMessage(edge.getTargetVertexId(), new DoubleWritable(distance));
	        } 
	      }
	      
	      vertex.voteToHalt();
	    }

}