# Add project specific ProGuard rules here.

# By default, the flags in this file are appended to flags specified

# in D:\android\sdk/tools/proguard/proguard-android.txt

# You can edit the include path and order by changing the proguardFiles

# directive in build.gradle.

#

# For more details, see

#  http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following

# and specify the fully qualified class name to the JavaScript interface

# class:

#-keepclassmembers class fqcn.of.javascript.interface.for.webview {

#  public *;

#}

# Uncomment this to preserve the line number information for

# debugging stack traces.

#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to

# hide the original source file name.

#-renamesourcefileattribute SourceFile

#-------------------------------------------定制化区域----------------------------------------------

#---------------------------------1.实体类---------------------------------

-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,Annotation,EnclosingMethod,MethodParameters

-keep class *.R$ {
*;
}

# 对于R（资源）类中的静态属性不能被混淆
-keepclassmembers class **.R$* {
 public static <fields>;
}

-keep class com.dapeng.micromall.ui.user.* {*;}


#-------------------------------------------------------------------------

#---------------------------------2.第三方包-------------------------------

#retrofit2.0

# Platform calls Class.forName on types which do not exist on Android to determine platform.



# Platform used when running on Java 8 VMs. Will not be used at runtime.



# Retain generic type information for use by reflection by converters and adapters.

-keepattributesSignature




-dontusemixedcaseclassnames

-dontskipnonpubliclibraryclasses

-dontskipnonpubliclibraryclassmembers

-dontpreverify

-verbose

-printmappingproguardMapping.txt

-optimizations!code/simplification/cast,!field/*,!class/merging/*

-keepattributes *Annotation*,InnerClasses

-keepattributes Signature

-keepattributes SourceFile,LineNumberTable

#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------

-keep public class * extends android.app.Activity

-keep public class * extends androidx.fragment.*{*;}

-keep public class * extends android.app.Application

-keep public class * extends android.app.Service

-keep public class * extends android.content.BroadcastReceiver

-keep public class * extends android.content.ContentProvider

-keep public class * extends android.app.backup.BackupAgentHelper

-keep public class * extends android.preference.Preference

-keep public class * extends android.view.View

-dontnote


#androidx包使用混淆
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**

#跳过登出接口
-keep public class androidx.fragment.*{*;}
-keep public class com.dapeng.micromall.http_tool.*{*;}
-keep public class com.dapeng.micromall.ui.http.MicromallCallback{*;}
-keep public class com.dapeng.micromall.ui.http.HttpType{*;}
-keep public class com.dapeng.micromall.ui.http.ResponseData{*;}
-keep public class com.dapeng.micromall.ui.http.UserLoginRequst{*;}
-keep public class com.dapeng.micromall.ui.fragment.DpMicromallFragment{*;}

# Youzan SDK
-dontwarn com.youzan.**
-keep class com.youzan.jsbridge.* { *; }
-keep class com.youzan.spiderman.* { *; }
-keep class com.youzan.androidsdk.* { *; }
-keep class com.youzan.x5web.* { *; }
-keep class com.youzan.androidsdkx5.* { *; }
-keep class dalvik.system.VMStack.* { *; }
-keep class com.tencent.smtt.* { *; }
-dontwarn  com.tencent.smtt.**

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-keep class okio.*{*;}
-keep class com.squareup.okhttp.* { *; }
-keep interface com.squareup.okhttp.* { *; }

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-keep class okio.*{*;}
-keep class com.squareup.okhttp.* { *; }
-keep interface com.squareup.okhttp.* { *; }

#okhttp
-keep class okhttp3.*{*;}

#okio
-dontwarn okio.**
-keep class okio.*{*;}

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}
#okgo
-dontwarn com.lzy.okgo.**
-keep class com.lzy.okgo.**{*;}

#okrx
-dontwarn com.lzy.okrx.**
-keep class com.lzy.okrx.**{*;}

#okrx2
-dontwarn com.lzy.okrx2.**
-keep class com.lzy.okrx2.**{*;}

#okserver
-dontwarn com.lzy.okserver.**
-keep class com.lzy.okserver.**{*;}

#okhttp
-keep class okhttp3.*{*;}

#okio
-dontwarn okio.**
-keep class okio.*{*;}


#okgo
-dontwarn com.lzy.okgo.**
-keep class com.lzy.okgo.*{*;}

#okrx
-dontwarn com.lzy.okrx.**
-keep class com.lzy.okrx.*{*;}

#okrx2
-dontwarn com.lzy.okrx2.**
-keep class com.lzy.okrx2.*{*;}

#okserver
-dontwarn com.lzy.okserver.**
-keep class com.lzy.okserver.*{*;}

# IM
-keep class com.youzan.mobile.zanim.model.* { *; }

-dontwarn java.nio.file.*
-dontwarn javax.annotation.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
