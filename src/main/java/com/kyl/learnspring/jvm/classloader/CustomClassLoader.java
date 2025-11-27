package com.kyl.learnspring.jvm.classloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class CustomClassLoader extends ClassLoader{

    private String rootDir;

    public CustomClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }

    /**
     * 重写findClass/loadClass
     * 最好不要重写 loadClass， 内部有实现 双亲委派模型的逻辑
     * ClassLoader内部 loadClass 调用 findClass
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // Class<?> clazz = findLoadedClass(name);
        // FileChannel fileChanel = null;
        // WritableByteChannel outChannel = null;
        //
        // if(null == clazz){
        //     String classFile = getClassFile(name);
        //     FileInputStream fis = new FileInputStream(classFile);
        //     fileChanel = fis.getChannel();
        //     ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //
        // }
        return null;
    }

    private String getClassFile(String className){
        return rootDir + "\\" + className.replace('.', '\\') + ".class";
    }
}
