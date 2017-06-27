package com.example.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class Tools {

	//Test the change in github
	public Context context;
	public Tools(Context context){
		this.context = context;
	}
	public String getCode(){
		PackageManager manager=context.getPackageManager();
		int i = 0;
		String code = ".";
		char[] charCode = new char[10];
		try {
			PackageInfo info = manager.getPackageInfo("com.tencent.mm", 0);
			if(info != null){
				char key[]=code.toCharArray();
				char allCode[] =info.versionName.toString().toCharArray();
				int j=0;
				for(i=0; i<allCode.length; i++){
					if(allCode[i]==key[0]){
						j++;
						if(j==3){
							break;
						}
					}
					charCode[i]=allCode[i];
				}
				code=String.valueOf(charCode).substring(0, i);
			}else{
				code="";
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if(!(code.equals("6.3.13") ||code.equals("6.3.16") ||code.equals("6.3.18") || code.equals("6.3.22")|| code.equals("6.3.23"))){
			code="";
		}
		return code;
	}

	public String wechatVersion(int array){
		String code = getCode();
		String chatOpen="";
		String open="";
		String close="";
		String noMoney="";
		String closeChat="";
		String moneyText="";
		String[] all= new String[6];
		if(code.equals("6.3.13")){
			chatOpen = "b_";
			open="b43";
			close="c4u";
			noMoney="b47";
			closeChat="bb";
			moneyText="";
		}else if(code.equals("6.3.16")){
			chatOpen = "ba";
			open="b5d";
			close="c69";
			noMoney="b5h";
			closeChat="cg8";
			moneyText="b5p";
		}else if(code.equals("6.3.18")){
			chatOpen = "a0n";
			open="b9m";
			close="ey";
			noMoney="b9q";
			closeChat="eg";
			moneyText="b76";
		}else if(code.equals("6.3.22")){
			chatOpen = "a0s";
			open="b_b";
			close="f0";
			noMoney="b_f";
			closeChat="ei";
			moneyText="b7v";
		}else if(code.equals("6.3.23")){
			chatOpen = "a13";
			open="ba_";
			close="fa";
			noMoney="bad";
			closeChat="ew";
			moneyText="b8t";
		}
		
		all[0] = chatOpen;
		all[1] = open;
		all[2] = close;
		all[3] = noMoney;
		all[4] = closeChat;
		all[5] = moneyText;
		return all[array];
	}	
}









