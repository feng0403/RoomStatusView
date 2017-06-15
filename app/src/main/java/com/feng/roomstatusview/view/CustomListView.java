package com.feng.roomstatusview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Author：ClareChen
 * E-mail：ggchaifeng@gmail.com
 * Date：  16/9/1 下午5:12
 */
public class CustomListView extends ListView {

  public CustomListView(Context context) {
    super(context);
  }

  public CustomListView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CustomListView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  public int computeVerticalScrollOffset() {
    return super.computeVerticalScrollOffset();
  }

  @Override
  protected int computeHorizontalScrollOffset() {
    return super.computeHorizontalScrollOffset();
  }
}
