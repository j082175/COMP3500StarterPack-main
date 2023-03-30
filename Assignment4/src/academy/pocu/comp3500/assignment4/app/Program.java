package academy.pocu.comp3500.assignment4.app;

import academy.pocu.comp3500.assignment4.Project;
import academy.pocu.comp3500.assignment4.project.Task;
import org.junit.Test;

public class Program {

    public static void main(String[] args) {
        // write your code here
    }


    @Test
    public void test4() {
        {
            Task a = new Task("A", 3);
            Task b = new Task("B", 5);
            Task c = new Task("C", 3);
            Task d = new Task("D", 2);
            Task e = new Task("E", 1);
            Task f = new Task("F", 2);
            Task g = new Task("G", 6);
            Task h = new Task("H", 8);
            Task i = new Task("I", 2);
            Task j = new Task("J", 4);
            Task k = new Task("K", 2);
            Task l = new Task("L", 8);
            Task m = new Task("M", 7);
            Task n = new Task("N", 1);
            Task o = new Task("O", 1);
            Task p = new Task("P", 6);
            Task ms1 = new Task("ms1", 6);
            Task ms2 = new Task("ms2", 4);

            c.addPredecessor(b);
            d.addPredecessor(a);

            ms1.addPredecessor(a, c);

            e.addPredecessor(c);
            f.addPredecessor(g);
            g.addPredecessor(e);

            i.addPredecessor(h);
            j.addPredecessor(ms1);

            k.addPredecessor(j);
            n.addPredecessor(k);
            m.addPredecessor(n);
            l.addPredecessor(m);

            p.addPredecessor(i, j);
            o.addPredecessor(j);

            ms2.addPredecessor(o, p);

            Task[] tasks = new Task[]{
                    a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, ms1, ms2
            };

            Project project = new Project(tasks);

            int manMonths1 = project.findTotalManMonths("ms1");
            assert (manMonths1 == 17);

            int manMonths2 = project.findTotalManMonths("ms2");
            assert (manMonths2 == 42);

            int minDuration1 = project.findMinDuration("ms1");
            assert (minDuration1 == 14);

            int minDuration2 = project.findMinDuration("ms2");
            assert (minDuration2 == 28);

            int bonusCount1 = project.findMaxBonusCount("ms1");
            assert (bonusCount1 == 6);

            int bonusCount2 = project.findMaxBonusCount("ms2");
            assert (bonusCount2 == 4);
        }


        {
            Task a = new Task("A", 2);
            Task b = new Task("B", 1);
            Task c = new Task("C", 3);
            Task d = new Task("D", 5);
            Task e = new Task("E", 7);
            Task f = new Task("F", 2);
            Task g = new Task("G", 11);

            b.addPredecessor(a);
            c.addPredecessor(b);
            d.addPredecessor(c);

            f.addPredecessor(b, e);
            g.addPredecessor(d, f);

            Task[] tasks = new Task[]{
                    a, b, c, d, e, f, g
            };
            Project project = new Project(tasks);

            int bonusCount1 = project.findMaxBonusCount("G");
            assert (bonusCount1 == 3);
        }


    }
    @Test
    public void test2() {
        Task[] tasks = createTasksAssignment4();

        Project project = new Project(tasks);

        //int minDuration2 = project.findMinDuration("ms2");
        //assert (minDuration2 == 32);

        int bonusCount1 = project.findMaxBonusCount("ms1");
        assert (bonusCount1 == 6);

        int bonusCount2 = project.findMaxBonusCount("ms2");
        assert (bonusCount2 == 6);
    }

    @Test
    public void test1() {

        {
            Task[] tasks = createTasksMaximumFlow();
            Project project = new Project(tasks);
            int maximum = project.findMaxBonusCount("T");
            int a = 1;
        }

        {
            Task[] tasks = createTasksMultiMilestone();
            Project project = new Project(tasks);
            int maximum = project.findMinDuration("I");
            int a = 1;
        }

        {
            Task[] tasks = createTasks3();
            Project project = new Project(tasks);
            int maximum = project.findMinDuration("G");
            int a = 1;
        }

        {
            Task[] tasks = createTasksSample();
            Project project = new Project(tasks);
            int manMonths1 = project.findTotalManMonths("ms1");
            int a = 1;
        }

        {
            Task[] tasks = createTasks23();
            Project project = new Project(tasks);
            int manMonths1 = project.findTotalManMonths("3");
            int a = 1;
        }

        {
            Task a = new Task("A", 15);
            Task b = new Task("B", 12);
            Task c = new Task("C", 11);

            c.addPredecessor(b);
            b.addPredecessor(a);

            Task[] tasks = new Task[]{b, c, a};

            Project project = new Project(tasks);
            int manMonths1 = project.findTotalManMonths("ms1");
            int aa = 1;
        }

        {
            Task[] tasks = createTasks();
            Project project = new Project(tasks);
            int manMonths1 = project.findTotalManMonths("E");
            int a = 1;
        }

        {
            Task[] tasks = createTasks();
            Project project = new Project(tasks);
            int manMonths1 = project.findTotalManMonths("ms1");
            int a = 1;
        }

        {

            Task[] tasks = createTasks8_2loop();
            Project project = new Project(tasks);
            int manMonths1 = project.findTotalManMonths("ms1");
            int a = 1;
        }

        {

            Task[] tasks = createTasks8_3loop();
            Project project = new Project(tasks);
            int manMonths1 = project.findTotalManMonths("ms1");
            int a = 1;
        }

        {
            Task[] tasks = createTasks2();
            Project project = new Project(tasks);
            int manMonths1 = project.findTotalManMonths("ms1");
            int a = 1;
        }

        {
            Task[] tasks = createTasks3();
            Project project = new Project(tasks);
            int manMonths1 = project.findTotalManMonths("ms1");
            int a = 1;
        }


        {
            Task[] tasks = createTasks4();
            Project project = new Project(tasks);
            int manMonths1 = project.findTotalManMonths("ms1");
            int a = 1;
        }

        {
            Task[] tasks = createTasks5();
            Project project = new Project(tasks);
            int manMonths1 = project.findTotalManMonths("ms1");
            int a = 1;
        }

        {
            Task[] tasks = createTasks6();
            Project project = new Project(tasks);
            int manMonths1 = project.findTotalManMonths("ms1");
            int a = 1;
        }

        {
            Task[] tasks = createTasks8_4loop();
            Project project = new Project(tasks);
            int manMonths1 = project.findTotalManMonths("ms1");
            int a = 1;
        }


        {
            Task[] tasks = createTaskscycleErrorloop();
            Project project = new Project(tasks);
            int manMonths1 = project.findTotalManMonths("ms1");
            int a = 1;
        }


        {
            Task[] tasks = createTasks22();
            Project project = new Project(tasks);
            int manMonths1 = project.findTotalManMonths("ms1");
            int a = 1;
        }

        {
            Task[] tasks = createTasks23();
            Project project = new Project(tasks);
            int manMonths1 = project.findTotalManMonths("ms1");
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
        Task a = new Task("A", 1);
        Task b = new Task("B", 2);
        Task c = new Task("C", 3);
        Task d = new Task("D", 1);
        Task e = new Task("E", 2);
        Task f = new Task("F", 3);
        Task g = new Task("G", 1);
        Task h = new Task("H", 2);
        Task i = new Task("I", 3);
        Task j = new Task("J", 1);
        Task k = new Task("K", 2);
        Task l = new Task("L", 3);
        Task m = new Task("M", 1);
        Task n = new Task("N", 2);
        Task o = new Task("O", 3);
        Task p = new Task("P", 1);
        Task q = new Task("Q", 2);
        Task r = new Task("R", 3);
        Task s = new Task("S", 1);

        Task t = new Task("T", 1);
        Task u = new Task("U", 2);
        Task v = new Task("V", 2);
        Task w = new Task("W", 1);
        Task x = new Task("X", 2);
        Task y = new Task("Y", 3);
        Task z = new Task("Z", 3);


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

        t.addPredecessor(x, w, z);
        u.addPredecessor(w);
        v.addPredecessor(y);
        h.addPredecessor(v, t, u);

        return new Task[]{
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
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
                t, s, r, q, p, z, y, x, w, v, u, o, n, m, l, k, j, i, h, g, f, e, d, c, b, a,
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
                i, h, g, f, e, d, c, b, a,

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
                b0, b1, b2, b3, b4, b5,
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

    private static Task[] createTasks23() {
        Task a1 = new Task("1", 12);
        Task a2 = new Task("2", 7);
        Task a3 = new Task("3", 10);
        Task a4 = new Task("4", 9);
        Task a5 = new Task("5", 8);
        Task a6 = new Task("6", 11);
        Task a7 = new Task("7", 14);
        Task a8 = new Task("8", 13);
        Task a9 = new Task("9", 6);
        Task a10 = new Task("10", 10);
        Task a11 = new Task("11", 10);
        Task a12 = new Task("12", 10);
        Task a13 = new Task("13", 10);
        Task a14 = new Task("14", 10);
        Task a15 = new Task("15", 10);
        Task a16 = new Task("16", 10);
        Task a17 = new Task("17", 10);
        Task a18 = new Task("18", 10);


        a2.addPredecessor(a1);
        a3.addPredecessor(a2);
        a4.addPredecessor(a3, a16);
        a5.addPredecessor(a4);
        a6.addPredecessor(a5);
        a7.addPredecessor(a5);
        a8.addPredecessor(a6);
        a9.addPredecessor(a7, a8);
        a10.addPredecessor(a9);
        a11.addPredecessor(a10, a12);
        a12.addPredecessor(a4);
        a13.addPredecessor(a11);
        a14.addPredecessor(a13);
        a15.addPredecessor(a13);
        a16.addPredecessor(a14, a15, a18);
        a17.addPredecessor(a15);
        a18.addPredecessor(a17);

        return new Task[]{
                a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18
        };
    }

    private static Task[] createTasksSample() {
        Task a0 = new Task("0", 12);
        Task a1 = new Task("1", 7);
        Task a2 = new Task("2", 10);
        Task a3 = new Task("3", 9);
        Task a4 = new Task("4", 8);
        Task a5 = new Task("5", 11);
        Task a6 = new Task("6", 11);

        a3.addPredecessor(a2);
        a2.addPredecessor(a3, a0);
        a0.addPredecessor(a1, a5);
        a1.addPredecessor(a6);
        a4.addPredecessor(a0);
        a5.addPredecessor(a4);


        return new Task[]{
                a3, a2, a0, a1, a4, a5, a6
        };
    }

    private static Task[] createTasksTakim() {
        Task a = new Task("A", 12);
        Task b = new Task("B", 7);
        Task c = new Task("C", 10);
        Task d = new Task("D", 9);
        Task e = new Task("E", 8);
        Task f = new Task("F", 11);
        Task g = new Task("G", 11);
        Task h = new Task("H", 11);
        Task i = new Task("I", 11);

        a.addPredecessor(b);
        b.addPredecessor(c, e);
        c.addPredecessor(d, e);
        d.addPredecessor(g);
        e.addPredecessor(f);
        f.addPredecessor(i);
        g.addPredecessor(h);
        h.addPredecessor(d);


        return new Task[]{
                a, b, c, d, e, f, g, h, i
        };
    }

    @Test
    public void test3() {
        {
            Task[] tasks = createTasksAssignment4();

            Project project = new Project(tasks);

            int manMonths1 = project.findTotalManMonths("ms1");
            assert (manMonths1 == 17);

            int manMonths2 = project.findTotalManMonths("ms2");
            assert (manMonths2 == 46);

            int minDuration1 = project.findMinDuration("ms1");
            assert (minDuration1 == 14);

            int minDuration2 = project.findMinDuration("ms2");
            assert (minDuration2 == 32);

            int bonusCount1 = project.findMaxBonusCount("ms1");
            assert (bonusCount1 == 6);

            int bonusCount2 = project.findMaxBonusCount("ms2");
            assert (bonusCount2 == 6);
        }
    }

    private static Task[] createTasksAssignment4() {
        Task a = new Task("A", 3);
        Task b = new Task("B", 5);
        Task c = new Task("C", 3);
        Task d = new Task("D", 2);
        Task e = new Task("E", 1);
        Task f = new Task("F", 2);
        Task g = new Task("G", 6);
        Task h = new Task("H", 8);
        Task i = new Task("I", 2);
        Task j = new Task("J", 4);
        Task k = new Task("K", 2);
        Task l = new Task("L", 8);
        Task m = new Task("M", 7);
        Task n = new Task("N", 1);
        Task o = new Task("O", 1);
        Task p = new Task("P", 6);
        Task ms1 = new Task("ms1", 6);
        Task ms2 = new Task("ms2", 8);

        c.addPredecessor(b);
        d.addPredecessor(a);

        ms1.addPredecessor(a, c);

        e.addPredecessor(c);
        f.addPredecessor(g);
        g.addPredecessor(e);

        i.addPredecessor(h);
        j.addPredecessor(ms1);

        k.addPredecessor(j);
        n.addPredecessor(k);
        m.addPredecessor(n);
        l.addPredecessor(m);

        p.addPredecessor(i, j);
        o.addPredecessor(j);

        ms2.addPredecessor(o, p);

        Task[] tasks = new Task[]{
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, ms1, ms2
        };

        return tasks;
    }

    private static Task[] createTasksMultiMilestone() {
        Task a = new Task("A", 1);
        Task b = new Task("B", 1);
        Task c = new Task("C", 1);
        Task d = new Task("D", 1);
        Task e = new Task("E", 1);
        Task f = new Task("F", 1);
        Task g = new Task("G", 1);
        Task h = new Task("H", 1);
        Task i = new Task("I", 1);
        Task j = new Task("J", 1);
        Task k = new Task("K", 1);

        d.addPredecessor(a, b, c);
        e.addPredecessor(d);
        h.addPredecessor(f, e, g);
        i.addPredecessor(j, h, k);

        Task[] tasks = new Task[]{
                a, b, c, d, e, f, g, h, i, j, k
        };

        return tasks;
    }

    private static Task[] createTasksMaximumFlow() {
        Task a = new Task("A", 7);
        Task b = new Task("B", 5);
        Task c = new Task("C", 3);
        Task d = new Task("D", 4);
        Task s = new Task("S", 2);
        Task t = new Task("T", 6);

        a.addPredecessor(s);
        b.addPredecessor(s);
        c.addPredecessor(a, b);
        d.addPredecessor(b, c);
        t.addPredecessor(c, d);

        Task[] tasks = new Task[]{
                a, b, c, d, s, t
        };

        return tasks;
    }

    private static Task[] createTasksMaximumFlow2() {
        Task a = new Task("1", 7);
        Task b = new Task("2", 5);
        Task c = new Task("3", 3);
        Task d = new Task("4", 4);
        Task s = new Task("S", 2);
        Task t = new Task("T", 6);

        a.addPredecessor(s);
        b.addPredecessor(s);
        c.addPredecessor(a, b);
        d.addPredecessor(b, c);
        t.addPredecessor(c, d);

        Task[] tasks = new Task[]{
                a, b, c, d, s, t
        };

        return tasks;
    }
}
