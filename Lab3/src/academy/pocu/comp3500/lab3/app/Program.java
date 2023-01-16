package academy.pocu.comp3500.lab3.app;

import java.util.ArrayList;

import academy.pocu.comp3500.lab3.MissionControl;

public class Program {

    public static void main(String[] args) {
        final int[] altitudes = new int[] { 1, 2, 3, 4, 5, 6, 7, 4, 3, 2 };

        final int maxAltitudeTime = MissionControl.findMaxAltitudeTime(altitudes); // maxAltitudeTime: 6

        final int[] altitudes2 = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 10, 8, 5, 3, 1 };
        int maxAltitudeTime2 = MissionControl.findMaxAltitudeTime(altitudes2);

        final int[] altitudes3 = new int[] { 2, 4, 8, 9, 10, 12, 14, 15, 17, 14, 13, 11, 9, 6, 1 };
        int maxAltitudeTime3 = MissionControl.findMaxAltitudeTime(altitudes3);

        final int[] altitudes4 = new int[] { 1, 3, 4, 5, 6, 8, 10, 15, 16, 17, 18, 22, 26, 33, 35, 36, 37, 38, 40, 39,
                38, 37, 36, 35, 33, 20, 10 };
        int maxAltitudeTime4 = MissionControl.findMaxAltitudeTime(altitudes4);

        final int[] altitudes5 = new int[] { 1, 2, 3, 4, 5, 6, 7, 4, 3, 2 };

        ArrayList<Integer> bounds = MissionControl.findAltitudeTimes(altitudes5, 2); // bounds: [ 1, 9 ]
        bounds = MissionControl.findAltitudeTimes(altitudes5, 5); // bounds: [ 4 ]

        bounds = MissionControl.findAltitudeTimes(altitudes4, 33);
    }
}
