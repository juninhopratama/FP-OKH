import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;


public class Main
{
    static String fileName[][] = {{"car-s-91", "CAR91"}, {"car-f-92", "CAR92"}, {"ear-f-83", "EAR83"}, {"hec-s-92", "HEC92"}, {"kfu-s-93", "KFU93"}, {"lse-f-91", "LSE91"}, {"pur-s-93", "Pur92"}, {"rye-s-93", "RYE92"}, {"sta-f-83", "STA83"}, {"tre-s-92", "TRE92"}, {"uta-s-92", "UTA92"}, {"ute-s-92", "UTE92"}, {"yor-f-83", "YOR83"}};;
    static int[][] conflictMatrix, sortedCourse, weightedClashMatrix, sortedWeightedCourse;
    static int timeslot[];

    public static void main(String[] args) throws IOException
    {
        System.out.println("");
        System.out.println("List Dataset: ");

        for(int i=0; i< fileName.length; i++)
        {
            System.out.println(i+1 +". "+  fileName[i][1]);
        }

        System.out.print("\nPilih dataset  : ");
        Scanner input = new Scanner(System.in);

        int dataset = input.nextInt();

        System.out.print("Jumlah iterasi : ");
        int itr = input.nextInt();

        String filePilihanInput = fileName[dataset-1][0];
        System.out.println("\n================================================\n");

        CourseData course = new CourseData(filePilihanInput);

        //Mendapatkan conflict_matrix:
        conflictMatrix = course.getConflictMatrix();
        course.showConflictMatrix(50);
        System.out.println(" ");

        //Mendapatkan hasil sorting largest degree:
        sortedCourse = course.sortByDegree();
        System.out.println("\n================================================\n");

        //Melakukan scheduling (Largest Degree)
        ExamScheduling sch = new ExamScheduling(conflictMatrix, sortedCourse);
        long startTimeLD = System.nanoTime();
        timeslot = sch.scheduleByDegree();
        long endTimeLD = System.nanoTime();

        //Mengecek apakah ditemukan konflik pada schedule
        System.out.println(" ");
        System.out.println("Apakah ada Konflik? : "+ sch.isConflicted());
        System.out.print("");

        int minimumTimeslot = sch.getTimeslot();
        System.out.println("Minimal Timeslots: "+ minimumTimeslot);
        System.out.println(Penalty.countPenalty(course.getStudentData(), timeslot));
        double initialPenalty = Penalty.countPenalty(course.getStudentData(), timeslot);

        long timeElapsed = endTimeLD - startTimeLD;
        System.out.println("Largest Degree Initial Solution Execution time in milliseconds : " +
                timeElapsed / 1000000);

//-------------------------------------Hill Climbiing--------------------------------//

    //    HillClimbing hcl = new HillClimbing(conflictMatrix, timeslot, itr, minimumTimeslot, course.getStudentData());
    //    long startTimeHC = System.nanoTime();
    //    hcl.optimizeTimeslot();
    //    long endTimeHC = System.nanoTime();
    //    double penaltyHC = hcl.finalPenalty;
    //    long timeElapsedHC = endTimeHC - startTimeHC;

    //    double delta = ((initialPenalty-penaltyHC)/initialPenalty)*100;

    //    System.out.println();
    //    System.out.println("Initial penalty = " + initialPenalty);
    //    System.out.println("Final penalty = " + penaltyHC);
    //    System.out.println("Delta = " + delta + "%");
    //    System.out.println("Hill Climbing execution time in seconds : " +
    //            timeElapsedHC / 1000000000);




//--------------------------------Random Meta Heuristic--------------------------------//

        RandomMetaheuristic RMH = new RandomMetaheuristic(conflictMatrix, timeslot, itr, minimumTimeslot, course.getStudentData());
        long startTimeRMH = System.nanoTime();
        RMH.optimizeTimeslot();
        long endTimeRMH = System.nanoTime();
        double penaltyRMH = RMH.finalPenalty;
        long timeElapsedRMH = endTimeRMH - startTimeRMH;

        double delta = ((initialPenalty-penaltyRMH)/initialPenalty)*100;

        System.out.println();
        System.out.println("Initial penalty = " + initialPenalty);
        System.out.println("Final penalty = " + penaltyRMH);
        System.out.println("Delta = " + delta + "%");
        System.out.println("Random Meta Heuristic execution time in seconds : " +
                timeElapsedRMH / 1000000000);


     }
}
