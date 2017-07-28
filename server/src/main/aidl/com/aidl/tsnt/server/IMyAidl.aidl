// IMyAidl.aidl
package com.aidl.tsnt.server;

import com.aidl.tsnt.server.Dog;//导包不要忘记

interface IMyAidl {
   int add(int num1,int num2);


   List<Dog> addDog(in Dog dog);//这里的in注意不要少
}
