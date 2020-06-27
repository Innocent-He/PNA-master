package org.xidian.alg.escycle.util;

import java.io.*;

/**
 * @Author He ymLiu
 * @Date 2020/4/3 18:20
 * @Version 1.0
 */
public class LoadDataUtil {
    public void outFile(String content) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("src/org/xidian/alg/escycle/resources/outPut.txt");
            fos.write(content.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
