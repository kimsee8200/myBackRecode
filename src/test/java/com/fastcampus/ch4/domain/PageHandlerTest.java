//package com.fastcampus.ch4.domain;
//
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class PageHandlerTest {
//    @Test
//    public void Test(){
//        PageHandler ph = new PageHandler(250, 1);
//        assertTrue(ph.getBeginPage() ==1);
//        assertTrue(ph.getEndPage() ==10);
//        assertTrue(ph.getTotalPage() == 25);
//        ph.print();
//    }
//
//    @Test
//    public void Test2(){
//        PageHandler ph = new PageHandler(250, 11);
//        assertTrue(ph.getBeginPage() ==11);
//        assertTrue(ph.getEndPage() ==20);
//        assertTrue(ph.getTotalPage() == 25);
//        ph.print();
//    }
//
//    @Test
//    public void Test3(){
//        PageHandler ph = new PageHandler(255, 25);
//        assertTrue(ph.getBeginPage() ==21);
//        assertTrue(ph.getEndPage() ==26);
//        assertTrue(ph.getTotalPage() == 26);
//        ph.print();
//    }
//}