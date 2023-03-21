package academy.pocu.comp3500.lab10.app;

import academy.pocu.comp3500.lab10.Project;
import academy.pocu.comp3500.lab10.project.Task;
import org.junit.jupiter.api.Test;


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
            Task[] tasks = createTasks2();

            List<String> schedule = Project.findSchedule(tasks, true);

            tasks = createTasks3();
            schedule = Project.findSchedule(tasks, true);


            tasks = createTasks4();
            schedule = Project.findSchedule(tasks, true);

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
}
