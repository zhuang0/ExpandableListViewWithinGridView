package com.src.zhang.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Toast;

import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseExpandableListAdapter implements
        OnItemClickListener {
    public static final int ItemHeight = 48;// ÿ��ĸ߶�
    public static final int PaddingLeft = 36;// ÿ��ĸ߶�
    private int myPaddingLeft = 0;

    private MyGridView[] toolbarGrid;

    private String menu_toolbar_name_array[] = {"存储卡", "我的下载", "图书导入", "系统备份",
            "系统恢复", "清除全部", "在线升级", "快速入门", "关于开卷", "退出系统", "在线升级", "快速入门",
            "关于开卷", "退出系统", "关于开卷", "退出系统", "关于开卷", "退出系统", "关于开卷", "退出系统"};
    private int menu_toolbar_image_array[] = {R.drawable.icon_sdcard,
            R.drawable.icon_sdcard, R.drawable.icon_sdcard,
            R.drawable.icon_sdcard, R.drawable.icon_sdcard,
            R.drawable.icon_sdcard, R.drawable.icon_sdcard,
            R.drawable.icon_sdcard, R.drawable.icon_sdcard,
            R.drawable.icon_sdcard, R.drawable.icon_sdcard,
            R.drawable.icon_sdcard, R.drawable.icon_sdcard,
            R.drawable.icon_sdcard, R.drawable.icon_sdcard,
            R.drawable.icon_sdcard, R.drawable.icon_sdcard,
            R.drawable.icon_sdcard, R.drawable.icon_sdcard,
            R.drawable.icon_sdcard};

    private List<TreeNode> treeNodes = new ArrayList<TreeNode>();

    private Context parentContext;

    private LayoutInflater layoutInflater;

    static public class TreeNode {
        Object parent;
        List<Object> childs = new ArrayList<Object>();
    }

    public ListViewAdapter(Context view, int myPaddingLeft) {
        parentContext = view;
        this.myPaddingLeft = myPaddingLeft;
    }

    public List<TreeNode> GetTreeNode() {
        return treeNodes;
    }

    public void UpdateTreeNode(List<TreeNode> nodes) {
        treeNodes = nodes;
    }

    public void RemoveAll() {
        treeNodes.clear();
    }

    public Object getChild(int groupPosition, int childPosition) {
        return treeNodes.get(groupPosition).childs.get(childPosition);
    }

    public int getChildrenCount(int groupPosition) {
        return treeNodes.get(groupPosition).childs.size();
    }

    static public TextView getTextView(Context context) {
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ItemHeight);

        TextView textView = new TextView(context);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        return textView;
    }

    /**
     * ���Զ���ExpandableListView
     */
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            layoutInflater = (LayoutInflater) parentContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.view, null);
        }
        if (toolbarGrid[groupPosition] == null) {
            toolbarGrid[groupPosition] = (MyGridView) convertView
                    .findViewById(R.id.GridView_toolbar);
        }
        toolbarGrid[groupPosition].setNumColumns(4);// ����ÿ������
        toolbarGrid[groupPosition].setGravity(Gravity.CENTER);// λ�þ���
        toolbarGrid[groupPosition].setHorizontalSpacing(10);// ˮƽ���
        toolbarGrid[groupPosition].setAdapter(getMenuAdapter(menu_toolbar_name_array,
                menu_toolbar_image_array));// ���ò˵�Adapter
        toolbarGrid[groupPosition].setOnItemClickListener(this);
        return convertView;
    }

    /**
     * ���Զ���list
     */
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        TextView textView = getTextView(this.parentContext);
        textView.setText(getGroup(groupPosition).toString());
        textView.setPadding(myPaddingLeft + PaddingLeft, 0, 0, 0);
        return textView;
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public Object getGroup(int groupPosition) {
        return treeNodes.get(groupPosition).parent;
    }

    public int getGroupCount() {
        int size = treeNodes.size();
        toolbarGrid = new MyGridView[size];
        return size;
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public boolean hasStableIds() {
        return true;
    }

    /**
     * ����˵�Adapter
     *
     * @param menuNameArray      ���
     * @param imageResourceArray ͼƬ
     * @return SimpleAdapter
     */
    private SimpleAdapter getMenuAdapter(String[] menuNameArray,
                                         int[] imageResourceArray) {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < menuNameArray.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", imageResourceArray[i]);
            map.put("itemText", menuNameArray[i]);
            data.add(map);
        }
        SimpleAdapter simperAdapter = new SimpleAdapter(parentContext, data,
                R.layout.item_menu, new String[]{"itemImage", "itemText"},
                new int[]{R.id.item_image, R.id.item_text});
        return simperAdapter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Toast.makeText(parentContext, "��ǰѡ�е���:" + position, Toast.LENGTH_SHORT)
                .show();

    }
}
