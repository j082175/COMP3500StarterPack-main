package academy.pocu.comp3500.assignment4.app;

import academy.pocu.comp3500.assignment4.Project;
import academy.pocu.comp3500.assignment4.project.Task;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Program {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st = null;

    static final int MAX_SIZE = 52;
    static int N, maxFlow, S, T = 25, aPath[], capacity[][], flow[][];
    static Queue<Integer> queue;
    public static void main(String[] args) throws IOException {
        // write your code here
        capacity = new int[MAX_SIZE][MAX_SIZE];
        flow = new int[MAX_SIZE][MAX_SIZE];

        N = Integer.parseInt(br.readLine());
        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int start = charToInt(st.nextToken().charAt(0));
            int end = charToInt(st.nextToken().charAt(0));
            int weight = Integer.parseInt(st.nextToken());
            // 여기서는 그냥 연결이기 때문에 무향 그래프라서 양방향으로 웨이트를 둘 다 더해줌
            capacity[start][end] += weight;
            // capacity[end][start] += weight;
        }

        queue = new LinkedList<>();
        aPath = new int[MAX_SIZE];
        // 더 이상 증가경로가 없을 때 까지 반복
        while(true) {
            // 큐, 증가경로 초기화
            queue.clear();
            Arrays.fill(aPath, -1);
            aPath[S] = S;
            queue.add(S);

            // BFS로 최단거리 증가경로 찾기
            while(!queue.isEmpty() && aPath[T] == -1) {
                int from = queue.poll();
                for(int to = 0; to < MAX_SIZE; to++) {
                    // 유량이 흐를 수 있으면서, 아직 방문하지 않았다면
                    if(capacity[from][to] > flow[from][to] && aPath[to] == -1) {
                        queue.add(to);
                        aPath[to] = from;
                    }
                }
            }

            // 경로가 없으면 종료
            if(aPath[T] == -1) break;

            // 찾은 증가 경로의 r(u,v)의 최솟값 (최소 잔여 용량)을 찾음
            int flowAmount = Integer.MAX_VALUE;
            for(int i = T; i != S; i = aPath[i])
                flowAmount = Math.min(capacity[aPath[i]][i] - flow[aPath[i]][i], flowAmount);

            for(int i = T; i != S; i = aPath[i]) {
                flow[aPath[i]][i] += flowAmount; // 경로들에 유량 흘러줌
                flow[i][aPath[i]] -= flowAmount; // 유량의 대칭성으로 반대 경로에 - 유량 흘림
            }

            maxFlow += flowAmount;
        }

        System.out.println(maxFlow);
    }

    // 문자를 인덱스로 매핑하기 위해 변환
    public static int charToInt(char c) {
        if('a' <= c && c <= 'z') c -= 6;
        return c - 65;
    }

    @Test
    public void test9() {
        Task t0 = new Task("t0", 5);
        Task t1 = new Task("t1", 10);
        Task t2 = new Task("t2", 2);
        Task t3 = new Task("t3", 8);
        Task t4 = new Task("t4", 3);
        Task t5 = new Task("t5", 6);
        Task t6 = new Task("t6", 7);
        Task t7 = new Task("t7", 3);
        Task t8 = new Task("t8", 5);
        Task t9 = new Task("t9", 4);
        Task t10 = new Task("t10", 2);
        Task t11 = new Task("t11", 3);
        Task t12 = new Task("t12", 11);


        t1.addPredecessor(t0);
        t2.addPredecessor(t1);
        t3.addPredecessor(t2);
        t4.addPredecessor(t1);
        t5.addPredecessor(t4);
        t6.addPredecessor();
        t7.addPredecessor(t6);
        t8.addPredecessor(t7);
        t9.addPredecessor();
        t10.addPredecessor(t9);
        t11.addPredecessor(t10);
        t12.addPredecessor(t3, t5, t8, t11);

        Task[] tasks = new Task[]{
                t0, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12
        };

        Project project = new Project(tasks);
        int res = project.findMaxBonusCount("t12");
        assert res == 10;

    }

    @Test
    public void test4() {
        {
            Task a = new Task("A", 3);
            Task b = new Task("B", 1);
            Task c = new Task("C", 2);
            Task d = new Task("D", 8);


            d.addPredecessor(a, b, c);

            Task[] tasks = new Task[]{
                    a, b, c, d
            };

            Project project = new Project(tasks);

            int bonusCount1 = project.findMaxBonusCount("D");
            assert (bonusCount1 == 3);
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
            Task[] tasks = createTasksMaximumFlowSquare();
            Project project = new Project(tasks);
            int maximum = project.findMaxBonusCount("O");
            int a = 1;
        }

        {
            Task[] tasks = createTasksMaximumFlow2();
            Project project = new Project(tasks);
            int maximum = project.findMaxBonusCount("6");
            int a = 1;
        }

        {
            Task[] tasks = createTasksMaximumFlow();
            Project project = new Project(tasks);
            int maximum = project.findMaxBonusCount("T");
            int a = 1;
        }

        {
            Task[] tasks = createTasksMultiMilestone();
            Project project = new Project(tasks);
            int maximum = project.findMaxBonusCount("I");
            int a = 1;
        }

        {
            Task[] tasks = createTasks3();
            Project project = new Project(tasks);
            int maximum = project.findMaxBonusCount("G");
            int a = 1;
        }

        {
            Task[] tasks = createTasksSample();
            Project project = new Project(tasks);
            int manMonths1 = project.findMaxBonusCount("6");
            int a = 1;
        }

        {
            Task[] tasks = createTasks23();
            Project project = new Project(tasks);
            int manMonths1 = project.findMaxBonusCount("3");
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
            int manMonths1 = project.findMaxBonusCount("C");
            int aa = 1;
        }

        {
            Task[] tasks = createTasks();
            Project project = new Project(tasks);
            int manMonths1 = project.findMaxBonusCount("E");
            int a = 1;
        }

        {

            Task[] tasks = createTasks8_2loop();
            Project project = new Project(tasks);
            int manMonths1 = project.findMaxBonusCount("B");
            int a = 1;
        }

        {

            Task[] tasks = createTasks8_3loop();
            Project project = new Project(tasks);
            int manMonths1 = project.findMaxBonusCount("G");
            int a = 1;
        }

        {
            Task[] tasks = createTasks2();
            Project project = new Project(tasks);
            int manMonths1 = project.findMaxBonusCount("D");
            int a = 1;
        }

        {
            Task[] tasks = createTasks3();
            Project project = new Project(tasks);
            int manMonths1 = project.findMaxBonusCount("G");
            int a = 1;
        }


        {
            Task[] tasks = createTasks4();
            Project project = new Project(tasks);
            int manMonths1 = project.findMaxBonusCount("G");
            int a = 1;
        }

        {
            Task[] tasks = createTasks5();
            Project project = new Project(tasks);
            int manMonths1 = project.findMaxBonusCount("Q");
            int a = 1;
        }

        {
            Task[] tasks = createTasks6();
            Project project = new Project(tasks);
            int manMonths1 = project.findMaxBonusCount("G");
            int a = 1;
        }

        {
            Task[] tasks = createTasks8_4loop();
            Project project = new Project(tasks);
            int manMonths1 = project.findMaxBonusCount("G");
            int a = 1;
        }


        {
            Task[] tasks = createTaskscycleErrorloop();
            Project project = new Project(tasks);
            int manMonths1 = project.findMaxBonusCount("8");
            int a = 1;
        }


        {
            Task[] tasks = createTasks22();
            Project project = new Project(tasks);
            int manMonths1 = project.findMaxBonusCount("G");
            int a = 1;
        }

        {
            Task[] tasks = createTasks23();
            Project project = new Project(tasks);
            int manMonths1 = project.findMaxBonusCount("5");
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
        Task b = new Task("B", 9);
        Task c = new Task("C", 6);
        Task d = new Task("D", 1);
        Task e = new Task("E", 17);
        Task f = new Task("F", 33);
        Task g = new Task("G", 50);
        Task h = new Task("H", 13);
        Task i = new Task("I", 12);
        Task j = new Task("J", 8);
        Task k = new Task("K", 7);
        Task l = new Task("L", 5);
        Task m = new Task("M", 9);
        Task n = new Task("N", 16);
        Task o = new Task("O", 19);
        Task p = new Task("P", 21);
        Task q = new Task("Q", 11);
        Task r = new Task("R", 14);
        Task s = new Task("S", 18);

        Task t = new Task("T", 12);
        Task u = new Task("U", 7);
        Task v = new Task("V", 5);
        Task w = new Task("W", 7);
        Task x = new Task("X", 8);
        Task y = new Task("Y", 18);
        Task z = new Task("Z", 21);


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
        Task i = new Task("I", 10);
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
        Task s = new Task("S", 12);
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
        Task a = new Task("0", 2);
        Task b = new Task("1", 1);
        Task c = new Task("2", 3);
        Task d = new Task("3", 5);
        Task e = new Task("4", 7);
        Task f = new Task("5", 2);
        Task g = new Task("6", 11);

        b.addPredecessor(a);
        c.addPredecessor(b);
        d.addPredecessor(c);
        f.addPredecessor(b, e);
        g.addPredecessor(d, f);

        Task[] tasks = new Task[]{
                a, b, c, d, e, f, g
        };

        return tasks;
    }

    private static Task[] createTasksMaximumFlowSquare() {
        Task a = new Task("A", 20);
        Task b = new Task("B", 15);
        Task c = new Task("C", 17);
        Task d = new Task("D", 11);
        Task e = new Task("E", 16);
        Task f = new Task("F", 12);
        Task g = new Task("G", 15);
        Task h = new Task("H", 16);
        Task i = new Task("I", 13);
        Task j = new Task("J", 7);
        Task k = new Task("K", 9);
        Task l = new Task("L", 10);
        Task m = new Task("M", 18);
        Task n = new Task("N", 14);
        Task o = new Task("O", 23);
        Task p = new Task("P", 20);


        b.addPredecessor(a);
        c.addPredecessor(b, d);
        e.addPredecessor(a);
        f.addPredecessor(b, e, g);
        g.addPredecessor(c, h);
        h.addPredecessor(d);
        i.addPredecessor(e);
        j.addPredecessor(i, f);
        k.addPredecessor(j, g, l);
        l.addPredecessor(h);
        m.addPredecessor(i);
        n.addPredecessor(m, j, o);
        o.addPredecessor(k, p);
        p.addPredecessor(l);

        Task[] tasks = new Task[]{
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p
        };

        return tasks;
    }
}
