ScrollDelete
-------
[ ![Download](https://api.bintray.com/packages/luodijackshen/jack-view/ScrollDelLayout/images/download.svg) ](https://bintray.com/luodijackshen/jack-view/ScrollDelLayout/_latestVersion)
<br>
<a href="https://blog.csdn.net/a199581/article/details/79838924" title="博客地址">博客地址</a>
<br>
仿qq滑动删除-已经处理了滑动冲突，使用简单。<br>

效果图
-------
![效果图](https://github.com/LuodiJackShen/ScrollDelete/blob/master/demo.gif)

使用
-------
0.  加入依赖
```java
        compile 'jack.view:scrolldel:1.0.1'
```

1.  在布局文件里引入
```xml
        <jack.view.ScrollDeleteLayout
                android:layout_width="match_parent"
                android:layout_height="100dp"
                android:layout_marginTop="20dp"
                android:background="@android:color/darker_gray"
                app:from_left="false">

                <TextView
                    android:id="@+id/show_view"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:background="@android:color/holo_orange_dark"
                    android:gravity="center"
                    android:text="@string/app_name" />

                ...其他控件

            </jack.view.ScrollDeleteLayout>
```

2.ViewHolder实现```OnExtendListener```接口,并设置监听，demo：
```java
        class ItemViewHolder extends RecyclerView.ViewHolder implements ScrollDeleteLayout.OnExtendListener {
            TextView tv;
            Button btn1;
            Button btn2;
            ScrollDeleteLayout layout;

            ItemViewHolder(View itemView) {
                super(itemView);
                layout = (ScrollDeleteLayout) itemView;
                /***
                 * 设置展开监听器.
                 */
                layout.setOnExtendListener(this);

                tv = itemView.findViewById(R.id.show_view);
                btn1 = itemView.findViewById(R.id.delete);
                btn2 = itemView.findViewById(R.id.collect);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "delete", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "collect", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
            }

            void bindView(int position) {
                tv.setText(String.valueOf(position));
            }

            /***
            * 实现监听器
            */
            @Override
            public void onExtend(ScrollDeleteLayout layout) {
                    if (mDeleteLayout != null) {
                        mDeleteLayout.shrink();
                    }
                    mDeleteLayout = null;
                    mDeleteLayout = layout;
            }
        }
```
3.```ScrollDeleteLayout```父布局设置滑动监听，demo:
```java
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mDeleteLayout != null) {
                    /***
                     * 当外面的布局滑动时，使ScrollDeleteLayout收缩布局。
                     */
                    mDeleteLayout.shrink();
                }
            }
        });
```

Demo
-------
```app```下的```MainActivity```

最后
-------
欢迎在issue中批评指教，欢迎star，欢迎访问 <a href="http://blog.csdn.net/a199581" title="我的博客">我的博客</a>

License
-------

    Copyright 2018 Luodi Jack Shen

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


