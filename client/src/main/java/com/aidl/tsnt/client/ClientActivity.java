package com.aidl.tsnt.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aidl.tsnt.server.Dog;
import com.aidl.tsnt.server.IMyAidl;

import java.util.List;

public class ClientActivity extends AppCompatActivity {
    private IMyAidl iMyAidl;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyAidl = IMyAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMyAidl = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        initView();
        bindService();
    }

    private void initView() {
        final EditText num1Edt = (EditText) findViewById(R.id.num1);
        final EditText num2Edt = (EditText) findViewById(R.id.num2);
        Button addBtn = (Button) findViewById(R.id.add);
        final TextView resultTv = (TextView) findViewById(R.id.result);
        final TextView printTv = (TextView) findViewById(R.id.print);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num1 = num1Edt.getText().toString().trim();
                String num2 = num2Edt.getText().toString().trim();
                if (TextUtils.isEmpty(num1)) {
                    Toast.makeText(ClientActivity.this, "请输入第一个数字", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(num2)) {
                    Toast.makeText(ClientActivity.this, "请输入第二个数字", Toast.LENGTH_LONG).show();
                } else {
                    if (iMyAidl != null) {
                        try {
                            int result = iMyAidl.add(Integer.valueOf(num1), Integer.valueOf(num2));
//                            resultTv.setText(result + "");
                            List<Dog> dogs = iMyAidl.addDog(new Dog(result));
                            StringBuffer buffer = new StringBuffer();
                            buffer.append("现在有");
                            for (int i = 0; i < dogs.size(); i++) {
                                buffer.append(dogs.get(i).getAge() + "岁,");
                            }
                            buffer.append("共" + dogs.size() + "只狗");
                            printTv.setText(buffer);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.aidl.tsnt.server", "com.aidl.tsnt.server.RemoteService"));
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }
}
