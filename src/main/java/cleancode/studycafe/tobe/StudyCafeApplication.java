package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.io.LockerPassFileReader;
import cleancode.studycafe.tobe.io.SeatPassFileReader;
import cleancode.studycafe.tobe.provider.LockerPassProvider;
import cleancode.studycafe.tobe.provider.SeatPassProvider;

public class StudyCafeApplication {

    public static void main(String[] args) {
        SeatPassProvider seatPassFileReader = new SeatPassFileReader();
        LockerPassProvider lockerPassProvider = new LockerPassFileReader();

        StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(seatPassFileReader, lockerPassProvider);
        studyCafePassMachine.run();
    }

}