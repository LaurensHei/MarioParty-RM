package de.laurens.marioparty.minigame;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MinigameManager {

    public File minigameFolder;

    public MinigameManager(File minigameFolder) {
        this.minigameFolder = minigameFolder;
        loadMinigames();
    }

    public void loadMinigames() {
        if (!minigameFolder.exists()) {
            System.out.println("Minigame Folder not exists. Creating a new one");
            minigameFolder.mkdirs();
            loadMinigames();
        }
        File[] files = minigameFolder.listFiles();
        for (File file : files) {
            if (!file.getName().toLowerCase().endsWith(".jar")) continue;
            try {
                JarFile jarFile = new JarFile(file);
                Enumeration<JarEntry> jarEntries = jarFile.entries();
                while (jarEntries.hasMoreElements()) {
                    JarEntry jarEntry = jarEntries.nextElement();
                    if (jarEntry != null && jarEntry.getName().endsWith(".class")) {

                        String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6);
                        className = className.replace("/", ".");
                        ClassLoader loader = URLClassLoader.newInstance(new URL[]{file.toURL()}, getClass().getClassLoader());
                        Class<?> c = Class.forName(className, true, loader);
                        if (c.getSuperclass().getName().equals("de.laurens.marioparty.minigame.Minigame")) {
                            System.out.println("MINIGAME DETECTED!!");
                            for (Annotation annotation : c.getAnnotations()) {
                                Class<? extends Annotation> type = annotation.annotationType();
                                System.out.println("Values of " + type.getName());

                                for (Method method : type.getDeclaredMethods()) {
                                    Object value = method.invoke(annotation, (Object[]) null);
                                    System.out.println(" " + method.getName() + " :" + value);
                                }
                            }
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
