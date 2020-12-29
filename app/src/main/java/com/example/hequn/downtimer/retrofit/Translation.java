package com.example.hequn.downtimer.retrofit;

/**
 * author:hequnyu
 * Description:
 * Date:2020/12/22
 */
public class Translation {

    private int status;
    private Content content;

    private static  class Content{

        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;

    }

    public void show(){

        System.out.println("Rxjava翻译结果:" + status);
        System.out.println("Rxjava翻译结果:" + content.from);
        System.out.println("Rxjava翻译结果:" + content.to);
        System.out.println("Rxjava翻译结果:" + content.vendor);
        System.out.println("Rxjava翻译结果:" + content.out);
        System.out.println("Rxjava翻译结果:" + content.errNo);

    }

}
