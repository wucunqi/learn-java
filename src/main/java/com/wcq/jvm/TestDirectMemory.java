package com.wcq.jvm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestDirectMemory {
    /*
    适合读写大文件，分配回收效率低
    */
    private static final String FROM = "/Volumes/a.data";
    private static final String TO = "/Volumes/b.data";
    private static final int BUFFER_SIZE = 1024 * 1024;

    public static void main(String[] args) {
        
    }

    private void copyFile(){
        long start = System.currentTimeMillis();

        try(
            FileInputStream fis = new FileInputStream(FROM);
            FileOutputStream fos = new FileOutputStream(TO);
        ){
            byte[] buffer = new byte[BUFFER_SIZE];

            while(true){
                int count = fis.read(buffer);
                if(count == -1){
                    break;
                }
                fos.write(buffer, 0, count);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "ms");
    }

    /*
    也会出现直接内存溢出
     */
    private void copyFileByDirectMemory(){
        long start = System.currentTimeMillis();

        try(
            FileChannel fis = new FileInputStream(FROM).getChannel();
            FileChannel fos = new FileOutputStream(TO).getChannel();
        ){
            ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);

            while(true){
                int count = fis.read(buffer);
                if(count == -1){
                    break;
                }
                // fos.write(buffer, 0, count);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "ms");
    }
}
