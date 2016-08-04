package com.example.money;

import java.util.List;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.example.tools.MusicPlay;
import com.example.tools.Tools;

@SuppressWarnings("deprecation")
@SuppressLint("NewApi")
public class ZService extends AccessibilityService {
	
    public AccessibilityNodeInfo nodeInfo =getRootInActiveWindow();
    public List<AccessibilityNodeInfo> adsId;
    public static Boolean isCloseY = false;
    public static Boolean isCloseN = false;
    public String tag = "----my---";
    public Tools tool;
    
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
    	Log.e(tag, "执行红包");
    	tool = new Tools(getBaseContext());
        AccessibilityNodeInfo msgs = getRootInActiveWindow();
        if (msgs == null || tool.getCode().equals("")) {
            return;
        }
        int t = accessibilityEvent.getEventType();
        switch (t) {
        //消息栏提示
        case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
        	
        	List<CharSequence> texts = accessibilityEvent.getText();
        		if (!texts.isEmpty()) {
        			for (CharSequence textt : texts) {
        				String content = textt.toString();
        				if (content.contains(this.getResources().getString(R.string.keyString))){
        					keyLight();
        					unLock();
        					if (accessibilityEvent.getParcelableData() != null&& accessibilityEvent.getParcelableData() instanceof Notification) {
        						Notification notification = (Notification) accessibilityEvent.getParcelableData();
        						PendingIntent pendingIntent = notification.contentIntent;
        						try {
        							pendingIntent.send();
        						} catch (CanceledException e) {
        							e.printStackTrace();
        						}
        					}
        				}
        			}
        		}break;
        case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
        	
        	String className = accessibilityEvent.getClassName().toString();
        	Log.e(tag, "改变");
     	    if (className.equals(this.getResources().getString(R.string.open))) {
     		    openPacket();
     	    }else if(className.equals(this.getResources().getString(R.string.close))){
     	    	closeUI();
     	    }else{
     	    	if(className.equals("android.widget.ListView")){
                	cheakPaket(accessibilityEvent);
     	    	}
     	    }
     	    
        break;
        case AccessibilityEvent.TYPE_VIEW_SCROLLED:
        	Log.e(tag, "滚动");
        	String className1 = accessibilityEvent.getClassName().toString();
     	    if (className1.equals(this.getResources().getString(R.string.open))) {
     		    openPacket();
     	    }else if(className1.equals(this.getResources().getString(R.string.close))){
     	    	closeUI();
     	    }else{
     	    	if(className1.equals("android.widget.ListView")){
                	cheakPaket(accessibilityEvent);
     	    	}
     	    }
        break;
        }
    }    	

       private void cheakPaket(AccessibilityEvent event){
    	   Log.e(tag, "cheakPaket");
    	   if(event.getSource() == null || event.getSource().getChildCount()==0 || ((event.getToIndex()<event.getItemCount()-1))){
    		   Log.e(tag, "return1");
    		   return;
    	   }else if(event.getSource().getChild(event.getSource().getChildCount()-1) != null){
        	   adsId = event.getSource().getChild(event.getSource().getChildCount()-1)
    				   .findAccessibilityNodeInfosByText(this.getResources().getString((R.string.lhb)));
        	   Log.e(tag, adsId.toString());
        	   if (adsId.toString().equals("[]")){
        		   Log.e(tag, "return2");
        	        return;
        	   }
    	   }
    	   adsId = event.getSource().getChild(event.getSource().getChildCount()-1)
				   .findAccessibilityNodeInfosByViewId(this.getResources().getString((R.string.id))+tool.wechatVersion(0));
    	   Log.e(tag, adsId.toString());
   		   if (adsId!=null) {
   			   for (AccessibilityNodeInfo nodeInfoViewId : adsId) {
   				   nodeInfoViewId.performAction(AccessibilityNodeInfo.ACTION_CLICK);
   				   isCloseN = true;
   			  	}
   		   }
       }
       
       private void openPacket() {
    	   Log.e(tag, "openPacket");
    	   nodeInfo = getRootInActiveWindow();
    	   if (nodeInfo != null) {
    		   //有
    		   adsId = nodeInfo.findAccessibilityNodeInfosByViewId(this.getResources().getString(R.string.id)+tool.wechatVersion(1));
    		   isCloseY=true;
    		   if(adsId == null || adsId.toString().equals("[]")){
    			   //没有
        		   adsId = nodeInfo.findAccessibilityNodeInfosByViewId(this.getResources().getString(R.string.id)+tool.wechatVersion(3));
        		   isCloseY = false;
    		   }
    		   for (AccessibilityNodeInfo n : adsId) {
         			if(isCloseN){
         				n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
         				isCloseN = false;
         			}
       			}
       		}
       	}

       private void closeUI(){
    	   nodeInfo = getRootInActiveWindow();
    	   if (nodeInfo != null) {
    		   adsId = nodeInfo.findAccessibilityNodeInfosByViewId(this.getResources().getString(R.string.id)+tool.wechatVersion(2));
    		   for (AccessibilityNodeInfo n : adsId) {
    			   if(isCloseY){
         				n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
         				adsId = nodeInfo.findAccessibilityNodeInfosByViewId(this.getResources().getString(R.string.id)+tool.wechatVersion(5));
         	    		for (AccessibilityNodeInfo money : adsId) {
         	    			Log.e(tag, "money值：---  "+money.getText());
         	    			Toast.makeText(getBaseContext(), "抢到："+money.getText()+"元", Toast.LENGTH_LONG).show();
         	    			float moneyF = Float.parseFloat(money.getText().toString());
         	    			@SuppressWarnings("unused")
							MusicPlay music;
         	    			if(moneyF > 5){
         	    				music = new MusicPlay(getBaseContext(), R.raw.big);
         	    			}else{
         	    				music = new MusicPlay(getBaseContext(), R.raw.small);
         	    			}
							

         	    		}
         	    		isCloseY=false;
    			   }
         		}
        	}
       }

       @SuppressWarnings("unused")
       private void closeChatUI(){
    	   if(!tool.getCode().equals("6.3.11")){
    		   return;
    	   }
    	   nodeInfo = getRootInActiveWindow();
    	   if (nodeInfo != null) {
    		   adsId = nodeInfo.findAccessibilityNodeInfosByViewId(this.getResources().getString(R.string.id)+tool.wechatVersion(4));
    		   for (AccessibilityNodeInfo n : adsId) {
         			n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
         		}
        	}
       }
       
       @SuppressLint("Wakelock")
       public void keyLight(){
    	   PowerManager pm = (PowerManager)getSystemService(POWER_SERVICE);
    	   WakeLock mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP|PowerManager.FULL_WAKE_LOCK,"SimpleTimer");
    	   mWakelock.acquire();
       }
       
       public void unLock(){
    	   //键盘锁管理器对象  
    	   KeyguardManager km= (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);  
    	   //Log.e(tag, "键盘锁屏幕显示或在受限按键输入模式："+km.inKeyguardRestrictedInputMode()+"\n键盘是否已经锁定："+km.isKeyguardLocked()+"\n键盘是否密码或者图案锁："+km.isKeyguardSecure());
    	   //这里参数”unLock”作为调试时//LogCat中的Tag
    	   KeyguardLock kl = km.newKeyguardLock("unLock");   
    	   if(km.isKeyguardLocked() && !km.isKeyguardSecure()){
    		   kl.reenableKeyguard();
        	   kl.disableKeyguard();  //解锁
        	   //Log.e(tag, "-----------键盘锁屏幕显示或在受限按键输入模式："+km.inKeyguardRestrictedInputMode()+"\n键盘是否已经锁定："+km.isKeyguardLocked()+"\n键盘是否密码或者图案锁："+km.isKeyguardSecure());
    	   }
       }

       public void onInterrupt() {
    	   
       } 
}
