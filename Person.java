/**
 * @author : Sharwari Salunkhe ss3398@rit.edu
 * @author : Omkar Sarde       os4802@rit.edu
 * @version : 1.1
 */

/**
 * Person class
 */
public class Person extends Thread implements Comparable<Person> {
    static Object lock ;
    int number;
    int weight;
    int entry;
    int destination;
    boolean workStatus = false;
    String direction,personName;
    Building building;

    /**
     * Constructor
     * @param num Person number
     * @param w weight of the person
     * @param in entry floor
     * @param out exit floor
     * @param c building
     * @param lck object
     */
    Person(int num, int w, int in, int out, Building c, Object lck) {
        this.weight = w;
        this.entry = in;
        this.destination = out;
        this.building = c;
        this.number = num;
        this.lock = lck;
        this.personName = "Person "+ number;

        System.out.println("Person Created :\nName: " + personName + " weight : "
                + weight + " entry Floor : " + entry + " destination : " + destination + "\n");
        if(entry == destination ) {
            System.out.println("Source floor and destination floor cannot be the same.");
            System.exit(1);
        }

        direction = (entry < destination) ? "up" : "down";
    }

    /**
     * Call for elevator to enter from lobby to workplace
     */
    public void getToWork(){
        try {   synchronized (building) {
            building.callElevator(this);
        }
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    /**
     * Method call for elevator so as to go back to lobby
     */
    public void doWork(){
        try { synchronized (lock) {
            System.out.println("Person: " + this + " has reached work, will start work at floor: " + this.destination);
            sleep(2900);
            int temp = entry;
            entry = destination;
            destination = temp;
            if (direction.equals("up")) {
                direction = "down";
            } else {
                direction = "up";
            }
            workStatus = true;
            System.out.println("Person: " + this + " has finished work, moving to exit from at floor: " + this.entry);
            pause();
            building.callElevator(this);
            lock.wait();
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Thread run method
     */
    public void run() {
        try{
            pause();
            getToWork();
            pause();
            synchronized (this){
                wait();
            }
            pause();
            doWork();
            pause();
        } catch(InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * Thread has completed its task on the floor. Thread is in the elevator.
     * Method helps thread to get out of the elevator
     */
    void doWorkNotify( ) {
        try {
            pause();
            synchronized (lock) {
                lock.notify();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Activates thread when it reaches its target floor
     */
    void goToWorkNotify( ) {
        try {
            pause();
            synchronized (this) {
                this.notify();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Compares entry floor for each person
     * @param p
     * @return
     */
    public int compareTo( Person p ) {
        if( this.entry < p.entry) {
            return -1;
        }
        else if( this.entry > p.entry) {
            return 1;
        } else {
            return 0;
        }

    }

    /**
     * To avoid deadlocks and starvation
     */
    void pause(){
        try {
            sleep(10);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * toString method
     * @return
     */
    public String toString() {
        return  personName;
    }
}