import ListenerTask.ListenerTask;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Listener {
    public static void main(String[] args) {

        Integer port = 8000;
        String processName = "Listener";

        if (args.length == 0) {
            System.out.println("실행시 최소 1개의 인자가 필요합니다.");
            System.out.println("첫번째 인자 = processName");
            System.out.println("두번째 인자 = port");
            System.out.println("프로세스 실행을 취소합니다.");
            return;
        }

        processName = args[0];

        if (args.length == 2) {
            port = Integer.valueOf(args[1]);
        }

        String targetClass = System.getProperty("targetClass");
        Thread listener;
        if (targetClass == null) {
            targetClass = "ListenerTask.BasicListenerTask";
        }

        try {

            Class<?> runnerClass = Class.forName(targetClass);
            Object targetInstance = runnerClass.getDeclaredConstructor(Integer.class).newInstance(port);
            listener = new Thread((Runnable) targetInstance);

        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found : input VM option is ["+ targetClass +"]");
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            System.out.println("InstantiationException : input VM option is ["+ targetClass +"]");
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException : input VM option is ["+ targetClass +"]");
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            System.out.println("InvocationTargetException : input VM option is ["+ targetClass +"]");
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            System.out.println("NoSuchMethodException : input VM option is ["+ targetClass +"]");
            throw new RuntimeException(e);
        }

        System.out.println("listener = " + listener.getClass());
        System.out.println("Setting is end. now Start Task");
        listener.start();

    }
}
