public class Person {

    private int pId;
    private String name;
    private int age;

    //Default Constructor
    private Person() {
    }

    //Parameterized Constructor
    public Person(int pId, String name, int age) {
        this.pId = pId;
        this.name = name;
        this.age = age;
    }

    public int getpId() {
        return pId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
