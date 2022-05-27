package com.imantou.common.utils;


import com.imantou.common.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Component
public class FileUtils {



  /*  public static void main(String[] args) {
        for (int i = 0; i <1570 ; i++) {
                System.out.println(CodeGenerator.getUUID32());
            }
    }*/

    /**
     * 图片文件
     *
     * @param file
     * @return
     */
//    public static String uploadImg(MultipartFile file, String path) {
//        if (file != null) {
//            //10M
//            long size = 10 * 1024L * 1024L;
//            if (file.getSize() > size) {
//                throw new BusinessException("上传图片不能超过" + (size / 1024L / 1024L + "M"));
//            }
//            try {
//                //原文件名
//                String filename = file.getOriginalFilename();
//                //获取图片后缀
//                String subName = filename.substring(filename.lastIndexOf("."));
//                //新图片名
//                String newFileName = TokenGenerator.generateValue() + subName;
//                //设定文件保存目录
//                String lastString = path.substring(path.length() - 1);
//                if (Constant.SLASH.equals(lastString) || Constant.BACKSLASH.equals(lastString)) {
//                    path = path + newFileName;
//                } else {
//                    path = path + "/" + newFileName;
//                }
//                File dest = new File(path);
//                //没有则生成父目录
//                if (!dest.getParentFile().exists()) {
//                    dest.getParentFile().mkdirs();
//                }
//                //保存文件
//                file.transferTo(dest);
//                return newFileName;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        throw new BusinessException("照片上传失败");
//    }

    /**
     * 保存文件
     *
     * @param file
     * @return
     */
    public static void uploadImg(MultipartFile file, String path) {

        File dest = new File(path);
        //没有则生成父目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        //保存文件
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传失败！");
        }

    }

    /**
     * 获取新文件名
     *
     * @param file
     * @return
     */
    public static String getNewFileName(MultipartFile file) {
        //原文件名
        String originalFileName = file.getOriginalFilename();
        //获取文件后缀
        String subName = originalFileName.substring(originalFileName.lastIndexOf("."));
        //新文件名
        return UUID.randomUUID() + subName;
    }

    /**
     * 获取原始文件名不带后缀
     *
     * @param file
     * @return
     */
    public static String getOriginalFileName(MultipartFile file) {
        //原文件名
        String fileName = file.getOriginalFilename();
        String originalFileName = fileName.substring(0, fileName.lastIndexOf("."));
        return originalFileName;
    }


    /**
     * 删除文件，可以是单个文件或文件夹
     *
     * @param fileName 待删除的文件名
     * @return 文件删除成功返回true, 否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败：" + fileName + "文件不存在");
            return false;
        } else {
            if (file.isFile()) {

                return deleteFile(fileName);
            } else {
                return deleteDirectory(fileName);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 被删除文件的文件名
     * @return 单个文件删除成功返回true, 否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            boolean delete = file.delete();
            if (delete) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            }
        }
        System.out.println("删除单个文件" + fileName + "失败！");
        return false;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param dir 被删除目录的文件路径
     * @return 目录删除成功返回true, 否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            System.out.println("删除目录失败" + dir + "目录不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            System.out.println("删除目录失败");
            return false;
        }

        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            System.out.println("删除目录" + dir + "失败！");
            return false;
        }
    }
    // 删除文件夹
    // param folderPath 文件夹完整绝对路径

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 删除指定文件夹下所有文件
    // param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 上传文件
     *
     * @param bytes
     * @param filePath
     * @param fileName
     */
    public static void uploadFile(byte[] bytes, String filePath, String fileName) {
        OutputStream os = null;
        FileOutputStream fos = null;
        try {
            File dir = new File(filePath);
            //判断文件目录是否存在
            if (!dir.exists() && !dir.isDirectory()) {
                dir.mkdirs();
            }
            File file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            os = new BufferedOutputStream(fos);
            os.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //原文件名
        String originalFileName = "ed62070013047405a2057b305366e4f5.jpg";
        //获取文件后缀
        String subName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        System.out.println(subName);
    }

}
