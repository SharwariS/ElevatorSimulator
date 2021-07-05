/**
 * @author : Sharwari Salunkhe ss3398@rit.edu
 * @author : Omkar Sarde       os4802@rit.edu
 * @version : 1.1
 */

/**
 * This program implements a elevator using Threads
 */
public class Elevator extends Thread {
    /*
    Member variables
     */
    final static int maxWeight = 700;
    final static int minWeight = 100;
    final static int minFloor = 1;
    final static int numFloors = 5;
    static int currentFloor;
    String direction;
    String door;
    Building building = null;

    /*
    Linked List of people
     */
    LinkedList<Person> upwards;
    LinkedList<Person> downwards;
    LinkedList<Person> inElevator;

    /**
     * Constructor
     */
    Elevator() {
        currentFloor = minFloor;
        direction = "up";
        door = "closed";

        upwards = new LinkedList<>();
        downwards = new LinkedList<>();
        inElevator = new LinkedList<>();

    }

    /**
     * Thread run method
     */
    public void run() {
        while (true) {
            if (isInterrupted()) {
                interrupted();
                while (upwards.head != null || downwards.head != null || inElevator.head != null) {
                    if (direction == "up" && currentFloor <= numFloors) {
                        pause();
                        moveUp();
                        pause();
                        direction = "down";
                    } else if (direction == "down" && currentFloor >= minFloor) {
                        pause();
                        moveDown();
                        pause();
                        direction = "up";
                    }
                }
                break;
            }

        }
    }

    /**
     * Method to move upwards in the building
     */
    void moveUp() {
        //First person in line to move up through elevator
        Node<Person> currentInUpQueue = upwards.head;
        //First person inside the elevator
        Node<Person> currentInElevator = inElevator.head;
        LinkedList<Person> waitingQueue = new LinkedList<>();
        boolean isDoorOpen = false;
        while (currentInUpQueue != null || currentInElevator != null) {

            System.out.println("\n------------------ FLOOR : " + currentFloor + " : ---------------------\n");

            if (currentInElevator != null) {

                currentInElevator = inElevator.head;
                while (currentInElevator != null) {
                    if (currentInElevator.data.destination == currentFloor) {
                        if (!isDoorOpen) {
                            pause();
                            openDoor();
                            pause();
                            isDoorOpen = false;
                        }
                        // Remove the person whose destination floor has arrived
                        inElevator.remove(currentInElevator.data);
                        System.out.println("Person : " + currentInElevator.data + " is leaving lift");
                        currentInElevator.data.goToWorkNotify();
                        pause();
                        if(currentInElevator.data.workStatus==false){
                            currentInElevator.data.doWorkNotify();
                        }
                        pause();
                    }
                    currentInElevator = currentInElevator.next;
                }

            }
            if (currentInUpQueue != null) {
                if (currentInUpQueue.data.entry == currentFloor) {
                    pause();
                    openDoor();
                    pause();
                    while (currentInUpQueue.data.entry == currentFloor) {
                        System.out.println("Trying to take in : " + currentInUpQueue.data + " at floor : " + currentFloor);
                        inElevator.addIncreasing(currentInUpQueue.data);
                        if (checkForWeight()) {
                            pause();
                            inElevator.remove(currentInUpQueue.data);
                            pause();
                            System.out.println("All cannot fit in elevator");
                            System.out.println("People will be served in ascending order");
                            pause();
                            upwards.remove(currentInUpQueue.data);
                            pause();
                            waitingQueue.addIncreasing(currentInUpQueue.data);
                        } else {
                            upwards.remove(currentInUpQueue.data);
                        }
                        currentInUpQueue = currentInUpQueue.next;
                        if (currentInUpQueue == null)
                            break;
                    }
                    closeDoor();
                }
            } else {
                closeDoor();
            }
            System.out.println("In Elevator : " + inElevator);
            currentInUpQueue = upwards.head;
            currentInElevator = inElevator.head;


            if (currentInUpQueue != null || currentInElevator != null)
                if(currentFloor + 1 <=numFloors) {
                    currentFloor = currentFloor + 1;
                }
        }
        Node<Person> currentInWaiting = waitingQueue.head;
        while (currentInWaiting != null) {
            upwards.addIncreasing(currentInWaiting.data);
            currentInWaiting = currentInWaiting.next;
        }

        if (downwards.head != null) {
            int downFloorStart = downwards.head.data.entry;
            if (downFloorStart > currentFloor) {
                while (downFloorStart != currentFloor) {
                    currentFloor = currentFloor + 1;
                    System.out.println("\n------------------ FLOOR : " + currentFloor + " : ---------------------\n");
                    System.out.println("In Elevator : " + inElevator);

                }

            }
        }


    }

    /**
     * Method to move downwards in the building
     */
    void moveDown() {

        Node<Person> currentInDownQueue = downwards.head;
        Node<Person> currentInElevator = inElevator.head;
        LinkedList<Person> tempList = new LinkedList<>();
        boolean isDoorOpen = false;

        while (currentInDownQueue != null || currentInElevator != null) {
            System.out.println("\n------------------ FLOOR : " + currentFloor + " : ---------------------\n");
            if (currentInElevator != null) {
                currentInElevator = inElevator.head;
                while (currentInElevator != null) {
                    if (currentInElevator.data.destination == currentFloor) {
                        if (!isDoorOpen) {
                            openDoor();
                            isDoorOpen = false;
                        }
                        inElevator.remove(currentInElevator.data);
                        System.out.println("Person : " + currentInElevator.data + " is leaving lift");
                        currentInElevator.data.goToWorkNotify();
                        pause();
                        if(currentInElevator.data.workStatus==true){
                            currentInElevator.data.doWorkNotify();
                        }
                        pause();
                    }
                    currentInElevator = currentInElevator.next;
                }
            }

            if (currentInDownQueue != null) {
                if (currentInDownQueue.data.entry == currentFloor) {
                    pause();
                    openDoor();
                    pause();
                    while (currentInDownQueue.data.entry == currentFloor) {
                        System.out.println("Trying to take in : " + currentInDownQueue.data + " at floor : " + currentFloor);
                        inElevator.addDecreasing(currentInDownQueue.data);
                        if (checkForWeight()) {
                            pause();
                            inElevator.remove(currentInDownQueue.data);
                            pause();
                            System.out.println("All cannot fit in elevator");
                            System.out.println("People will be served in ascending order");
                            pause();
                            downwards.remove(currentInDownQueue.data);
                            pause();
                            tempList.addDecreasing(currentInDownQueue.data);
                        } else {
                            downwards.remove(currentInDownQueue.data);
                        }

                        currentInDownQueue = currentInDownQueue.next;
                        if (currentInDownQueue == null)
                            break;
                    }

                    closeDoor();

                }
            } else {
                closeDoor();
            }
            System.out.println("In Elevator : " + inElevator);
            currentInDownQueue = downwards.head;
            currentInElevator = inElevator.head;
            if (currentInDownQueue != null || currentInElevator != null)
                if(currentFloor - 1 >=minFloor) {
                    currentFloor = currentFloor - 1;
                }
        }
        /*
        If weight of the elevator exceeds 700 pounds,
        people added in the tempList
         */
        Node<Person> currentPersonInTempList = tempList.head;
        while (currentPersonInTempList != null) {
            downwards.addDecreasing(currentPersonInTempList.data);
            currentPersonInTempList = currentPersonInTempList.next;
        }
        if (upwards.head != null) {
            int upFloorStart = upwards.head.data.entry;
            if (upFloorStart < currentFloor) {
                while (upFloorStart != currentFloor) {
                    currentFloor = currentFloor - 1;
                    System.out.println("\n------------------ FLOOR : " + currentFloor + " : ---------------------\n");
                    System.out.println("Inlist : " + inElevator);
                }
            }
        }
    }

    /**
     * Method to check maximum weight of people in elevator
     * as it should not weigh more than 700 pounds
     * @return
     */
    boolean checkForWeight() {
        boolean overWeightCapacity = false;
        Node<Person> currentPersonInElevator = inElevator.head;
        int totalWeight = 0;
        while (currentPersonInElevator != null) {
            totalWeight = totalWeight + currentPersonInElevator.data.weight;
            currentPersonInElevator = currentPersonInElevator.next;
        }

        if (totalWeight > maxWeight)
            overWeightCapacity = true;

        return overWeightCapacity;
    }

    /**
     * Mthod to open elevator door
     */
    void openDoor() {
        door = "open";
    }

    /**
     * Method to close elevator door
     */
    void closeDoor() {
        door = "closed";
    }

    /**
     * Sleep method to avoid starvation and deadlocks
     */
    void pause(){
        try {
            sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}



