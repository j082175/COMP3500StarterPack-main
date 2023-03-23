package academy.pocu.comp3500.lab10.app;

import academy.pocu.comp3500.lab10.Project;
import academy.pocu.comp3500.lab10.project.Task;
import org.junit.jupiter.api.Test;
import java.util.List;

public class Program {

    private static void shiftOnce(Task[] tasks) {
        Task right = tasks[tasks.length - 1];

        for (int i = 1; i < tasks.length; i++) {
            tasks[tasks.length -i] = tasks[tasks.length - 1 - i];
        }

        tasks[0] = right;
    }

    public static void main(String[] args) {

    }


    @Test
    public void test1() {
        {
            Task a = new Task("A", 15);
            Task b = new Task("B", 12);
            Task c = new Task("C", 11);

            c.addPredecessor(b);
            b.addPredecessor(a);

            Task[] tasks = new Task[]{b, c, a};

            List<String> schedule = Project.findSchedule(tasks, false);

            assert (schedule.size() == 3);
            assert (schedule.get(0).equals("A"));
            assert (schedule.get(1).equals("B"));
            assert (schedule.get(2).equals("C"));
        }

        {
            Task[] tasks = createTasks();

            List<String> schedule = Project.findSchedule(tasks, false);

            assert (schedule.size() == 6);
            assert (schedule.get(0).equals("A"));
            assert (schedule.get(1).equals("B"));
            assert (schedule.get(2).equals("C"));
            assert (schedule.get(3).equals("E"));
            assert (schedule.get(4).equals("F"));
            assert (schedule.get(5).equals("I"));
        }

        {
            Task[] tasks = createTasks();

            List<String> schedule = Project.findSchedule(tasks, true);

            assert (schedule.size() == 9);
            assert (schedule.get(0).equals("A"));
            assert (schedule.get(1).equals("B"));
            assert (schedule.get(2).equals("C"));
            assert (schedule.indexOf("C") < schedule.indexOf("E"));
            assert (schedule.indexOf("E") < schedule.indexOf("F"));
            assert (schedule.indexOf("F") < schedule.indexOf("I"));

            assert (schedule.indexOf("C") < schedule.indexOf("D"));
            assert (schedule.indexOf("D") < schedule.indexOf("G"));
            assert (schedule.indexOf("G") < schedule.indexOf("H"));
        }


        {

            Task[] tasks = createTasks8_2loop();
            List<String> schedule = Project.findSchedule(tasks, true);
            for (int i = 0; i < tasks.length; i++) {
                tasks = createTasks8_2loop();
                schedule = Project.findSchedule(tasks, true);
                assert (schedule.size() == 9);
                assert (schedule.get(0).equals("A"));
                assert (schedule.get(1).equals("B"));
                assert (schedule.indexOf("B") < schedule.indexOf("H"));
                assert (schedule.indexOf("B") < schedule.indexOf("C"));
                assert (schedule.indexOf("C") < schedule.indexOf("D"));
                assert (schedule.indexOf("D") < schedule.indexOf("E"));
                assert (schedule.indexOf("C") < schedule.indexOf("F"));
                assert (schedule.indexOf("F") < schedule.indexOf("I"));
                assert (schedule.indexOf("I") < schedule.indexOf("G"));

                tasks = createTasks8_2loop();
                schedule = Project.findSchedule(tasks, false);
                assert (schedule.size() == 3);
                assert (schedule.get(0).equals("A"));
                assert (schedule.get(1).equals("B"));
                assert (schedule.get(2).equals("H"));
                shiftOnce(tasks);
            }


            tasks = createTasks8_3loop();
            schedule = Project.findSchedule(tasks, true);
            for (int i = 0; i < tasks.length; i++) {
                tasks = createTasks8_3loop();
                schedule = Project.findSchedule(tasks, true);
                assert (schedule.size() == 31);
                assert (schedule.indexOf("A") < schedule.indexOf("B"));
                assert (schedule.indexOf("A") < schedule.indexOf("R"));
                assert (schedule.indexOf("B") < schedule.indexOf("C"));
                assert (schedule.indexOf("C") < schedule.indexOf("G"));
                assert (schedule.indexOf("F") < schedule.indexOf("G"));
                assert (schedule.indexOf("J") < schedule.indexOf("G"));
                assert (schedule.indexOf("E") < schedule.indexOf("F"));
                assert (schedule.indexOf("E") < schedule.indexOf("J"));
                assert (schedule.indexOf("D") < schedule.indexOf("E"));
                assert (schedule.indexOf("I") < schedule.indexOf("E"));
                assert (schedule.indexOf("S") < schedule.indexOf("E"));
                assert (schedule.indexOf("H") < schedule.indexOf("I"));
                assert (schedule.indexOf("H") < schedule.indexOf("S"));
                assert (schedule.indexOf("G") < schedule.indexOf("K"));
                assert (schedule.indexOf("K") < schedule.indexOf("L"));
                assert (schedule.indexOf("L") < schedule.indexOf("M"));
                assert (schedule.indexOf("M") < schedule.indexOf("N"));
                assert (schedule.indexOf("G") < schedule.indexOf("O"));
                assert (schedule.indexOf("G") < schedule.indexOf("P"));
                assert (schedule.indexOf("G") < schedule.indexOf("Q"));
                assert (schedule.indexOf("Q") < schedule.indexOf("A1"));
                assert (schedule.indexOf("A1") < schedule.indexOf("B1"));
                assert (schedule.indexOf("B1") < schedule.indexOf("H1"));
                assert (schedule.indexOf("B1") < schedule.indexOf("C1"));
                assert (schedule.indexOf("C1") < schedule.indexOf("F1"));
                assert (schedule.indexOf("F1") < schedule.indexOf("G1"));
                assert (schedule.indexOf("C1") < schedule.indexOf("D1"));
                assert (schedule.indexOf("D1") < schedule.indexOf("E1"));
                assert (schedule.indexOf("C1") < schedule.indexOf("I1"));
                assert (schedule.indexOf("I1") < schedule.indexOf("J1"));
                assert (schedule.indexOf("C1") < schedule.indexOf("K1"));
                assert (schedule.indexOf("K1") < schedule.indexOf("L1"));

                tasks = createTasks8_3loop();
                schedule = Project.findSchedule(tasks, false);
                assert (schedule.size() == 18);
                assert (schedule.indexOf("A") < schedule.indexOf("B"));
                assert (schedule.indexOf("A") < schedule.indexOf("R"));
                assert (schedule.indexOf("B") < schedule.indexOf("C"));
                assert (schedule.indexOf("C") < schedule.indexOf("G"));
                assert (schedule.indexOf("F") < schedule.indexOf("G"));
                assert (schedule.indexOf("J") < schedule.indexOf("G"));
                assert (schedule.indexOf("E") < schedule.indexOf("F"));
                assert (schedule.indexOf("E") < schedule.indexOf("J"));
                assert (schedule.indexOf("D") < schedule.indexOf("E"));
                assert (schedule.indexOf("I") < schedule.indexOf("E"));
                assert (schedule.indexOf("S") < schedule.indexOf("E"));
                assert (schedule.indexOf("H") < schedule.indexOf("I"));
                assert (schedule.indexOf("H") < schedule.indexOf("S"));
                assert (schedule.indexOf("G") < schedule.indexOf("O"));
                assert (schedule.indexOf("G") < schedule.indexOf("P"));
                assert (schedule.indexOf("G") < schedule.indexOf("Q"));
                assert (schedule.indexOf("Q") < schedule.indexOf("A1"));
                assert (schedule.indexOf("A1") < schedule.indexOf("B1"));
                assert (schedule.indexOf("B1") < schedule.indexOf("H1"));
                shiftOnce(tasks);
            }


            tasks = createTasks2();
            schedule = Project.findSchedule(tasks, true);
            tasks = createTasks2();
            schedule = Project.findSchedule(tasks, false);

            tasks = createTasks3();
            schedule = Project.findSchedule(tasks, true);
            tasks = createTasks3();
            schedule = Project.findSchedule(tasks, false);

            tasks = createTasks4();
            schedule = Project.findSchedule(tasks, true);
            tasks = createTasks4();
            schedule = Project.findSchedule(tasks, false);

            tasks = createTasks5();
            schedule = Project.findSchedule(tasks, true);
            tasks = createTasks5();
            schedule = Project.findSchedule(tasks, false);

            tasks = createTasks6();
            schedule = Project.findSchedule(tasks, true);
            tasks = createTasks6();
            schedule = Project.findSchedule(tasks, false);

            tasks = createTasks8_4loop();
            schedule = Project.findSchedule(tasks, true);
            for (int i = 0; i < tasks.length; i++) {
                tasks = createTasks8_4loop();
                schedule = Project.findSchedule(tasks, true);
                assert (schedule.size() == 29);
                assert (schedule.indexOf("O") < schedule.indexOf("N"));
                assert (schedule.indexOf("P") < schedule.indexOf("N"));
                assert (schedule.indexOf("Q") < schedule.indexOf("N"));
                assert (schedule.indexOf("N") < schedule.indexOf("A"));
                assert (schedule.indexOf("M") < schedule.indexOf("A"));
                assert (schedule.indexOf("A") < schedule.indexOf("B"));
                assert (schedule.indexOf("B") < schedule.indexOf("B0"));
                assert (schedule.indexOf("B0") < schedule.indexOf("B1"));
                assert (schedule.indexOf("B1") < schedule.indexOf("B2"));
                assert (schedule.indexOf("B") < schedule.indexOf("B3"));
                assert (schedule.indexOf("B3") < schedule.indexOf("B4"));
                assert (schedule.indexOf("B4") < schedule.indexOf("B5"));
                assert (schedule.indexOf("B") < schedule.indexOf("H"));
                assert (schedule.indexOf("H") < schedule.indexOf("H1"));
                assert (schedule.indexOf("B") < schedule.indexOf("R"));
                assert (schedule.indexOf("B") < schedule.indexOf("C"));
                assert (schedule.indexOf("C") < schedule.indexOf("F"));
                assert (schedule.indexOf("F") < schedule.indexOf("G1"));
                assert (schedule.indexOf("G1") < schedule.indexOf("G"));
                assert (schedule.indexOf("C") < schedule.indexOf("D"));
                assert (schedule.indexOf("D") < schedule.indexOf("E1"));
                assert (schedule.indexOf("E1") < schedule.indexOf("E"));
                assert (schedule.indexOf("C") < schedule.indexOf("I"));
                assert (schedule.indexOf("I") < schedule.indexOf("J1"));
                assert (schedule.indexOf("J1") < schedule.indexOf("J"));
                assert (schedule.indexOf("C") < schedule.indexOf("K"));
                assert (schedule.indexOf("K") < schedule.indexOf("K1"));
                assert (schedule.indexOf("K1") < schedule.indexOf("L"));

                tasks = createTasks8_4loop();
                schedule = Project.findSchedule(tasks, false);
                assert (schedule.size() == 6);
                assert (schedule.indexOf("O") < schedule.indexOf("N"));
                assert (schedule.indexOf("P") < schedule.indexOf("N"));
                assert (schedule.indexOf("Q") < schedule.indexOf("N"));
                assert (schedule.indexOf("N") < schedule.indexOf("A"));
                assert (schedule.indexOf("M") < schedule.indexOf("A"));

                shiftOnce(tasks);
            }


            tasks = createTaskscycleErrorloop();
            schedule = Project.findSchedule(tasks, false);
            for (int i = 0; i < tasks.length; i++) {
                tasks = createTaskscycleErrorloop();
                schedule = Project.findSchedule(tasks, true);
                assert (schedule.size() == 14);
                assert (schedule.indexOf("1") < schedule.indexOf("2"));
                assert (schedule.indexOf("2") < schedule.indexOf("3"));
                assert (schedule.indexOf("1") < schedule.indexOf("5"));
                assert (schedule.indexOf("5") < schedule.indexOf("6"));
                assert (schedule.indexOf("2") < schedule.indexOf("6"));
                assert (schedule.indexOf("3") < schedule.indexOf("4"));
                assert (schedule.indexOf("6") < schedule.indexOf("4"));
                assert (schedule.indexOf("4") < schedule.indexOf("7"));
                assert (schedule.indexOf("4") < schedule.indexOf("9"));
                assert (schedule.indexOf("7") < schedule.indexOf("8"));
                assert (schedule.indexOf("9") < schedule.indexOf("8"));
                assert (schedule.indexOf("4") < schedule.indexOf("10"));
                assert (schedule.indexOf("10") < schedule.indexOf("11"));
                assert (schedule.indexOf("11") < schedule.indexOf("12"));
                assert (schedule.indexOf("10") < schedule.indexOf("13"));
                assert (schedule.indexOf("13") < schedule.indexOf("14"));

                tasks = createTaskscycleErrorloop();
                schedule = Project.findSchedule(tasks, false);
                assert (schedule.size() == 9);
                assert (schedule.indexOf("1") < schedule.indexOf("2"));
                assert (schedule.indexOf("2") < schedule.indexOf("3"));
                assert (schedule.indexOf("1") < schedule.indexOf("5"));
                assert (schedule.indexOf("5") < schedule.indexOf("6"));
                assert (schedule.indexOf("2") < schedule.indexOf("6"));
                assert (schedule.indexOf("3") < schedule.indexOf("4"));
                assert (schedule.indexOf("6") < schedule.indexOf("4"));
                assert (schedule.indexOf("4") < schedule.indexOf("7"));
                assert (schedule.indexOf("4") < schedule.indexOf("9"));
                assert (schedule.indexOf("7") < schedule.indexOf("8"));
                assert (schedule.indexOf("9") < schedule.indexOf("8"));
                shiftOnce(tasks);
            }


            tasks = createTasks22();
            schedule = Project.findSchedule(tasks, true);

            assert (schedule.size() == 11);
            assert (schedule.get(0).equals("A"));
            assert (schedule.get(1).equals("B"));
            assert (schedule.get(2).equals("C"));
            assert (schedule.indexOf("C") < schedule.indexOf("E"));
            assert (schedule.indexOf("E") < schedule.indexOf("F"));
            assert (schedule.indexOf("F") < schedule.indexOf("I"));

            assert (schedule.indexOf("C") < schedule.indexOf("D"));
            assert (schedule.indexOf("D") < schedule.indexOf("G"));
            assert (schedule.indexOf("D") < schedule.indexOf("J"));
            assert (schedule.indexOf("G") < schedule.indexOf("H"));
            assert (schedule.indexOf("J") < schedule.indexOf("K"));
            assert (schedule.indexOf("K") < schedule.indexOf("H"));


            int a = 1;
        }
    }

    private static Task[] createTasks() {
        Task a = new Task("A", 12);
        Task b = new Task("B", 7);
        Task c = new Task("C", 10);
        Task d = new Task("D", 9);
        Task e = new Task("E", 8);
        Task f = new Task("F", 11);
        Task g = new Task("G", 14);
        Task h = new Task("H", 13);
        Task i = new Task("I", 6);

        i.addPredecessor(f);
        f.addPredecessor(e);
        e.addPredecessor(b, c);
        d.addPredecessor(c, h);
        c.addPredecessor(b);
        b.addPredecessor(a);
        h.addPredecessor(g);
        g.addPredecessor(d);

        return new Task[]{
                a, b, c, d, e, f, g, h, i
        };

    }

    private static Task[] createTasks2() {
        Task a = new Task("A", 12);
        Task b = new Task("B", 7);
        Task c = new Task("C", 10);
        Task d = new Task("D", 9);
        Task e = new Task("E", 8);
        Task f = new Task("F", 11);
        Task g = new Task("G", 14);
        Task h = new Task("H", 13);
        Task i = new Task("I", 6);
        Task j = new Task("J", 6);
        Task k = new Task("K", 6);
        Task l = new Task("L", 6);
        Task m = new Task("M", 6);
        Task n = new Task("N", 6);

        i.addPredecessor(f);
        f.addPredecessor(e);
        e.addPredecessor(b, c);
        d.addPredecessor(c, k);
        c.addPredecessor(b);
        b.addPredecessor(a);
        h.addPredecessor(g);
        g.addPredecessor(d);
        j.addPredecessor(h);
        k.addPredecessor(j);
        l.addPredecessor(n);
        m.addPredecessor(l);
        n.addPredecessor(m, c);

        return new Task[]{
                a, b, c, d, e, f, g, h, i, j, k, l, m, n
        };
    }

    private static Task[] createTasks3() {
        Task a = new Task("A", 12);
        Task b = new Task("B", 7);
        Task c = new Task("C", 10);
        Task d = new Task("D", 9);
        Task e = new Task("E", 8);
        Task f = new Task("F", 11);
        Task g = new Task("G", 14);
        Task h = new Task("H", 13);
        Task i = new Task("I", 6);
        Task j = new Task("J", 6);
        Task k = new Task("K", 6);
        Task l = new Task("L", 6);
        Task m = new Task("M", 6);
        Task n = new Task("N", 6);
        Task o = new Task("O", 6);
        Task p = new Task("P", 6);
        Task q = new Task("Q", 6);
        Task r = new Task("R", 6);
        Task s = new Task("S", 6);


        b.addPredecessor(a);
        c.addPredecessor(b);
        i.addPredecessor(h);
        e.addPredecessor(d, i, s);
        f.addPredecessor(e);
        j.addPredecessor(e);
        g.addPredecessor(c, f, j);
        k.addPredecessor(g, n);
        n.addPredecessor(m);
        m.addPredecessor(l);
        l.addPredecessor(k);
        o.addPredecessor(g);
        p.addPredecessor(g);
        q.addPredecessor(g);
        r.addPredecessor(a);
        s.addPredecessor(h);

        return new Task[]{
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s
        };
    }

    private static Task[] createTasks4() {
        Task a = new Task("A", 12);
        Task b = new Task("B", 7);
        Task c = new Task("C", 10);
        Task d = new Task("D", 9);
        Task e = new Task("E", 8);
        Task f = new Task("F", 11);
        Task g = new Task("G", 14);
        Task h = new Task("H", 13);
        Task i = new Task("I", 6);
        Task j = new Task("J", 6);
        Task k = new Task("K", 6);
        Task l = new Task("L", 6);
        Task m = new Task("M", 6);
        Task n = new Task("N", 6);
        Task o = new Task("O", 6);
        Task p = new Task("P", 6);
        Task q = new Task("Q", 6);
        Task r = new Task("R", 6);
        Task s = new Task("S", 6);
        Task t = new Task("T", 6);
        Task u = new Task("U", 6);
        Task v = new Task("V", 6);
        Task w = new Task("W", 6);
        Task x = new Task("X", 6);
        Task y = new Task("Y", 6);
        Task z = new Task("Z", 6);

        h.addPredecessor(a);
        i.addPredecessor(k, h);
        j.addPredecessor(i);
        k.addPredecessor(j);
        l.addPredecessor(h);
        g.addPredecessor(l, b, c);
        d.addPredecessor(c, f);
        e.addPredecessor(d);
        f.addPredecessor(e);
        m.addPredecessor(g);
        q.addPredecessor(m);
        r.addPredecessor(q);
        s.addPredecessor(q);
        p.addPredecessor(m);
        o.addPredecessor(m);
        n.addPredecessor(m);
        t.addPredecessor(m, z);
        u.addPredecessor(t);
        v.addPredecessor(u);
        x.addPredecessor(v);
        y.addPredecessor(x);
        z.addPredecessor(y);
        w.addPredecessor(p);

        return new Task[]{
                z, y, x, w, v, u, t, s, r, q, p, o, n, m, l, k, j, i, h, g, f, e, d, c, b, a
        };
    }

    private static Task[] createTasks5() {
        Task a = new Task("A", 12);
        Task b = new Task("B", 7);
        Task c = new Task("C", 10);
        Task d = new Task("D", 9);
        Task e = new Task("E", 8);
        Task f = new Task("F", 11);
        Task g = new Task("G", 14);
        Task h = new Task("H", 13);
        Task i = new Task("I", 6);
        Task j = new Task("J", 6);
        Task k = new Task("K", 6);
        Task l = new Task("L", 6);
        Task m = new Task("M", 6);
        Task n = new Task("N", 6);
        Task o = new Task("O", 6);
        Task p = new Task("P", 6);
        Task q = new Task("Q", 6);
        Task r = new Task("R", 6);
        Task s = new Task("S", 6);
        Task t = new Task("T", 6);
        Task u = new Task("U", 6);
        Task v = new Task("V", 6);
        Task w = new Task("W", 6);
        Task x = new Task("X", 6);
        Task y = new Task("Y", 6);
        Task z = new Task("Z", 6);

        Task c1 = new Task("C1", 6);
        Task c2 = new Task("C2", 6);
        Task b1 = new Task("B1", 6);
        Task b2 = new Task("B2", 6);
        Task a1 = new Task("A1", 6);
        Task a2 = new Task("A2", 6);
        Task a3 = new Task("A3", 6);
        Task a4 = new Task("A4", 6);
        Task a5 = new Task("A5", 6);

        Task a11 = new Task("A11", 6);
        Task a12 = new Task("A12", 6);
        Task a13 = new Task("A13", 6);

        h.addPredecessor(a);
        i.addPredecessor(k, h);
        j.addPredecessor(i);
        k.addPredecessor(j);
        l.addPredecessor(h);
        g.addPredecessor(l, b, c);
        d.addPredecessor(c, f);
        e.addPredecessor(d);
        f.addPredecessor(e);
        m.addPredecessor(g);
        q.addPredecessor(m);
        r.addPredecessor(q);
        s.addPredecessor(q);
        p.addPredecessor(m);
        o.addPredecessor(m);
        n.addPredecessor(m);
        t.addPredecessor(m, z);
        u.addPredecessor(t);
        v.addPredecessor(u);
        x.addPredecessor(v);
        y.addPredecessor(x);
        z.addPredecessor(y);
        w.addPredecessor(p);

        a.addPredecessor(a1, a2, a3, a4, a5);
        b.addPredecessor(b1, b2);
        c.addPredecessor(c1, c2);
        a11.addPredecessor(a3, a13);
        a12.addPredecessor(a11);
        a13.addPredecessor(a12);

        return new Task[]{
                 t, s, r, q, p,z, y, x, w,v, u, o, n, m, l, k, j, i, h, g, f, e, d, c, b, a,
                a1, a2, a3, a4, a5, a11, a12, a13, b1, b2, c1, c2
        };
    }

    private static Task[] createTasks6() {
        Task a = new Task("A", 12);
        Task b = new Task("B", 7);
        Task c = new Task("C", 10);
        Task d = new Task("D", 9);
        Task e = new Task("E", 8);
        Task e1 = new Task("E1", 8);
        Task f = new Task("F", 11);
        Task g = new Task("G", 14);
        Task h = new Task("H", 13);
        Task i = new Task("I", 6);
        Task j = new Task("J", 6);
        Task k = new Task("K", 6);
        Task l = new Task("L", 6);
        Task m = new Task("M", 6);
        Task n = new Task("N", 6);
        Task o = new Task("O", 6);
        Task p = new Task("P", 6);
        Task q = new Task("Q", 6);

        b.addPredecessor(a);
        c.addPredecessor(b, e1);
        d.addPredecessor(c);
        e1.addPredecessor(d);

        e.addPredecessor(b);
        f.addPredecessor(e, h);
        g.addPredecessor(f);
        h.addPredecessor(g);

        i.addPredecessor(e);
        j.addPredecessor(i, l);
        k.addPredecessor(j);
        l.addPredecessor(k);

        m.addPredecessor(b, e, i);
        q.addPredecessor(m);
        n.addPredecessor(m, p);
        p.addPredecessor(o);
        o.addPredecessor(n);


        return new Task[]{
                q, p, o, n, m, l, k, j, i, h, g, f, e, d, c, b, a, e1

        };
    }

    private static Task[] createTasks7noloop() {
        Task a = new Task("A", 12);
        Task b = new Task("B", 7);
        Task c = new Task("C", 10);
        Task d = new Task("D", 9);
        Task e = new Task("E", 8);
        Task e1 = new Task("E1", 8);
        Task f = new Task("F", 11);
        Task g = new Task("G", 14);
        Task h = new Task("H", 13);
        Task i = new Task("I", 6);
        Task j = new Task("J", 6);
        Task k = new Task("K", 6);
        Task l = new Task("L", 6);
        Task m = new Task("M", 6);
        Task n = new Task("N", 6);
        Task o = new Task("O", 6);
        Task p = new Task("P", 6);
        Task q = new Task("Q", 6);

        b.addPredecessor(a);
        c.addPredecessor(b, e1);
        d.addPredecessor(c);
        e1.addPredecessor(d);

        e.addPredecessor(b);
        f.addPredecessor(e, h);
        g.addPredecessor(f);
        h.addPredecessor(g);

        i.addPredecessor(e);
        j.addPredecessor(i, l);
        k.addPredecessor(j);
        l.addPredecessor(k);

        m.addPredecessor(b, e, i);
        q.addPredecessor(m);
        n.addPredecessor(m, p);
        p.addPredecessor(o);
        o.addPredecessor(n);


        return new Task[]{
                q, p, o, n, m, l, k, j, i, h, g, f, e, d, c, b, a, e1

        };
    }

    private static Task[] createTasks8_2loop() {
        Task a = new Task("A", 12);
        Task b = new Task("B", 7);
        Task c = new Task("C", 10);
        Task d = new Task("D", 9);
        Task e = new Task("E", 8);
        Task f = new Task("F", 11);
        Task g = new Task("G", 14);
        Task h = new Task("H", 13);
        Task i = new Task("I", 13);

        b.addPredecessor(a);
        h.addPredecessor(b);
        c.addPredecessor(b, e, g);
        d.addPredecessor(c);
        e.addPredecessor(d);
        f.addPredecessor(c);
        g.addPredecessor(i);
        i.addPredecessor(f);


        return new Task[]{
                h, g, f, e, d, c, b, a,

        };
    }

    private static Task[] createTasks8_3loop() {
        Task a1 = new Task("A1", 12);
        Task b1 = new Task("B1", 7);
        Task c1 = new Task("C1", 10);
        Task d1 = new Task("D1", 9);
        Task e1 = new Task("E1", 8);
        Task f1 = new Task("F1", 11);
        Task g1 = new Task("G1", 14);
        Task h1 = new Task("H1", 13);
        Task i1 = new Task("I1", 13);
        Task j1 = new Task("J1", 13);
        Task k1 = new Task("K1", 13);
        Task l1 = new Task("L1", 13);

        Task a = new Task("A", 12);
        Task b = new Task("B", 7);
        Task c = new Task("C", 10);
        Task d = new Task("D", 9);
        Task e = new Task("E", 8);
        Task f = new Task("F", 11);
        Task g = new Task("G", 14);
        Task h = new Task("H", 13);
        Task i = new Task("I", 6);
        Task j = new Task("J", 6);
        Task k = new Task("K", 6);
        Task l = new Task("L", 6);
        Task m = new Task("M", 6);
        Task n = new Task("N", 6);
        Task o = new Task("O", 6);
        Task p = new Task("P", 6);
        Task q = new Task("Q", 6);
        Task r = new Task("R", 6);
        Task s = new Task("S", 6);

        a1.addPredecessor(q);
        b1.addPredecessor(a1);
        h1.addPredecessor(b1);
        c1.addPredecessor(b1, e1, g1, j1, l1);
        d1.addPredecessor(c1);
        e1.addPredecessor(d1);
        f1.addPredecessor(c1);
        g1.addPredecessor(f1);
        j1.addPredecessor(i1);
        i1.addPredecessor(c1);
        l1.addPredecessor(k1);
        k1.addPredecessor(c1);


        b.addPredecessor(a);
        c.addPredecessor(b);
        i.addPredecessor(h);
        e.addPredecessor(d, i, s);
        f.addPredecessor(e);
        j.addPredecessor(e);
        g.addPredecessor(c, f, j);
        k.addPredecessor(g, n);
        n.addPredecessor(m);
        m.addPredecessor(l);
        l.addPredecessor(k);
        o.addPredecessor(g);
        p.addPredecessor(g);
        q.addPredecessor(g);
        r.addPredecessor(a);
        s.addPredecessor(h);


        return new Task[]{
                l1, k1, j1, i1, h1, g1, f1, e1, d1, c1, b1, a1,
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s

        };
    }

    private static Task[] createTasks8_4loop() {
        Task a = new Task("A", 12);
        Task b = new Task("B", 7);
        Task c = new Task("C", 10);
        Task d = new Task("D", 9);
        Task e = new Task("E", 8);
        Task f = new Task("F", 11);
        Task g = new Task("G", 14);
        Task h = new Task("H", 13);
        Task i = new Task("I", 13);
        Task j = new Task("J", 13);
        Task k = new Task("K", 13);
        Task l = new Task("L", 13);

        Task n = new Task("N", 13);
        Task m = new Task("M", 13);
        Task o = new Task("O", 13);
        Task p = new Task("P", 13);
        Task q = new Task("Q", 13);

        Task g1 = new Task("G1", 13);
        Task e1 = new Task("E1", 13);
        Task j1 = new Task("J1", 13);
        Task k1 = new Task("K1", 13);

        Task b0 = new Task("B0", 13);
        Task b1 = new Task("B1", 13);
        Task b2 = new Task("B2", 13);
        Task b3 = new Task("B3", 13);
        Task b4 = new Task("B4", 13);
        Task b5 = new Task("B5", 13);

        Task h1 = new Task("H1", 13);
        Task r = new Task("R", 13);

        b.addPredecessor(a, b2, b5);
        h.addPredecessor(b);
        c.addPredecessor(b, e, g, j, l);
        d.addPredecessor(c);
        e.addPredecessor(e1);
        f.addPredecessor(c);
        g.addPredecessor(g1);
        j.addPredecessor(j1);
        i.addPredecessor(c);
        l.addPredecessor(k1);
        k.addPredecessor(c);

        a.addPredecessor(n, m);
        n.addPredecessor(o, p, q);

        g1.addPredecessor(f);
        e1.addPredecessor(d);
        j1.addPredecessor(i);
        k1.addPredecessor(k);

        b0.addPredecessor(b);
        b1.addPredecessor(b0);
        b2.addPredecessor(b1);

        b3.addPredecessor(b);
        b4.addPredecessor(b3);
        b5.addPredecessor(b4);

        h1.addPredecessor(h);
        r.addPredecessor(b);

        return new Task[]{
                g1, e1, k1, j1,
                l, k, j, i, h, g, f, e, d, c, b, a,
               n, m, o, p, q,
                b0, b1, b2, b3, b4 ,b5,
                h1, r

        };
    }

    private static Task[] createTaskscycleErrorloop() {
        Task a1 = new Task("1", 12);
        Task a2 = new Task("2", 7);
        Task a3 = new Task("3", 10);
        Task a4 = new Task("4", 9);
        Task a5 = new Task("5", 8);
        Task a6 = new Task("6", 11);
        Task a7 = new Task("7", 14);
        Task a8 = new Task("8", 13);
        Task a9 = new Task("9", 13);
        Task a10 = new Task("10", 13);
        Task a11 = new Task("11", 13);
        Task a12 = new Task("12", 13);
        Task a13 = new Task("13", 13);
        Task a14 = new Task("14", 13);

        a2.addPredecessor(a1);
        a3.addPredecessor(a2);
        a5.addPredecessor(a1);
        a6.addPredecessor(a5, a2);
        a4.addPredecessor(a6, a3);
        a7.addPredecessor(a4);
        a8.addPredecessor(a7, a9);
        a9.addPredecessor(a4);
        a10.addPredecessor(a4, a12, a14);
        a11.addPredecessor(a10);
        a12.addPredecessor(a11);
        a13.addPredecessor(a10);
        a14.addPredecessor(a13);

        return new Task[]{
                a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14,
        };
    }

    private static Task[] createTasks22() {
        Task a = new Task("A", 12);
        Task b = new Task("B", 7);
        Task c = new Task("C", 10);
        Task d = new Task("D", 9);
        Task e = new Task("E", 8);
        Task f = new Task("F", 11);
        Task g = new Task("G", 14);
        Task h = new Task("H", 13);
        Task i = new Task("I", 6);
        Task j = new Task("J", 10);
        Task k = new Task("K", 10);


        i.addPredecessor(f);
        f.addPredecessor(e);
        e.addPredecessor(b, c);
        d.addPredecessor(c, h);
        c.addPredecessor(b);
        b.addPredecessor(a);
        h.addPredecessor(g, k);
        g.addPredecessor(d);
        j.addPredecessor(d);
        k.addPredecessor(j);

        return new Task[]{
                a, b, c, d, e, f, g, h, i, j, k
        };
    }

}
