package academy.pocu.comp3500.lab9.app;


import academy.pocu.comp3500.lab9.CodingMan;
import academy.pocu.comp3500.lab9.ProfitCalculator;
import academy.pocu.comp3500.lab9.PyramidBuilder;
import academy.pocu.comp3500.lab9.data.Task;
import academy.pocu.comp3500.lab9.data.VideoClip;
import org.junit.Test;


public class Program {

    public static void main(String[] args) {


    }

    @Test
    public void test1() {
        VideoClip[] clips = new VideoClip[]{
                new VideoClip(0, 15),
                new VideoClip(10, 20),
                new VideoClip(30, 35)
        };

        int count = CodingMan.findMinClipsCount(clips, 10); // 1
        assert count == 1;
        count = CodingMan.findMinClipsCount(clips, 20); // 2
        assert count == 2;
        count = CodingMan.findMinClipsCount(clips, 25); // -1
        assert count == -1;
        count = CodingMan.findMinClipsCount(clips, 35); // -1
        assert count == -1;

        Task[] tasks = new Task[]{
                new Task(20, 30),
                new Task(30, 40),
                new Task(10, 35)
        };

        int profit = ProfitCalculator.findMaxProfit(tasks, new int[]{10}); // 35
        profit = ProfitCalculator.findMaxProfit(tasks, new int[]{20, 25}); // 70
        profit = ProfitCalculator.findMaxProfit(tasks, new int[]{40, 15, 5}); // 75


        int pyramidHeight = PyramidBuilder.findMaxHeight(new int[]{5}, 10); // 0
        pyramidHeight = PyramidBuilder.findMaxHeight(new int[]{6, 8}, 10); // 1
        pyramidHeight = PyramidBuilder.findMaxHeight(new int[]{3, 3, 4, 4, 30, 12, 10, 10, 6}, 5); // 3
        pyramidHeight = PyramidBuilder.findMaxHeight(new int[]{2, 2, 2, 2, 3, 3}, 1);
        assert (pyramidHeight == 2);


        // PyramidBuilder
        pyramidHeight = PyramidBuilder.findMaxHeight(new int[]{3}, 2);

        assert (pyramidHeight == 0);

        pyramidHeight = PyramidBuilder.findMaxHeight(new int[]{5, 5}, 10);

        assert (pyramidHeight == 0);

        pyramidHeight = PyramidBuilder.findMaxHeight(new int[]{5, 5}, 9);

        assert (pyramidHeight == 1);

        pyramidHeight = PyramidBuilder.findMaxHeight(new int[]{5, 4, 6}, 8);

        assert (pyramidHeight == 1);

        pyramidHeight = PyramidBuilder.findMaxHeight(new int[]{5, 6, 8, 10, 12, 16, 16}, 17);

        assert (pyramidHeight == 2);

        pyramidHeight = PyramidBuilder.findMaxHeight(new int[]{60, 40, 20, 16, 16, 12, 10, 8, 6, 5}, 10);

        assert (pyramidHeight == 3);

        pyramidHeight = PyramidBuilder.findMaxHeight(new int[]{1, 3, 4, 4, 5, 5, 5, 6, 7, 9, 11, 13, 14, 14, 15, 18}, 10);

        assert (pyramidHeight == 3);

        // ProfitCalculator
        tasks = new Task[]{
                new Task(20, 30),
        };
        int[] skillLevels = new int[]{20};

        profit = ProfitCalculator.findMaxProfit(tasks, skillLevels);

        assert (profit == 30);

        tasks = new Task[]{
                new Task(20, 30),
        };
        skillLevels = new int[]{10};

        profit = ProfitCalculator.findMaxProfit(tasks, skillLevels);

        assert (profit == 0);

        tasks = new Task[]{
                new Task(20, 50),
                new Task(20, 40)
        };
        skillLevels = new int[]{25};

        profit = ProfitCalculator.findMaxProfit(tasks, skillLevels);

        assert (profit == 50);

        tasks = new Task[]{
                new Task(20, 40),
                new Task(30, 40),
                new Task(50, 25),
                new Task(60, 45)
        };
        skillLevels = new int[]{10, 20, 35, 70, 45};

        profit = ProfitCalculator.findMaxProfit(tasks, skillLevels);

        assert (profit == 165);

        // CodingMan
        clips = new VideoClip[]{
                new VideoClip(0, 10),
        };
        int airTime = 10;

        count = CodingMan.findMinClipsCount(clips, airTime);

        assert (count == 1);

        clips = new VideoClip[]{
                new VideoClip(30, 60),
                new VideoClip(0, 20)
        };
        airTime = 60;

        count = CodingMan.findMinClipsCount(clips, airTime);

        assert (count == -1);

        clips = new VideoClip[]{
                new VideoClip(0, 5),
                new VideoClip(0, 20),
                new VideoClip(5, 30),
                new VideoClip(25, 35),
                new VideoClip(35, 70),
                new VideoClip(50, 75)
        };
        airTime = 70;

        count = CodingMan.findMinClipsCount(clips, airTime);

        assert (count == 4);

        clips = new VideoClip[]{
                new VideoClip(0, 7),
                new VideoClip(0, 15),
                new VideoClip(10, 20),
                new VideoClip(15, 25),
                new VideoClip(20, 26),
                new VideoClip(23, 30),
                new VideoClip(24, 31),
                new VideoClip(25, 32),
                new VideoClip(27, 32),
                new VideoClip(28, 32),
                new VideoClip(25, 33),
                new VideoClip(29, 33),
                new VideoClip(30, 35),
                new VideoClip(33, 35),
                new VideoClip(31, 40),
                new VideoClip(40, 50),
        };
        airTime = 35;

        count = CodingMan.findMinClipsCount(clips, airTime);

        assert (count == 4);

        clips = new VideoClip[]{
                new VideoClip(0, 7),
                new VideoClip(7, 15),
                new VideoClip(15, 20),
                new VideoClip(20, 25),
                new VideoClip(25, 35)
        };
        airTime = 35;

        count = CodingMan.findMinClipsCount(clips, airTime);

        assert (count == 5);

        clips = new VideoClip[]{
                new VideoClip(0, 7),
                new VideoClip(4, 8),
                new VideoClip(5, 15),
                new VideoClip(13, 16),
                new VideoClip(15, 34),
                new VideoClip(20, 35),
                new VideoClip(23, 37),
                new VideoClip(35, 60),
                new VideoClip(38, 62)
        };
        airTime = 61;

        count = CodingMan.findMinClipsCount(clips, airTime);

        assert (count == 6);

        clips = new VideoClip[]{
                new VideoClip(0, 3),
                new VideoClip(2, 4),
                new VideoClip(3, 5),
                new VideoClip(5, 12),
                new VideoClip(10, 17)
        };
        airTime = 13;

        count = CodingMan.findMinClipsCount(clips, airTime);

        assert (count == 4);

        clips = new VideoClip[]{
                new VideoClip(0, 3),
                new VideoClip(2, 5),
                new VideoClip(4, 8),
                new VideoClip(1, 3),
                new VideoClip(2, 3),
        };
        airTime = 7;

        count = CodingMan.findMinClipsCount(clips, airTime);

        assert (count == 3);

        clips = new VideoClip[]{
                new VideoClip(0, 3),
                new VideoClip(2, 5),
                new VideoClip(4, 8),
                new VideoClip(1, 3),
                new VideoClip(2, 3),
        };
        airTime = 9;

        count = CodingMan.findMinClipsCount(clips, airTime);

        assert (count == 3);


    }
}
