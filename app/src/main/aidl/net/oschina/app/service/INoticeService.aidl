package net.oschina.app.service;

interface INoticeService
{ 
   void scheduleNotice();
   void requestNotice();
   void clearNotice(in int uid,in int type);
}