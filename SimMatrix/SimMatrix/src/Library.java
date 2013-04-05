/**
 *
 * @author Ke Wang Since June 2011
 */

/*
 * this is the library class which defines all the global parameters
 */

import java.io.*;

public class Library 
{
    public static int numComputeNode;         // No. of Compute Nodes of the systems
    public static int numCorePerNode;      // No. of cores per node
    public static int numTaskPerCore;

    public static double linkSpeed;    // The network link speed
    public static double netLat;    // The network latency
    public static double oneMsgCommTime;
    public static double stealMsgCommTime;
    public static double procTimePerTask;    // The processing time per job
    
    public static double taskSize;              // Job description size
    public static double maxTaskLength;
    public static long numAllTask;     // Total no. of jobs
    public static boolean taskLog;    // Log for each task or not
    public static double logTimeInterval;  // The interval to do logging
    public static double visualTimeInterval;
    public static double screenCapMilInterval;

    public static long numTaskToSubmit;
    public static long numTaskLowBd;
    public static long numTaskSubmitted;
    public static long numTaskFinished;
    public static long eventId;

    public static int numNeigh;
    public static double infoMsgSize;
    public static long numStealTask;
    public static BufferedWriter logBuffWriter = null;  // Summary log writer
    public static BufferedWriter taskBuffWriter = null; // Task log writer

    public static Runtime runtime;
    public static double currentSimuTime;
    public static String numUser;
    public static String numResource;
    public static int numThread;
    public static long numAllCore;
    public static long numFreeCore;
    public static String numPendCore;
    public static long numBusyCore;
    public static long waitQueueLength;
    public static String waitNotQueueLength;
    public static long activeQueueLength;
    public static String doneQueueLength;
    public static long deliveredTask;
    public static long oldDeliveredTask;
    public static double throughput;
    public static long successTask;
    public static String failedTask;
    public static String retriedTask;
    public static String resourceAllocated;
    public static String cacheSize;
    public static String cacheLocalHit;
    public static String cacheGlobalHit;
    public static String cacheMiss;
    public static String cacheLocalHitRatio;
    public static String cacheGlobalHitRatio;
    public static String systemCPUUser;
    public static String systemCPUSystem;
    public static String systemCPUIdle;
    public static long jvmSize;
    public static long jvmFreeSize;
    public static long jvmMaxSize;

    public static Integer[] hsInt;
    public static boolean[] target;

    public static long numMsg;
    public static long numWorkStealing;
    public static long numFailWorkStealing;

    /* write to the summary log */
    public static void printSummaryLog(long readyTaskListSize, boolean cs)
    {
        if (cs)
        {
            currentSimuTime = CentralSimulator.getSimuTime();
        }
        else
        {
            currentSimuTime = DistributedSimulator.getSimuTime();
        }
        numThread = Thread.activeCount();
        numFreeCore = numAllCore - numBusyCore;
        waitQueueLength = readyTaskListSize;
        activeQueueLength = numBusyCore;
        deliveredTask = numTaskFinished;
        throughput = (deliveredTask - oldDeliveredTask)
                / Library.logTimeInterval;
        successTask = deliveredTask;
        jvmSize = runtime.totalMemory();
        jvmFreeSize = runtime.freeMemory();
        if(jvmSize > jvmMaxSize)
        {
            jvmMaxSize = jvmSize;
        }
        String line = Double.toString(currentSimuTime) + " " + numUser + " " +
                numResource + " " + Integer.toString(numThread) + " " +
                Long.toString(numAllCore) + " " + Long.toString(numFreeCore) +
                " " + numPendCore + " " + Long.toString(numBusyCore) + " " +
                Long.toString(waitQueueLength) + " " + waitNotQueueLength + " "
                + Long.toString( activeQueueLength) + " " + doneQueueLength +
                " " + Long.toString(deliveredTask) + " " +
                Double.toString(throughput) + " " +
                Long.toString(successTask) + " " + failedTask + " " +
                retriedTask + " " + resourceAllocated + " " + numTaskSubmitted +
                " " + cacheSize + " " + cacheLocalHit + " " + cacheGlobalHit
                + " " + cacheMiss + " " + cacheLocalHitRatio + " " +
                cacheGlobalHitRatio + " " + systemCPUUser + " " +
                systemCPUSystem + " " + systemCPUIdle + " " +
                Long.toString(jvmSize) + " " + Long.toString(jvmFreeSize) + " "
                + jvmMaxSize + "\r\n";
        try
        {
            logBuffWriter.write(line);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        oldDeliveredTask = deliveredTask;
    }
}