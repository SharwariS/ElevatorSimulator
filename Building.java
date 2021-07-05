
import java.util.Random;

/**
 * @author : Sharwari Salunkhe ss3398@rit.edu
 * @author : Omkar Sarde       os4802@rit.edu
 * @version : 1.1
 */

class Building {
    static Object lock ="1";
    Elevator elevator;

    /**
     * Constructor
     */
    Building() {
        elevator = new Elevator();
        elevator.start();

    }

    /**
     * Call for the elevator
     * @param p Person entering the elevator
     */
    void callElevator(Person p) {

        if(p.direction == "up")
            elevator.upwards.addIncreasing(p);
        else
            elevator.downwards.addDecreasing(p);
    }

    /**
     * Driver function
     * @param args
     */
    public static void main(String args[]) {
        Building c = new Building();
        c.testCase();


    }

    /**
     * Generates destination floor, weight randomly for each person
     */
    void testCase( ) {
        elevator.building = this;
        Person person;
        Random random = new Random();
        int number,weight,entry,destination;
        for(int i =0;i<10;i++){
            number = i+1;
            weight = random.nextInt(150)+100;
            entry = 1;
            destination = random.nextInt(Elevator.numFloors)+1;
            if(entry == destination) {
                while (entry == destination) {
                    destination = random.nextInt(Elevator.numFloors) + 1;
                }
            }
            person = new Person(number,weight,entry,destination,this,lock);
            person.start();
        }
        try{
            Thread.sleep(1000);

        }catch(Exception e){ e.printStackTrace(); }
        elevator.interrupt();
    }
}