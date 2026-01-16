import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListAssignment {
    public static void main(String[] args) {
//    Scanner allows to take user input
        Scanner sc = new Scanner(System.in);

//    Defining a List
        List<Person> personList = new ArrayList<>();

//    Defining Size of the List so that we know how many times the user needs to be asked for input details
        System.out.println("Enter the size of the List : ");
        int personCount = sc.nextInt();

        for (int i = 0; i < personCount; i++) {
//        Asks user to enter ID as input
            System.out.println("Enter the person ID : ");
            int pId = sc.nextInt(); //Capturing user input as Person ID

//        Asks user to enter Name as input
            System.out.println("Enter the name of the Person : ");
            sc.nextLine(); //to prevent new line to be skipped
            String name = sc.nextLine();//Capturing user input as Person Name
//        Asks user to enter Age as input
            System.out.println("Enter the age of the Person : ");
            int age = sc.nextInt();//Capturing user input as Person Age

//      Adding all captured details into the person object in the person List
            personList.add(new Person(pId, name, age));
        }
//        for (Person p : personList){
//            System.out.println("Person ID: "+p.pId+" , Name: "+p.name+" , Age: "+p.age);
//        }
//        Asks User Input for ID to search in the person List
        System.out.println("Enter the ID to search for the person : ");
        int searchId = sc.nextInt();

//    Retrieving Person details using ID as input parameter
        getPersonDetailsById(personList, searchId);

        sc.close(); //Scanner is closed
    }

    /***
     * This method gets the Person details from persons List searching with ID
     * @param personList
     * @param personID
     * Prints person details if found. Else, it will print person not found
     */
    public static void getPersonDetailsById(List<Person> personList, int personID) {
        for (Person p : personList) {
            if (p.getpId() == personID) {
                System.out.println("Person Name : " + p.getName() + " , Person Age : " + p.getAge());
                return;
            }
        }
        System.out.println("Person with ID " + personID + " is not found");
    }
}
