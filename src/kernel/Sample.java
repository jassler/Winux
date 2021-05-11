package kernel;

import os.screen.Cursor;

public class Sample {

    static abstract class A {
        final String name;
        A(String name) {
            this.name = name;
        }
    }

    static class B extends A {
        B(String name) {
            super(name);
        }
    }

    static class C extends A {
        C() {
            super("C class");
        }
    }

    static class Container {
        A[] someAs = new A[10];
        int ptr = 0;

        void add(A a) {
            someAs[ptr++] = a;
        }

        void print(Cursor c) {
            for (int i = 0; i < ptr; i++) {
                c.print(i);
                c.print('=');
                c.println(someAs[i].name);
            }
        }
    }

    static void evaluate(Cursor c) {
        Container container = new Container();
        A a = new B("someName");

        container.add(a);
        container.add(new B("oha"));
        container.add(new C());

        c.print("a      = ");
        c.println(a.name);

        container.print(c);
    }

}
