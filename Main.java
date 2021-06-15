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

        // long startTime = System.nanoTime();
        int dataset = input.nextInt();
        long startTime = System.nanoTime();
        
        String filePilihanInput = fileName[dataset-1][0];
        System.out.println("\n================================================\n");
        
        CourseData course = new CourseData(filePilihanInput);

        int jumlahCourse = course.getNumberOfCourses();
        conflictMatrix = new int[jumlahCourse][jumlahCourse];

        //mendapatkan conflict_matrix:
        conflictMatrix = course.getConflictMatrix();
        System.out.println(" ");

        //mendapatkan weightedClashMatrix:
        weightedClashMatrix = course.getWeightedClashMatrix();
        System.out.println(" ");

        //mendapatkan hasil sorting largest degree:
        sortedCourse = course.sortByDegree(conflictMatrix, jumlahCourse);
        System.out.println("\n================================================\n");

//        mendapatkan hasil sorting largest weighted degree:
//        sortedWeightedCourse = course.sortByWeightedDegree(weightedClashMatrix, jumlahCourse);
//        System.out.println("\n================================================\n");

        //melakukan scheduling (Largest Degree)
        ExamScheduling sch = new ExamScheduling(filePilihanInput, conflictMatrix, jumlahCourse);
        timeslot = sch.scheduleByDegree(sortedCourse,jumlahCourse);

//        melakukan scheduling (Largest Weighted Degree)
//        ExamScheduling sch = new ExamScheduling(filePilihanInput, conflictMatrix, jumlahCourse);
//        timeslot = sch.scheduleByWeightedDegree(sortedWeightedCourse,jumlahCourse);

        //mengecek apakah ditemukan konflik pada schedule
        System.out.println(" ");
        System.out.println("Apakah ada Konflik? : "+ sch.isConflicted());
        System.out.println(Penalty.countPenalty(course.getStudentData(), timeslot));
        sch.getTimeslot();
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in milliseconds : " +
                timeElapsed / 1000000);
    }
}
