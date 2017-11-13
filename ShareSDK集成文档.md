# ShareSDK集成文档

## 配置gradle

### 1、将下面的脚本添加到您的根模块build.gradle中：
``` gradle
buildscript {
    // 添加MobSDK的maven地址
    repositories {
        maven {
            url "http://mvn.mob.com/android"
        }
    }

    dependencies {
        // 注册MobSDK
        classpath 'com.mob.sdk:MobSDK:+'
    }
}
```

### 2、在使用ShareSDK模块的build.gradle中，添加MobSDK插件和扩展，如：
``` gradle
// 添加插件
apply plugin: 'com.mob.sdk'

// 在MobSDK的扩展中注册ShareSDK的相关信息
MobSDK {
	appKey "d580ad56b4b5"
	appSecret "7fcae59a62342e7e2759e9e397c82bdd"

	ShareSDK {
        	devInfo {
			SinaWeibo {
				appKey "568898243"
				appSecret "38a4f8204cc784f81f9f0daaf31e02e3"
				callbackUri "http://www.sharesdk.cn"
				shareByAppClient false
			}
			Wechat {
				appId "wx4868b35061f87885"
				appSecret "64020361b8ec4c99936c0e3999a9f249"
			}
		}
	}
}
```
其中的`devInfo`为来自社交平台的应用信息。

## 添加代码

添加配置后，即可调用授权、获取资料、分享等操作，如：
```java
private void showShare() {
     OnekeyShare oks = new OnekeyShare();
     //关闭sso授权
     oks.disableSSOWhenAuthorize(); 

    // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
     //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
     // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
     oks.setTitle(getString(R.string.share));
     // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
     oks.setTitleUrl("http://sharesdk.cn");
     // text是分享文本，所有平台都需要这个字段
     oks.setText("我是分享文本");
     // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
     oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
     // url仅在微信（包括好友和朋友圈）中使用
     oks.setUrl("http://sharesdk.cn");
     // comment是我对这条分享的评论，仅在人人网和QQ空间使用
     oks.setComment("我是测试评论文本");
     // site是分享此内容的网站名称，仅在QQ空间使用
     oks.setSite(getString(R.string.app_name));
     // siteUrl是分享此内容的网站地址，仅在QQ空间使用
     oks.setSiteUrl("http://sharesdk.cn");

    // 启动分享GUI
    oks.show(this);
}
```

## 混淆设置
ShareSDK已经做了混淆处理，再次混淆会导致不可预期的错误，请在您的混淆脚本中添加如下的配置，跳过对ShareSDK的混淆操作：
```
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
```

## 可用的社交平台

任何配置在`devInfo`下的社交平台都是可用的，他们包括：

分类|平台名称
----|----
常用平台|SinaWeibo（新浪微博）、Wechat（微信好友）、WechatMoments（微信朋友圈）、QQ（QQ好友）、<br/>Facebook、FacebookMessenger
其它主流平台|TencentWeibo（腾讯微博）、QZone（QQ空间）、Renren（人人网）、Twitter、Douban（豆瓣）、<br/>Tumblr、GooglePlus（Google+）、Pinterest、Line、Instagram、Alipay（支付宝好友）、<br/>AlipayMoments（支付宝朋友动态）、Youtube、Meipai（美拍）
其它平台|WechatFavorite（微信收藏）、KaiXin（开心网）、Email（电子邮件）、ShortMessage（短信）、<br/>YouDao（有道云笔记）、Evernote（印象笔记）、LinkedIn（领英）、FourSquare、Flickr、<br/>Dropbox、VKontakte、Yixin（易信）、YixinMoments（易信朋友圈）、Mingdao（明道）、<br/>KakaoTalk、KakaoStory、WhatsApp、Pocket、Instapaper、Dingding（钉钉）、Telegram

## 归一化的社交平台信息

ShareSDK已将每个社交平台可用的`devInfo`做了归一化处理，其可用的属性包括：

属性 | 类型 | 说明
------- | :-------: | -------
Id | 文本 | 可选字段，并且你可以设置为任何不重复的字符串
SortId | 数字 | 可选字段，并且你可以设置为任何不重复的数字
AppId | 文本 | 对应[ShareSDK.xml][1]中的AppId、ClientID、ApplicationId、ChannelID
AppKey | 文本 | 对应[ShareSDK.xml][1]中的AppKey、ConsumerKey、ApiKey、OAuthConsumerKey
AppSecret | 文本 | 对应[ShareSDK.xml][1]中的AppSecret、ConsumerSecret、SecretKey、<br/>Secret、ClientSecret、ApiSecret、ChannelSecret
CallbackUri | 文本 | 对应[ShareSDK.xml][1]中的RedirectUrl、RedirectUri、CallbackUrl、
ShareByAppClient | 布尔值 | ShareByAppClient标识是否使用客户端分享
Enable | 布尔值 | Enable字段表示此平台是否启用
BypassApproval | 布尔值 | BypassApproval表示是否绕过审核
userName | 文本 | userName在微信小程序中使用
path | 文本 | path在微信小程序中使用
HostType | 文本 | 表示服务器类型，在YouDao和Evernote中使用

## 注意事项

 1. ShareSDK默认会添加OnekeyShare库，如果您不需要这个库，可以在`ShareSDK`下设置“gui false”来关闭OnekeyShare
 2. MobSDK默认为ShareSDK提供最新版本的集成，如果您想锁定某个版本，可以在`ShareSDK`下设置“version "某个版本"”来固定使用这个版本 
 3. 如果使用MobSDK的模块会被其它模块依赖，请确保依赖它的模块也引入MobSDK插件，或在此模块的gradle中添加：
``` gradle
repositories {
	maven {
		url "http://mvn.mob.com/android"
	}
}
```
 4. 其他的一些问题可以参考：
   - [新浪微博分享说明][2]
   - [QQ分享说明][3]
   - [微信分享说明][4]
   - [Facebook分享说明][5]

  [1]: http://mvn.mob.com/android/ShareSDK.xml
  [2]: http://bbs.mob.com/forum.php?mod=viewthread&tid=24689&page=1&extra=#pid61902
  [3]: http://bbs.mob.com/forum.php?mod=viewthread&tid=24653&extra=page%3D2
  [4]: http://bbs.mob.com/thread-24656-1-1.html
  [5]: http://bbs.mob.com/forum.php?mod=viewthread&tid=24684&page=1&extra=#pid61877
