### BottomNavigationView 的使用及遇到的坑

#### BottomNavigationView 的使用

布局中设置

```xml
<android.support.design.widget.BottomNavigationView
    android:id="@+id/navigation"
    android:layout_width="match_parent"
    android:layout_height="?attr/actionBarSize"
    android:layout_gravity="bottom"
    android:background="?android:attr/windowBackground"
    app:menu="@menu/navigation" />
```

BottomNavigationView 是通过 menu 来设置item，文件如下：

```Xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/navigation_home"
        android:icon="@drawable/ic_home_black_24dp"
        android:title="@string/title_home" />
    <item
        android:id="@+id/navigation_dashboard"
        android:icon="@drawable/ic_dashboard_black_24dp"
        android:title="@string/title_dashboard" />
    <item
        android:id="@+id/navigation_notifications"
        android:icon="@drawable/ic_notifications_black_24dp"
        android:title="@string/title_notifications" />
    <item
        android:id="@+id/navigation_person"
        android:icon="@drawable/ic_person_black_24dp"
        android:title="@string/title_person" />
</menu>
```

Activity 中使用

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bottom_navigation);
    BottomNavigationView navigation = (BottomNavigationView)findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
}

private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
                case R.id.navigation_person:
                    return true;
            }
            return false;
        }

    };
```

#### 遇到的坑

- 问题一：使用的时候 item 数大于 3 个时会有位移

  [原回答](https://stackoverflow.com/questions/40176244/how-to-disable-bottomnavigationview-shift-mode)

  使用下面的类通过反射来修改

  ```java
  public class BottomNavigationViewHelper {
      public static void disableShiftMode(BottomNavigationView view) {
          BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
          try {
              Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
              shiftingMode.setAccessible(true);
              shiftingMode.setBoolean(menuView, false);
              shiftingMode.setAccessible(false);
              for (int i = 0; i < menuView.getChildCount(); i++) {
                  BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                  //noinspection RestrictedApi
                  item.setShiftingMode(false);
                  // set once again checked value, so view will be updated
                  //noinspection RestrictedApi
                  item.setChecked(item.getItemData().isChecked());
              }
          } catch (NoSuchFieldException e) {
              Log.e("BNVHelper", "Unable to get shift mode field", e);
          } catch (IllegalAccessException e) {
              Log.e("BNVHelper", "Unable to change value of shift mode", e);
          }
      }
  }
  ```

  使用：

  ```java
  BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
  BottomNavigationViewHelper.disableShiftMode(navigation);
  ```

- 问题二：

  ![图是网上找到](https://github.com/ittianyu/BottomNavigationViewEx/raw/master/read_me_images/center_icon_only.jpg)

  要实现上面的这样的效果：

  1. 我们把 app:menu="@menu/navigation" 指向的 navigation 修改为

     ```xml
     <?xml version="1.0" encoding="utf-8"?>
     <menu xmlns:android="http://schemas.android.com/apk/res/android">

         <item
             android:id="@+id/navigation_home"
             android:icon="@drawable/ic_home_black_24dp"
             android:title="@string/title_home" />

         <item
             android:id="@+id/navigation_dashboard"
             android:icon="@drawable/ic_dashboard_black_24dp"
             android:title="@string/title_dashboard" />

         <item
             android:id="@+id/navigation_center"
             android:icon="@null"
             android:title="" />

         <item
             android:id="@+id/navigation_notifications"
             android:icon="@drawable/ic_notifications_black_24dp"
             android:title="@string/title_notifications" />

         <item
             android:id="@+id/navigation_person"
             android:icon="@drawable/ic_person_black_24dp"
             android:title="@string/title_person" />

     </menu>
     ```

     即中间第三个 menu 不给设置图片和文字

  2. 然后我们修改布局文件（即用一个LinearLayout (只是正中间有一个Image), 覆盖在 BottomNavigation 之上）

     > 如果中间的按钮需要的是替换当前页面的fragment的话我们那么我们的 image 只用作显示使用，如果需要按钮的功能是打开一个新页面，那么我们需要给这个 imageVIew 设置一个点击事件，这样 BottomNavigation 就不会拿到点击事件，把中间的 item 标记为选中

     ```xml
     <FrameLayout
         android:layout_width="match_parent"
         android:layout_height="?attr/actionBarSize"
         android:clipToPadding="true">

         <android.support.design.widget.BottomNavigationView
             android:id="@+id/navigation"
             android:layout_width="match_parent"
             android:layout_height="?attr/actionBarSize"
             android:layout_gravity="bottom"
             android:background="?android:attr/windowBackground"
             app:menu="@menu/navigation" />

         <LinearLayout
             android:layout_width="match_parent"
             android:layout_height="match_parent"
             android:elevation="16dp">

             <View
                 android:layout_width="0dp"
                 android:layout_height="match_parent"
                 android:layout_weight="2" />

             <ImageView
                 android:id="@+id/navigation_center_image"
                 android:layout_width="0dp"
                 android:layout_height="match_parent"
                 android:layout_gravity="center"
                 android:layout_weight="1"
                 android:padding="5dp"
                 android:src="@mipmap/ic_launcher" />

             <View
                 android:layout_width="0dp"
                 android:layout_height="match_parent"
                 android:layout_weight="2" />

         </LinearLayout>
     </FrameLayout>
     ```

- 问题三：选中 item 时 item 的文本会有一个动画，那么如何取消动画。icon 也会上移一点。

  [原回答](http://www.jianshu.com/p/7b2d842267ab)

  design_bottom_navigation_active_text_size

  design_bottom_navigation_text_size

  上面的参数是item选中没有选中的文本大小，我们把它设置成一样的话就没有动画了

  design_bottom_navigation_margin

  是图标的 margin bottom 的值我们可以调整他让 icon 垂直居中

  ```xml
  <!--http://www.jianshu.com/p/7b2d842267ab-->
  <!--BottomNavigationView 的选中没有选中的字体大小-->
  <dimen name="design_bottom_navigation_active_text_size">10sp</dimen>
  <dimen name="design_bottom_navigation_text_size">10sp</dimen>


  <!--BottomNavigationView 只放图标时的设置-->
  <dimen name="design_bottom_navigation_active_text_size">0sp</dimen>
  <dimen name="design_bottom_navigation_text_size">0sp</dimen>
  <dimen name="design_bottom_navigation_margin">16dp</dimen>
  ```

  把上面的 根据需要 copy 到 res/values/dimens 中就行