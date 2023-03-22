package academy.pocu.comp3500.lab10.app;

import academy.pocu.comp3500.lab10.Project;
import academy.pocu.comp3500.lab10.project.Task;
import org.junit.jupiter.api.Test;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Program {

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
            tasks = createTasks8_2loop();
            schedule = Project.findSchedule(tasks, false);

            tasks = createTasks8_3loop();
            schedule = Project.findSchedule(tasks, true);
            tasks = createTasks8_3loop();
            schedule = Project.findSchedule(tasks, false);

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
            tasks = createTasks8_4loop();
            schedule = Project.findSchedule(tasks, false);

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

        b.addPredecessor(a);
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

        return new Task[]{
                g1, e1, k1, j1,
                l, k, j, i, h, g, f, e, d, c, b, a,
               n, m, o, p, q

        };
    }
}
